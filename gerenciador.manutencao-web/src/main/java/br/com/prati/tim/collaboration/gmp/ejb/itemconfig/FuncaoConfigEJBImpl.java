package br.com.prati.tim.collaboration.gmp.ejb.itemconfig;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.itemconfig.FuncaoConfigDAO;
import br.com.prati.tim.collaboration.gmp.dao.menuconfig.MenuConfigDAO;
import br.com.prati.tim.collaboration.gmp.dao.tipocomponente.TipoComponenteDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.MenuConfig;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

@Stateless
public class FuncaoConfigEJBImpl extends AbstractCrudEJB<FuncaoConfig> implements FuncaoConfigEJB{

	@Inject
	private FuncaoConfigDAO funcaoConfigDAO;
	
	@Inject
	private MenuConfigDAO menuConfigDAO;
	
	@Inject
	private TipoComponenteDAO tipoComponenteDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public FuncaoConfig save(FuncaoConfig entityBean) {
		
		if (entityBean.getIdFuncaoConfig() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
		}
		
		return funcaoConfigDAO.update(entityBean);
	}

	@Override
	public GenericDAO<FuncaoConfig> getCrudDAO() {
		return funcaoConfigDAO;
	}

	@Override
	public List<MenuConfig> findAllMenus() {
		return menuConfigDAO.findAll();
	}

	@Override
	public List<TipoComponente> findAllTipoComponentes() {
		return tipoComponenteDAO.findAllOrderByAsc("descricao");
	}

}
