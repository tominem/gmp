package br.com.prati.tim.collaboration.gmp.dao.receitafaca;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.ReceitaFaca;

public interface ReceitaFacaDAO extends GenericDAO<ReceitaFaca>{

	List<ReceitaFaca> findReceitaFacasFetchByMaquinaAndEquipamentoAndFaca(Maquina maquina, Equipamento equipamento, Faca faca);

}
