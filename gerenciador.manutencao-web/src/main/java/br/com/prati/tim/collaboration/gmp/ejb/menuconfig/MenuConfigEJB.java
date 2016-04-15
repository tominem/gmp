package br.com.prati.tim.collaboration.gmp.ejb.menuconfig;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;

public interface MenuConfigEJB extends CrudEJB<MenuConfig>{

	List<MenuConfig> findActives();

}
