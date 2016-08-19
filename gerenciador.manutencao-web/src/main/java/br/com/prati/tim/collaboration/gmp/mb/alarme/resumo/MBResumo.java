package br.com.prati.tim.collaboration.gmp.mb.alarme.resumo;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.notapm.NotaPmMaquinaEJB;
import br.com.prati.tim.collaboration.gmp.ejb.notificacao.NotificacaoEJB;
import br.com.prati.tim.collaboration.gmp.ejb.programacaomaquina.ProgramacaoMaquinaEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.NotaPmMaquina;
import br.prati.tim.collaboration.gp.jpa.Notificacao;
import br.prati.tim.collaboration.gp.jpa.ProgramacaoMaquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.EStatusNotaPm;

@Named("mbResumo")
@ViewScoped
public class MBResumo extends AbstractBaseMB implements Serializable {

	private static final long			serialVersionUID	= 1L;

	@Inject
	private NotaPmMaquinaEJB			ejbNotaPM;

	@Inject
	private NotificacaoEJB				ejbNotificacao;
	
	@Inject
	private ProgramacaoMaquinaEJB		ejbProgramacaoMaquina;
	
	private List<NotaPmMaquina>			notasPm;
	
	private NotaPmMaquina				notaPm;

	private List<Notificacao>			notificacoes;

	private List<ProgramacaoMaquina>	programacoes;

	
	@PostConstruct
	public void init(){
		
		preencherNotasPM();
		preencherNotificacoes();
		preencherProgramacoes();
		
	}
	
	public void fecharNotaPM(){
		try {
			ejbNotaPM.updateStatusNota(notaPm, EStatusNotaPm.ENCERRADA);
			addInfoMessage("Nota PM fechada com sucesso!");
			preencherNotasPM();
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
		}
	}
	
	private void preencherNotasPM(){
		notasPm 		= ejbNotaPM.findByStatus(EStatusNotaPm.ABERTA);
	}
	
	private void preencherNotificacoes(){
		notificacoes	= ejbNotificacao.findByVisualizacao(false);
	}
	
	private void preencherProgramacoes(){
		programacoes	= ejbProgramacaoMaquina.findByServicosPendentes();
	}
	
	
	public List<NotaPmMaquina> getNotasPm() {
		return notasPm;
	}

	public void setNotasPm(List<NotaPmMaquina> notasPm) {
		this.notasPm = notasPm;
	}

	public List<Notificacao> getNotificacoes() {
		return notificacoes;
	}

	public void setNotificacoes(List<Notificacao> notificacoes) {
		this.notificacoes = notificacoes;
	}

	public List<ProgramacaoMaquina> getProgramacoes() {
		return programacoes;
	}

	public void setProgramacoes(List<ProgramacaoMaquina> programacoes) {
		this.programacoes = programacoes;
	}

	public NotaPmMaquina getNotaPm() {
		return notaPm;
	}

	public void setNotaPm(NotaPmMaquina notaPm) {
		this.notaPm = notaPm;
	}

	@Override
	public boolean validate(ComponentSystemEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		// TODO Auto-generated method stub
		return null;
	}

}
