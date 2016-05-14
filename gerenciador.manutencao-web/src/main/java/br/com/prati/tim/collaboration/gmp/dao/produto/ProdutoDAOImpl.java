package br.com.prati.tim.collaboration.gmp.dao.produto;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Produto;

public class ProdutoDAOImpl extends AbstractJPADAO<Produto> implements ProdutoDAO{

	public ProdutoDAOImpl() {
		super(Produto.class);
	}

}
