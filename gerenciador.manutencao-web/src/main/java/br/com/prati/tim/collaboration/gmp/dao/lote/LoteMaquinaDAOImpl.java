package br.com.prati.tim.collaboration.gmp.dao.lote;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.LoteMaquina;

public class LoteMaquinaDAOImpl extends AbstractJPADAO<LoteMaquina> implements LoteMaquinaDAO{

	public LoteMaquinaDAOImpl() {
		super(LoteMaquina.class);
	}

}
