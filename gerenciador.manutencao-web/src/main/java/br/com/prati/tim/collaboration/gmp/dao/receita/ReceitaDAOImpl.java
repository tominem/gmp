package br.com.prati.tim.collaboration.gmp.dao.receita;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;

public class ReceitaDAOImpl extends AbstractJPADAO<Receita> implements ReceitaDAO{

	public ReceitaDAOImpl() {
		super(Receita.class);
	}

	@Override
	public List<Receita> findReceitasFetchByMaquinaAndEquipamentoAndTipoInspecao(Maquina maquina, Equipamento equipamento, TipoInspecao tipoInspecao) {
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
			.innerJoinFetch("maquina")
			.innerJoinFetch("configEquipamento")
			.innerJoinFetch("configEquipamento.funcaoConfig")
			
			
			.andEquals("maquina", maquina)
			.andEquals("configEquipamento.equipamento", equipamento)
			.andEquals("tipoInspecao", tipoInspecao)
			
			.getResultList();	
	}

	@Override
	public List<Receita> findByMaquinaAndTipoInspecao(Maquina maquina, TipoInspecao tipoInspecao) {
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
				
				.innerJoinFetch("maquina")
				.innerJoinFetch("tipoInspecao")
				.innerJoinFetch("configEquipamento")
				.innerJoinFetch("configEquipamento.funcaoConfig")
				.leftJoinFetch ("valorReceitas")
				.leftJoinFetch("valorReceitas.subproduto")
				
				.andEquals("maquina", maquina)
				.andEquals("tipoInspecao", tipoInspecao)
				
				.getResultList();
	}

}
