package br.com.prati.tim.collaboration.gmp.ejb.valorconfigequip;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.valorconfigequip.IValorConfigEquipDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.ValorConfigEquip;

@Stateless
public class ValorConfigEquipEJB extends AbstractCrudEJB<ValorConfigEquip> implements IValorConfigEquipEJB {

	@Inject
	private IValorConfigEquipDAO valorConfigEquipDAO;

	@Override
	public ValorConfigEquip save(ValorConfigEquip entityBean) throws Exception {
		
		return valorConfigEquipDAO.update(entityBean);
	}

	@Override
	public GenericDAO<ValorConfigEquip> getCrudDAO() {
		return valorConfigEquipDAO;
	}
	
	@Override
	public ValorConfigEquip findByConfigEquipamento(ConfigEquipamento configEquipamento){
		return valorConfigEquipDAO.findByConfigEquipamento(configEquipamento);
				
	}

}
