package br.com.prati.tim.collaboration.gmp.mb.tipocodigo;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipocodigo.TipoCodigoEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.TipoCodigo;

@Named("searchTipoCodigoMB")
@ViewScoped
public class SearchTipoCodigoMB extends SearchableMB<TipoCodigo> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private TipoCodigoEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Tipo Código";
	}

	@Override
	public String getFormName() {
		return "formPesquisaTipoCodigo";
	}

	@Override
	public String getEntityName() {
		return "Tipo de Código";
	}
	
	@Override
	public CrudEJB<TipoCodigo> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC)
			
		};
	}

}
