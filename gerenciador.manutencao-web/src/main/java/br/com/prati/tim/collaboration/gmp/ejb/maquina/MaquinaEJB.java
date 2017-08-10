package br.com.prati.tim.collaboration.gmp.ejb.maquina;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public interface MaquinaEJB extends CrudEJB<Maquina>{

	List<EquipamentoMaquina> findEquipamentoMaquinaByMaquina(Maquina entityBean);

	GenericDAO<EquipamentoMaquina> getEquipamentoMaquinaDAO();

	List<Maquina> getWithQueryLike(String maquina, Boolean b);
}
