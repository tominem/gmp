package br.com.prati.tim.collaboration.gmp.ejb.receitafaca;

import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.prati.tim.collaboration.gmp.mb.receita.Region;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;
import br.prati.tim.collaboration.gp.jpa.ReceitaFaca;
import br.prati.tim.collaboration.gp.jpa.ValorReceitaFaca;

public interface ConfigReceitaFacaEJB {
	
	List<ValorReceitaFaca> findValorReceitaByReceita(ReceitaFaca receita);

	ReceitaFaca findReceitaById(Long idReceitaFaca);
	
	ValorReceitaFaca saveValorReceitaFaca(ValorReceitaFaca valorReceitaFaca) throws Exception;

	List<ValorReceitaFaca> retornaValoresReceitasFacas(List<ReceitaFaca> receitaFacas, Map<Long, Region> mapRegiao, Faca faca);
	
	ValorReceitaFaca getValorReceitaConverted(ValorReceitaFaca valorReceitaFaca, Map<Long, Region>	mapRegiao, boolean isToDisplay);
	
	void exportarConfiguracao(List<ProdutoSubproduto> 		produtoSubprodutosSelected, 
			 				  Map<Long, Region> 			mapRegiao,
			 				  List<ReceitaFaca> 			receitaFacas,
			 				  List<ValorReceitaFaca> 		valoresReceitaFaca) throws Exception;
	
	void saveValoresReceitaFaca(List<ValorReceitaFaca> valoresReceita, Map<Long, Region> mapRegiao) throws Exception;
	
	List<Equipamento> findEquipamentosByMaquina(Maquina maquina);

	List<Faca> findAllFacas();
	
	Set<Faca> findFacasByMaquinaAndEquipamento(Maquina maquina, Equipamento equipamento);
}
