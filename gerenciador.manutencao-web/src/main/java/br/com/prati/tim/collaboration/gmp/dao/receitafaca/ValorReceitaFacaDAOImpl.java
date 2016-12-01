package br.com.prati.tim.collaboration.gmp.dao.receitafaca;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.ReceitaFaca;
import br.prati.tim.collaboration.gp.jpa.ValorReceitaFaca;

public class ValorReceitaFacaDAOImpl extends AbstractJPADAO<ValorReceitaFaca> implements ValorReceitaFacaDAO{

	public ValorReceitaFacaDAOImpl() {
		super(ValorReceitaFaca.class);
	}

	@Override
	public List<ValorReceitaFaca> findByReceitaFaca(ReceitaFaca receitaFaca) {
		return createQueryCriteria(getEntityManager(), getEntityClass())
				.andEquals("receitaFaca", receitaFaca)
				.getResultList();
	}

}
