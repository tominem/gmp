package br.com.prati.tim.collaboration.gmp.mb.faca;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.faca.FacaEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Faca;

@Named("searchFacaMB")
@ViewScoped
public class SearchFacaMB extends SearchableMB<Faca> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private FacaEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Faca";
	}

	@Override
	public String getFormName() {
		return "formPesquisaFaca";
	}

	@Override
	public String getEntityName() {
		return "Faca";
	}
	
	@Override
	public CrudEJB<Faca> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC)
			
		};
	}

}
