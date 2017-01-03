package br.com.prati.tim.collaboration.gmp.mb.statuslote;

import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterCriteria;
import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.lote.LoteEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Lote;

@Named("mbSearchLote")
@ViewScoped
public class MBSearchLote extends SearchableMB<Lote> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LoteEJB		ejb;

	@Override
	public String getTitle() {
		return "Pesquisar Lote";
	}

	@Override
	public String getFormName() {
		return "formPesquisaLote";
	}

	@Override
	public String getEntityName() {
		return "Lote";
	}

	@Override
	public CrudEJB<Lote> getCrudEJB() {
		return ejb;
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[] {

				new FilterParam<String>("Número Lote", "numeroLote", FilterCriteria.BOTH_LIKE, ASC),
				new FilterParam<String>("Ordem Produção", "ordemProducao", FilterCriteria.EQUAL)

		};
	}

}
