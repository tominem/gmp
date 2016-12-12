package br.com.prati.tim.collaboration.gmp.ejb.faca;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoMaquina;

public interface FacaEJB extends CrudEJB<Faca>{
	
	ProdutoMaquina findProdutoMaquinaByProdutoAndMaquina(Maquina maquina, Produto produto);

}
