package br.com.prati.tim.collaboration.gmp.dao.equipamento;

import javax.persistence.EntityManager;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;

public interface EquipamentoDAO extends GenericDAO<Equipamento>{

	public EntityManager getEntityManager();

}
