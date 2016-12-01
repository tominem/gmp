package br.com.prati.tim.collaboration.gmp.ejb.receitafaca;

import java.util.List;

import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.ReceitaFaca;


public interface ReceitaFacaEJB {

	List<ReceitaFaca> findReceitaFacasFetchByMaquinaAndEquipamentoAndFaca(Maquina maquina, Equipamento equipamento, Faca faca);
	
	void remove(ReceitaFaca receita) throws Exception;
	
	ReceitaFaca save(ReceitaFaca receitaFaca) throws Exception;

	List<ConfigEquipamento> findConfigEquipamentoFetchByEquipamento(Equipamento equipamento) throws Exception;

	List<EquipamentoMaquina> findEquipamentoMaquinaByMaquina(Maquina maquina);

	List<Faca> findAllFacas();

}
