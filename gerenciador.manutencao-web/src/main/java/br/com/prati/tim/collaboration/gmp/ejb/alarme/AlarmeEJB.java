package br.com.prati.tim.collaboration.gmp.ejb.alarme;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.Alarme;

public interface AlarmeEJB extends CrudEJB<Alarme>{

	List<Alarme> findByCodigoAlarmeAndTagSistema(Integer codigoAlarme, String tagSistema);

	byte[] getBeforeFileContent();

	void setBeforeFileContent(byte[] beforeFileContent);

}
