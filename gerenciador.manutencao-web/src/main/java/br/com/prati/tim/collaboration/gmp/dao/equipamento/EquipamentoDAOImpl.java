package br.com.prati.tim.collaboration.gmp.dao.equipamento;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;

public class EquipamentoDAOImpl extends AbstractJPADAO<Equipamento> implements EquipamentoDAO{

	public EquipamentoDAOImpl() {
		super(Equipamento.class);
	}

}
