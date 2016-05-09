package br.com.prati.tim.collaboration.gmp.ejb.categoriaalarme;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.categoriaalarme.CategoriaAlarmeDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.CategoriaAlarme;

@Stateless
public class CategoriaAlarmeEJBImpl extends AbstractCrudEJB<CategoriaAlarme> implements CategoriaAlarmeEJB{

	@Inject
	private TimeZone defaultTimeZone;

	@Inject
	private CategoriaAlarmeDAO categoriaAlarmeDAO;
	
	@Override
	public CategoriaAlarme save(CategoriaAlarme entityBean) throws Exception {
		if (entityBean.getIdCategoria() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return categoriaAlarmeDAO.update(entityBean);
	}

	@Override
	public GenericDAO<CategoriaAlarme> getCrudDAO() {
		return categoriaAlarmeDAO;
	}

}
