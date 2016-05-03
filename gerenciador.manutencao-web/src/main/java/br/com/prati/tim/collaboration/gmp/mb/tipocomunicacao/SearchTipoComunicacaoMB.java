package br.com.prati.tim.collaboration.gmp.mb.tipocomunicacao;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipocomunicaco.TipoComunicacaoEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.TipoComunicacao;

@Named("searchTipoComunicacaoMB")
@ViewScoped
public class SearchTipoComunicacaoMB extends SearchableMB<TipoComunicacao> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private TipoComunicacaoEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Tipo de Comunicação";
	}

	@Override
	public String getFormName() {
		return "formPesquisaTipoComunicacao";
	}

	@Override
	public String getEntityName() {
		return "Tipo de Comunicação";
	}
	
	@Override
	public CrudEJB<TipoComunicacao> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC)
			
		};
	}

}
