package br.com.prati.tim.collaboration.gmp.ejb.tipocomponente;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.tipocomponente.TipoComponenteDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

@Stateless
public class TipoComponenteEJBImpl extends AbstractCrudEJB<TipoComponente> implements TipoComponenteEJB{

	@Inject
	private TimeZone defaultTimeZone;

	@Inject
	private TipoComponenteDAO tipoComponenteDAO;
	
	@Override
	public TipoComponente save(TipoComponente entityBean) {
		
		if (entityBean.getIdTipoComponente() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return tipoComponenteDAO.update(entityBean);
	}
	
	@Override
	public GenericDAO<TipoComponente> getCrudDAO() {
		return tipoComponenteDAO;
	}


}
