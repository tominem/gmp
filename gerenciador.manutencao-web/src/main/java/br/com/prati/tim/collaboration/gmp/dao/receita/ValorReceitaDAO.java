package br.com.prati.tim.collaboration.gmp.dao.receita;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.ValorReceita;

public interface ValorReceitaDAO extends GenericDAO<ValorReceita> {

	List<ValorReceita> findByReceita(Receita receita);
	
}
