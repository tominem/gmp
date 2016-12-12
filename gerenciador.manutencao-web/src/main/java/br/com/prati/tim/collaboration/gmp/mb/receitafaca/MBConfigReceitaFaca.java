package br.com.prati.tim.collaboration.gmp.mb.receitafaca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.receitafaca.ConfigReceitaFacaEJB;
import br.com.prati.tim.collaboration.gmp.ejb.receitafaca.ReceitaFacaEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.com.prati.tim.collaboration.gmp.mb.receita.Region;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ReceitaFaca;
import br.prati.tim.collaboration.gp.jpa.ValorReceitaFaca;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Named("mbConfigReceitaFaca")
@ViewScoped
public class MBConfigReceitaFaca extends AbstractBaseMB implements Serializable {
	
	private static final long serialVersionUID = -5144587074129076687L;

	@Inject
	private ConfigReceitaFacaEJB		ejbConfigReceitaFaca;

	@Inject
	private ReceitaFacaEJB				ejbReceitaFaca;

	private Maquina						maquina;

	private Faca                        faca;
	
	private Equipamento                 equipamento;

	private List<Equipamento> 			equipamentos;

	private List<ReceitaFaca>			receitas;

	private List<ValorReceitaFaca>		valoresReceitaFaca;

	private Map<Long, Region>			mapRegiao;

	private List<Produto>				produtosExport;

	private List<Faca>				    facas;


	@PostConstruct
	public void init(){
		
		setValoresReceitaFaca		(new ArrayList<ValorReceitaFaca>());
		setReceitaFacas				(new ArrayList<ReceitaFaca>());
		setMapRegiao			    (new HashMap<Long, Region>());
		setFacas                    (new ArrayList<Faca>());
		setEquipamentos             (new ArrayList<Equipamento>());
		
	}
	
	public void save(){
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.ALTERACAO)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.ALTERACAO.getDescricao() + ".");
			return;
		}
		
		try {
			ejbConfigReceitaFaca.saveValoresReceitaFaca(valoresReceitaFaca, mapRegiao);
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			return;
		}
		
		clean();
		
		addInfoMessage("Configuração salva com sucessos.");
		
	}

	public void clean(){
		
		init();
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public void select(SelectEvent event){
		
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(Maquina.class.getName())){
			
			Maquina maquinaSelecionada = (Maquina) object;
			
			if (maquinaSelecionada.equals(getMaquina())){
				return;
			}
			
			selectMaquina(maquinaSelecionada);
			
			loadReceitaFaca();
		}
		
	}
	
	public void selectEquipamento(final AjaxBehaviorEvent event)  {
		loadFacas();
		loadReceitaFaca();
	}
	
	public void selectFaca(final AjaxBehaviorEvent event){
		loadReceitaFaca();
	}
	
	private void selectMaquina(Maquina maquina){
		
		setMaquina(maquina);
		setEquipamento(null);
		setFaca(null);
		
		loadEquipamentos();
		
		loadFacas();
		
		loadReceitaFaca();
	}
	
	private void loadFacas() {

		//TODO carregar as facas do facas prod máquina
		
	}

	private void loadEquipamentos() {
		
		if (maquina != null) {
			List<Equipamento> equipamentos = ejbConfigReceitaFaca.findEquipamentosByMaquina(maquina);
			setEquipamentos(equipamentos);
		}
		
	}

	private void loadReceitaFaca(){
		
		setValoresReceitaFaca(new ArrayList<>());
		
		if (maquina != null && equipamento != null && faca!= null){
			
			List<ReceitaFaca> findReceitaFacasFetchByMaquinaAndEquipamentoAndFaca = ejbReceitaFaca.findReceitaFacasFetchByMaquinaAndEquipamentoAndFaca(maquina, equipamento, faca);
			
			setReceitaFacas(findReceitaFacasFetchByMaquinaAndEquipamentoAndFaca);
			
			loadComponentes();
			
			setValoresReceitaFaca(ejbConfigReceitaFaca.retornaValoresReceitasFacas(receitas, mapRegiao, faca));
			
		}
		
	}
	
	private void loadComponentes(){
		
		for (ReceitaFaca receita : receitas) {
			receita.getConfigEquipamento().getValorConfigEquip();
		}
		
	}
	
	public boolean hasMenuItem(ValorReceitaFaca valorReceitaFacaAtual, int index){
		
		MenuConfig menuConfigAtual = valorReceitaFacaAtual.getReceitaFaca().getConfigEquipamento().getFuncaoConfig().getMenuConfig();

		if (index > 0) {
			
			ValorReceitaFaca valorReceitaFacaAnterior = getValoresReceitaFaca().get(index -1);
			
			MenuConfig menuConfigAnterior = valorReceitaFacaAnterior.getReceitaFaca().getConfigEquipamento().getFuncaoConfig().getMenuConfig();
			
			return !menuConfigAtual.equals(menuConfigAnterior) && menuConfigAtual.getStatus();
			
		}
		
		else{
			
			return menuConfigAtual != null && menuConfigAtual.getStatus();
			
		}
		
	}
	
	public Maquina getMaquina() {
		return maquina;
	}
	
	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}
	
	public List<ReceitaFaca> getReceitaFacas() {
		return receitas;
	}	
	
	public void setReceitaFacas(List<ReceitaFaca> receitas) {
		this.receitas = receitas;
	}
	
	public List<ValorReceitaFaca> getValoresReceitaFaca() {
		return valoresReceitaFaca;
	}

	public void setValoresReceitaFaca(List<ValorReceitaFaca> valoresReceitaFaca) {
		this.valoresReceitaFaca = valoresReceitaFaca;
	}
	
	public Map<Long, Region> getMapRegiao() {
		return mapRegiao;
	}
	
	public void setMapRegiao(Map<Long, Region> mapRegiao) {
		this.mapRegiao = mapRegiao;
	}
	
	public List<Produto> getProdutosExport() {
		return produtosExport;
	}
	
	public Faca getFaca() {
		return faca;
	}

	public void setFaca(Faca faca) {
		this.faca = faca;
	}

	public List<Faca> getFacas() {
		return facas;
	}

	public void setFacas(List<Faca> facas) {
		this.facas = facas;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public List<Equipamento> getEquipamentos() {
		return equipamentos;
	}

	public void setEquipamentos(List<Equipamento> equipamentos) {
		this.equipamentos = equipamentos;
	}
	
}
