package br.com.prati.tim.collaboration.gmp.mb.historico.manutencao;

import java.util.Date;

import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public class HistoricoPesquisaParams {

	private String		notaPm;

	private Maquina		maquina;

	private Equipamento	equipamento;

	private Date		datahoraInicio;
	private Date		datahoraFim;

	private String		descricao;

	public String getNotaPm() {
		return notaPm;
	}

	public void setNotaPm(String notaPm) {
		this.notaPm = notaPm;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public Date getDatahoraInicio() {
		return datahoraInicio;
	}

	public void setDatahoraInicio(Date datahoraInicio) {
		this.datahoraInicio = datahoraInicio;
	}

	public Date getDatahoraFim() {
		return datahoraFim;
	}

	public void setDatahoraFim(Date datahoraFim) {
		this.datahoraFim = datahoraFim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
