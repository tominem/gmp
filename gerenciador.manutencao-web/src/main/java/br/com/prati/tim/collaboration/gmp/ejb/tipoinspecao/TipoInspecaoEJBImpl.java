package br.com.prati.tim.collaboration.gmp.ejb.tipoinspecao;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.tipoinspecao.TipoInspecaoDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class TipoInspecaoEJBImpl extends AbstractCrudEJB<TipoInspecao> implements TipoInspecaoEJB{

	@Inject
	private TipoInspecaoDAO tipoInspecaoDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public TipoInspecao save(TipoInspecao entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdTipoInspecao() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		try {
			
			if (entityBean.getIdTipoInspecao() == null) {
				entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
				entityBean.setStatus(Boolean.TRUE);
			}
			
			return tipoInspecaoDAO.update(entityBean);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			throw e;
			
		}
	}

	@Override
	public GenericDAO<TipoInspecao> getCrudDAO() {
		return tipoInspecaoDAO;
	}

}
