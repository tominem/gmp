package br.com.prati.tim.collaboration.gmp.dao.lote;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.LogLote;

public class LogLoteDAOImpl extends AbstractJPADAO<LogLote> implements LogLoteDAO{

	public LogLoteDAOImpl() {
		super(LogLote.class);
	}

}
