package br.com.prati.tim.collaboration.gmp.dao.produtomaquina;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoMaquina;

public interface ProdutoMaquinaDAO extends GenericDAO<ProdutoMaquina>{

	ProdutoMaquina findByMaquinaAndProduto(Maquina maquina, Produto produto);

}
