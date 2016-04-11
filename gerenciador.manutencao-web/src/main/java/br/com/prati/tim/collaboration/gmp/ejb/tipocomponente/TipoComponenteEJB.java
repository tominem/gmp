package br.com.prati.tim.collaboration.gmp.ejb.tipocomponente;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.CrudDAO;
import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.dao.tipocomponente.TipoComponenteDAO;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

@Stateless
public class TipoComponenteEJB implements CrudEJB<TipoComponente>{

	@Inject
	private TipoComponenteDAO tipoComponenteDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public TipoComponente save(TipoComponente entityBean) {
		
		if (entityBean.getIdTipoComponente() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return tipoComponenteDAO.update(entityBean);
	}

	@Override
	public CrudDAO<TipoComponente> getCrudDAO() {
		return tipoComponenteDAO;
	}

	@Override
	public void exclude(TipoComponente entityBean) {
		tipoComponenteDAO.delete(entityBean);
	}

	@Override
	public List<TipoComponente> findAllWithLimit(Optional<Boolean> statusValue) {
		return tipoComponenteDAO.findAllWithLimit(statusValue);
	}

	@Override
	public List<TipoComponente> findLikeOrNotLikeWithLimit(
			String pattern, 
			Optional<Integer> limit,
			Optional<Boolean> active, 
			FilterParam<?>... filterParams) {
		
		return tipoComponenteDAO.findLikeOrNotLikeWithLimit(pattern, limit, active, filterParams);
	}

}
