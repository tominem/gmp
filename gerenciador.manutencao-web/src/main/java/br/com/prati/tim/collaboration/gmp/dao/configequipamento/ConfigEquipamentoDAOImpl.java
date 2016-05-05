package br.com.prati.tim.collaboration.gmp.dao.configequipamento;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;

public class ConfigEquipamentoDAOImpl extends AbstractJPADAO<ConfigEquipamento> implements ConfigEquipamentoDAO {

	public ConfigEquipamentoDAOImpl() {
		super(ConfigEquipamento.class);
	}

	@Override
	public List<ConfigEquipamento> findFecthOnFuncaoConfigByEquipamento(Equipamento equipamento) throws Exception{
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
					.innerJoinFetch("funcaoConfig")
					.andEquals("equipamento", equipamento)
					.getResultList();
	}

}
