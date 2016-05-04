package br.com.prati.tim.collaboration.gmp.mb.maquina;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.FetchType;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.maquina.MaquinaEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Maquina;

@Named("searchMaquinaMB")
@ViewScoped
public class SearchMaquinaMB extends SearchableMB<Maquina> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private MaquinaEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Máquina";
	}

	@Override
	public String getFormName() {
		return "formPesquisaMaquina";
	}

	@Override
	public String getEntityName() {
		return "Máquina";
	}
	
	@Override
	public CrudEJB<Maquina> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Tag", "tag", BOTH_LIKE, ASC),
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE),
			new FilterParam<String>("Linha", "linhaproducao.descricao", BOTH_LIKE, FetchType.EAGER),
			new FilterParam<String>("Sala", "sala.descricao", BOTH_LIKE, FetchType.EAGER)
			
		};
	}

}
