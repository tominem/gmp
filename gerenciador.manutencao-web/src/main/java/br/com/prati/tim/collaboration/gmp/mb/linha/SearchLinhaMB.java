package br.com.prati.tim.collaboration.gmp.mb.linha;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.linha.LinhaproducaoEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Linhaproducao;

@Named("searchLinhaMB")
@ViewScoped
public class SearchLinhaMB extends SearchableMB<Linhaproducao> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private LinhaproducaoEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Linha de Produção";
	}

	@Override
	public String getFormName() {
		return "formPesquisaLinhaproducao";
	}

	@Override
	public String getEntityName() {
		return "Linha de Produção";
	}
	
	@Override
	public CrudEJB<Linhaproducao> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC),
			new FilterParam<String>("Tag", "tag", BOTH_LIKE)
			
		};
	}

}
