package br.com.prati.tim.collaboration.gmp.dao.faca;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Faca;

public class FacaDAOImpl extends AbstractJPADAO<Faca> implements FacaDAO{

	public FacaDAOImpl() {
		super(Faca.class);
	}

}
