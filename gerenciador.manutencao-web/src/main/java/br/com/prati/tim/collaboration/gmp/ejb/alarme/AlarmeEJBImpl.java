package br.com.prati.tim.collaboration.gmp.ejb.alarme;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.alarme.AlarmeDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Alarme;

@Stateless
public class AlarmeEJBImpl extends AbstractCrudEJB<Alarme> implements AlarmeEJB{

	@Inject
	private TimeZone defaultTimeZone;

	@Inject
	private AlarmeDAO alarmeDAO;
	
	@Override
	public Alarme save(Alarme entityBean) throws Exception {
		if (entityBean.getIdAlarme() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return alarmeDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Alarme> getCrudDAO() {
		return alarmeDAO;
	}


}
