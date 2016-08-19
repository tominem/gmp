package br.com.prati.tim.collaboration.gmp.dao.programacaomaquina;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import com.uaihebert.uaicriteria.UaiCriteria;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.ProgramacaoMaquina;

public class ProgramacaoMaquinaDAOImpl extends AbstractJPADAO<ProgramacaoMaquina> implements ProgramacaoMaquinaDAO{

	public ProgramacaoMaquinaDAOImpl() {
		super(ProgramacaoMaquina.class);
	}

	@Override
	public List<ProgramacaoMaquina> findByServicosPendentes(List<Long> idServicos) {
		UaiCriteria<ProgramacaoMaquina> criteria = createQueryCriteria(getEntityManager(), ProgramacaoMaquina.class);
		
		criteria.innerJoinFetch("servico");
		criteria.andAttributeIn("servico.idServico", idServicos);
		criteria.andIsNull("inicioReal");
		criteria.andIsNull("fimReal");
		criteria.orderByAsc("dataHoraInicio");
		
		return criteria.getResultList();
	}

}
