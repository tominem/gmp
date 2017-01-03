package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.persistence.Id;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

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
			
			int cutIndex = (e.getMessage().indexOf("Detalhe:")-1);
			
			if(cutIndex == -1) {
				cutIndex = (e.getMessage().indexOf("Detail:")-1);
			}
			
			if (cutIndex == -1) {
				cutIndex = 0;
			}
			
			addErrorMessage(
					"Não foi possível excluir " + getEntityBean().getClass().getSimpleName()
					+ e.getMessage().substring(e.getMessage().indexOf("Detalhe:")-1));
			
			return false;
			
		}
		
	}

	private void excludePerform() throws Exception {
		
		if (!temAcesso(ETipoAcessoGUM.EXCLUSAO)){
    		return;
    	}
		
		getCrudEJB().exclude(this.entityBean);

		clean();

		showMensagemExclusaoSucesso();
	}

	public void activateOrInactivate(){
		
		if (!temAcesso(ETipoAcessoGUM.ALTERACAO)){
    		return;
    	}
		
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
	public boolean validate(ComponentSystemEvent event) {
		
		ValidateComponent[] validateComps = getValidaComponents();
		
		if (validateComps != null && validateComps.length > 0) {
			
			for (ValidateComponent validateComponent : validateComps) {
				
				UIComponent components = event.getComponent();

				UIInput uiInputDescricao = (UIInput) components.findComponent(validateComponent.getComponentID());
				Object value = uiInputDescricao.getSubmittedValue() != null ? ""
						: uiInputDescricao.getLocalValue();

				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put(validateComponent.getEntityAttribute(), value);
				
				if (value == null || (value instanceof String && value.toString().isEmpty())) continue;
				
				List<T> result = getCrudEJB().checkIfExists(params);
				
				boolean exists = false;
				
				if (result != null) {
					
					//===========================================================================================================
					// Varre os resultados a fim de encontrar o mesmo id, o que significa que o usuário está fazendo um update. 
					// Caso encontre algum outro de id diferente, imediatamente invalida o submmit
					//===========================================================================================================
					
					exists = result.stream().filter(e -> compare(e, getEntityId())).findFirst().isPresent();
					
				}
				
				if (exists) {
					
					//invalidate inputs
					uiInputDescricao.setValid(false);
					
					//add validation message
					addErrorMessage("Existe um(a) " + getEntityBean().getClass().getSimpleName() +" já cadastrado(a) com o(a) mesmo(a) " +
							validateComponent.getLabelComponent() + "!");
					
					return false;
				}
				
			}
			
		}
		
		return true;
	}
	
	/**
	 * Método que compara os objetos encontrados no banco 
	 * com a entidade do formulário por Reflection
	 * 
	 * @param e         	elemento genérico que representa o tipo da entidade do formulário
	 * 
	 * @param  entityId  	id da entidade do formulário
	 * 
	 * @return true 		(se encontrar um id diferente do da entidade do formulário)
	 * 		   false		(se encontrar o mesmo id ou nenhum)
	 */
	private boolean compare(T e, P entityId) {
		
		Optional<Field> findFirst = Arrays.asList(e.getClass().getDeclaredFields()).stream().filter(e1 -> e1.isAnnotationPresent(Id.class)).findFirst();
		
		if (findFirst.isPresent()) {
			
			Field f = findFirst.get();
			
			try {
				
				Method m = e.getClass().getMethod("get" + StringUtils.capitalize(f.getName()));
				
				boolean equals = m.invoke(e).equals(entityId);
				
				return !equals;
				
			} catch (IllegalArgumentException e2) {
				e2.printStackTrace();
			} catch (IllegalAccessException e2) {
				e2.printStackTrace();
			} catch (NoSuchMethodException e2) {
				e2.printStackTrace();
			} catch (SecurityException e2) {
				e2.printStackTrace();
			} catch (InvocationTargetException e2) {
				e2.printStackTrace();
			}
			
		}
		
		return false;
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
        
    	if (!temAcesso(ETipoAcessoGUM.CONSULTA)){
    		return;
    	}
    	
        clean();

        Map<String, Object> options = getParamsDialogPesquisa();
        
        RequestContext.getCurrentInstance().openDialog(getResourceDialogPath(), options, null);
        
    }
    
    private boolean temAcesso(ETipoAcessoGUM tipoAcesso){
    	
//    	if (!SessionUtil.temPermissaoGUM(tipoAcesso)){
//    		UtilsMessage.addErrorMessage("Usuário sem permissão de " + tipoAcesso.getDescricao() + ".");
//    		return false;
//    	}
    	
    	return true;
    }

}