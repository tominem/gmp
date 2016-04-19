package br.com.prati.tim.collaboration.gmp.dao.tipocodigo;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.TipoCodigo;

public class TipoCodigoDAOImpl extends AbstractJPADAO<TipoCodigo> implements TipoCodigoDAO{

	public TipoCodigoDAOImpl() {
		super(TipoCodigo.class);
	}

}
