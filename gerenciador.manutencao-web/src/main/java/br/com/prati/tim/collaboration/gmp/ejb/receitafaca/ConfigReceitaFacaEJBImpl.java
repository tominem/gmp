package br.com.prati.tim.collaboration.gmp.ejb.receitafaca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.converters.ConverterComp;
import br.com.prati.tim.collaboration.gmp.converters.DoubleValueToHexConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorComboBoxConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorFtpFieldConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorHexFiedConverter;
import br.com.prati.tim.collaboration.gmp.dao.equipamentoMaquina.EquipamentoMaquinaDAO;
import br.com.prati.tim.collaboration.gmp.dao.faca.FacaDAO;
import br.com.prati.tim.collaboration.gmp.dao.facasprodmaq.FacasProdMaqDAO;
import br.com.prati.tim.collaboration.gmp.dao.receitafaca.ReceitaFacaDAO;
import br.com.prati.tim.collaboration.gmp.dao.receitafaca.ValorReceitaFacaDAO;
import br.com.prati.tim.collaboration.gmp.mb.receita.Region;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.FacasProdMaq;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;
import br.prati.tim.collaboration.gp.jpa.ReceitaFaca;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;
import br.prati.tim.collaboration.gp.jpa.TipoComponente.ETipoComponente;
import br.prati.tim.collaboration.gp.jpa.ValorReceitaFaca;
import br.prati.tim.collaboration.gp.jpa.enumerator.EComponentConverter;

@Stateless
public class ConfigReceitaFacaEJBImpl implements ConfigReceitaFacaEJB {
	
	@Inject
	private ValorReceitaFacaDAO	valorReceitaDAO;

	
	@Inject
	private ReceitaFacaDAO		receitaDAO;

	@Inject
	private FacaDAO facaDAO;
	
	@Inject
	private EquipamentoMaquinaDAO equipamentoMaquinaDAO;
	
	@Inject
	private FacasProdMaqDAO facasProdMaqDAO;

	
	@Override
	public List<ValorReceitaFaca> findValorReceitaByReceita(ReceitaFaca receitaFaca) {
		return valorReceitaDAO.findByReceitaFaca(receitaFaca);
	}

	
	@Override
	public ReceitaFaca findReceitaById(Long idReceita) {
		return receitaDAO.findById(idReceita);
	}

	
	@Override
	public ValorReceitaFaca saveValorReceitaFaca(ValorReceitaFaca valorReceitaFaca) throws Exception {
		if (valorReceitaFaca.getIdValorReceitaFaca() == null){
			return valorReceitaDAO.persist(valorReceitaFaca) ;
		}else{
			return valorReceitaDAO.update(valorReceitaFaca) ;
		}
	}
	
	
	@Override
	public List<ValorReceitaFaca> retornaValoresReceitasFacas(List<ReceitaFaca> receitas, Map<Long, Region>	mapRegiao, Faca faca){
		
		List<ValorReceitaFaca> valoresReceita = new ArrayList<ValorReceitaFaca>();
		
		Collections.sort(receitas, new Comparator<ReceitaFaca>(){
			
			@Override
			public int compare(ReceitaFaca receita1,  ReceitaFaca receita2)
			{
				
				Integer configOrd1 = receita1.getConfigEquipamento().getOrdem();
				Integer configOrd2 = receita2.getConfigEquipamento().getOrdem();
				
				if (receita1.getOrdem() == null && receita2.getOrdem() == null) 
				{
					
					return configOrd1.compareTo(configOrd2);
					
				}
				
				if (receita1.getOrdem() == null){
					return 1;
				}
				
				if (receita2.getOrdem() == null){
					return -1;
				}
				
				return receita1.getOrdem().compareTo(receita2.getOrdem());  	
			}
		});
		
		for (ReceitaFaca receitaFaca : receitas) {
			
			ValorReceitaFaca 	valorReceitaFaca	= null;

			FuncaoConfig        funcaoConfig        = receitaFaca.getConfigEquipamento().getFuncaoConfig();
			
			TipoComponente 		tipoComponente 		= funcaoConfig.getTipoComponente();
			
			if (tipoComponente.getNomeComponente().equals(ETipoComponente.ARRAY_INT.getValue())) {
				
				fillNewValorReceitaFacas(faca, valoresReceita, receitaFaca, valorReceitaFaca, mapRegiao);
				
			}
			
			else{
				
				List<ValorReceitaFaca> valorReceitaFacas = receitaFaca.getValorReceitaFacas();
				
				valorReceitaFaca = valorReceitaFacas != null && valorReceitaFacas.size() > 0 ? valorReceitaFacas.get(0) : null;
				
				if (valorReceitaFaca == null){
					valorReceitaFaca = buildNewValorReceitaFaca(receitaFaca, 0);
					
					
					if (tipoComponente.getNomeComponente().equals(ETipoComponente.REGION.getValue())){
						
						valorReceitaFaca.setValor("0 64 0 64");
						
						try {
							
							saveValorReceitaFaca(valorReceitaFaca);
							
						} catch (Exception e) {
							e.printStackTrace();
							return null;
						}
						
					}
					
				}
				
				valorReceitaFaca = getValorReceitaConverted(valorReceitaFaca, mapRegiao, true);
				
				if (!valoresReceita.contains(valorReceitaFaca)){
					valoresReceita.add(valorReceitaFaca);
				}
				
			}
			
//			for (ValorReceitaFaca valorReceitaItem : receitaFaca.getValorReceitaFacas()) {
//				
//				if (valorReceitaItem.getSubproduto().equals(subproduto)){
//					valorReceitaFaca = valorReceitaItem;
//				}
//			}
			
			
		}
		
		return valoresReceita;
	}


