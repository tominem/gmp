package br.com.prati.tim.collaboration.gmp.mb.subproduto;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.subproduto.SubprodutoEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Subproduto;

@Named("searchSubprodutoMB")
@ViewScoped
public class SearchSubprodutoMB extends SearchableMB<Subproduto> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private SubprodutoEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Subproduto";
	}

	@Override
	public String getFormName() {
		return "formPesquisaSubproduto";
	}

	@Override
	public String getEntityName() {
		return "Subproduto";
	}
	
	@Override
	public CrudEJB<Subproduto> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC)
			
		};
	}

}
