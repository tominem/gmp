package br.com.prati.tim.collaboration.gmp.dao.notificacao;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.mb.notificacao.NotificacaoFiltros;
import br.prati.tim.collaboration.gp.jpa.Notificacao;

public interface NotificacaoDAO extends GenericDAO<Notificacao> {

	List<Notificacao> findByVisualizacao(Boolean visualizado);
	
	List<Notificacao> findByFiltros(NotificacaoFiltros notificacaoFiltros);
	
}
