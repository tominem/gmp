package br.com.prati.tim.collaboration.gmp.ejb.variaveisclp;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.variaveisclp.VariaveisClpDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.VariaveisClp;

@Stateless
public class VariaveisClpEJBImpl extends AbstractCrudEJB<VariaveisClp> implements VariaveisClpEJB{

	@Inject
	private VariaveisClpDAO variaveisClpDAO; 
	
	@Override
	public VariaveisClp save(VariaveisClp entityBean) throws Exception {
		return variaveisClpDAO.update(entityBean);
	}

	@Override
	public GenericDAO<VariaveisClp> getCrudDAO() {
		return variaveisClpDAO;
	}

	@Override
	public void save(List<VariaveisClp> list) throws Exception {
		
		for (VariaveisClp var : list) {
			save(var);
		}
		
	}

	@Override
	public List<VariaveisClp> findVariaveisClpByMaquinaAndCLP(Maquina maquina, Equipamento clp) {
		return variaveisClpDAO.findByMaquinaAndEquipamento(maquina, clp);
	}

}
