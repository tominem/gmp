package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.PersistenceException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 * Crud Bean Base to conventional crud Form
 * 
 * @author ewerton.costa
 *
 * @param <T> type of entityBean in Form
 * @param <P> type of id from entityBean in form
 */
public abstract class AbstractCrudMB<T extends Serializable, P extends Serializable> 
		
				extends AbstractBaseMB implements Serializable, Searchable<T>, BaseMB, CrudMB<T, P> {

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
	
	@PostConstruct
	public void initObjects() {
		try {
			
			this.entityBean = getEntityClass().newInstance();
			
		} catch (Exception e) {
			//do nothing
		}
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
		try {
			
			entityBean = getCrudEJB().activateOrInactivate(getEntityBean());
			showMensagemAlterarSituacaoSucesso();
			
		} catch (Exception e) {
			
			addErrorMessage(e.getMessage());
		}
	};

	public void save(){
		
		try {
			
			this.entityBean = getCrudEJB().save(entityBean);
			
			clean();
			
			showMensagemSucessoSalvar();
			
		} catch (Exception e) {

			PersistenceException ex = isConstraintViolationException(e);
			
			if (ex != null) {
				
				int indexDetalhe = ex.getMessage().indexOf("Detalhe:");

				int indexErro = ex.getMessage().indexOf("ERROR:");
				
				addErrorMessage(
						"Não foi possível salvar " + getEntityBean().getClass().getSimpleName()
						+ ex.getMessage().substring(indexDetalhe > 0 ? indexDetalhe : indexErro));
				
			} else{
				
				addErrorMessage(e.getMessage());
				
			}
			
		}
		
	}
	
	@Override
	public void validate(ComponentSystemEvent event) {
		
		if (getEntityId() != null) return;
		
		ValidateComponent[] validateComps = getValidaComponents();
		
		if (validateComps != null && validateComps.length > 0) {
			
			for (ValidateComponent validateComponent : validateComps) {
				
				UIComponent components = event.getComponent();

				UIInput uiInputDescricao = (UIInput) components.findComponent(validateComponent.getComponentID());
				String value = uiInputDescricao.getSubmittedValue() != null ? ""
						: uiInputDescricao.getLocalValue().toString();

				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put(validateComponent.getEntityAttribute(), value);
				
				if (value.isEmpty()) continue;
				
				boolean exists = getCrudEJB().checkIfExists(params);
				
				if (exists) {
					
					//invalidate inputs
					uiInputDescricao.setValid(false);
					
					//add validation message
					addErrorMessage("Existe um(a) " + getEntityBean().getClass().getSimpleName() +" já cadastrado(a) com o(a) mesmo(a) " +
							validateComponent.getLabelComponent() + "!");
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	@Override
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
	
    public void search() {
        
        clean();

        Map<String, Object> options = getParamsDialogPesquisa();
        
        RequestContext.getCurrentInstance().openDialog(getResourceDialogPath(), options, null);
        
    }

}