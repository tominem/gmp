package br.com.prati.tim.collaboration.gmp.dao.servico;

import java.util.Date;
import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.LogServico;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public interface LogServicoDAO extends GenericDAO<LogServico> {

	List<LogServico> findByPeriodoMaquinasAndServicos(Date dataInicial, Date dataFinal, List<Maquina> maquinas, List<Long> idServicos );	
	
}
