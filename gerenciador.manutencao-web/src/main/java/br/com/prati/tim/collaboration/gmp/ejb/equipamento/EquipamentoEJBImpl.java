package br.com.prati.tim.collaboration.gmp.ejb.equipamento;

import java.util.Calendar;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.equipamento.EquipamentoDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Equipamento;

@Stateless
public class EquipamentoEJBImpl extends AbstractCrudEJB<Equipamento> implements EquipamentoEJB{

	@Inject
	private EquipamentoDAO equipamentoDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public Equipamento save(Equipamento entityBean) {
		
		if (entityBean.getIdEquipamento() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return equipamentoDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Equipamento> getCrudDAO() {
		return equipamentoDAO;
	}

}
