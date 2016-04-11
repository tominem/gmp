package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;

public abstract class AbstractCrudMB<T extends Serializable> implements Serializable, Searchable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 201119042011L;
	
	protected T entityBean;
	
	public void setEntityBean(T entityBean) {
		this.entityBean = entityBean;
	}
	
	public T getEntityBean() {
		return entityBean;
	}

	@Override
	public String getResourceDialogPath() {
		return "/cadastros/searchTemplate.xhtml";
	}
	
	/**
	 * Returns the form name of the view
	 * 
	 * @return 
	 */
	public abstract String getFormName();
	
	public abstract CrudEJB<T> getCrudEJB();
	
	public void initObjects(){
		clean();
	};

	public void exclude(){
		
		getCrudEJB().exclude(this.entityBean);
			
		clean();
			
		showMensagemExclusaoSucesso();
		
	}

	public abstract void activateOrInactivate();

	public void save(){
		
		this.entityBean = getCrudEJB().save(entityBean);
		
		clean();
		
		showMensagemSucessoSalvar();
		
	}

	public void clean(){
		
		try {
			
			getRequestContext().reset(getFormName());
			
			this.entityBean = getEntityClass().newInstance();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}

	public abstract Class<T> getEntityClass();
	
	@SuppressWarnings("unchecked")
	@Override
	public void selectObjectAfterSearch(SelectEvent event) {
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(getEntityClass().getName())) {
			this.entityBean = (T) object;
			showMensagemSucessoConsulta();
		}
	}

	public void showMensagemSucessoSalvar() {
		UtilsMessage.addInfoMessage("Cadastro salvo com sucesso.");
	}

	public void showMensagemSucessoConsulta() {
		UtilsMessage.addInfoMessage("Consulta realizada com sucesso.");
	}

	public void showMensagemAlterarSituacaoSucesso() {
		UtilsMessage.addInfoMessage("Situação alterada com sucesso.");
	}

	public void showMensagemExclusaoSucesso() {
		UtilsMessage.addInfoMessage("Exclusão realizada com sucesso.");
	}
	
	public static void addErrorMessage(String errorMessage) {
		UtilsMessage.addErrorMessage(errorMessage);
	}

	public static void addErrorMessage(String errorMessage, String componentId) {
		UtilsMessage.addErrorMessage(errorMessage, componentId);
	}

	public static void addInfoMessage(String infoMessage) {
		UtilsMessage.addInfoMessage(infoMessage);
	}

	public static void addWarningMessage(String warningMessage) {
		UtilsMessage.addWarningMessage(warningMessage);
	}

	public void showMessageInDialog(String errorMessage) {
		UtilsMessage.showMessageInDialog(errorMessage);
	}

	public RequestContext getRequestContext() {
		return RequestContext.getCurrentInstance();
	}
	
    public void search() {
        
        clean();

        Map<String, Object> options = getParamsDialogPesquisa();
        
        RequestContext.getCurrentInstance().openDialog(getResourceDialogPath(), options, null);
        
    }

	public Map<String, Object> getParamsDialogPesquisa() {
		
		Map<String,Object> options = new HashMap<String, Object>();

        options.put("modal",        true);
        options.put("resizable",    false);
        options.put("width", 		780);
        options.put("contentWidth", 738);
        options.put("responsive", 	true);
        
        //pass the attribute searchable as a parameter to Dialog
        options.put("searchable", 	this);
        
		return options;
		
	}

	public UIInput getUIComponentById(String id) {

		UIInput component = (UIInput) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);

		return component;
	}
	
	public static void  setValidComponents(List<UIComponent> children) {
		
		for (UIComponent uiComponent : children) {

			if (uiComponent.getChildren().isEmpty()) {

				if (uiComponent instanceof UIInput) {
					((UIInput) uiComponent).setValid(true);
				}

			} else {
				setValidComponents(uiComponent.getChildren());
			}

		}
	}

}