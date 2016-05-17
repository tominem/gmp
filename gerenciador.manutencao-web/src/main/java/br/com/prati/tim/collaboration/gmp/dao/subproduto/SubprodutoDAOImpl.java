package br.com.prati.tim.collaboration.gmp.dao.subproduto;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Subproduto;

public class SubprodutoDAOImpl extends AbstractJPADAO<Subproduto> implements SubprodutoDAO{

	public SubprodutoDAOImpl() {
		super(Subproduto.class);
	}

	@Override
	public List<Subproduto> findByCodigoSap(List<Integer> codigosSap) throws Exception{
		
		return createQueryCriteria(getEntityManager(), getEntityClass())
				.andAttributeIn("codigoSap", codigosSap)
				.getResultList();
		
	}


}
