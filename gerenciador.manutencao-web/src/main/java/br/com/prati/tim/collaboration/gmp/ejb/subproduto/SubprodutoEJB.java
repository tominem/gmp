package br.com.prati.tim.collaboration.gmp.ejb.subproduto;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.Unidade;

public interface SubprodutoEJB extends CrudEJB<Subproduto>{

	List<Unidade> findUnidades();

}