	private void fillNewValorReceitaFacas(Faca faca, List<ValorReceitaFaca> valoresReceita, ReceitaFaca receitaFaca, ValorReceitaFaca valorReceitaFaca, Map<Long, Region> mapRegiao) {
		List<ValorReceitaFaca> valorReceitaFacas = receitaFaca.getValorReceitaFacas();

		if (valorReceitaFacas == null || valorReceitaFacas.size() != faca.getCortes()) {
			
			for (int i = 0; i < faca.getCortes(); i++) {
				
				ValorReceitaFaca item = buildNewValorReceitaFaca(receitaFaca, i + 1);
				
				valoresReceita.add(item);
				
			}
			
		}
		
		else{
			
			Collections.sort(valorReceitaFacas, (r1, r2) -> r1.getNumeroBlister().compareTo(r2.getNumeroBlister()));
			
			for (ValorReceitaFaca valor : valorReceitaFacas) {
				
				valor = getValorReceitaConverted(valor, mapRegiao, true);
				
				valoresReceita.add(valor);
			}
			
			
		}
	}


	private ValorReceitaFaca buildNewValorReceitaFaca(ReceitaFaca receitaFaca, int valor) {
		ValorReceitaFaca item = new ValorReceitaFaca();
		item.setNumeroBlister(valor);
		item.setValor("0");
		item.setReceitaFaca(receitaFaca);
		
		return item;
	}
	
