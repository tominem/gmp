package br.com.prati.tim.collaboration.gmp.mb.fabricante;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.fabricante.FabricanteEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.Fabricante;

@Named("fabricanteMB")
@ViewScoped
public class FabricanteCrudMB extends AbstractCrudMB<Fabricante, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;

	
	@Inject
	private FabricanteEJB ejb;
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/fabricante/searchFabricante.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdFabricante();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdFabricante(entityId);
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
	public CrudEJB<Fabricante> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Fabricante> getEntityClass() {
		return Fabricante.class;
	}


}
