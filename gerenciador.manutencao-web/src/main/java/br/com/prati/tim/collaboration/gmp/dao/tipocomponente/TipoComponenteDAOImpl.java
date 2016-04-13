package br.com.prati.tim.collaboration.gmp.dao.tipocomponente;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

public class TipoComponenteDAOImpl extends AbstractJPADAO<TipoComponente> implements TipoComponenteDAO{

	public TipoComponenteDAOImpl() {
		super(TipoComponente.class);
	}

}
