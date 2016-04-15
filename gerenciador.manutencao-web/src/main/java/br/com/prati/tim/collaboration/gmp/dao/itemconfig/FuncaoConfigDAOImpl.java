package br.com.prati.tim.collaboration.gmp.dao.itemconfig;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;

public class FuncaoConfigDAOImpl extends AbstractJPADAO<FuncaoConfig> implements FuncaoConfigDAO {
	
	public FuncaoConfigDAOImpl() {
		super(FuncaoConfig.class);
	}

}
