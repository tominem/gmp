package br.com.prati.tim.collaboration.gmp.mb.equipamento;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.EQUAL;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.FetchType;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.equipamento.EquipamentoEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoEquipamento;

@Named("searchCLPMB")
@ViewScoped
public class SearchCLPMB extends SearchableMB<Equipamento> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	private static final int MAX_RESULTS = 20;
	
	@Inject
	private EquipamentoEJB ejb;
	
	@Override
	public String getTitle() {
		return "Pesquisar CLP";
	}

	@Override
	public String getFormName() {
		return "formPesquisaCLP";
	}

	@Override
	public String getEntityName() {
		return "CLP";
	}
	
	@Override
	public CrudEJB<Equipamento> getCrudEJB() {
		return ejb;
	}
	
	@Override
	public void search() {
		List<Equipamento> objectList;

		if (getPattern() == null || getPattern().trim().isEmpty()) {
			objectList = getCrudEJB().findAllWithLimit(MAX_RESULTS, Optional.ofNullable(getStatusSituation()), getFilterParams());

		} else {

			objectList = getCrudEJB().findLikeOrNotLikeWithLimit(getPattern(), Optional.ofNullable(getMaxResults()), Optional.ofNullable(getStatusSituation()), getFilterParams());
		}

		setObjectList(objectList != null ? objectList.stream().filter(e -> e.getTipoEquipamento() == ETipoEquipamento.CLP).collect(Collectors.toList()) : null);
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
