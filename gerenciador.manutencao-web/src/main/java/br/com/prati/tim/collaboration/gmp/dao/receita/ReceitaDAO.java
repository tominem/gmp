package br.com.prati.tim.collaboration.gmp.dao.receita;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;

public interface ReceitaDAO extends GenericDAO<Receita>{

	List<Receita> findReceitasFetchByMaquinaAndEquipamentoAndTipoInspecao(Maquina maquina, Equipamento equipamento, TipoInspecao tipoInspecao);

}
