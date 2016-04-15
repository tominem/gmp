package br.com.prati.tim.collaboration.gmp.dao.menuconfig;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;

public class MenuConfigDAOImpl extends AbstractJPADAO<MenuConfig> implements MenuConfigDAO{

	public MenuConfigDAOImpl() {
		super(MenuConfig.class);
	}

}
