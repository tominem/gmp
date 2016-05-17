package br.com.prati.tim.collaboration.gmp.mb.equipamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.equipamento.EquipamentoEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Fabricante;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.IoEquipamento;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoEquipamento;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoIoEquipamento;

@Named("equipamentoMB")
@ViewScoped
public class EquipamentoCrudMB extends AbstractCrudMB<Equipamento, Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	
	@Inject
	private EquipamentoEJB ejb;
	
	private List<ETipoEquipamento> tiposEquipamentos;

	private IoEquipamento ioEquipamentoInserted;
	
	private List<IoEquipamento> ioEquipamentos;
	
	private List<ConfigEquipamento> itensConfigEquipamento;
	
	private List<ConfigEquipamento> itensConfigEquipamentoSelected;
	
	private boolean clp = false;
	
	private boolean saida = false;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	//========================= METHODS ============================//
	
	@Override
	@PostConstruct
	public void initObjects() {
		
		load();
		
	}
	
	public void load() {
		
		this.entityBean = new Equipamento();
		
		tiposEquipamentos = Arrays.asList(ETipoEquipamento.values());
		
		ioEquipamentos = new ArrayList<IoEquipamento>();
		
		ioEquipamentoInserted = new IoEquipamento();
		
		itensConfigEquipamento = new ArrayList<ConfigEquipamento>();
		
		if (itensConfigEquipamentoSelected == null) {
			itensConfigEquipamentoSelected = new ArrayList<ConfigEquipamento>();
		}
		
		else{
			itensConfigEquipamentoSelected.clear();
		}
		
		populateItens();
		
	}
	
	private void populateItens() {
		
		List<FuncaoConfig> funcaoConfigs = ejb.findAllFuncaoConfig();
		
		funcaoConfigs.forEach(fc -> itensConfigEquipamento.add(new ConfigEquipamento(fc)));
		
	}

	@Override
	public void clean() {

		load();
		
		getRequestContext().reset(getFormName());
		
	}
	
	@Override
	public void save() {

		if (itensConfigEquipamentoSelected.size() > 0) {
			
			//set configEquipamento into entityBean
			entityBean.setConfigEquipamentos(new ArrayList<ConfigEquipamento>());
			entityBean.setConfigEquipamentos(itensConfigEquipamentoSelected);
			itensConfigEquipamentoSelected.forEach(ic -> ic.setEquipamento(entityBean));
			
		}
		
		if (ioEquipamentos.size() > 0) {
			
			//set IOEquipamentos into entityBean
			entityBean.setIoEquipamentos(new ArrayList<IoEquipamento>());
			entityBean.setIoEquipamentos(ioEquipamentos);
			ioEquipamentos.forEach(io -> io.setEquipamento(entityBean));
			
		}
		
		super.save();
	}
	
	public void checkIfIsCLP(){
		setClp(entityBean.getTipoEquipamento() != null && entityBean.getTipoEquipamento() == ETipoEquipamento.CLP);
	}
	
	public List<ConfigEquipamento> getItensConfigEquipamento() {
		return itensConfigEquipamento;
	}

	public void setItensConfigEquipamento(List<ConfigEquipamento> itensConfigEquipamento) {
		this.itensConfigEquipamento = itensConfigEquipamento;
	}

	public List<ConfigEquipamento> getItensConfigEquipamentoSelected() {
		return itensConfigEquipamentoSelected;
	}

	public void setItensConfigEquipamentoSelected(
			List<ConfigEquipamento> itensConfigEquipamentoSelected) {
		this.itensConfigEquipamentoSelected = itensConfigEquipamentoSelected;
	}

	public List<ETipoIoEquipamento> getTiposIoEquipamentos(){
		return Arrays.asList(ETipoIoEquipamento.values());
	}
	
	public List<IoEquipamento> getIoEquipamentos() {
		return ioEquipamentos;
	}

	public void setIoEquipamentos(List<IoEquipamento> ioEquipamentos) {
		this.ioEquipamentos = ioEquipamentos;
	}
	
	public IoEquipamento getIoEquipamentoInserted() {
		return ioEquipamentoInserted;
	}

	public void setIoEquipamentoInserted(IoEquipamento ioEquipamentoInserted) {
		this.ioEquipamentoInserted = ioEquipamentoInserted;
	}

	public List<ETipoEquipamento> getTiposEquipamentos() {
		return tiposEquipamentos;
	}

	public void setTiposEquipamentos(List<ETipoEquipamento> tiposEquipamentos) {
		this.tiposEquipamentos = tiposEquipamentos;
	}

	@Override
	public String getResourceDialogPath() {
		return "/cadastros/equipamento/searchEquipamento.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdEquipamento();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdEquipamento(entityId);
	}

	@Override
	public Boolean getEntityStatus() {
		return entityBean.getStatus();
	}

	@Override
	public void setEntityStatus(Boolean status) {
		entityBean.setStatus(status);
	}

	@Override
	public CrudEJB<Equipamento> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Equipamento> getEntityClass() {
		return Equipamento.class;
	}
	
	public void openFabricanteDialog(){
		
		Map<String, Object> params = getParamsDialogPesquisa();
		
		String searchFabPath = "/cadastros/fabricante/searchFabricante.xhtml";
		
		RequestContext.getCurrentInstance().openDialog(searchFabPath, params, null);
		
	}

	public void selectFabricante(SelectEvent event) {
		
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(Fabricante.class.getName())) {
			this.entityBean.setFabricante((Fabricante) object);
			
			UtilsMessage.addInfoMessage("Fabricante informado com sucesso.");
		}
		
	}
	
	public void addIOIntoTable(){
		
		try {
			
			validateInsertIO();
			
			ioEquipamentoInserted.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			
			ioEquipamentos.add(ioEquipamentoInserted);
				
			ioEquipamentoInserted = new IoEquipamento();
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
	}
	
	private void validateInsertIO() throws FacesValidateException {
		
		String descricao 			= 	ioEquipamentoInserted.getDescricao();
		ETipoIoEquipamento tipoIO 	= 	ioEquipamentoInserted.getTipo();
		String variavel 			=	ioEquipamentoInserted.getVariavel();
		
		if ( descricao == null || descricao.isEmpty()) {
			throw new FacesValidateException("Descrição requerida!", "formCad:descricaoIO");
		}

		if ( tipoIO == null) {
			throw new FacesValidateException("Tipo de IO requerido!", "formCad:tipoIO");
		}
		
		if ( variavel == null) {
			throw new FacesValidateException("Variável requerida!", "formCad:variavelIO");
		}
		
	}

	public void removeIoIntoTable(IoEquipamento removedIoEquipamento){
		
		ioEquipamentos.remove(removedIoEquipamento);
		
	}
	
	@Override
	public void selectObjectAfterSearch(SelectEvent event) {
		
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(getEntityClass().getName())) {
			this.entityBean = (Equipamento) object;
		
			loadItensConfig();
			
			loadIOEquipamento();
			
			checkIfIsCLP();
			
			showMensagemSucessoConsulta();
		}
	}

	private void loadIOEquipamento() {
		
		ioEquipamentos = entityBean.getIoEquipamentos();
		
	}

	private void loadItensConfig() {
		
		List<ConfigEquipamento> configEquipamentos = entityBean.getConfigEquipamentos();
		
		if (configEquipamentos != null) {
		
			configEquipamentos.forEach(ce -> {

				ArrayList<ConfigEquipamento> buffer = new ArrayList<ConfigEquipamento>(itensConfigEquipamento);

				for (int i = 0; i < buffer.size(); i++) {
					
					if (buffer.get(i).getFuncaoConfig().getIdFuncaoConfig().equals(ce.getFuncaoConfig().getIdFuncaoConfig())) {
						itensConfigEquipamento.set(i, ce);
					}
				}

			});
			
			ordenaItensConfigPorOrdem();
			
			ordenaItensConfigPorSelecao();
			
			itensConfigEquipamentoSelected = configEquipamentos;
		}
	}

	private void ordenaItensConfigPorOrdem() {
		Collections.sort(itensConfigEquipamento, (x1, x2) -> {
			
			if (x1.getOrdem() != null && x2.getOrdem() != null) {
				return Integer.compare(x1.getOrdem(), x2.getOrdem());
			}
			
			else if(x2.getOrdem() == null) 
				return -1;

			else return 1;
			
		});
	}
	
	public void tipoIoOnChange(){
		
		setSaida( !(ioEquipamentoInserted != null && ioEquipamentoInserted.getTipo() == ETipoIoEquipamento.SAIDA) );
	}
	
	private void ordenaItensConfigPorSelecao() {
		Collections.sort(itensConfigEquipamento, (x1, x2) -> {
			
			if (x1.getIdConfigEquipamento() != null && x2.getIdConfigEquipamento() != null) 
				return 0;
			else if (x1.getIdConfigEquipamento() != null)
				return -1;
			else
				return 1;
			
		});
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		
		try {
			
			validateBefore();
			
			return super.validate(event);
			
		} catch (FacesValidateException e) {
			addErrorMessage(e.getMessage());
		}
		
		return false;
	}
	
	@Override
	public ValidateComponent[] getValidaComponents() {
		return new ValidateComponent[]{
			
			new ValidateComponent("formCad:tag", "Tag", "tag"),
			new ValidateComponent("formCad:ip", "IP", "ip")
				
		};
	}

	private void validateBefore() throws FacesValidateException {
		
		if (entityBean.getFabricante() == null) {
			throw new FacesValidateException("Fabricante requerido!", "formCad:fabricante");
		}
		
	}

	public boolean isClp() {
		return clp;
	}

	public void setClp(boolean clp) {
		this.clp = clp;
	}

	public boolean isSaida() {
		return saida;
	}

	public void setSaida(boolean saida) {
		this.saida = saida;
	}
	
}
