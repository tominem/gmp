package br.com.prati.tim.collaboration.gmp.dao.facasprodmaq;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.FacasProdMaq;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public class FacasProdMaqDAOImpl extends AbstractJPADAO<FacasProdMaq> implements FacasProdMaqDAO{

	public FacasProdMaqDAOImpl() {
		super(FacasProdMaq.class);
	}

	@Override
	public List<FacasProdMaq> findByMaquinaAndEquipamento(Maquina maquina, Equipamento equipamento) {
		return createQueryCriteria(getEntityManager(), getEntityClass())
				.setDistinctTrue()
				.innerJoinFetch("faca")
				.innerJoinFetch("produtoMaquina")
				.innerJoinFetch("produtoMaquina.maquina")
				.innerJoinFetch("produtoMaquina.maquina.equipamentoMaquinas")
				
				.andEquals("produtoMaquina.maquina",                                 maquina)
				.andEquals("produtoMaquina.maquina.equipamentoMaquinas.equipamento", equipamento)
				
				.orderByAsc("faca.descricao")
				
				.getResultList();
	}

}
