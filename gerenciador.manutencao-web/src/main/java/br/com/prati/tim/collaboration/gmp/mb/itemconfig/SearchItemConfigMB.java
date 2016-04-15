package br.com.prati.tim.collaboration.gmp.mb.itemconfig;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.menuconfig.MenuConfigEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;

@Named("searcItemConfigMB")
@ViewScoped
public class SearchItemConfigMB extends SearchableMB<MenuConfig> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private MenuConfigEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Menu Configuração";
	}

	@Override
	public String getFormName() {
		return "formPesquisaMenuConfig";
	}

	@Override
	public String getEntityName() {
		return "Menu de configuração";
	}
	
	@Override
	public CrudEJB<MenuConfig> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC),
			new FilterParam<String>("Pai", "menuConfig.descricao", BOTH_LIKE)
			
		};
	}

}
