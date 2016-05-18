package br.com.prati.tim.collaboration.gmp.mb.tipocomponente;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipocomponente.TipoComponenteEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
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
	public ValidateComponent[] getValidaComponents() {
		return new ValidateComponent[]{
				
			new ValidateComponent("descricao", "Descrição", "descricao"),
			new ValidateComponent("nomeComponente", "Nome do Componente", "nomeComponente")
			
		};
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
