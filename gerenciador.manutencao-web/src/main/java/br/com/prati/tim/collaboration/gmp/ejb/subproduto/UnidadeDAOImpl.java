package br.com.prati.tim.collaboration.gmp.ejb.subproduto;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.com.prati.tim.collaboration.gmp.dao.unidade.UnidadeDAO;
import br.prati.tim.collaboration.gp.jpa.Unidade;

public class UnidadeDAOImpl extends AbstractJPADAO<Unidade> implements UnidadeDAO{

	public UnidadeDAOImpl() {
		super(Unidade.class);
	}

}
