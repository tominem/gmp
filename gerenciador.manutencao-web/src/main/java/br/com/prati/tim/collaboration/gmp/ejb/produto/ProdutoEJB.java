package br.com.prati.tim.collaboration.gmp.ejb.produto;

import java.util.List;
import java.util.Optional;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoMateriais;

public interface ProdutoEJB extends CrudEJB<Produto>{

	List<ProdutoSubproduto> parseToProdutoSubprodutos(Produto produto, List<OrdemProducaoMateriais> materiais) throws Exception;
	
	List<Produto> findByMaquina(Maquina maquina) throws Exception;
	
	List<ProdutoSubproduto> findProdutoSubproduto(Produto produto) throws Exception;

	List<Produto> findByMaquinaWithLimit(int maxResults, Optional<Boolean> optStatus, FilterParam<?>[] filterParams, Maquina maquina);

}
