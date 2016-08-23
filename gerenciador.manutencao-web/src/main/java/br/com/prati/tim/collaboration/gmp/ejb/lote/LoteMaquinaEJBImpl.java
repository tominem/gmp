package br.com.prati.tim.collaboration.gmp.ejb.lote;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.lote.LoteMaquinaDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.LoteMaquina;

@Stateless
public class LoteMaquinaEJBImpl extends AbstractCrudEJB<LoteMaquina> implements LoteMaquinaEJB {

	@Inject
	private LoteMaquinaDAO lotemaquinaDAO;

	@Override
	public LoteMaquina save(LoteMaquina entityBean) throws Exception {
		return lotemaquinaDAO.update(entityBean);
	}

	@Override
	public GenericDAO<LoteMaquina> getCrudDAO() {
		return lotemaquinaDAO;
	}

}
