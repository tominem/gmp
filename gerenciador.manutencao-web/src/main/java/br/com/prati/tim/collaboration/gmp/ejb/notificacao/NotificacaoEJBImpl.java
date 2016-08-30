package br.com.prati.tim.collaboration.gmp.ejb.notificacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.configuracaogeral.ConfiguracaoGeralEJB;
import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.notificacao.NotificacaoDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.usuario.PapelView;
import br.com.prati.tim.collaboration.gmp.mb.notificacao.NotificacaoFiltros;
import br.com.prati.tim.collaboration.gmp.ws.DynamicServiceLocator;
import br.prati.tim.collaboration.gp.jpa.ConfiguracaoGeral;
import br.prati.tim.collaboration.gp.jpa.Notificacao;
import br.prati.tim.gmp.ws.usuario.Papel;
import br.prati.tim.gmp.ws.usuario.UsuarioService;
import br.prati.tim.gmp.ws.usuario.UsuarioWS;

@Stateless
public class NotificacaoEJBImpl extends AbstractCrudEJB<Notificacao> implements NotificacaoEJB {

	@Inject
	private NotificacaoDAO			notificacaoDAO;

	@Inject
	private ConfiguracaoGeralEJB	ejbConfiguracao;

	@Override
	public Notificacao save(Notificacao entityBean) throws Exception {

		if (entityBean.getIdNotificacao() != null && entityBean.getIdNotificacao() > 0) {
			return notificacaoDAO.update(entityBean);
		} else {
			entityBean.setDataRegistro(new Date());
			return notificacaoDAO.persist(entityBean);
		}
	}

	@Override
	public GenericDAO<Notificacao> getCrudDAO() {
		return notificacaoDAO;
	}

	@Override
	public List<Notificacao> findByVisualizacao(Boolean visualizado) {
		return notificacaoDAO.findByVisualizacao(visualizado);
	}

	@Override
	public List<PapelView> getPapeisSistema(String sistema) {

		try {
			UsuarioWS 			usuarioWS 		= getUsuarioService();
			List<Papel> 		papeisSistema 	= usuarioWS.retornaPapeisPorSistema(sistema);
			List<PapelView> 	papeisView 		= new ArrayList<>();
			
			for (Papel papel : papeisSistema) {
				PapelView view = new PapelView();
				view.setIdPapel(papel.getIdPapel());
				view.setNome(papel.getNome());
				papeisView.add(view);
			}
			
			return papeisView;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private UsuarioWS getUsuarioService() throws Exception {

		ConfiguracaoGeral ipGUM = ejbConfiguracao.getByChave("ip_ws_usuarios");

		String ipServidorGUM = ipGUM == null || ipGUM.getValor().isEmpty() ? "localhost:8080" : ipGUM.getValor();

		UsuarioWS usuarioWS = new DynamicServiceLocator(ipServidorGUM).getPort(UsuarioWS.class, UsuarioService.class);
		return usuarioWS;
	}

	@Override
	public List<Notificacao> findByFiltros(NotificacaoFiltros notificacaoFiltros) {
		return notificacaoDAO.findByFiltros(notificacaoFiltros);
				
	}

}
