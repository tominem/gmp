package br.com.prati.tim.collaboration.gmp.mb.tipoinspecao;

import java.io.Serializable;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipoinspecao.TipoInspecaoEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.TipoCodigo;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;

@Named("tipoInspecaoMB")
@ViewScoped
public class TipoInspecaoCrudMB extends AbstractCrudMB<TipoInspecao, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;

	
	@Inject
	private TipoInspecaoEJB ejb;
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/tipoinspecao/searchTipoInspecao.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdTipoInspecao();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdTipoInspecao(entityId);
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
	public CrudEJB<TipoInspecao> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<TipoInspecao> getEntityClass() {
		return TipoInspecao.class;
	}
	
	public void selectTipoDeCodigo(SelectEvent event) {
		
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(TipoCodigo.class.getName())) {
			this.entityBean.setTipoCodigo((TipoCodigo) object);
			
			UtilsMessage.addInfoMessage("Tipo de código informado com sucesso.");
		}
		
	}
	
	@Override
	public ValidateComponent[] getValidaComponents() {
		return new ValidateComponent[] {
			
			new ValidateComponent("formCad:descricao", "Descrição", "descricao")
				
		};
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		
		try {
			
			if( validatePerform() ){
				
				return super.validate(event);
			}
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
		return false;
	}

	private boolean validatePerform() throws FacesValidateException {

		if (entityBean.getTipoCodigo() == null){
			throw new FacesValidateException("Tipo de código requerido!", "formCad:tipoCodigo");
		}
		
		return false;
	}


}
