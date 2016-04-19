package br.com.prati.tim.collaboration.gmp.mb.tipocodigo;

import java.io.Serializable;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipocodigo.TipoCodigoEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.TipoCodigo;

@Named("tipoCodigoMB")
@ViewScoped
public class TipoCodigoCrudMB extends AbstractCrudMB<TipoCodigo, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	
	@Inject
	private TipoCodigoEJB ejb;
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/tipocodigo/searchTipoCodigo.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdTipoCodigo();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdTipoCodigo(entityId);
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
	public CrudEJB<TipoCodigo> getCrudEJB() {
		return ejb;
	}

	@Override
	public void validate(ComponentSystemEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<TipoCodigo> getEntityClass() {
		return TipoCodigo.class;
	}


}
