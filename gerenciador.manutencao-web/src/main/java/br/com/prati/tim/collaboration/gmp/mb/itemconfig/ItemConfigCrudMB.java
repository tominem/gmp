package br.com.prati.tim.collaboration.gmp.mb.itemconfig;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.ValidationException;

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
	
	private ValoresFuncao valorFuncaoInserted;
	
	
	//=================== METHODS ==========================//
	
	@PostConstruct
	@Override
	public void initObjects() {
		
		this.entityBean = new FuncaoConfig();
		
		load();
		
	}
	
	public ValoresFuncao getValorFuncaoInserted() {
		return valorFuncaoInserted;
	}

	public void setValorFuncaoInserted(ValoresFuncao valorFuncaoInserted) {
		this.valorFuncaoInserted = valorFuncaoInserted;
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
	public boolean validate(ComponentSystemEvent event) {
		
		UIComponent components = event.getComponent();

		UIInput uiInputDescricao = (UIInput) components.findComponent("formCad:descricao");
		String descricaovalue = uiInputDescricao.getSubmittedValue() != null ? ""
				: uiInputDescricao.getLocalValue().toString();

		UIInput uiInputMenu = (UIInput) components.findComponent("formCad:menu");
		MenuConfig menuConfig = uiInputMenu.getSubmittedValue() != null ? null
				: (MenuConfig) uiInputMenu.getLocalValue();

		UIInput uiComando = (UIInput) components.findComponent("formCad:comando");
		String comando = uiComando.getSubmittedValue() != null ? ""
				: uiComando.getLocalValue().toString();
		
		List<FuncaoConfig> results = ejb.findByDescricaoAndMenuConfig(descricaovalue, menuConfig);
		
		if( results != null && findDifferent(results) ) {
			
			uiInputDescricao.setValid(false);
			
			addErrorMessage("Já existe um Item de Configuração com a mesma descrição, vinculado ao Menu: " + menuConfig.getDescricao() + "!");
			
			return false;
			
		}
		
		results = ejb.findByComandoAndMenuConfig(comando, menuConfig);
		
		if( results != null && findDifferent(results) ) {
			
			uiComando.setValid(false);
			
			addErrorMessage("Já existe um Item de Configuração com o mesmo comando, vinculado ao Menu: " + menuConfig.getDescricao() + "!");
			
			return false;
			
		}
				
		return true;
	}
	
	
	private boolean findDifferent(List<FuncaoConfig> results) {
		
		if (results.size() == 0) {
			return false;
		}
		
		else if(entityBean.getIdFuncaoConfig() == null) {
			return true;
		}
		
		return results.stream().filter(f -> !entityBean.getIdFuncaoConfig().equals(f.getIdFuncaoConfig())).findFirst().isPresent();
	}

	@Override
	public void clean() {
			
		load();

		super.clean();
			
	}
	
	private void load() {
		
		this.valoresFuncao = new ArrayList<ValoresFuncao>();
		
		valoresFuncao = new ArrayList<ValoresFuncao>();
		
		valorFuncaoInserted = new ValoresFuncao();
		
		menus = ejb.findAllMenus();
		tiposComponentes = ejb.findAllTipoComponentes();
		conversores = Arrays.asList(EComponentConverter.values());
		
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

	public Boolean getShowScalable(){
		
		TipoComponente tipoComponenteSel = entityBean.getTipoComponente();
		
		if(tipoComponenteSel == null) return false;
		
		ETipoComponente lookup = ETipoComponente.lookup(tipoComponenteSel.getDescricao());
		
		if (lookup == null) return false;
		
		if (lookup.getType() == ETipoComponenteType.SCALABLE) {
			return true;
		}
		
		return false;
	}
	
	public void addValorFuncao(){
		
		try {
			
			validateAdd();
			
			valorFuncaoInserted.setOrdem(valoresFuncao.size()+1);
			valorFuncaoInserted.setFuncaoConfig(entityBean);
			valorFuncaoInserted.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			
			valoresFuncao.add(valorFuncaoInserted);
			
			valorFuncaoInserted = new ValoresFuncao();
			
		} catch (ValidationException e) {
			
			addErrorMessage(e.getMessage());
			
		}
	}
	
	public void validateAdd() throws ValidationException{
		
		if(valorFuncaoInserted.getValor() == null || valorFuncaoInserted.getValor().isEmpty()){
			
			throw new ValidationException("Entre com o valor da lista antes de adicionar");
		}
		
		if (valoresFuncao.stream().filter(v -> v.getValor().equals(valorFuncaoInserted.getValor())).count() > 0) {
			
			throw new ValidationException("Valor da lista já inserido");
		}
		
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
	public Boolean getEntityStatus() {
		return null;
	}
	
	@Override
	public void save() {
		
		entityBean.setValorMinimo(entityBean.getValorMinimo() == null ? new BigDecimal("1.0") : entityBean.getValorMinimo());
		entityBean.setValorMaximo(entityBean.getValorMaximo() == null ? new BigDecimal("1.0") : entityBean.getValorMaximo());
		entityBean.setEscala(entityBean.getEscala() == null ? new BigDecimal("1.0") : entityBean.getEscala());
		
		entityBean.setValoresFuncaos(new ArrayList<ValoresFuncao>());
		
		entityBean.setValoresFuncaos(valoresFuncao);
		
		super.save();

		resetForm();
	}

	private void resetForm() {
		getRequestContext().reset(getFormName());
	}
	
	@Override
	public boolean exclude() {
		boolean exclude = super.exclude();
		
		if (exclude) {
			resetForm();
		}
		
		return exclude;		
	}

	@Override
	public void setEntityStatus(Boolean status) { }

	@Override
	public String getResourceDialogPath() {
		return "/cadastros/itemconfig/searchItemConfig.xhtml";
	}
	
	public void removeVal(ValoresFuncao valorFuncaoSelecionado){
		
		if (valorFuncaoSelecionado != null) {
			valoresFuncao.remove(valorFuncaoSelecionado);
			updateOrder();
		}
		
	}

	private void updateOrder() {
		
		for (int i = 0; i < valoresFuncao.size(); i++) {
			valoresFuncao.get(i).setOrdem(i+1);
		}
		
	}

	@Override
	public void selectObjectAfterSearch(SelectEvent event) {
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(FuncaoConfig.class.getName())) {
			this.entityBean = (FuncaoConfig) object;
			valoresFuncao = entityBean.getValoresFuncaos();
			showMensagemSucessoConsulta();
		}
	}
	
}