	@Override
	public ValorReceitaFaca getValorReceitaConverted(ValorReceitaFaca valorReceitaFaca, Map<Long, Region>	mapRegiao, boolean isToDisplay) {
		
		ConfigEquipamento 	configEquipamento 		= 	valorReceitaFaca.getReceitaFaca().getConfigEquipamento();
		TipoComponente 		tipoComponente 			= 	configEquipamento.getFuncaoConfig().getTipoComponente();
		EComponentConverter componentConverter 		= 	configEquipamento.getFuncaoConfig().getConverter();
		String 				valor 					= 	valorReceitaFaca.getValor();
			
		if (valor != null && tipoComponente.getNomeComponente().equals(ETipoComponente.CHECKBOX.getValue())){
			
			String valorBoolean = null;
			
			if (valor.equals(Boolean.TRUE.toString()) || valor.equals("1")){
				valorBoolean = isToDisplay ? Boolean.TRUE.toString()  : "1";
			} else{
				valorBoolean = isToDisplay ? Boolean.FALSE.toString() : "0";
			}
			
			valorReceitaFaca.setValor(valorBoolean);
				
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
			
			if (converter == null){
				
				if (valor == null){
					valor = "";
				}
				valorConvertido = valor;
			} else {
				
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
							return valorReceitaFaca;
						}
						
						Object	x1 = converter.display(regionArray[0]);
						Object	x2 = converter.display(regionArray[1]);
						Object	y1 = converter.display(regionArray[2]);
						Object	y2 = converter.display(regionArray[3]);
						
						region.setX1((int)Double.parseDouble(x1.toString()));
						region.setX2((int)Double.parseDouble(x2.toString()));
						region.setY1((int)Double.parseDouble(y1.toString()));
						region.setY2((int)Double.parseDouble(y2.toString()));
						
						mapRegiao.put(valorReceitaFaca.getReceitaFaca().getIdReceitaFaca(), region);
						
						valorConvertido = "";
						
					} else {
						
						for (Entry<Long, Region> regiaoItem : mapRegiao.entrySet()){
							
							if (regiaoItem.getKey().equals(valorReceitaFaca.getReceitaFaca().getIdReceitaFaca())){
								
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
			
			}

			valorReceitaFaca.setValor(valorConvertido.toString());
			
		}
		
		return valorReceitaFaca;
	}
	
	@Override
	public void exportarConfiguracao(List<ProdutoSubproduto> 	produtoSubprodutosSelected, 
									 Map<Long, Region> 			mapRegiao, 
									 List<ReceitaFaca> 				receitas, 
									 List<ValorReceitaFaca> 		valoresReceita) throws Exception{
		
		if (produtoSubprodutosSelected == null || produtoSubprodutosSelected.isEmpty()){
			throw new Exception("Nenhum subproduto selecionado para exportação.");
		}
		
		for (ProdutoSubproduto produtoSubproduto : produtoSubprodutosSelected) {
			
			Subproduto subprodutoDestino = produtoSubproduto.getSubproduto();
			
			List<ReceitaFaca> receitasSubprodDestino = new ArrayList<>();
			
			for (ReceitaFaca receitaFaca : receitas) {
				
//				for (ValorReceitaFaca valorReceitaItem : receitaFaca.getValorReceitaFacas()) {
//					if (valorReceitaItem.getSubproduto().equals(subprodutoDestino)){
//						if (!receitasSubprodDestino.contains(receitaFaca)){
//							receitasSubprodDestino.add(receitaFaca);
//						}
//					}
//				}
			}
			
			List<ValorReceitaFaca> valorReceitasOrigem = valoresReceita;
			
			
			for (ValorReceitaFaca valorReceitaOrigem : valorReceitasOrigem) {
				
				ValorReceitaFaca valorReceitaDestino = null;
				
				for (ReceitaFaca receitaDestino : receitasSubprodDestino) {
					
					List<ValorReceitaFaca> valorReceitasDestino = receitaDestino.getValorReceitaFacas();
					
//					for (ValorReceitaFaca vlReceitaDestino : valorReceitasDestino) {
//						if (vlReceitaDestino.getReceitaFaca().getIdReceitaFaca().equals(valorReceitaOrigem.getReceitaFaca().getIdReceitaFaca()) &&
//								vlReceitaDestino.getSubproduto().getIdSubproduto().equals(subprodutoDestino.getIdSubproduto())){
//							valorReceitaDestino = vlReceitaDestino;
//						}
//					}
					
				}
				
				if (valorReceitaDestino == null){
					valorReceitaDestino = new ValorReceitaFaca();
					valorReceitaDestino.setReceitaFaca(valorReceitaOrigem.getReceitaFaca());
//					valorReceitaDestino.setSubproduto(subprodutoDestino);
				}

				valorReceitaDestino.setValor(valorReceitaOrigem.getValor());
				
				try {
					
					valorReceitaDestino = getValorReceitaConverted(valorReceitaDestino, mapRegiao, false);
					
					saveValorReceitaFaca(valorReceitaDestino);
				} catch (Exception e) {
					throw e;
				}
			}
			
		}
	}

	@Override
	public void saveValoresReceitaFaca(List<ValorReceitaFaca> valoresReceita, Map<Long, Region>	mapRegiao) throws Exception {
		
		for (ValorReceitaFaca valorReceitaFaca: valoresReceita) {
			
			if ((valorReceitaFaca != null && 
				 valorReceitaFaca.getValor() != null && 
				!valorReceitaFaca.getValor().isEmpty() ) || mapRegiao.containsKey(valorReceitaFaca.getReceitaFaca().getIdReceitaFaca())){
				
				try {
					valorReceitaFaca = getValorReceitaConverted(valorReceitaFaca, mapRegiao, false);
					
					saveValorReceitaFaca(valorReceitaFaca);
				} catch (Exception e) {
					throw new Exception("Erro ao persistir o valor da configuração. Erro: " + e.getMessage());
				}
				
			}
		}
	
	}


	@Override
	public List<Faca> findAllFacas() {
		return facaDAO.findAll();
	}


	@Override
	public List<Equipamento> findEquipamentosByMaquina(Maquina maquina) {
		
		Optional<List<EquipamentoMaquina>> opt = Optional.ofNullable(equipamentoMaquinaDAO.findByMaquina(maquina));
		if(opt.isPresent())
		{
		
			List<EquipamentoMaquina> equipamentoMaquinas = opt.get();
			
			return equipamentoMaquinas.stream().map(EquipamentoMaquina::getEquipamento).collect(Collectors.toList());
			
		}
		
		return null;
	}


	@Override
	public Set<Faca> findFacasByMaquinaAndEquipamento(Maquina maquina, Equipamento equipamento) {
		
		List<FacasProdMaq> facasProdMaq = facasProdMaqDAO.findByMaquinaAndEquipamento(maquina, equipamento);
		
		if( facasProdMaq != null && facasProdMaq.size() > 0 ){
			
			Set<Faca> facas = facasProdMaq.stream().map(FacasProdMaq::getFaca).collect(Collectors.toSet());
			
			return facas;
			
		}
		
		return null;
	}
}
