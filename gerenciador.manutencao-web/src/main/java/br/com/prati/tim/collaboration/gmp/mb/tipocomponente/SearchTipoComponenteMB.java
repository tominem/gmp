package br.com.prati.tim.collaboration.gmp.mb.tipocomponente;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipocomponente.TipoComponenteEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

@Named("searchTipoCompMB")
@ViewScoped
public class SearchTipoComponenteMB extends SearchableMB<TipoComponente> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private TipoComponenteEJB ejb;
	
	@Override
	@PostConstruct
	public void init() {
		search();
	}
	
	@Override
	public String getTitle() {
		return "Pesquisar Tipo Componente";
	}

	@Override
	public String getFormName() {
		return "formPesquisaTpComponente";
	}

	@Override
	public String getEntityName() {
		return "tipo de componente";
	}
	
	@Override
	public CrudEJB<TipoComponente> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC), 
			new FilterParam<String>("Nome", "nomeComponente", BOTH_LIKE)
			
		};
	}

}
