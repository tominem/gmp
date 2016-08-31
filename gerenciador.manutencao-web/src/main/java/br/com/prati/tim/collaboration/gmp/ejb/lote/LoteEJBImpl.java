package br.com.prati.tim.collaboration.gmp.ejb.lote;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.lote.LoteDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Lote;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class LoteEJBImpl extends AbstractCrudEJB<Lote> implements LoteEJB{

	@Inject
	private LoteDAO loteDAO;

	@Override
	public Lote save(Lote entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdLote() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		return loteDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Lote> getCrudDAO() {
		return loteDAO;
	}

}
