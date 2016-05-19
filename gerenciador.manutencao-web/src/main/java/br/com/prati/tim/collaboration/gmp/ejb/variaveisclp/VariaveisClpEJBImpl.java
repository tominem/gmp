package br.com.prati.tim.collaboration.gmp.ejb.variaveisclp;

import javax.ejb.Stateless;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.VariaveisClp;

@Stateless
public class VariaveisClpEJBImpl extends AbstractCrudEJB<VariaveisClp> implements VariaveisClpEJB{

	@Override
	public VariaveisClp save(VariaveisClp entityBean) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenericDAO<VariaveisClp> getCrudDAO() {
		// TODO Auto-generated method stub
		return null;
	}

}
