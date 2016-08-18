package br.com.prati.tim.collaboration.gmp.ejb.receita;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.converters.ConverterComp;
import br.com.prati.tim.collaboration.gmp.converters.DoubleValueToHexConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorComboBoxConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorFtpFieldConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorHexFiedConverter;
import br.com.prati.tim.collaboration.gmp.dao.receita.ReceitaDAO;
import br.com.prati.tim.collaboration.gmp.dao.receita.ValorReceitaDAO;
import br.com.prati.tim.collaboration.gmp.mb.receita.Region;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;
import br.prati.tim.collaboration.gp.jpa.ValorReceita;
import br.prati.tim.collaboration.gp.jpa.ValoresFuncao;
import br.prati.tim.collaboration.gp.jpa.TipoComponente.ETipoComponente;
import br.prati.tim.collaboration.gp.jpa.enumerator.EComponentConverter;

@Stateless
public class ConfigReceitaEJBImpl implements ConfigReceitaEJB {
	
	@Inject
	private ValorReceitaDAO	valorReceitaDAO;

	
	@Inject
	private ReceitaDAO		receitaDAO;

	
	@Override
	public List<ValorReceita> findValorReceitaByReceita(Receita receita) {
		return valorReceitaDAO.findByReceita(receita);
	}

	
	@Override
	public Receita findReceitaById(Long idReceita) {
		return receitaDAO.findById(idReceita);
	}

	
	@Override
	public ValorReceita saveValorReceita(ValorReceita valorReceita) throws Exception {
		if (valorReceita.getIdValorReceita() == null){
			return valorReceitaDAO.persist(valorReceita) ;
		}else{
			return valorReceitaDAO.update(valorReceita) ;
		}
	}
	
	
	@Override
	public List<ValorReceita> retornaValoresReceitas(List<Receita> receitas, Map<Long, Region>	mapRegiao, Subproduto subproduto){
		
		List<ValorReceita> valoresReceita = new ArrayList<ValorReceita>();
		
		if (subproduto == null || receitas == null ){
			return valoresReceita;
		}
		
		Collections.sort(receitas, new Comparator<Receita>(){
			
			@Override
			public int compare(Receita receita1,  Receita receita2){
				
					if (receita1.getOrdem() == null){
						return 1;
					}
					
					if (receita2.getOrdem() == null){
						return -1;
					}
					
					return receita1.getOrdem().compareTo(receita2.getOrdem());  	
				}
			}
		);
		
		for (Receita receita : receitas) {
			
			ValorReceita 	  valorReceita	= null;
			for (ValorReceita valorReceitaItem : receita.getValorReceitas()) {
				
				if (valorReceitaItem.getSubproduto().equals(subproduto)){
					valorReceita = valorReceitaItem;
				}
			}
			
			if (valorReceita == null){
				valorReceita = new ValorReceita();
				valorReceita.setReceita(receita);
				valorReceita.setSubproduto(subproduto);
				
				TipoComponente 		tipoComponente 		= receita.getConfigEquipamento().getFuncaoConfig().getTipoComponente();
				
				if (tipoComponente.getNomeComponente().equals(ETipoComponente.REGION.getValue())){
					
					valorReceita.setValor("0 64 0 64");
					
					try {
						saveValorReceita(valorReceita);
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					
				}
				
			}
			
			valorReceita = getValorReceitaConverted(valorReceita, mapRegiao, true);
			
			if (!valoresReceita.contains(valorReceita)){
				valoresReceita.add(valorReceita);
			}
			
		}
		
		return valoresReceita;
	}
	
	@Override
	public ValorReceita getValorReceitaConverted(ValorReceita valorReceita, Map<Long, Region>	mapRegiao, boolean isToDisplay) {
		
		ConfigEquipamento 	configEquipamento 	= valorReceita.getReceita().getConfigEquipamento();
		TipoComponente 		tipoComponente 		= configEquipamento.getFuncaoConfig().getTipoComponente();
		EComponentConverter componentConverter 	= configEquipamento.getFuncaoConfig().getConverter();
		List<ValoresFuncao> valoresFuncaos 		= configEquipamento.getFuncaoConfig().getValoresFuncaos();
		String 				valor 				= valorReceita.getValor();
			
		if (valor != null && tipoComponente.getNomeComponente().equals(ETipoComponente.CHECKBOX.getValue())){
			
			String valorBoolean = null;
			
			if (valor.equals(Boolean.TRUE.toString()) || valor.equals("1")){
				valorBoolean = isToDisplay ? Boolean.TRUE.toString()  : "1";
			} else{
				valorBoolean = isToDisplay ? Boolean.FALSE.toString() : "0";
			}
			
			valorReceita.setValor(valorBoolean);
			
		} else if (valoresFuncaos != null && tipoComponente.getNomeComponente().equals(ETipoComponente.COMBOBOX.getValue())){
			
			valorReceita.setValor(valor);
			
		} else if (componentConverter != null){
			
			ConverterComp 	converter 			= null;
			Object		  	valorConvertido 	= null;
			boolean 		isRegionComponent 	= tipoComponente.getNomeComponente().equals(ETipoComponente.REGION.getValue());
			
			if (componentConverter == EComponentConverter.DOUBLE_VALUE_TO_HEX_CONVERTER){
				converter = new DoubleValueToHexConverter();
			} else if (componentConverter == EComponentConverter.LECTOR_COMBOBOX_CONVERTER){
				converter 	= new LectorComboBoxConverter();
			} else if (componentConverter == EComponentConverter.LECTOR_FTP_FIELD_CONVERTER){
				converter 	= new LectorFtpFieldConverter();
			} else if (componentConverter == EComponentConverter.LECTOR_HEX_FIELD_CONVERTER){
				converter 	= new LectorHexFiedConverter();
			}
			
			if (valor != null && !isRegionComponent){
				
				if (isToDisplay){
					valorConvertido = converter.display(valor);
				}else{
					valorConvertido = converter.convert(valor);
				}
				
			} else {
				
				if(isToDisplay){
					
					if (valor == null){
						valor = "";
					}
					
					Region   region 	 = new Region();
					String[] regionArray = valor.split(" ");
					
					if (regionArray == null || regionArray.length != 4){
						return valorReceita;
					}
					
					Object	x1 = converter.display(regionArray[0]);
					Object	x2 = converter.display(regionArray[1]);
					Object	y1 = converter.display(regionArray[2]);
					Object	y2 = converter.display(regionArray[3]);
					
					region.setX1((int)Double.parseDouble(x1.toString()));
					region.setX2((int)Double.parseDouble(x2.toString()));
					region.setY1((int)Double.parseDouble(y1.toString()));
					region.setY2((int)Double.parseDouble(y2.toString()));
					
					mapRegiao.put(valorReceita.getReceita().getIdReceita(), region);
					
					valorConvertido = "";
					
				} else{
					
					for (Entry<Long, Region> regiaoItem : mapRegiao.entrySet()){
						
						if (regiaoItem.getKey().equals(valorReceita.getReceita().getIdReceita())){
							
							converter 	= new LectorHexFiedConverter();
							
							Object x1 = converter.convert(String.valueOf(regiaoItem.getValue().getX1()));
							Object x2 = converter.convert(String.valueOf(regiaoItem.getValue().getX2()));
							Object y1 = converter.convert(String.valueOf(regiaoItem.getValue().getY1()));
							Object y2 = converter.convert(String.valueOf(regiaoItem.getValue().getY2()));
							
							valorConvertido = x1 + " " + x2 + " " + y1 + " " + y2;
							
						}
					}
				}
			}

			valorReceita.setValor(valorConvertido.toString());
			
		}
		
		return valorReceita;
	}
	
	@Override
	public void exportarConfiguracao(List<ProdutoSubproduto> 	produtoSubprodutosSelected, 
									 Map<Long, Region> 			mapRegiao, 
									 List<Receita> 				receitas, 
									 List<ValorReceita> 		valoresReceita) throws Exception{
		
		if (produtoSubprodutosSelected == null || produtoSubprodutosSelected.isEmpty()){
			throw new Exception("Nenhum subproduto selecionado para exportação.");
		}
		
		for (ProdutoSubproduto produtoSubproduto : produtoSubprodutosSelected) {
			
			Subproduto subprodutoDestino = produtoSubproduto.getSubproduto();
			
			List<Receita> receitasSubprodDestino = new ArrayList<>();
			
			for (Receita receita : receitas) {
				
				for (ValorReceita valorReceitaItem : receita.getValorReceitas()) {
					if (valorReceitaItem.getSubproduto().equals(subprodutoDestino)){
						if (!receitasSubprodDestino.contains(receita)){
							receitasSubprodDestino.add(receita);
						}
					}
				}
			}
			
			List<ValorReceita> valorReceitasOrigem = valoresReceita;
			
			
			for (ValorReceita valorReceitaOrigem : valorReceitasOrigem) {
				
				ValorReceita valorReceitaDestino = null;
				
				for (Receita receitaDestino : receitasSubprodDestino) {
					
					List<ValorReceita> valorReceitasDestino = receitaDestino.getValorReceitas();
					
					for (ValorReceita vlReceitaDestino : valorReceitasDestino) {
						if (vlReceitaDestino.getReceita().getIdReceita().equals(valorReceitaOrigem.getReceita().getIdReceita()) &&
								vlReceitaDestino.getSubproduto().getIdSubproduto().equals(subprodutoDestino.getIdSubproduto())){
							valorReceitaDestino = vlReceitaDestino;
						}
					}
					
				}
				
				if (valorReceitaDestino == null){
					valorReceitaDestino = new ValorReceita();
					valorReceitaDestino.setReceita(valorReceitaOrigem.getReceita());
					valorReceitaDestino.setSubproduto(subprodutoDestino);
				}

				valorReceitaDestino.setValor(valorReceitaOrigem.getValor());
				
				try {
					
					valorReceitaDestino = getValorReceitaConverted(valorReceitaDestino, mapRegiao, false);
					
					saveValorReceita(valorReceitaDestino);
				} catch (Exception e) {
					throw e;
				}
			}
			
		}
	}

	@Override
	public void saveValoresReceita(List<ValorReceita> valoresReceita, Map<Long, Region>	mapRegiao) throws Exception {
		
		for (ValorReceita valorReceita: valoresReceita) {
			
			if ((valorReceita != null && 
				 valorReceita.getValor() != null && 
				!valorReceita.getValor().isEmpty() ) || mapRegiao.containsKey(valorReceita.getReceita().getIdReceita())){
				
				try {
					valorReceita = getValorReceitaConverted(valorReceita, mapRegiao, false);
					
					saveValorReceita(valorReceita);
				} catch (Exception e) {
					throw new Exception("Erro ao persistir o valor da configuração. Erro: " + e.getMessage());
				}
				
			}
		}
	
	}
}
