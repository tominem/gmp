package br.com.prati.tim.collaboration.gmp.dao.fabricante;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Fabricante;

public class FabricanteDAOImpl extends AbstractJPADAO<Fabricante> implements FabricanteDAO{

	public FabricanteDAOImpl() {
		super(Fabricante.class);
	}

}
