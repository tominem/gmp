package br.com.prati.tim.collaboration.gmp.mb.tipocomponente;

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
import br.com.prati.tim.collaboration.gmp.ejb.tipocomponente.TipoComponenteEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

@Named("tipoComponenteMB")
@ViewScoped
public class TipoComponenteCrudMB extends AbstractCrudMB<TipoComponente, Long> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;
	
	@Inject
	private TipoComponenteEJB ejb;

	@Override
	public String getFormName() {
		return "formCadTpComponente";
	}
	
	@Override
	public CrudEJB<TipoComponente> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<TipoComponente> getEntityClass() {
		return TipoComponente.class;
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdTipoComponente();
	}
	
	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdTipoComponente(entityId);
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		
		UIComponent components = event.getComponent();

		// get descricao
		UIInput uiInputDescricao = (UIInput) components.findComponent("descricao");
		String descricao = uiInputDescricao.getSubmittedValue() != null ? ""
				: uiInputDescricao.getLocalValue().toString();

		// get nome
		UIInput uiInputNome = (UIInput) components.findComponent("nomeComponente");
		String nome = uiInputNome.getSubmittedValue() != null ? ""
				: uiInputNome.getLocalValue().toString();
		
		Map<String, Object> params = putParams(descricao, nome);
		
		//validate if object is present in db
		boolean exists = getCrudEJB().checkIfExists(params);
		
		if (exists) {
			
			//invalidate inputs
			uiInputDescricao.setValid(false);
			uiInputNome.setValid(false);
			
			//add validation message
			addErrorMessage("Tipo Componente já está cadastrado, com a mesma descrição, nome ou ambos");
			
			return false;
		}
		
		return true;
	}

	private Map<String, Object> putParams(String descricao, String nome) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("descricao", descricao);
		params.put("nomeComponente", nome);
		
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
		return "/cadastros/tipocomponente/searchTipoComponente.xhtml";
	}

}
