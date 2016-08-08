package br.com.prati.tim.collaboration.gmp.ejb.valorconfigequip;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.ValorConfigEquip;

public interface IValorConfigEquipEJB extends CrudEJB<ValorConfigEquip>{
	
	ValorConfigEquip findByConfigEquipamento(ConfigEquipamento configEquipamento);
}
