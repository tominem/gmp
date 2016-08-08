package br.com.prati.tim.collaboration.gmp.mb.configequipamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.converters.DoubleValueToHexConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorComboBoxConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorFtpFieldConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorHexFiedConverter;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.valorconfigequip.IValorConfigEquipEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.ValorConfigEquip;
import br.prati.tim.collaboration.gp.jpa.enumerator.EComponentConverter;

@Named("mbConfigEquipamento")
@ViewScoped
public class MBConfigEquipamento implements Serializable{

	private static final long		serialVersionUID	= 1L;

	private Equipamento				equipamento;

	@EJB
	private IValorConfigEquipEJB	ejb;

	private List<ValorConfigEquip>	valorConfigEquipamento;

	private List<ConfigEquipamento>	configEquipamentoList;

	public Integer parseValueToInt(String value){
		
		if (value == null || value.isEmpty()){
			return null;
		}
		
		return Integer.parseInt(value);
	}
	
	public void select(SelectEvent event){
		
		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(Equipamento.class.getName())) {
			
			Equipamento equipamentoSelecionado = (Equipamento) object;
			
			if (equipamentoSelecionado.equals(getEquipamento())) return;
			
			selectEquipamento(equipamentoSelecionado);
			
			loadValorConfigEquipamento();
		}
		
	}
	
	private void selectEquipamento (Equipamento equipamento){
		setEquipamento(equipamento);
	}
	@PostConstruct
	public void load(){
		
		this.setValorConfigEquipamento(new ArrayList<ValorConfigEquip>());
		this.setConfigEquipamentoList(new ArrayList<ConfigEquipamento>());
		
	}
	
	public void save(){
		
		
	}
	
	public void clean(){
		
	}
	
	private void loadValorConfigEquipamento(){
	
		this.setConfigEquipamentoList(new ArrayList<ConfigEquipamento>());
		
		List<ConfigEquipamento> configsEquipamento 	= equipamento.getConfigEquipamentos();
		
		for (ConfigEquipamento configEquipamento : configsEquipamento) {
			
			List<Receita>	receitasEquipamento = configEquipamento.getReceitas();
			
			if (receitasEquipamento != null && !receitasEquipamento.isEmpty()){
				continue;
			}
			
			ValorConfigEquip valorConfigEquip = configEquipamento.getValorConfigEquip();
			
			if (valorConfigEquip == null){
				
				valorConfigEquip = new ValorConfigEquip();
				valorConfigEquip.setConfigEquipamento(configEquipamento);
				
				configEquipamento.setValorConfigEquip(valorConfigEquip);
			}
			
			EComponentConverter converter 	= configEquipamento.getFuncaoConfig().getConverter();
			String 				valor 		= valorConfigEquip.getValor();
			
			if (valor != null && converter != null){
				
				if (converter == EComponentConverter.DOUBLE_VALUE_TO_HEX_CONVERTER){
					
					DoubleValueToHexConverter 	doubleConverter = new DoubleValueToHexConverter();
					Double 						valorDouble 	= doubleConverter.display(valor);
					
					valorConfigEquip.setValor(valorDouble.toString());
				} else if (converter == EComponentConverter.LECTOR_COMBOBOX_CONVERTER){
						
					LectorComboBoxConverter 	lectorConverter = new LectorComboBoxConverter();
					String 						valorConvertido = lectorConverter.display(valor);
					
					valorConfigEquip.setValor(valorConvertido);
				} else if (converter == EComponentConverter.LECTOR_FTP_FIELD_CONVERTER){
						
					LectorFtpFieldConverter 	lectorConverter = new LectorFtpFieldConverter();
					String 						valorConvertido = lectorConverter.display(valor);
					
					valorConfigEquip.setValor(valorConvertido);
				} else if (converter == EComponentConverter.LECTOR_HEX_FIELD_CONVERTER){
					
					LectorHexFiedConverter 	lectorConverter = new LectorHexFiedConverter();
					String 					valorConvertido = lectorConverter.display(valor);
					
					valorConfigEquip.setValor(valorConvertido);
				}
			} 
			
			
			getConfigEquipamentoList().add(configEquipamento);
		}
		
		
		Collections.sort(configEquipamentoList, new Comparator<ConfigEquipamento>(){
			
				@Override
				public int compare(ConfigEquipamento config1,  ConfigEquipamento config2){
					
					if (config1.getOrdem() == null){
						return 1;
					}
					
					if (config2.getOrdem() == null){
						return -1;
					}
					
					return config1.getOrdem().compareTo(config2.getOrdem());  	
				}
			}
		);
	}
	
	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public List<ValorConfigEquip> getValorConfigEquipamento() {
		return valorConfigEquipamento;
	}

	public void setValorConfigEquipamento(List<ValorConfigEquip> valorConfigEquipamento) {
		this.valorConfigEquipamento = valorConfigEquipamento;
	}

	public List<ConfigEquipamento> getConfigEquipamentoList() {
		return configEquipamentoList;
	}

	public void setConfigEquipamentoList(List<ConfigEquipamento> configEquipamentoList) {
		this.configEquipamentoList = configEquipamentoList;
	}


}
