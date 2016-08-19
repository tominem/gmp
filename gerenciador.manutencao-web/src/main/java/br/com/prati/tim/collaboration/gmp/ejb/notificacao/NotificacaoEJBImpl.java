package br.com.prati.tim.collaboration.gmp.ejb.notificacao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.notificacao.NotificacaoDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Notificacao;

@Stateless
public class NotificacaoEJBImpl extends AbstractCrudEJB<Notificacao> implements NotificacaoEJB{

	@Inject
	private NotificacaoDAO notificacaoDAO;
	
	@Override
	public Notificacao save(Notificacao entityBean) throws Exception {
		entityBean.setDataRegistro(new Date());
		return notificacaoDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Notificacao> getCrudDAO() {
		return notificacaoDAO;
	}

	@Override
	public List<Notificacao> findByVisualizacao(Boolean visualizado) {
		return notificacaoDAO.findByVisualizacao(visualizado);
	}

}
