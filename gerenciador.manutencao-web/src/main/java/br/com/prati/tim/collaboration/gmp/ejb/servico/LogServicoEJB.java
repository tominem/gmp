package br.com.prati.tim.collaboration.gmp.ejb.servico;

import java.util.Date;
import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.LogServico;
import br.prati.tim.collaboration.gp.jpa.Maquina;

public interface LogServicoEJB extends CrudEJB<LogServico>{

	List<LogServico> findByPeriodoMaquinasAndServicos(Date dataInicial, Date dataFinal, List<Maquina> maquinas );	
	
}
