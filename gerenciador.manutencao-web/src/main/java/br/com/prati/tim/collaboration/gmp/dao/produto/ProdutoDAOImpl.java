package br.com.prati.tim.collaboration.gmp.dao.produto;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.ArrayList;
import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoMaquina;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;

public class ProdutoDAOImpl extends AbstractJPADAO<Produto> implements ProdutoDAO{

	public ProdutoDAOImpl() {
		super(Produto.class);
	}

	@Override
	public List<Produto> findByMaquina(Maquina maquina) throws Exception {
		List<ProdutoMaquina> resultList = createQueryCriteria(getEntityManager(), ProdutoMaquina.class)
				.andEquals("maquina", maquina)
				.innerJoinFetch("produto")
				.innerJoinFetch("maquina")
				.getResultList();
		
		List<Produto> produtos = new ArrayList<Produto>();
		
		for (ProdutoMaquina produtoMaquina : resultList) {
			produtos.add(produtoMaquina.getProduto());
		}
		
		return produtos;
	}

	@Override
	public List<ProdutoSubproduto> findProdutoSubproduto(Produto produto) throws Exception {
		
		List<ProdutoSubproduto> resultList = createQueryCriteria(getEntityManager(), ProdutoSubproduto.class)
				.andEquals("produto", produto)
				.innerJoinFetch("produto")
				.innerJoinFetch("subproduto")
				.getResultList();
		
		
		return resultList;
	}
	
	

}
