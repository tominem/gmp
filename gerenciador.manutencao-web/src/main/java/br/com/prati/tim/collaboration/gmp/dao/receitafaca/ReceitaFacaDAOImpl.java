package br.com.prati.tim.collaboration.gmp.dao.receitafaca;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.ReceitaFaca;

public class ReceitaFacaDAOImpl extends AbstractJPADAO<ReceitaFaca> implements ReceitaFacaDAO{

	public ReceitaFacaDAOImpl() {
		super(ReceitaFaca.class);
	}

	@Override
	public List<ReceitaFaca> findReceitaFacasFetchByMaquinaAndEquipamentoAndFaca(Maquina maquina, Equipamento equipamento, Faca faca) {
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
				.innerJoinFetch("maquina")
				.innerJoinFetch("configEquipamento")
				.innerJoinFetch("configEquipamento.equipamento")
				.innerJoinFetch("configEquipamento.funcaoConfig")
				.innerJoinFetch("configEquipamento.funcaoConfig.tipoComponente")
				.innerJoinFetch("configEquipamento.funcaoConfig.menuConfig")
				.innerJoinFetch("faca")
				.leftJoinFetch("valorReceitaFacas")
				
				.andEquals("maquina", maquina)
				.andEquals("configEquipamento.equipamento", equipamento)
				.andEquals("faca", faca)
				.setDistinctTrue()
				
				.getResultList();
	}


}
