package br.com.prati.tim.collaboration.gmp.ejb.receitafaca;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.configequipamento.ConfigEquipamentoDAO;
import br.com.prati.tim.collaboration.gmp.dao.equipamentoMaquina.EquipamentoMaquinaDAO;
import br.com.prati.tim.collaboration.gmp.dao.faca.FacaDAO;
import br.com.prati.tim.collaboration.gmp.dao.receitafaca.ReceitaFacaDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.ReceitaFaca;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class ReceitaFacaEJBImpl extends AbstractCrudEJB<ReceitaFaca> implements ReceitaFacaEJB{

	@Inject
	private ReceitaFacaDAO receitaFacaDAO;
	
	@Inject
	private ConfigEquipamentoDAO configEquipamentoDAO;
	
	@Inject
	private EquipamentoMaquinaDAO equipamentoMaquinaDAO;

	@Inject
	private TimeZone defaultTimeZone;

	@Inject
	private FacaDAO facaDAO;
	
	@Override
	public List<ReceitaFaca> findReceitaFacasFetchByMaquinaAndEquipamentoAndFaca(Maquina maquina, Equipamento equipamento, Faca faca){
		return receitaFacaDAO.findReceitaFacasFetchByMaquinaAndEquipamentoAndFaca(maquina, equipamento, faca);
	}
	
	@Override
	public void remove(ReceitaFaca receitaFaca) throws Exception {
		receitaFacaDAO.delete(receitaFaca);
	}

	@Override
	public ReceitaFaca save(ReceitaFaca receitaFaca) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = receitaFaca.getIdReceitaFaca() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		if (receitaFaca.getIdReceitaFaca() == null) 
		{
			receitaFaca.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
		}
		
		return receitaFacaDAO.update(receitaFaca);
	}

	@Override
	public GenericDAO<ReceitaFaca> getCrudDAO() {
		return receitaFacaDAO;
	}

	@Override
	public List<ConfigEquipamento> findConfigEquipamentoFetchByEquipamento(Equipamento equipamento) throws Exception {
		return configEquipamentoDAO.findFecthOnFuncaoConfigByEquipamento(equipamento);
	}
	
	@Override
	public List<EquipamentoMaquina> findEquipamentoMaquinaByMaquina(Maquina maquina) {
		return equipamentoMaquinaDAO.findByMaquina(maquina);
	}

	@Override
	public List<Faca> findAllFacas() {
		Optional<List<Faca>> optFacas = Optional.ofNullable(facaDAO.findAll());
		optFacas.ifPresent(facas -> Collections.sort(facas, (x1,x2) -> x1.getDescricao().compareTo(x2.getDescricao())));

		return optFacas.orElse(null);
	}
	
}
