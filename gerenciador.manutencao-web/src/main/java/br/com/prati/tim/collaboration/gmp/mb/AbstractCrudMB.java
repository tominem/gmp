package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;

/**
 * Crud Bean Base to conventional crud Form
 * 
 * @author ewerton.costa
 *
 * @param <T> type of entityBean in Form
 * @param <P> type of id from entityBean in form
 */
public abstract class AbstractCrudMB<T extends Serializable, P extends Serializable> implements Serializable, Searchable<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 201119042011L;
	
	protected T entityBean;
	
	protected P entityId;
	
	public void setEntityBean(T entityBean) {
		this.entityBean = entityBean;
	}
	
	public T getEntityBean() {
		return entityBean;
	}
	
	public String getContentPane(){
		return "contentPane";
	}

	public abstract String getResourceDialogPath();
	
	public abstract P getEntityId();
	
	public abstract void setEntityId(P entityId);
	
	public abstract Boolean getEntityStatus();

	public abstract void setEntityStatus(Boolean status);
	
	/**
	 * Returns the form name of the view
	 * 
	 * @return 
	 */
	public String getFormName(){
		return "formCad";
	}
	
	public abstract CrudEJB<T> getCrudEJB();
	
	public abstract void validate(ComponentSystemEvent event);
	
	@PostConstruct
	public void initObjects() {
		clean();
	}

	public boolean exclude(){
			
		try {
			
			excludePerform();
			
			return true;
			
		} catch (Exception e) {
			
			addErrorMessage(
					"Não foi possível excluir " + getEntityBean().getClass().getSimpleName()
					+ e.getMessage().substring(e.getMessage().indexOf("Detalhe:")));
			
			return false;
			
		}
		
	}

	private void excludePerform() throws Exception {
		getCrudEJB().exclude(this.entityBean);

		clean();

		showMensagemExclusaoSucesso();
	}

	public void activateOrInactivate(){
		entityBean = getCrudEJB().activateOrInactivate(getEntityBean());
		showMensagemAlterarSituacaoSucesso();
	};

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

        options.put("modal"			,  	true);
        options.put("resizable"		,  	false);
        options.put("width"			,	780);
        options.put("contentWidth"	, 	738);
        options.put("responsive"	, 	true);
        
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