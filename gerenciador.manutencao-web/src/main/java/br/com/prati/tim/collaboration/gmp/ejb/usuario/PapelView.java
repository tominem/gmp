package br.com.prati.tim.collaboration.gmp.ejb.usuario;

import java.io.Serializable;

public class PapelView implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long	idPapel;

	private String	nome;

	public Long getIdPapel() {
		return idPapel;
	}

	public void setIdPapel(Long idPapel) {
		this.idPapel = idPapel;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPapel == null) ? 0 : idPapel.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PapelView other = (PapelView) obj;
		if (idPapel == null) {
			if (other.idPapel != null)
				return false;
		} else if (!idPapel.equals(other.idPapel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return this.nome;
	}

}
