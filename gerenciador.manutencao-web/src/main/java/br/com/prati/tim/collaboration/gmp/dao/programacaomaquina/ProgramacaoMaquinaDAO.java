package br.com.prati.tim.collaboration.gmp.dao.programacaomaquina;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.ProgramacaoMaquina;
import br.prati.tim.collaboration.gp.jpa.Servico;

public interface ProgramacaoMaquinaDAO extends GenericDAO<ProgramacaoMaquina> {

	List<ProgramacaoMaquina> findByServicosPendentes(List<Long> idServicos);
	
}
