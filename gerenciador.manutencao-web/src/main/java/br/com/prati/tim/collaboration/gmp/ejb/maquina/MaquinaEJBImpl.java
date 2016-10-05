package br.com.prati.tim.collaboration.gmp.ejb.maquina;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.equipamentoMaquina.EquipamentoMaquinaDAO;
import br.com.prati.tim.collaboration.gmp.dao.maquina.MaquinaDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class MaquinaEJBImpl extends AbstractCrudEJB<Maquina> implements MaquinaEJB{

	@Inject
	private TimeZone defaultTimeZone;
	
	@Inject
	private MaquinaDAO maquinaDAO;
	
	@Inject
	private EquipamentoMaquinaDAO equipamentoMaquinaDAO;
	
	@Override
	public Maquina save(Maquina entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdMaquina() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		if (entityBean.getIdMaquina() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return maquinaDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Maquina> getCrudDAO() {
		return maquinaDAO;
	}

	@Override
	public List<EquipamentoMaquina> findEquipamentoMaquinaByMaquina(Maquina maquina) {
		return equipamentoMaquinaDAO.findByMaquina(maquina);
	}

	@Override
	public GenericDAO<EquipamentoMaquina> getEquipamentoMaquinaDAO() {
		return equipamentoMaquinaDAO;
	}

}
