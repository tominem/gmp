package br.com.prati.tim.collaboration.gmp.dao.variaveisclp;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.VariaveisClp;

public class VariaveisClpDAOImpl extends AbstractJPADAO<VariaveisClp> implements VariaveisClpDAO{

	public VariaveisClpDAOImpl() {
		super(VariaveisClp.class);
	}

	@Override
	public List<VariaveisClp> findByMaquinaAndEquipamento(Maquina maquina, Equipamento clp) {
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
					.innerJoin("maquina")
					.innerJoin("equipamento")
					
						.andEquals("equipamento", clp)
						.andEquals("maquina", maquina)
						
					.getResultList();
		
	}

}
