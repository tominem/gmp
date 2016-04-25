package br.com.prati.tim.collaboration.gmp.mb.itemconfig;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.itemconfig.FuncaoConfigEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;

@Named("searchItemConfigMB")
@ViewScoped
public class SearchItemConfigMB extends SearchableMB<FuncaoConfig> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private FuncaoConfigEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Item de Configuração";
	}

	@Override
	public String getFormName() {
		return "formPesquisaItemConfig";
	}

	@Override
	public String getEntityName() {
		return "Item de configuração";
	}
	
	@Override
	public CrudEJB<FuncaoConfig> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC),
			new FilterParam<String>("Menu", "menuConfig.descricao", BOTH_LIKE),
			new FilterParam<String>("Menu", "tipoComponente.descricao", BOTH_LIKE)
			
		};
	}

}
