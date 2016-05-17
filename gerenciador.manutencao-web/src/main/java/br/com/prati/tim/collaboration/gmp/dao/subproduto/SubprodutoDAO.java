package br.com.prati.tim.collaboration.gmp.dao.subproduto;

import java.util.List;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.prati.tim.collaboration.gp.jpa.Subproduto;

public interface SubprodutoDAO extends GenericDAO<Subproduto>{

	List<Subproduto> findByCodigoSap(List<Integer> codigosSap) throws Exception;

}
