package br.com.prati.tim.collaboration.gmp.ejb.tipocomunicaco;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.tipocomunicaco.TipoComunicacaoDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.TipoComunicacao;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class TipoComunicacaoEJBImpl extends AbstractCrudEJB<TipoComunicacao> implements TipoComunicacaoEJB{

	@Inject
	private TipoComunicacaoDAO tipoComunicacaoDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public TipoComunicacao save(TipoComunicacao entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdTipoComunicacao() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		try {
			
			if (entityBean.getIdTipoComunicacao() == null) {
				entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
				entityBean.setStatus(Boolean.TRUE);
			}
			
			return tipoComunicacaoDAO.update(entityBean);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw e;
			
		}
	}

	@Override
	public GenericDAO<TipoComunicacao> getCrudDAO() {
		return tipoComunicacaoDAO;
	}

}
