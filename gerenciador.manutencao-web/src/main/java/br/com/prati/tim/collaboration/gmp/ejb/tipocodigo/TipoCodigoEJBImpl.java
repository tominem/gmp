package br.com.prati.tim.collaboration.gmp.ejb.tipocodigo;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.tipocodigo.TipoCodigoDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.TipoCodigo;

@Stateless
public class TipoCodigoEJBImpl extends AbstractCrudEJB<TipoCodigo> implements TipoCodigoEJB{

	@Inject
	private TipoCodigoDAO tipoCodigoDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public TipoCodigo save(TipoCodigo entityBean) {
		if (entityBean.getIdTipoCodigo() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return tipoCodigoDAO.update(entityBean);
	}

	@Override
	public GenericDAO<TipoCodigo> getCrudDAO() {
		return tipoCodigoDAO;
	}

}
