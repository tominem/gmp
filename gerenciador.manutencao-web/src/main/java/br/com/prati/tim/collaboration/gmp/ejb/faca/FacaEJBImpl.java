package br.com.prati.tim.collaboration.gmp.ejb.faca;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.faca.FacaDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class FacaEJBImpl extends AbstractCrudEJB<Faca> implements FacaEJB {

	@Inject
	private FacaDAO facaDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public Faca save(Faca entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdFaca() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		if (entityBean.getIdFaca() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return facaDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Faca> getCrudDAO() {
		return facaDAO;
	}

}
