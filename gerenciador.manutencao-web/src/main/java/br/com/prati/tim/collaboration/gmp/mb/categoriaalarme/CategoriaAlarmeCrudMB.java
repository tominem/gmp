package br.com.prati.tim.collaboration.gmp.mb.categoriaalarme;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.categoriaalarme.CategoriaAlarmeEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.CategoriaAlarme;

@Named("categoriaAlarmeMB")
@ViewScoped
public class CategoriaAlarmeCrudMB extends AbstractCrudMB<CategoriaAlarme, Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	
	private final String DESCRICAO_INPUT_ID = "formCad:descricao";
	
	private String FUNCAO_GUM_INPUT_ID		= "formCad:funcao";

	@Inject
	private CategoriaAlarmeEJB ejb;


	
	//================ METHODS ========================//
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/categoriaalarme/searchCategoriaAlarme.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdCategoria();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdCategoria(entityId);
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
	public CrudEJB<CategoriaAlarme> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<CategoriaAlarme> getEntityClass() {
		return CategoriaAlarme.class;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return new ValidateComponent[] {
				
			new ValidateComponent(DESCRICAO_INPUT_ID, "Descrição", "descricao"),
			new ValidateComponent(FUNCAO_GUM_INPUT_ID, "Função do G.U.M.", "funcaoGum")
				
		};
	}

}
