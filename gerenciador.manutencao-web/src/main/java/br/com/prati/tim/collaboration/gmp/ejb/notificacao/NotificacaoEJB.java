package br.com.prati.tim.collaboration.gmp.ejb.notificacao;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.usuario.PapelView;
import br.com.prati.tim.collaboration.gmp.mb.notificacao.NotificacaoFiltros;
import br.prati.tim.collaboration.gp.jpa.Notificacao;
import br.prati.tim.gmp.ws.usuario.Papel;

public interface NotificacaoEJB extends CrudEJB<Notificacao> {

	List<Notificacao> findByVisualizacao(Boolean visualizado);
	
	List<Notificacao> findByFiltros(NotificacaoFiltros notificacaoFiltros);
	
	List<PapelView> getPapeisSistema(String sistema); 
	
}
