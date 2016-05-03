package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;
import java.util.Map;

import javax.annotation.PostConstruct;
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
				
				addErrorMessage(
						"Não foi possível salvar " + getEntityBean().getClass().getSimpleName()
						+ ex.getMessage().substring(ex.getMessage().indexOf("Detalhe:")));
				
			} else{
				
				addErrorMessage(e.getMessage());
				
			}
			
		}
		
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