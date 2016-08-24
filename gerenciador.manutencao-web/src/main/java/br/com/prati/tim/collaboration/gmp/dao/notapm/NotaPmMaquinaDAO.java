package br.com.prati.tim.collaboration.gmp.dao.notapm;

import java.util.Date;
import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.NotaPmMaquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.EStatusNotaPm;

public interface NotaPmMaquinaDAO extends GenericDAO<NotaPmMaquina> {

	List<NotaPmMaquina> findByStatus(EStatusNotaPm status);
	
	List<NotaPmMaquina> findByPeriodoAndMaquinas(Date dataInicial, Date dataFinal, List<Maquina> maquinas);
	
}
