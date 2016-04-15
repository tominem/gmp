package br.com.prati.tim.collaboration.gmp.dao.tipocomponente;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

public interface TipoComponenteDAO extends GenericDAO<TipoComponente>{

	List<TipoComponente> findAllOrderByAsc(String string);

}
