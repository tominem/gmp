package br.com.prati.tim.collaboration.gmp.dao.equipamentoMaquina;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public class EquipamentoMaquinaDAOImpl extends AbstractJPADAO<EquipamentoMaquina> implements EquipamentoMaquinaDAO{

	public EquipamentoMaquinaDAOImpl() {
		super(EquipamentoMaquina.class);
	}

	@Override
	public List<EquipamentoMaquina> findByMaquina(Maquina maquina) {
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
					.leftJoinFetch("maquina")
					.leftJoinFetch("tipoInspecao")
					.andEquals("maquina", maquina)
					.getResultList();
	}

}
