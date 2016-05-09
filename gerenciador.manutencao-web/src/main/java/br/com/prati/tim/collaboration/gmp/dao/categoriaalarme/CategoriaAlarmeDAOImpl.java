package br.com.prati.tim.collaboration.gmp.dao.categoriaalarme;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.CategoriaAlarme;

public class CategoriaAlarmeDAOImpl extends AbstractJPADAO<CategoriaAlarme> implements CategoriaAlarmeDAO{

	public CategoriaAlarmeDAOImpl() {
		super(CategoriaAlarme.class);
	}

}
