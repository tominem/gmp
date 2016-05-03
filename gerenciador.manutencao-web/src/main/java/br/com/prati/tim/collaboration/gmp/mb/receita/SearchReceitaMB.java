package br.com.prati.tim.collaboration.gmp.mb.receita;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.receita.ReceitaEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Receita;

@Named("searchReceitaMB")
@ViewScoped
public class SearchReceitaMB extends SearchableMB<Receita> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private ReceitaEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Receita";
	}

	@Override
	public String getFormName() {
		return "formPesquisaReceita";
	}

	@Override
	public String getEntityName() {
		return "Receita";
	}
	
	@Override
	public CrudEJB<Receita> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC)
			
		};
	}

}
