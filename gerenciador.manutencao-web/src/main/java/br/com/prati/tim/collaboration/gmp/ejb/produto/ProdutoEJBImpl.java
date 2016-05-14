package br.com.prati.tim.collaboration.gmp.ejb.produto;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.produto.ProdutoDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Produto;

@Stateless
public class ProdutoEJBImpl extends AbstractCrudEJB<Produto> implements ProdutoEJB{

	@Inject
	private ProdutoDAO produtoDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public Produto save(Produto entityBean) throws Exception {
		if (entityBean.getIdProduto() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return produtoDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Produto> getCrudDAO() {
		return produtoDAO;
	}

}
