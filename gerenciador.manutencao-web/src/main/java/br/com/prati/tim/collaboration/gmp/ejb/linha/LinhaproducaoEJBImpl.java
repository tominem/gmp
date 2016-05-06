package br.com.prati.tim.collaboration.gmp.ejb.linha;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.linha.LinhaproducaoDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Linhaproducao;

@Stateless
public class LinhaproducaoEJBImpl extends AbstractCrudEJB<Linhaproducao> implements LinhaproducaoEJB{

	@Inject
	private TimeZone defaultTimeZone;
	
	@Inject
	private LinhaproducaoDAO linhaproducaoDAO; 
	
	@Override
	public Linhaproducao save(Linhaproducao entityBean) throws Exception {
		if (entityBean.getIdLinhaproducao() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return linhaproducaoDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Linhaproducao> getCrudDAO() {
		return linhaproducaoDAO;
	}

}
