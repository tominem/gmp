package br.com.prati.tim.collaboration.gmp.dao.receitafaca;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.ReceitaFaca;
import br.prati.tim.collaboration.gp.jpa.ValorReceitaFaca;

public interface ValorReceitaFacaDAO extends GenericDAO<ValorReceitaFaca> {

	List<ValorReceitaFaca> findByReceitaFaca(ReceitaFaca receita);
	
}
