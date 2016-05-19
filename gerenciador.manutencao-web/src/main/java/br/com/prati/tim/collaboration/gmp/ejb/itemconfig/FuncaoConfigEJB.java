package br.com.prati.tim.collaboration.gmp.ejb.itemconfig;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

public interface FuncaoConfigEJB extends CrudEJB<FuncaoConfig> {

	List<MenuConfig> findAllMenuActives();

	List<TipoComponente> findAllTipoComponentesActives();

	List<FuncaoConfig> findByDescricaoAndMenuConfig(String descricao, MenuConfig menuConfig);

	List<FuncaoConfig> findByComandoAndMenuConfig(String comando, MenuConfig menuConfig);

}
