package br.com.prati.tim.collaboration.gmp.mb.subproduto;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.subproduto.SubprodutoEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.Subproduto;

@Named("subprodutoMB")
@ViewScoped
public class SubprodutoCrudMB extends AbstractCrudMB<Subproduto, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	
	private final String DESCRICAO_INPUT_ID = "formCad:descricao";
	

	@Inject
	private SubprodutoEJB ejb;

	
	//================ METHODS ========================//
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/subproduto/searchSubproduto.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdSubproduto();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdSubproduto(entityId);
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
	public CrudEJB<Subproduto> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Subproduto> getEntityClass() {
		return Subproduto.class;
	}


}
