package br.com.prati.tim.collaboration.gmp.ejb.sala;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.Sala;
import br.prati.tim.collaboration.gp.jpa.Setor;

public interface SalaEJB extends CrudEJB<Sala>{

	List<Setor> findActivesSetores();

}
