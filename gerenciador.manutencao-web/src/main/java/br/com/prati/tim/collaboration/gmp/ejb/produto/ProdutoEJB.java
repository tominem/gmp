package br.com.prati.tim.collaboration.gmp.ejb.produto;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoMateriais;

public interface ProdutoEJB extends CrudEJB<Produto>{

	List<ProdutoSubproduto> parseToProdutoSubprodutos(Produto produto, List<OrdemProducaoMateriais> materiais) throws Exception;

}
