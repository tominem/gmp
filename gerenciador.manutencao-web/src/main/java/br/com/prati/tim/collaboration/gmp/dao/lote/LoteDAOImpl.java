package br.com.prati.tim.collaboration.gmp.dao.lote;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Lote;

public class LoteDAOImpl extends AbstractJPADAO<Lote> implements LoteDAO{

	public LoteDAOImpl() {
		super(Lote.class);
	}

}
