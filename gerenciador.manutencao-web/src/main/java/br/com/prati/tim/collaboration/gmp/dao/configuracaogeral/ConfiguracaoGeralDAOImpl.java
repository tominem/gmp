package br.com.prati.tim.collaboration.gmp.dao.configuracaogeral;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.ConfiguracaoGeral;

public class ConfiguracaoGeralDAOImpl extends AbstractJPADAO<ConfiguracaoGeral> implements ConfiguracaoGeralDAO {

	public ConfiguracaoGeralDAOImpl() {
		super(ConfiguracaoGeral.class);
	}

}
