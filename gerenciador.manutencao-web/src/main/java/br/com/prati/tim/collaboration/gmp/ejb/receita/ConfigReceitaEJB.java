package br.com.prati.tim.collaboration.gmp.ejb.receita;

import java.util.List;

import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.ValorReceita;

public interface ConfigReceitaEJB {
	
	List<ValorReceita> findValorReceitaByReceita(Receita receita);

	Receita findReceitaById(Long idReceita);

}
