package br.com.prati.tim.collaboration.gmp.ejb.receita;

import java.util.List;
import java.util.Map;

import br.com.prati.tim.collaboration.gmp.mb.receita.Region;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.ValorReceita;

public interface ConfigReceitaEJB {
	
	List<ValorReceita> findValorReceitaByReceita(Receita receita);

	Receita findReceitaById(Long idReceita);
	
	ValorReceita saveValorReceita(ValorReceita valorReceita) throws Exception;

	List<ValorReceita> retornaValoresReceitas(List<Receita> receitas, Map<Long, Region>	mapRegiao, Subproduto subproduto);
	
	ValorReceita getValorReceitaConverted(ValorReceita valorReceita, Map<Long, Region>	mapRegiao, boolean isToDisplay);
	
	void exportarConfiguracao(List<ProdutoSubproduto> 		produtoSubprodutosSelected, 
			 				  Map<Long, Region> 			mapRegiao,
			 				  List<Receita> 				receitas,
			 				  List<ValorReceita> 			valoresReceita) throws Exception;
	
	void saveValoresReceita(List<ValorReceita> valoresReceita, Map<Long, Region> mapRegiao) throws Exception;
}
