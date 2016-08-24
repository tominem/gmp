package br.com.prati.tim.collaboration.gmp.dao.notapm;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.uaihebert.uaicriteria.UaiCriteria;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.NotaPmMaquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.EStatusNotaPm;

public class NotaPmMaquinaDAOImpl extends AbstractJPADAO<NotaPmMaquina> implements NotaPmMaquinaDAO{

	public NotaPmMaquinaDAOImpl() {
		super(NotaPmMaquina.class);
	}

	@Override
	public List<NotaPmMaquina> findByStatus(EStatusNotaPm status) {
		UaiCriteria<NotaPmMaquina> criteria = createQueryCriteria(getEntityManager(), NotaPmMaquina.class);
		
		criteria.andEquals("status", status);
		criteria.orderByAsc("dataRegistro");
		
		return criteria.getResultList();
	}

	@Override
	public List<NotaPmMaquina> findByPeriodoAndMaquinas(Date dataInicial, Date dataFinal, List<Maquina> maquinas) {
		
		UaiCriteria<NotaPmMaquina> criteria = createQueryCriteria(getEntityManager(), NotaPmMaquina.class);
		
		List<String> tagMaquinas = new ArrayList<String>();
		
		for(Maquina maquina: maquinas ){
			tagMaquinas.add(maquina.getTag());
		}
		
		criteria.innerJoinFetch("maquina");
		criteria.andBetween("dataRegistro", dataInicial, dataFinal);
		
		if (tagMaquinas.size() > 0){
			criteria.andStringIn("maquina.tag", tagMaquinas);
		}
		
		return criteria.getResultList();
	}

}
