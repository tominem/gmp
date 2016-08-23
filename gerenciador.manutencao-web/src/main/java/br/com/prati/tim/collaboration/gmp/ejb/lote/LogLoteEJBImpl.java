package br.com.prati.tim.collaboration.gmp.ejb.lote;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.lote.LogLoteDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.LogLote;

@Stateless
public class LogLoteEJBImpl extends AbstractCrudEJB<LogLote> implements LogLoteEJB{

	@Inject
	private LogLoteDAO	logLoteDAO;
	
	@Override
	public LogLote save(LogLote entityBean) throws Exception {
		entityBean.setDataHora(new Date());
		return logLoteDAO.persist(entityBean);
	}

	@Override
	public GenericDAO<LogLote> getCrudDAO() {
		return logLoteDAO;
	}

}
