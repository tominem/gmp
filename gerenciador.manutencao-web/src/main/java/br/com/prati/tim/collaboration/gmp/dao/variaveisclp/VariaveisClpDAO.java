package br.com.prati.tim.collaboration.gmp.dao.variaveisclp;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.VariaveisClp;

public interface VariaveisClpDAO extends GenericDAO<VariaveisClp>{

	List<VariaveisClp> findByMaquinaAndEquipamento(Maquina maquina,	Equipamento clp);

}
