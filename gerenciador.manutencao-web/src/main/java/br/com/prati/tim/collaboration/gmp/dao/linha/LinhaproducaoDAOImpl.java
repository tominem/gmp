package br.com.prati.tim.collaboration.gmp.dao.linha;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Linhaproducao;

public class LinhaproducaoDAOImpl extends AbstractJPADAO<Linhaproducao> implements LinhaproducaoDAO{

	public LinhaproducaoDAOImpl() {
		super(Linhaproducao.class);
	}

}
