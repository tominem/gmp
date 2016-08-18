package br.com.prati.tim.collaboration.gmp.ejb.receita;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.configequipamento.ConfigEquipamentoDAO;
import br.com.prati.tim.collaboration.gmp.dao.equipamentoMaquina.EquipamentoMaquinaDAO;
import br.com.prati.tim.collaboration.gmp.dao.itemconfig.FuncaoConfigDAO;
import br.com.prati.tim.collaboration.gmp.dao.receita.ReceitaDAO;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
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
	private EquipamentoMaquinaDAO equipamentoMaquinaDAO;
	
	@Inject
	private ConfigEquipamentoDAO configEquipamentoDAO;
	
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

	@Override
	public List<EquipamentoMaquina> findEquipamentoMaquinaByMaquina(Maquina maquina) {
		return equipamentoMaquinaDAO.findByMaquina(maquina);
	}

	@Override
	public void save(Receita receita) throws Exception {
		
		if (receita.getIdReceita() == null) {
			receita.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
		}
		
		receitaDAO.update(receita);
	}

	@Override
	public List<ConfigEquipamento> findConfigEquipamentoFetchByEquipamento(Equipamento equipamento) throws Exception{
		return configEquipamentoDAO.findFecthOnFuncaoConfigByEquipamento(equipamento);
	}

	@Override
	public void remove(Receita receita) throws Exception {
		receitaDAO.delete(receita);
	}

	@Override
	public List<Receita> findByMaquinaAndTipoInspecao(Maquina maquina, TipoInspecao tipoInspecao) {
		return receitaDAO.findByMaquinaAndTipoInspecao(maquina, tipoInspecao);
	}
	
}
