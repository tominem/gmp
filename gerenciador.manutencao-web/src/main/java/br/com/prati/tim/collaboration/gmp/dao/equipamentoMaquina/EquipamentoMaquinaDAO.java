package br.com.prati.tim.collaboration.gmp.dao.equipamentoMaquina;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public interface EquipamentoMaquinaDAO extends GenericDAO<EquipamentoMaquina>{

	List<EquipamentoMaquina> findByMaquina(Maquina maquina);
	
	List<EquipamentoMaquina> findByMaquinaFetchConfigEquipamentoAndReceitaFaca(Maquina maquina);

}
