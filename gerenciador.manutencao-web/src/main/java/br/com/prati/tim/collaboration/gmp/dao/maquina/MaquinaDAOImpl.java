package br.com.prati.tim.collaboration.gmp.dao.maquina;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public class MaquinaDAOImpl extends AbstractJPADAO<Maquina> implements MaquinaDAO{

	public MaquinaDAOImpl() {
		super(Maquina.class);
	}

}
