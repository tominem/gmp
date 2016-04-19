package br.com.prati.tim.collaboration.gmp.ejb.fabricante;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.fabricante.FabricanteDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Fabricante;

@Stateless
public class FabricanteEJBImpl extends AbstractCrudEJB<Fabricante> implements FabricanteEJB{

	@Inject
	private FabricanteDAO fabricanteDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public Fabricante save(Fabricante entityBean) {
		if (entityBean.getIdFabricante() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return fabricanteDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Fabricante> getCrudDAO() {
		return fabricanteDAO;
	}

}
