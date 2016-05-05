package br.com.prati.tim.collaboration.gmp.dao.configequipamento;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;

public interface ConfigEquipamentoDAO extends GenericDAO<ConfigEquipamento>{

	List<ConfigEquipamento> findFecthOnFuncaoConfigByEquipamento(Equipamento equipamento) throws Exception;

}
