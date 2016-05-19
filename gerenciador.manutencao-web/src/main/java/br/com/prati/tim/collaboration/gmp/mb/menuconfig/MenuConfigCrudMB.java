package br.com.prati.tim.collaboration.gmp.mb.menuconfig;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.menuconfig.MenuConfigEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;

@Named("menuConfigMB")
@ViewScoped
public class MenuConfigCrudMB extends AbstractCrudMB<MenuConfig, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;

	
	@Inject
	private MenuConfigEJB ejb;

	private List<MenuConfig> menus;
	
	
	//=================== METHODS ==========================//
	
	@PostConstruct
	@Override
	public void initObjects() {
		
		try {
			
			this.entityBean = getEntityClass().newInstance();
			
			load();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<MenuConfig> getMenus() {
		return menus;
	}

	public void setMenus(List<MenuConfig> menus) {
		this.menus = menus;
	}

	@Override
	public void clean() {
		try {
			
			getRequestContext().reset(getFormName());
			
			this.entityBean = getEntityClass().newInstance();
			
			load();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	private void load() {
		menus = ejb.findActives().stream().filter(e -> !e.equals(entityBean)).collect(Collectors.toList());
	}

	@Override
	public CrudEJB<MenuConfig> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<MenuConfig> getEntityClass() {
		return MenuConfig.class;
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdMenuConfig();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdMenuConfig(entityId);
	}

	@Override
	public boolean validate(ComponentSystemEvent event) {
		
		//ensure is on update mode
		if (entityBean.getIdMenuConfig() != null) return true;

		UIComponent components = event.getComponent();

		// get descricao
		UIInput uiInputDescricao = (UIInput)
				components.findComponent("descricao");
		String descricao = uiInputDescricao.getSubmittedValue() != null ? ""
				: uiInputDescricao.getLocalValue().toString();

		Map<String, Object> params = putParams(descricao);

		//validate if object is present in db
		boolean exists = getCrudEJB().checkIfExistsBoolean(params);

		if (exists) {

			//invalidate inputs
			uiInputDescricao.setValid(false);

			//add validation message
			addErrorMessage("Já existe um Menu de configuração cadastrado com a mesma descrição");
			
			return false;
		}
		
		return true;
	}

	 private Map<String, Object> putParams(String descricao) {
	
		 Map<String, Object> params = new HashMap<String, Object>();

		 params.put("descricao", descricao);

		 return params;
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
	public String getResourceDialogPath() {
		return "/cadastros/menuconfig/searchMenuConfig.xhtml";
	}

	public MenuConfig getMenuPaiSelected() {
		return entityBean.getMenuConfig();
	}

	public void setMenuPaiSelected(MenuConfig menuPaiSelected) {
		entityBean.setMenuConfig(menuPaiSelected);
	}
	
	@Override
	public void selectObjectAfterSearch(SelectEvent event) {
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(getEntityClass().getName())) {
			this.entityBean = (MenuConfig) object;
			load();
			showMensagemSucessoConsulta();
		}
	}

}
