package br.com.prati.tim.collaboration.gmp.mb.notificacao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.usuario.PapelView;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public class NotificacaoFiltros implements Serializable {

	private static final long	serialVersionUID	= 1L;

	private Date				dataInicial;

	private Date				dataFinal;

	private String				descricao;

	private List<Maquina>		maquinasSelecionadas;

	private List<PapelView>		papeisSelecionados;
	
	private boolean				visualizado;
	
	public NotificacaoFiltros(){
		visualizado = false;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public boolean isVisualizado() {
		return visualizado;
	}

	public void setVisualizado(boolean visualizado) {
		this.visualizado = visualizado;
	}

}
