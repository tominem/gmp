package br.com.prati.tim.collaboration.gmp.ejb.subproduto;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.subproduto.SubprodutoDAO;
import br.com.prati.tim.collaboration.gmp.dao.unidade.UnidadeDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.Unidade;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class SubprodutoEJBImpl extends AbstractCrudEJB<Subproduto> implements SubprodutoEJB{

	@Inject
	private SubprodutoDAO subprodutoDAO;
	
	@Inject
	private UnidadeDAO unidadeDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public Subproduto save(Subproduto entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdSubproduto() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		if (entityBean.getIdSubproduto() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return subprodutoDAO.update(entityBean);
	}
	
	@Override
	public GenericDAO<Subproduto> getCrudDAO() {
		return subprodutoDAO;
	}

	@Override
	public List<Unidade> findUnidades() {
		return unidadeDAO.findActives();
	}


}
