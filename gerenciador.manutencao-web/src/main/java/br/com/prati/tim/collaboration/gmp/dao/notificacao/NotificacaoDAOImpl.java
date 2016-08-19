package br.com.prati.tim.collaboration.gmp.dao.notificacao;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import com.uaihebert.uaicriteria.UaiCriteria;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Notificacao;

public class NotificacaoDAOImpl extends AbstractJPADAO<Notificacao> implements NotificacaoDAO{

	public NotificacaoDAOImpl() {
		super(Notificacao.class);
	}

	@Override
	public List<Notificacao> findByVisualizacao(Boolean visualizado) {
		UaiCriteria<Notificacao> criteria = createQueryCriteria(getEntityManager(), Notificacao.class);
		
		criteria.andEquals("visualizada", visualizado);
		criteria.orderByAsc("dataRegistro");
		
		return criteria.getResultList();
	}

}
