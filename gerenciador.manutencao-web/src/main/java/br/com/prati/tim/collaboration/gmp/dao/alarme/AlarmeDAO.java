package br.com.prati.tim.collaboration.gmp.dao.alarme;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Alarme;

public interface AlarmeDAO extends GenericDAO<Alarme>{

	List<Alarme> findByCodigoAlarmeAndTagSistema(Integer codigoAlarme,	String tagSistema);

}
