package br.com.prati.tim.collaboration.gmp.ejb.alarme;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.com.prati.tim.collaboration.gmp.dao.alarme.AlarmeDAO;
import br.prati.tim.collaboration.gp.jpa.Alarme;

public class AlarmeDAOImpl extends AbstractJPADAO<Alarme> implements AlarmeDAO{

	public AlarmeDAOImpl() {
		super(Alarme.class);
	}

}
