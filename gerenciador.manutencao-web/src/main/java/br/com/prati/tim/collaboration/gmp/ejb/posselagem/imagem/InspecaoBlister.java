package br.com.prati.tim.collaboration.gmp.ejb.posselagem.imagem;

import java.sql.Timestamp;

public class InspecaoBlister {

	private Integer		idInspecao;

	private String		codigoLote;

	private Timestamp	dataHoraRegistro;

	private String		retorno;

	public Integer getIdInspecao() {
		return idInspecao;
	}

	public void setIdInspecao(Integer idInspecao) {
		this.idInspecao = idInspecao;
	}

	public String getCodigoLote() {
		return codigoLote;
	}

	public void setCodigoLote(String codigoLote) {
		this.codigoLote = codigoLote;
	}

	public String getRetorno() {
		return retorno;
	}

	public void setRetorno(String retorno) {
		this.retorno = retorno;
	}

	public Timestamp getDataHoraRegistro() {
		return dataHoraRegistro;
	}

	public void setDataHoraRegistro(Timestamp dataHoraRegistro) {
		this.dataHoraRegistro = dataHoraRegistro;
	}

}
