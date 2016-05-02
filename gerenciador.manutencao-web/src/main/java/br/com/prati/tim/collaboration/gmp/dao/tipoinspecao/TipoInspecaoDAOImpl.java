package br.com.prati.tim.collaboration.gmp.dao.tipoinspecao;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;

public class TipoInspecaoDAOImpl extends AbstractJPADAO<TipoInspecao> implements TipoInspecaoDAO{

	public TipoInspecaoDAOImpl() {
		super(TipoInspecao.class);
	}

}
