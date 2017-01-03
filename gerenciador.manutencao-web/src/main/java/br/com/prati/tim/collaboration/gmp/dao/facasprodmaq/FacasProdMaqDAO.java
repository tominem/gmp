package br.com.prati.tim.collaboration.gmp.dao.facasprodmaq;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.FacasProdMaq;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public interface FacasProdMaqDAO extends GenericDAO<FacasProdMaq> {

	public List<FacasProdMaq> findByMaquinaAndEquipamento(Maquina maquina, Equipamento equipamento);
	
}
