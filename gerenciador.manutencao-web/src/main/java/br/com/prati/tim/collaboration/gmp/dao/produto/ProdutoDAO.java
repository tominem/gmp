package br.com.prati.tim.collaboration.gmp.dao.produto;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;

public interface ProdutoDAO extends GenericDAO<Produto>{

	List<Produto> findByMaquina(Maquina maquina) throws Exception;
	
	List<ProdutoSubproduto> findProdutoSubproduto(Produto produto) throws Exception;
}
