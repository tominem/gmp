package br.com.prati.tim.collaboration.gmp.mb.alarme;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.alarme.AlarmeEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.Alarme;
import br.prati.tim.collaboration.gp.jpa.CategoriaAlarme;

@Named("alarmeMB")
@ViewScoped
public class AlarmeCrudMB extends AbstractCrudMB<Alarme, Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	
	private final String DESCRICAO_INPUT_ID = "formCad:descricao";
	
	private String FUNCAO_GUM_INPUT_ID		= "formCad:funcao";

	@Inject
	private AlarmeEJB ejb;

	
	//================ METHODS ========================//
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/categoriaalarme/searchAlarme.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdAlarme();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdAlarme(entityId);
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
	public CrudEJB<Alarme> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Alarme> getEntityClass() {
		return Alarme.class;
	}
	
	public void selectCategoria(SelectEvent event) {

		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(CategoriaAlarme.class.getName())) {
			entityBean.setCategoriaAlarme((CategoriaAlarme) object);

			addInfoMessage("Categoria de alarme informada com sucesso.");
		}
			
	}

}
