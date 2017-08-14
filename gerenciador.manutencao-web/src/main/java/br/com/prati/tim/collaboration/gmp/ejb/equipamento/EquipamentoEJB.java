package br.com.prati.tim.collaboration.gmp.ejb.equipamento;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;

public interface EquipamentoEJB extends CrudEJB<Equipamento>{

	List<FuncaoConfig> findAllFuncaoConfig();
	
	List<Equipamento> getWithQueryLike(String maquina, Boolean situacao);


}
