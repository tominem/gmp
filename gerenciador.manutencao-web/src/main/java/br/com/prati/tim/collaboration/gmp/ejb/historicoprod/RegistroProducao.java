package br.com.prati.tim.collaboration.gmp.ejb.historicoprod;

import java.io.Serializable;
import java.sql.Timestamp;

import br.prati.tim.collaboration.gp.jpa.enumerator.EStatusMaquina;

public class RegistroProducao implements Serializable {

	private static final long serialVersionUID = 1L;

	private String			identificador;

	private String			acao;

	private Timestamp		dataHora;

	private String			descricao;

	private Integer			cracha;

	private EStatusMaquina	statusMaquina;

	public RegistroProducao (){
		
	}
	
	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getCracha() {
		return cracha;
	}

	public void setCracha(Integer cracha) {
		this.cracha = cracha;
	}

	public EStatusMaquina getStatusMaquina() {
		return statusMaquina;
	}

	public void setStatusMaquina(EStatusMaquina statusMaquina) {
		this.statusMaquina = statusMaquina;
	}

}
