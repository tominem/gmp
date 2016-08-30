package br.com.prati.tim.collaboration.gmp.dao.notificacao;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.ArrayList;
import java.util.List;

import com.uaihebert.uaicriteria.UaiCriteria;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.com.prati.tim.collaboration.gmp.ejb.usuario.PapelView;
import br.com.prati.tim.collaboration.gmp.mb.notificacao.NotificacaoFiltros;
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

	@Override
	public List<Notificacao> findByFiltros(NotificacaoFiltros notificacaoFiltros) {
		
		UaiCriteria<Notificacao> criteria = createQueryCriteria(getEntityManager(), Notificacao.class);
		
		criteria.andEquals("visualizada", 	notificacaoFiltros.isVisualizado());
		criteria.andBetween("dataRegistro", notificacaoFiltros.getDataInicial(), notificacaoFiltros.getDataFinal());
		
		if (notificacaoFiltros.getDescricao()!= null && !notificacaoFiltros.getDescricao().isEmpty()){
			criteria.andStringLike(true, "descricao", "%"+notificacaoFiltros.getDescricao().toLowerCase()+"%");
		}
		
		if (notificacaoFiltros.getMaquinasSelecionadas() != null && notificacaoFiltros.getMaquinasSelecionadas().size() > 0){
			criteria.andAttributeIn("maquina", notificacaoFiltros.getMaquinasSelecionadas());
		}
		
		if (notificacaoFiltros.getPapeisSelecionados() != null && notificacaoFiltros.getPapeisSelecionados().size() > 0){
			
			List<Long>  idPapeis = new ArrayList<Long>();
			
			for (PapelView papel : notificacaoFiltros.getPapeisSelecionados()) {
				idPapeis.add(papel.getIdPapel());
			}
			
			criteria.andAttributeIn("idPapelGum", idPapeis);
		}
		
		criteria.orderByDesc("dataRegistro");
		
		return criteria.getResultList();
	}

}
