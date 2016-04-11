package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;
import java.util.List;

public interface CriterioBusca<T extends Serializable> extends Serializable {

	public List<T> buscar();
	
}