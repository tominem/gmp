package br.com.prati.tim.collaboration.gmp.mb.sala;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.sala.SalaEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.Sala;
import br.prati.tim.collaboration.gp.jpa.Setor;

@Named("salaMB")
@ViewScoped
public class SalaCrudMB extends AbstractCrudMB<Sala, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;
	

	@Inject
	private SalaEJB ejb;

	private List<Setor> setores;

	
	//================ METHODS ========================//
	
	@Override
	@PostConstruct
	public void initObjects() {
		
		super.initObjects();
		
		load();
		
	}
	
	public void load(){
		
		List<Setor> foundSetores = ejb.findActivesSetores();
		
		if (foundSetores != null) {
			Collections.sort(foundSetores, (x1, x2) -> x1.getDescricao().compareTo(x2.getDescricao()));
		}
		
		setSetores(foundSetores);
		
	}
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/sala/searchSala.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdSala();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdSala(entityId);
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
	public CrudEJB<Sala> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Sala> getEntityClass() {
		return Sala.class;
	}

	public List<Setor> getSetores() {
		return setores;
	}

	public void setSetores(List<Setor> setores) {
		this.setores = setores;
	}

}
