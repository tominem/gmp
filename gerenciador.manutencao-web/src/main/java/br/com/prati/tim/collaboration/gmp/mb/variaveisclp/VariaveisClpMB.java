package br.com.prati.tim.collaboration.gmp.mb.variaveisclp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.variaveisclp.VariaveisClpEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.VariaveisClp;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoValorVariaveis;

@Named("variaveisClpMB")
@ViewScoped
public class VariaveisClpMB extends AbstractBaseMB implements Serializable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5144587074129076687L;

	@Inject
	private VariaveisClpEJB		ejb;

	@Inject
	private TimeZone			defaultTimeZone;

	private VariaveisClp		variaveisClp;
	
	private VariaveisClp		variaveisClpEdit;
	
	private List<VariaveisClp>	variaveis;

	private List<VariaveisClp>	variaveisRem;
	
	@PostConstruct
	public void init(){
		load();
	}

	private void load() {
		variaveisClpEdit = new VariaveisClp();
		VariaveisClp var = new VariaveisClp();
		
		var.setMaquina(new Maquina());
		var.setEquipamento(new Equipamento());
		var.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
		
		setVariaveisClp(var);
		
		setVariaveisRem(new ArrayList<VariaveisClp>());
		
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}
	
	public void loadVariaveis(){
		
		if (getMaquina().getIdMaquina() != null && getCLP().getIdEquipamento() != null) {
		
			if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.CONSULTA)){
				UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.CONSULTA.getDescricao() + ".");
				return;
			}
			
			variaveis = ejb.findVariaveisClpByMaquinaAndCLP(getMaquina(), getCLP());
			
			addInfoMessage("Variáveis carregadas com sucesso!");
			
		}
		
	}
	
	private Equipamento getCLP() {
		return variaveisClp.getEquipamento();
	}

	public Maquina getMaquina(){
		return variaveisClp.getMaquina();
	}
	
	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public VariaveisClp getVariaveisClp() {
		return variaveisClp;
	}

	public void setVariaveisClp(VariaveisClp variaveisClp) {
		this.variaveisClp = variaveisClp;
	}
	
	public List<ETipoValorVariaveis> getTiposValor() {
		return Arrays.asList(ETipoValorVariaveis.values());
	}

	public void select(SelectEvent event){
		
		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(Maquina.class.getName())) {
			
			Maquina maquina = (Maquina) object;
			
			if (maquina.equals(getMaquina())) return;
			
			selectMaquina(maquina);
		}

		else if (object != null	&& object.getClass().getName().equals(Equipamento.class.getName())) {
			
			Equipamento clp = (Equipamento) object;
			
			if (clp.equals(getCLP())) return;
			
			selectCLP(object);
		}
		
		
	}
	
	public boolean getTipoInteiro(){
		return variaveisClp.getTipoValor() == ETipoValorVariaveis.INTEGER;
	}
	
	public boolean getTipoInteiroEdicao(){
		return variaveisClp.getTipoValor() == ETipoValorVariaveis.INTEGER;
	}

	private void selectCLP(Object object) {
		variaveisClp.setEquipamento((Equipamento) object);
		
		loadVariaveis();

		addInfoMessage("CLP informado com sucesso.");
	}

	private void selectMaquina(Maquina maquina) {
		variaveisClp.setMaquina(maquina);
		
		loadVariaveis();
		
		addInfoMessage("Máquina informada com sucesso.");
	}
	
	public void add(){
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.INCLUSAO)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.INCLUSAO.getDescricao() + ".");
			return;
		}
		
		try {
			
			if (variaveis == null) {
				variaveis = new ArrayList<VariaveisClp>();
			}

			validateAdd();
			
			variaveis.add(variaveisClp);
			
			loadAfterAdd();
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
	}
	
	private void loadAfterAdd() {
		
		VariaveisClp var = new VariaveisClp();
		
		var.setMaquina(getMaquina());
		var.setEquipamento(getCLP());
		var.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
		
		setVariaveisClp(var);
		
	}

	public void save(){
		
		try {
			
			if (variaveis != null && variaveis.size() > 0) {
				
				savePerform();
				
				deletePerform();
				
				clean();
				
				addInfoMessage("Variáveis configuradas com sucesso!");
				
			} else{
				
				if (getVariaveisRem() != null && getVariaveisRem().size() > 0 ){					
					
					deletePerform();
					
					clean();
					
					addInfoMessage("Variáveis configuradas com sucesso!");
					
				}else{
					addErrorMessage("Nenhuma variável adicionada.");
				}
				
			}
			
		} catch (Exception e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
	}

	private void deletePerform() throws Exception {
		for (VariaveisClp var : getVariaveisRem()) {
			
			if (var.getIdVariaveisCLP() != null) {
				ejb.exclude(var);
			}
			
		}
	}

	private void savePerform() throws Exception {
		for (VariaveisClp var : variaveis) {
			
			ejb.save(var);
			
		}
	}
	
	@Override
	public void clean() {
		
		load();
		
		if (variaveis != null) {
			variaveis.clear();
		}
		
		super.clean();
	}
	
	private void validateAdd() throws FacesValidateException {

		if (variaveisClp.getVariavel() == null || variaveisClp.getVariavel().isEmpty()) {
			throw new FacesValidateException("Variável requerida!", "formCad:variavel");
		}

		if (variaveisClp.getTipoValor() == null) {
			throw new FacesValidateException("Tipo Valor requerido!", "formCad:tipoValor");
		}
		
		if (variaveisClp.getDescricao() == null || variaveisClp.getDescricao().isEmpty()) {
			throw new FacesValidateException("Descrição requerida!", "formCad:descricao");
		}

		if (variaveisClp.getValor() == null || variaveisClp.getValor().isEmpty()) {
			throw new FacesValidateException("Valor requerido!", "formCad:valor");
		}
		
		if (variaveis.stream().filter(e -> e.getDescricao().equalsIgnoreCase(variaveisClp.getDescricao())).findFirst().isPresent()){
			throw new FacesValidateException("Já existe um item com a mesma descrição na tabela, vinculado a mesma máquina e CLP!", "formCad:descricao");
		}
		
	}
	
	public void tipoValorChange(ValueChangeEvent event){  
	    variaveisClp.setValor(null);
	}
	
	public void edit(VariaveisClp var){
		
		setVariaveisClpEdit(var);
		
		RequestContext.getCurrentInstance().execute("PF('variavelClpDialogWidget').show()");
		
	}
	
	public void saveEdit() {
		
		if(!validateEdit()){
			return;
		}
		
		for (VariaveisClp variavel : variaveis) {
			
			if(variavel.getIdVariaveisCLP().equals(variaveisClpEdit.getIdVariaveisCLP())){
				
				variavel.setDescricao	(variaveisClpEdit.getDescricao());
				variavel.setTipoValor	(variaveisClpEdit.getTipoValor());
				variavel.setValor		(variaveisClpEdit.getValor());
				variavel.setVariavel	(variaveisClpEdit.getVariavel());
			}
		}
		
		limparEdicao();
		
	}
	
	public void limparEdicao(){
		
		RequestContext.getCurrentInstance().execute("PF('variavelClpDialogWidget').hide()");
	}
	
	
	private boolean validateEdit() {
		
		if (variaveisClp.getVariavel() == null || variaveisClp.getVariavel().isEmpty()) {
			addErrorMessage("Variável requerida!");
			return false;
		}

		if (variaveisClp.getTipoValor() == null) {
			addErrorMessage("Tipo Valor requerido!");
			return false;
		}
		
		if (variaveisClp.getDescricao() == null || variaveisClp.getDescricao().isEmpty()) {
			addErrorMessage("Descrição requerida!");
			return false;
		}
		
		if (variaveisClp.getValor() == null || variaveisClp.getValor().isEmpty()) {
			addErrorMessage("Descrição requerida!");
			return false;
		}
		
		return true;
	}

	public void remove(VariaveisClp variaveisClp){
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.EXCLUSAO)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.EXCLUSAO.getDescricao() + ".");
			return;
		}
		
		variaveis.remove(variaveisClp);
		getVariaveisRem().add(variaveisClp);
	}

	public List<VariaveisClp> getVariaveis() {
		return variaveis;
	}

	public void setVariaveis(List<VariaveisClp> variaveis) {
		this.variaveis = variaveis;
	}

	public List<VariaveisClp> getVariaveisRem() {
		return variaveisRem;
	}

	public void setVariaveisRem(List<VariaveisClp> variaveisRem) {
		this.variaveisRem = variaveisRem;
	}

	public VariaveisClp getVariaveisClpEdit() {
		return variaveisClpEdit;
	}

	public void setVariaveisClpEdit(VariaveisClp variaveisClpEdit) {
		this.variaveisClpEdit = variaveisClpEdit;
	}

}
