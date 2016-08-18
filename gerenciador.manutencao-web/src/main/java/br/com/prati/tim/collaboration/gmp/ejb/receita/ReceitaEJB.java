package br.com.prati.tim.collaboration.gmp.ejb.receita;

import java.util.List;

import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;


public interface ReceitaEJB {

	List<Receita> findReceitasFetchByMaquinaAndEquipamentoAndTipoInspecao(Maquina maquina, Equipamento equipamento, TipoInspecao tipoInspecao);
	
	List<Receita> findByMaquinaAndTipoInspecao(Maquina maquina, TipoInspecao tipoInspecao);

	List<FuncaoConfig> findAllFuncaoConfig();

	List<EquipamentoMaquina> findEquipamentoMaquinaByMaquina(Maquina maquina);

	void save(Receita r) throws Exception;

	List<ConfigEquipamento> findConfigEquipamentoFetchByEquipamento(Equipamento equipamento) throws Exception;

	void remove(Receita receita) throws Exception;

}
