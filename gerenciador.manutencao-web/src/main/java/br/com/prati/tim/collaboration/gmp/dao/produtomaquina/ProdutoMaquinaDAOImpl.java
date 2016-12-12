package br.com.prati.tim.collaboration.gmp.dao.produtomaquina;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoMaquina;

public class ProdutoMaquinaDAOImpl extends AbstractJPADAO<ProdutoMaquina> implements ProdutoMaquinaDAO{

	public ProdutoMaquinaDAOImpl() {
		super(ProdutoMaquina.class);
	}

	@Override
	public ProdutoMaquina findByMaquinaAndProduto(Maquina maquina, Produto produto) {
		return createQueryCriteria(getEntityManager(), getEntityClass())
				.andEquals("maquina", maquina)
				.andEquals("produto", produto)
				.getSingleResult();
	}

}
