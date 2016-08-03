package br.com.prati.tim.collaboration.gmp.ejb.receita;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.receita.ReceitaDAO;
import br.com.prati.tim.collaboration.gmp.dao.receita.ValorReceitaDAO;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.ValorReceita;

@Stateless
public class ConfigReceitaEJBImpl implements ConfigReceitaEJB {

	@Inject
	private ValorReceitaDAO valorReceitaDAO;
	
	@Inject
	private ReceitaDAO receitaDAO;
	
	@Override
	public List<ValorReceita> findValorReceitaByReceita(Receita receita) {
		return valorReceitaDAO.findByReceita(receita);
	}

	@Override
	public Receita findReceitaById(Long idReceita) {
		return receitaDAO.findById(idReceita);
	}

}
