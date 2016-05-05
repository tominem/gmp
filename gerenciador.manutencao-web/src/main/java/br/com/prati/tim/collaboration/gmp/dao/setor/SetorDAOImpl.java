package br.com.prati.tim.collaboration.gmp.dao.setor;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Setor;

public class SetorDAOImpl extends AbstractJPADAO<Setor> implements SetorDAO{

	public SetorDAOImpl() {
		super(Setor.class);
	}

}
