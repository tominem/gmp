package br.com.prati.tim.collaboration.gmp.ejb.programacaomaquina;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.ProgramacaoMaquina;

public interface ProgramacaoMaquinaEJB extends CrudEJB<ProgramacaoMaquina> {

	List<ProgramacaoMaquina> findByServicosPendentes();
	
}
