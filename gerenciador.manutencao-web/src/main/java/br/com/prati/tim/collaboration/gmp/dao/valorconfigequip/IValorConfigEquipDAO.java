package br.com.prati.tim.collaboration.gmp.dao.valorconfigequip;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.ValorConfigEquip;

public interface IValorConfigEquipDAO extends GenericDAO<ValorConfigEquip>{

	ValorConfigEquip findByConfigEquipamento(ConfigEquipamento configEquipamento);
}
