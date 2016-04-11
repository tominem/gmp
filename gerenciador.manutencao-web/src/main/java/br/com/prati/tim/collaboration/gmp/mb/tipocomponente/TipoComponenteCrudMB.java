package br.com.prati.tim.collaboration.gmp.mb.tipocomponente;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipocomponente.TipoComponenteEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

@Named("tipoComponenteMB")
@ViewScoped
public class TipoComponenteCrudMB extends AbstractCrudMB<TipoComponente> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;
	
	@Inject
	private TipoComponenteEJB ejb;

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC), 
			new FilterParam<String>("Nome", "nomeComponente", BOTH_LIKE)
			
		};
	}

	@Override
	public String getFormName() {
		return "formCadTpComponente";
	}

	@Override
	public CrudEJB<TipoComponente> getCrudEJB() {
		return null;
	}

	@Override
	public void activateOrInactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class<TipoComponente> getEntityClass() {
		return TipoComponente.class;
	}

}
