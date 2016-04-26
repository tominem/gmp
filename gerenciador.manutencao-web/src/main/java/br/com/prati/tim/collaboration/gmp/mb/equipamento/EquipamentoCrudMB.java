package br.com.prati.tim.collaboration.gmp.mb.equipamento;

import java.io.Serializable;

import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.equipamento.EquipamentoEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.Equipamento;

@Named("equipamentoMB")
@ViewScoped
public class EquipamentoCrudMB extends AbstractCrudMB<Equipamento, Long> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	
	@Inject
	private EquipamentoEJB ejb;
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/tipocodigo/searchEquipamento.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdEquipamento();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdEquipamento(entityId);
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
	public CrudEJB<Equipamento> getCrudEJB() {
		return ejb;
	}

	@Override
	public void validate(ComponentSystemEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<Equipamento> getEntityClass() {
		return Equipamento.class;
	}


}
