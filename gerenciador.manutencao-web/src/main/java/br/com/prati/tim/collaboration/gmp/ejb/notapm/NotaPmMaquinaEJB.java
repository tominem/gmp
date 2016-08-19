package br.com.prati.tim.collaboration.gmp.ejb.notapm;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.NotaPmMaquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.EStatusNotaPm;

public interface NotaPmMaquinaEJB extends CrudEJB<NotaPmMaquina> {

	List<NotaPmMaquina> findByStatus(EStatusNotaPm status);
	
	void updateStatusNota(NotaPmMaquina nota, EStatusNotaPm status) throws Exception ;
}
