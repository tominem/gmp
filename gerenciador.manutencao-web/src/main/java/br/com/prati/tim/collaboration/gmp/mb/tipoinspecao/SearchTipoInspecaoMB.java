package br.com.prati.tim.collaboration.gmp.mb.tipoinspecao;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.fabricante.FabricanteEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Fabricante;

@Named("searchTipoInspecaoMB")
@ViewScoped
public class SearchTipoInspecaoMB extends SearchableMB<Fabricante> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private FabricanteEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Fabricante";
	}

	@Override
	public String getFormName() {
		return "formPesquisaFabricante";
	}

	@Override
	public String getEntityName() {
		return "Fabricante";
	}
	
	@Override
	public CrudEJB<Fabricante> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Nome", "nome", BOTH_LIKE, ASC)
			
		};
	}

}
