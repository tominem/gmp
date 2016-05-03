package br.com.prati.tim.collaboration.gmp.ejb.receita;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.receita.ReceitaDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Receita;

@Stateless
public class ReceitaEJBImpl extends AbstractCrudEJB<Receita> implements ReceitaEJB{

	@Inject
	private ReceitaDAO receitaDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	
	@Override
	public Receita save(Receita entityBean) throws Exception {
		if (entityBean.getIdReceita() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
		}
		
		return receitaDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Receita> getCrudDAO() {
		return receitaDAO;
	}

}
