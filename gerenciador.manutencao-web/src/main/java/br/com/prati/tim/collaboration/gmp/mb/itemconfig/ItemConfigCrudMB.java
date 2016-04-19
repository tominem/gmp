package br.com.prati.tim.collaboration.gmp.mb.itemconfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.itemconfig.FuncaoConfigEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;
import br.prati.tim.collaboration.gp.jpa.TipoComponente.ETipoComponente;
import br.prati.tim.collaboration.gp.jpa.TipoComponente.ETipoComponenteType;
import br.prati.tim.collaboration.gp.jpa.ValoresFuncao;
import br.prati.tim.collaboration.gp.jpa.enumerator.EComponentConverter;

@Named("itemConfigMB")
@ViewScoped
public class ItemConfigCrudMB extends AbstractCrudMB<FuncaoConfig, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;

	
	@Inject
	private FuncaoConfigEJB ejb;
	
	@Inject
	private TimeZone defaultTimeZone;

	private List<MenuConfig> menus;
	
	private List<TipoComponente> tiposComponentes;

	private List<EComponentConverter> conversores;
	
	private List<ValoresFuncao> valoresFuncao;
	
	private ValoresFuncao valorFuncaoSelected;

	private ValoresFuncao valorFuncaoInserted;
	
	//=================== METHODS ==========================//
	

	public ValoresFuncao getValorFuncaoSelected() {
		return valorFuncaoSelected;
	}

	public ValoresFuncao getValorFuncaoInserted() {
		return valorFuncaoInserted;
	}

	public void setValorFuncaoInserted(ValoresFuncao valorFuncaoInserted) {
		this.valorFuncaoInserted = valorFuncaoInserted;
	}

	public void setValorFuncaoSelected(ValoresFuncao valorFuncaoSelected) {
		this.valorFuncaoSelected = valorFuncaoSelected;
	}

	public List<MenuConfig> getMenus() {
		return menus;
	}

	public List<ValoresFuncao> getValoresFuncao() {
		return valoresFuncao;
	}

	public void setValoresFuncao(List<ValoresFuncao> valoresFuncao) {
		this.valoresFuncao = valoresFuncao;
	}

	public List<EComponentConverter> getConversores() {
		return conversores;
	}

	public void setConversores(List<EComponentConverter> conversores) {
		this.conversores = conversores;
	}

	public MenuConfig getMenuConfigSelected() {
		return entityBean.getMenuConfig();
	}

	public void setMenuConfigSelected(MenuConfig menuConfigSelected) {
		this.entityBean.setMenuConfig(menuConfigSelected);
	}

	public TipoComponente getTipoComponenteSelected() {
		return entityBean.getTipoComponente();
	}

	public void setTipoComponenteSelected(TipoComponente tipoComponenteSelected) {
		entityBean.setTipoComponente(tipoComponenteSelected);
	}

	public List<TipoComponente> getTiposComponentes() {
		return tiposComponentes;
	}

	public void setTiposComponentes(List<TipoComponente> tiposComponentes) {
		this.tiposComponentes = tiposComponentes;
	}

	public void setMenus(List<MenuConfig> menus) {
		this.menus = menus;
	}
	
	public void setConversor(EComponentConverter converter){
		entityBean.setConverter(converter);
	}
	
	public EComponentConverter getConversor(){
		return entityBean.getConverter();
	}

	@Override
	public void clean() {
		try {
			
			//getRequestContext().reset(getFormName());
			
			this.entityBean = getEntityClass().newInstance();
			
			this.valoresFuncao = new ArrayList<ValoresFuncao>();
			
			load();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private void load() {
		
		menus = ejb.findAllMenus();
		tiposComponentes = ejb.findAllTipoComponentes();
		conversores = Arrays.asList(EComponentConverter.values());
		
		valoresFuncao = new ArrayList<ValoresFuncao>();
		
		valorFuncaoSelected = new ValoresFuncao();
		valorFuncaoInserted = new ValoresFuncao();
		
	}
	
	public Boolean getShowList(){
		
		TipoComponente tipoComponenteSel = entityBean.getTipoComponente();
		
		if(tipoComponenteSel == null) return false;
		
		ETipoComponente lookup = ETipoComponente.lookup(tipoComponenteSel.getDescricao());
		
		if (lookup == null) return false;
		
		if (lookup.getType() == ETipoComponenteType.LIST) {
			return true;
		}
		
		return false;
	}
	
	public void addValorFuncao(){
		
		valorFuncaoInserted.setOrdem(valoresFuncao.size()+1);
		valorFuncaoInserted.setFuncaoConfig(entityBean);
		valorFuncaoInserted.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
		
		valoresFuncao.add(valorFuncaoInserted);
		
		valorFuncaoInserted = new ValoresFuncao();
	}

	@Override
	public CrudEJB<FuncaoConfig> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<FuncaoConfig> getEntityClass() {
		return FuncaoConfig.class;
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdFuncaoConfig();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdFuncaoConfig(entityId);
	}

	@Override
	public void validate(ComponentSystemEvent event) {
		
		//ensure is on update mode
//		if (entityBean.getIdFuncaoConfig() != null) return;
//
//		UIComponent components = event.getComponent();
//
//		// get descricao
//		UIInput uiInputDescricao = (UIInput)
//				components.findComponent("descricao");
//		String descricao = uiInputDescricao.getSubmittedValue() != null ? ""
//				: uiInputDescricao.getLocalValue().toString();
//
//		Map<String, Object> params = putParams(descricao);
//
//		//validate if object is present in db
//		boolean exists = getCrudEJB().checkIfExists(params);
//
//		if (exists) {
//
//			//invalidate inputs
//			uiInputDescricao.setValid(false);
//
//			//add validation message
//			addErrorMessage("Já existe um Menu de configuração cadastrado com a mesma descrição");
//
//		}
	}

//	 private Map<String, Object> putParams(String descricao) {
//	
//		 Map<String, Object> params = new HashMap<String, Object>();
//
//		 params.put("descricao", descricao);
//
//		 return params;
//	 }

	@Override
	public Boolean getEntityStatus() {
		return null;
	}

	@Override
	public void setEntityStatus(Boolean status) { }

	@Override
	public String getResourceDialogPath() {
		return "/cadastros/itemconfig/searchItemConfig.xhtml";
	}

	@Override
	public void selectObjectAfterSearch(SelectEvent event) {
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(getEntityClass().getName())) {
			this.entityBean = (FuncaoConfig) object;
			load();
			showMensagemSucessoConsulta();
		}
	}
	
	public void teste(){
		
		System.out.println(entityBean);
		
	}

}