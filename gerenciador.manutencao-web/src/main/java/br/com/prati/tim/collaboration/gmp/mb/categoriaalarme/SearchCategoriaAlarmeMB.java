package br.com.prati.tim.collaboration.gmp.mb.categoriaalarme;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.categoriaalarme.CategoriaAlarmeEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.CategoriaAlarme;

@Named("searchCategoriaAlarmeMB")
@ViewScoped
public class SearchCategoriaAlarmeMB extends SearchableMB<CategoriaAlarme> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private CategoriaAlarmeEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Categoria de Alarme";
	}

	@Override
	public String getFormName() {
		return "formPesquisaCategoriaAlarme";
	}

	@Override
	public String getEntityName() {
		return "Categoria de Alarme";
	}
	
	@Override
	public CrudEJB<CategoriaAlarme> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC)
			
		};
	}

}
