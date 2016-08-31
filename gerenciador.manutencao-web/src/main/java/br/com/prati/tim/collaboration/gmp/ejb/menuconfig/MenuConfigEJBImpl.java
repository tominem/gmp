package br.com.prati.tim.collaboration.gmp.ejb.menuconfig;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.menuconfig.MenuConfigDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class MenuConfigEJBImpl extends AbstractCrudEJB<MenuConfig> implements MenuConfigEJB{

	@Inject
	private TimeZone defaultTimeZone;

	@Inject
	private MenuConfigDAO menuConfigDAO;
	
	@Override
	public MenuConfig save(MenuConfig entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdMenuConfig() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		if (entityBean.getIdMenuConfig() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return menuConfigDAO.update(entityBean);
	}
	
	@Override
	public GenericDAO<MenuConfig> getCrudDAO() {
		return menuConfigDAO;
	}

	@Override
	public List<MenuConfig> findActives() {
		return menuConfigDAO.findActives();
	}

}
