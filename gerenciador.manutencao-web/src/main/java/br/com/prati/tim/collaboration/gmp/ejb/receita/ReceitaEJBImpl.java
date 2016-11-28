package br.com.prati.tim.collaboration.gmp.ejb.receita;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.configequipamento.ConfigEquipamentoDAO;
import br.com.prati.tim.collaboration.gmp.dao.equipamentoMaquina.EquipamentoMaquinaDAO;
import br.com.prati.tim.collaboration.gmp.dao.itemconfig.FuncaoConfigDAO;
import br.com.prati.tim.collaboration.gmp.dao.receita.ReceitaDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class ReceitaEJBImpl extends AbstractCrudEJB<Receita> implements ReceitaEJB{

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

	@Override
	public Receita save(Receita entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdReceita() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		if (entityBean.getIdReceita() == null) 
		{
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
		}
		
		return receitaDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Receita> getCrudDAO() {
		return receitaDAO;
	}
	
}
