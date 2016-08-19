package br.com.prati.tim.collaboration.gmp.ejb.notificacao;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.Notificacao;

public interface NotificacaoEJB extends CrudEJB<Notificacao> {

	List<Notificacao> findByVisualizacao(Boolean visualizado);
}
