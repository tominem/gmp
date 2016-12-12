package br.com.prati.tim.collaboration.gmp.dao.produto;

import java.util.List;
import java.util.Optional;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;

public interface ProdutoDAO extends GenericDAO<Produto>{

	List<Produto> findByMaquina(Maquina maquina) throws Exception;
	
	List<ProdutoSubproduto> findProdutoSubproduto(Produto produto) throws Exception;
	
	List<Produto> findByMaquinaFetchProdutoMaquina(Maquina maquina) throws Exception;

	List<Produto> findByMaquinaWithLimit(
			int maxResults,
			Optional<Boolean> statusValue, 
			FilterParam<?>[] filterParams,
			Maquina maquina);

}
