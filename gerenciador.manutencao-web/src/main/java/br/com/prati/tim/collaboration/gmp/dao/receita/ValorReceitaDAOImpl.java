package br.com.prati.tim.collaboration.gmp.dao.receita;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.ValorReceita;

public class ValorReceitaDAOImpl extends AbstractJPADAO<ValorReceita> implements ValorReceitaDAO{

	public ValorReceitaDAOImpl() {
		super(ValorReceita.class);
	}

	@Override
	public List<ValorReceita> findByReceita(Receita receita) {
		return createQueryCriteria(getEntityManager(), getEntityClass())
				.andEquals("receita", receita)
				.getResultList();
	}

}
