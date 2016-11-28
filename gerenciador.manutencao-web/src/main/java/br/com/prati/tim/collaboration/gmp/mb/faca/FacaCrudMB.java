package br.com.prati.tim.collaboration.gmp.mb.faca;

import java.io.Serializable;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.faca.FacaEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.Faca;

@Named("facaMB")
@ViewScoped
public class FacaCrudMB extends AbstractCrudMB<Faca, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;

	
	@Inject
	private FacaEJB ejb;
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/faca/searchFaca.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdFaca();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdFaca(entityId);
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
	public CrudEJB<Faca> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Faca> getEntityClass() {
		return Faca.class;
	}
	
	@Override
	public ValidateComponent[] getValidaComponents() {
		return new ValidateComponent[]{
				
			new ValidateComponent("formCad:nome", "Nome", "descricao")
				
		};
	}
	
//	private boolean validatePerform() throws FacesValidateException {
//
//		if (entityBean.getDescricao() == null){
//			throw new FacesValidateException("Tipo de código requerido!", "formCad:tipoCodigo");
//		}
//		
//		return false;
//	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		
//		try {
			
//			if( validatePerform() ){
				
				return super.validate(event);
//			}
			
//		} catch (FacesValidateException e) {
			
//			addErrorMessage(e.getMessage());
			
//		}
		
//		return false;
	}


}
