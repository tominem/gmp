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
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Notificacao;
import br.prati.tim.gmp.ws.usuario.Papel;

@Named("mbNotificacao")
@ViewScoped
public class MBNotificacao extends AbstractBaseMB {

	private static final long	serialVersionUID	= 1L;

	@Inject
	private NotificacaoEJB		ejbNotificacao;

	@Inject
	private MaquinaEJB			ejbMaquina;

	private Notificacao			notificacao;

	private String				tempoExibicaoStr;

	private String				mensagem;

	private List<Maquina>		maquinasSelecionadas;

	private List<PapelView>		papeisSelecionados;

	@PostConstruct
	public void init(){
		setMaquinasSelecionadas(new ArrayList<>());
		setPapeisSelecionados(new ArrayList<>());
		setNotificacao(new Notificacao());
	}
	
	public void enviar(){
		
		if (!isValid()){
			return;
		}
		
		Integer minutosExebicao = getMinutosExibicao();
		
		notificacao.setDuracao(minutosExebicao);
		notificacao.setCrachaCriador(String.valueOf(SessionUtil.getCracha()));
		notificacao.setDescricao(mensagem);
		
		for (Maquina maquina : maquinasSelecionadas) {
			
			for (PapelView papel : papeisSelecionados) {
				
				Notificacao n = getCopyOf(notificacao, papel, maquina);
				
				try {
					ejbNotificacao.save(n);
				} catch (Exception e) {
					e.printStackTrace();
					addErrorMessage(e.getMessage());
					return;
				}
			}
		}
		
		cleanForm();
		
		addInfoMessage("Notificações enviadas com sucesso.");
	}
	
	private boolean isValid(){
		
		if (maquinasSelecionadas.size() == 0 ){
			addErrorMessage("Ao menos uma máquina deve ser selecionada.");
			return false;
		} else if (papeisSelecionados.size() == 0 ){
			addErrorMessage("Ao menos um papel deve ser selecionado.");
			return false;
		} else {
			return true;
		}
			
	}
	
	private Notificacao getCopyOf(Notificacao origem, PapelView papel, Maquina maquina){
		
		Notificacao destino = new Notificacao();
		
		destino.setCrachaCriador(origem.getCrachaCriador());
		destino.setDescricao(origem.getDescricao());
		destino.setDuracao(origem.getDuracao());
		destino.setIdPapelGum(papel.getIdPapel());
		destino.setNomePapel(papel.getNome());
		destino.setMaquina(maquina);
		destino.setVisualizada(false);
		
		return destino;
	}
	
	private Integer getMinutosExibicao(){
		
		String[] 	tempoArray 		= 	tempoExibicaoStr.split(":");
		Integer 	somaMinutos		=	0;
		
		if (tempoArray.length == 2) {
			Integer horas 	= Integer.parseInt(tempoArray[0]);
			Integer minutos = Integer.parseInt(tempoArray[1]);
			
			somaMinutos += horas * 60;
			somaMinutos += minutos;
		}
		
		return somaMinutos;
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}

	public String getTempoExibicaoStr() {
		return tempoExibicaoStr;
	}

	public void setTempoExibicaoStr(String tempoExibicaoStr) {
		this.tempoExibicaoStr = tempoExibicaoStr;
	}
	
	public List<PapelView> getPapeis(){
		return ejbNotificacao.getPapeisSistema("IEM");
	}

	public List<Maquina> getMaquinas() {
		return ejbMaquina.getCrudDAO().findActives();
	}

	public List<Maquina> getMaquinasSelecionadas() {
		return maquinasSelecionadas;
	}

	public void setMaquinasSelecionadas(List<Maquina> maquinasSelecionadas) {
		this.maquinasSelecionadas = maquinasSelecionadas;
	}

	public List<PapelView> getPapeisSelecionados() {
		return papeisSelecionados;
	}

	public void setPapeisSelecionados(List<PapelView> papeisSelecionados) {
		this.papeisSelecionados = papeisSelecionados;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
