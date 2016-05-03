package br.com.prati.tim.collaboration.gmp.dao.receita;

import javax.ejb.Stateless;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Receita;

@Stateless
public class ReceitaDAOImpl extends AbstractJPADAO<Receita> implements ReceitaDAO{

	public ReceitaDAOImpl() {
		super(Receita.class);
	}

}
