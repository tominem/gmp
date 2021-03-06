package br.com.prati.tim.collaboration.gmp.mb.alarme;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.alarme.AlarmeEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Alarme;

@Named("searchAlarmeMB")
@ViewScoped
public class SearchAlarmeMB extends SearchableMB<Alarme> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private AlarmeEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Alarme";
	}

	@Override
	public String getFormName() {
		return "formPesquisaAlarme";
	}

	@Override
	public String getEntityName() {
		return "Alarme";
	}
	
	@Override
	public CrudEJB<Alarme> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Título", "titulo", BOTH_LIKE, ASC),
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE),
			new FilterParam<String>("Categoria", "categoriaAlarme.descricao", BOTH_LIKE, EAGER)
			
		};
	}

}
