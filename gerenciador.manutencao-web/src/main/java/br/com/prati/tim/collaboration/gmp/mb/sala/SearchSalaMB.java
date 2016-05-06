package br.com.prati.tim.collaboration.gmp.mb.sala;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;
import static javax.persistence.FetchType.EAGER;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.sala.SalaEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Sala;

@Named("searchSalaMB")
@ViewScoped
public class SearchSalaMB extends SearchableMB<Sala> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private SalaEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Sala";
	}

	@Override
	public String getFormName() {
		return "formPesquisaSala";
	}

	@Override
	public String getEntityName() {
		return "Sala";
	}
	
	@Override
	public CrudEJB<Sala> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC),
			new FilterParam<String>("Sala", "setor.descricao", BOTH_LIKE, EAGER)
			
		};
	}

}
