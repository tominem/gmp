package br.com.prati.tim.collaboration.gmp.mb.setor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.setor.SetorEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.Setor;

@Named("setorMB")
@ViewScoped
public class SetorCrudCrudMB extends AbstractCrudMB<Setor, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	
	private final String DESCRICAO_INPUT_ID = "formCad:descricao";
	

	@Inject
	private SetorEJB ejb;

	
	//================ METHODS ========================//
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/setor/searchSetor.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdSetor();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdSetor(entityId);
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
	public CrudEJB<Setor> getCrudEJB() {
		return ejb;
	}

	@Override
	public void validate(ComponentSystemEvent event) {
		
		if (entityBean.getIdSetor() != null) return;
		
		UIComponent components = event.getComponent();

		// get descricao
		UIInput uiInputDescricao = (UIInput) components.findComponent(DESCRICAO_INPUT_ID);
		String descricao = uiInputDescricao.getSubmittedValue() != null ? ""
				: uiInputDescricao.getLocalValue().toString();

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("descricao", descricao);
		
		//validate if object is present in db
		boolean exists = getCrudEJB().checkIfExists(params);
		
		if (exists) {
			
			//invalidate inputs
			uiInputDescricao.setValid(false);
			
			//add validation message
			addErrorMessage("Existe um Setor já cadastrado com a mesma descrição!");
			
		}
		
	}

	@Override
	public Class<Setor> getEntityClass() {
		return Setor.class;
	}


}
