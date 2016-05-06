package br.com.prati.tim.collaboration.gmp.ejb.maquina;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.equipamentoMaquina.EquipamentoMaquinaDAO;
import br.com.prati.tim.collaboration.gmp.dao.maquina.MaquinaDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Maquina;

@Stateless
public class MaquinaEJBImpl extends AbstractCrudEJB<Maquina> implements MaquinaEJB{

	@Inject
	private MaquinaDAO maquinaDAO;
	
	@Inject
	private EquipamentoMaquinaDAO equipamentoMaquinaDAO;
	
	@Override
	public Maquina save(Maquina entityBean) throws Exception {
		return null;
	}

	@Override
	public GenericDAO<Maquina> getCrudDAO() {
		return maquinaDAO;
	}

	@Override
	public List<EquipamentoMaquina> findEquipamentoMaquinaByMaquina(Maquina maquina) {
		return equipamentoMaquinaDAO.findByMaquina(maquina);
	}

}
