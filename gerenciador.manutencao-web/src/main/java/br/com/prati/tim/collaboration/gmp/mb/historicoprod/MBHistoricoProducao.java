package br.com.prati.tim.collaboration.gmp.mb.historicoprod;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.ejb.historicoprod.HistoricoProducaoEJB;
import br.com.prati.tim.collaboration.gmp.ejb.historicoprod.RegistroProducao;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.Lote;
import br.prati.tim.collaboration.gp.jpa.Maquina;

@Named("mbHistoricoProducao")
@ViewScoped
public class MBHistoricoProducao extends AbstractBaseMB {

	private static final long		serialVersionUID	= 1L;

	@Inject
	private HistoricoProducaoEJB	ejbHistorico;

	private Lote					lote;

	private Maquina					maquina;

	private Date					dataInicial;

	private Date					dataFinal;

	private List<RegistroProducao>	historico;

	@PostConstruct
	public void init() {
		
		

	}

	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public List<RegistroProducao> getHistorico() {
		return historico;
	}

	public void setHistorico(List<RegistroProducao> historico) {
		this.historico = historico;
	}

	public Lote getLote() {
		return lote;
	}

	public void setLote(Lote lote) {
		this.lote = lote;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

}