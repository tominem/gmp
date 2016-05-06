package br.com.prati.tim.collaboration.gmp.dao.sala;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Sala;

public class SalaDAOImpl extends AbstractJPADAO<Sala> implements SalaDAO{

	public SalaDAOImpl() {
		super(Sala.class);
	}

}
