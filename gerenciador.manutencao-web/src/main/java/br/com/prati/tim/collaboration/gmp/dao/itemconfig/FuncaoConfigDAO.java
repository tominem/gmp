package br.com.prati.tim.collaboration.gmp.dao.itemconfig;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;

public interface FuncaoConfigDAO extends GenericDAO<FuncaoConfig>{

	List<FuncaoConfig> findByDescricaoAndMenuConfig(String descricao, MenuConfig menuConfig);

	List<FuncaoConfig> findByComandoAndMenuConfig(String comando, MenuConfig menuConfig);

}
