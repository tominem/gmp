package br.com.prati.tim.collaboration.gmp.mb.tipocomponente;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.ejb.tipocomponente.CadTipoComponenteEJB;
import br.com.prati.tim.collaboration.gmp.mb.ABasePesquisa;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

@Named("mbPesquisarTpComponente")
@ViewScoped
public class MBPesquisarTipoComponente extends ABasePesquisa<TipoComponente> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;
	
	@Inject
	private CadTipoComponenteEJB			cadTipoComponenteEJB;

	@Override
	public GenericDAO<TipoComponente> getDAO() {
		return cadTipoComponenteEJB;
	}

}