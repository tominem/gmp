package br.com.prati.tim.collaboration.gmp.ejb.sala;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.sala.SalaDAO;
import br.com.prati.tim.collaboration.gmp.dao.setor.SetorDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Sala;
import br.prati.tim.collaboration.gp.jpa.Setor;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class SalaEJBImpl extends AbstractCrudEJB<Sala> implements SalaEJB{

	@Inject
	private TimeZone defaultTimeZone;

	@Inject
	private SalaDAO salaDAO;
	
	@Inject
	private SetorDAO setorDAO;
	
	@Override
	public Sala save(Sala entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdSala() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		if (entityBean.getIdSala() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return salaDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Sala> getCrudDAO() {
		return salaDAO;
	}

	@Override
	public List<Setor> findActivesSetores() {
		return setorDAO.findActives();
	}
	
}
