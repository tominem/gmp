package br.com.prati.tim.collaboration.gmp.mb.tipoinspecao;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipoinspecao.TipoInspecaoEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;

@Named("searchTipoInspecaoMB")
@ViewScoped
public class SearchTipoInspecaoMB extends SearchableMB<TipoInspecao> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private TipoInspecaoEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Tipo de Inspeção";
	}

	@Override
	public String getFormName() {
		return "formPesquisaTipoInspecao";
	}

	@Override
	public String getEntityName() {
		return "Tipo de Inspeção";
	}
	
	@Override
	public CrudEJB<TipoInspecao> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC)
			
		};
	}

}
