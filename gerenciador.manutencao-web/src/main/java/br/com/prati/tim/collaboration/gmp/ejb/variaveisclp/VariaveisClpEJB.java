package br.com.prati.tim.collaboration.gmp.ejb.variaveisclp;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.VariaveisClp;

public interface VariaveisClpEJB extends CrudEJB<VariaveisClp>{

	List<VariaveisClp> findVariaveisClpByMaquinaAndCLP(Maquina maquina,	Equipamento clp);
	
}
