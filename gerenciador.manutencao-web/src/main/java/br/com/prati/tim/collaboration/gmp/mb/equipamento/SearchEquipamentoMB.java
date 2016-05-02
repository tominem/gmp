package br.com.prati.tim.collaboration.gmp.mb.equipamento;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.EQUAL;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.FetchType;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.equipamento.EquipamentoEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Equipamento;

@Named("searchEquipamentoMB")
@ViewScoped
public class SearchEquipamentoMB extends SearchableMB<Equipamento> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private EquipamentoEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar Equipamento";
	}

	@Override
	public String getFormName() {
		return "formPesquisaEquipamento";
	}

	@Override
	public String getEntityName() {
		return "Equipamento";
	}
	
	@Override
	public CrudEJB<Equipamento> getCrudEJB() {
		return ejb;
	}
	
	@Override
	public void search() {
		// TODO Auto-generated method stub
		super.search();
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Nome", "nome", BOTH_LIKE, ASC),
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE),
			new FilterParam<String>("Tag", "tag", BOTH_LIKE),
			new FilterParam<String>("Fabricante", "fabricante.nome", BOTH_LIKE, FetchType.EAGER),
			new FilterParam<String>("Tipo", "tipoEquipamento", EQUAL)
			
		};
	}

}
