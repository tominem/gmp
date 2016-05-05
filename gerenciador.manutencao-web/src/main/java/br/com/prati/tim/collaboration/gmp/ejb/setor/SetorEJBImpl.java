package br.com.prati.tim.collaboration.gmp.ejb.setor;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.setor.SetorDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Setor;

@Stateless
public class SetorEJBImpl extends AbstractCrudEJB<Setor> implements SetorEJB{

	@Inject
	private TimeZone defaultTimeZone;

	@Inject
	private SetorDAO setorDAO;
	
	@Override
	public Setor save(Setor entityBean) throws Exception {
		if (entityBean.getIdSetor() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return setorDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Setor> getCrudDAO() {
		return setorDAO;
	}


}
