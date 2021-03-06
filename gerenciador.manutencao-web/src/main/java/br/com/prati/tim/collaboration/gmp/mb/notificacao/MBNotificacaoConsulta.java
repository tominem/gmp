package br.com.prati.tim.collaboration.gmp.mb.notificacao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.maquina.MaquinaEJB;
import br.com.prati.tim.collaboration.gmp.ejb.notificacao.NotificacaoEJB;
import br.com.prati.tim.collaboration.gmp.ejb.usuario.PapelView;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Notificacao;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Named("mbNotificacaoConsulta")
@ViewScoped
public class MBNotificacaoConsulta extends AbstractBaseMB {

	private static final long	serialVersionUID	= 1L;

	@Inject
	private NotificacaoEJB		ejbNotificacao;
	
	@Inject
	private MaquinaEJB			ejbMaquina;
	
	private NotificacaoFiltros	filtros;
	
	private List<Maquina>		maquinas;

	private List<Notificacao>	notificacoes;
	
	private Notificacao			notificacao;
	
	@PostConstruct
	public void init() {
		setFiltros		(new NotificacaoFiltros());
		setMaquinas		(ejbMaquina.getCrudDAO().findActives());
		setNotificacoes	(new ArrayList<>());
	}

	public void find() {
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.CONSULTA)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.CONSULTA.getDescricao() + ".");
			return;
		}
		
		notificacoes = ejbNotificacao.findByFiltros(filtros);
		
		if (notificacoes.isEmpty()){
			addInfoMessage("Registros não encontrados de acordo com os filtros informados.");
		}else{
			addInfoMessage("Consulta realizada com sucesso.");
		}
		
	}

	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}
	
	public void atualizaVisualizacao() throws Exception{
		
		notificacao.setVisualizada(true);
		notificacao = ejbNotificacao.save(notificacao);
		
		String visualizado = notificacao.getVisualizada() ? "visualizada." : " não visualizada.";
		
		addInfoMessage("Atualizada notificação para " + visualizado);
	}
	
	public List<PapelView> getPapeis(){
		return ejbNotificacao.getPapeisSistema("IEM");
	}

	public NotificacaoFiltros getFiltros() {
		return filtros;
	}

	public void setFiltros(NotificacaoFiltros filtros) {
		this.filtros = filtros;
	}

	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public List<Maquina> getMaquinas() {
		return maquinas;
	}

	public void setMaquinas(List<Maquina> maquinas) {
		this.maquinas = maquinas;
	}

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}
}
