package br.com.prati.tim.collaboration.gmp.dao.subproduto;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Subproduto;

public class SubprodutoDAOImpl extends AbstractJPADAO<Subproduto> implements SubprodutoDAO{

	public SubprodutoDAOImpl() {
		super(Subproduto.class);
	}


}
