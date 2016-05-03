package br.com.prati.tim.collaboration.gmp.ejb.receita;

import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.itemconfig.FuncaoConfigDAO;
import br.com.prati.tim.collaboration.gmp.dao.receita.ReceitaDAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;

@Stateless
public class ReceitaEJBImpl implements ReceitaEJB{

	@Inject
	private ReceitaDAO receitaDAO;
	
	@Inject
	private FuncaoConfigDAO funcaoConfigDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public List<Receita> findReceitasFetchByMaquinaAndEquipamentoAndTipoInspecao(Maquina maquina, Equipamento equipamento, TipoInspecao tipoInspecao){
		return receitaDAO.findReceitasFetchByMaquinaAndEquipamentoAndTipoInspecao(maquina, equipamento, tipoInspecao);
	}
	
	@Override
	public List<FuncaoConfig> findAllFuncaoConfig() {
		return funcaoConfigDAO.findAll();
	}
	
}
