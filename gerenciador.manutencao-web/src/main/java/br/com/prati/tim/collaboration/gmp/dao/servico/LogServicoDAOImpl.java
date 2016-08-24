package br.com.prati.tim.collaboration.gmp.dao.servico;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uaihebert.uaicriteria.UaiCriteria;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.LogServico;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public class LogServicoDAOImpl extends AbstractJPADAO<LogServico> implements LogServicoDAO {

	public LogServicoDAOImpl() {
		super(LogServico.class);
	}

	@Override
	public List<LogServico> findByPeriodoMaquinasAndServicos(Date dataInicial, Date dataFinal, List<Maquina> maquinas, List<Long> idServicos) {

		UaiCriteria<LogServico> criteria = createQueryCriteria(getEntityManager(), LogServico.class);
		
		criteria.innerJoinFetch("maquina");
		criteria.innerJoinFetch("servico");
		
		if (maquinas != null && maquinas.size() > 0){
			
			List<String> tagMaquinas = new ArrayList<String>();
			
			for (Maquina maquina : maquinas) {
				tagMaquinas.add(maquina.getTag());
			}
			
			criteria.andStringIn("maquina.tag", tagMaquinas);
		}
		
		if (idServicos != null && idServicos.size() > 0){
			criteria.andAttributeIn("servico.idServico", idServicos);
		}
		
		criteria.andBetween("dataHorainicio", dataInicial, dataFinal);
		criteria.orderByDesc("dataHorafim");	
		
		return criteria.getResultList();
	}

}
