package br.com.prati.tim.collaboration.gmp.mb.setor;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.setor.SetorEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Setor;

@Named("searchSetorMB")
@ViewScoped
public class SearchSetorMB extends SearchableMB<Setor> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private SetorEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Setor";
	}

	@Override
	public String getFormName() {
		return "formPesquisaSetor";
	}

	@Override
	public String getEntityName() {
		return "Setor";
	}
	
	@Override
	public CrudEJB<Setor> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC)
			
		};
	}

}
