package br.com.prati.tim.collaboration.gmp.mb.linha;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.linha.LinhaproducaoEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.Linhaproducao;

@Named("linhaMB")
@ViewScoped
public class LinhaCrudMB extends AbstractCrudMB<Linhaproducao, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	

	@Inject
	private LinhaproducaoEJB ejb;

	
	//================ METHODS ========================//
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/linha/searchLinha.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdLinhaproducao();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdLinhaproducao(entityId);
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
	public CrudEJB<Linhaproducao> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Linhaproducao> getEntityClass() {
		return Linhaproducao.class;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return new ValidateComponent[] {
				new ValidateComponent("formCad:tag", "Tag", "tag")
		};
	}
	
}
