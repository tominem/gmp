package br.com.prati.tim.collaboration.gmp.mb.configequipamento;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.converters.ConverterComp;
import br.com.prati.tim.collaboration.gmp.converters.DoubleValueToHexConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorComboBoxConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorFtpFieldConverter;
import br.com.prati.tim.collaboration.gmp.converters.LectorHexFiedConverter;
import br.com.prati.tim.collaboration.gmp.ejb.valorconfigequip.IValorConfigEquipEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.ConfigEquipamento;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;
import br.prati.tim.collaboration.gp.jpa.TipoComponente.ETipoComponente;
import br.prati.tim.collaboration.gp.jpa.ValorConfigEquip;
import br.prati.tim.collaboration.gp.jpa.ValoresFuncao;
import br.prati.tim.collaboration.gp.jpa.enumerator.EComponentConverter;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Named("mbConfigEquipamento")
@ViewScoped
public class MBConfigEquipamento extends AbstractBaseMB implements Serializable{

	private static final long		serialVersionUID	= 1L;

	private Equipamento				equipamento;

	@EJB
	private IValorConfigEquipEJB	ejb;

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
			
			if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.CONSULTA)){
				UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.CONSULTA.getDescricao() + ".");
				return;
			}
			
			loadValorConfigEquipamento();
		}
		
	}
	
	private void selectEquipamento (Equipamento equipamento){
		setEquipamento(equipamento);
	}
	
	@PostConstruct
	public void load(){
		
		this.setConfigEquipamentoList(new ArrayList<ConfigEquipamento>());
		
	}
	
	public void save(){
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.ALTERACAO)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.ALTERACAO.getDescricao() + ".");
			return;
		}
		
		for (ConfigEquipamento configEquipamento : configEquipamentoList) {
			
			if (configEquipamento.getValorConfigEquip() != null){
				
				try {
					
					ValorConfigEquip valorConfigEquip = getValorConfig(configEquipamento, false); 
					
					if (valorConfigEquip 			!= null && 
						valorConfigEquip.getValor() != null && 
						!valorConfigEquip.getValor().isEmpty()){
						
						ejb.save(valorConfigEquip);
						
					}
					
				} catch (Exception e) {
					addErrorMessage("Erro ao persistir o valor da configuração. Erro: " + e.getMessage());
					return;
				}
			}
			
		}
		
		clean();
		
		addInfoMessage("Configuração salva com sucessos.");
		
	}

	public void clean(){
		
		load();
		
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
		
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
			
			valorConfigEquip = getValorConfig(configEquipamento, true);
			
			
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
	
	private ValorConfigEquip getValorConfig(ConfigEquipamento configEquipamento, boolean isToDisplay) {
		
		TipoComponente 		tipoComponente 		= configEquipamento.getFuncaoConfig().getTipoComponente();
		EComponentConverter componentConverter 	= configEquipamento.getFuncaoConfig().getConverter();
		List<ValoresFuncao> valoresFuncaos 		= configEquipamento.getFuncaoConfig().getValoresFuncaos();
		String 				valor 				= configEquipamento.getValorConfigEquip().getValor();
		
		if (valor != null){
			
			if (tipoComponente.getNomeComponente().equals(ETipoComponente.CHECKBOX.getValue())){
				
				String valorBoolean = null;
				
				if (valor.equals(Boolean.TRUE.toString()) || valor.equals("1")){
					valorBoolean = Boolean.TRUE.toString();
				} else{
					valorBoolean = Boolean.FALSE.toString();
				}
				
				configEquipamento.getValorConfigEquip().setValor(valorBoolean);
				
			} else if (valoresFuncaos != null && !valoresFuncaos.isEmpty()){
				
				configEquipamento.getValorConfigEquip().setValor(valor);
				
			} else if (componentConverter != null){
				
				ConverterComp converter = null;
				Object		  valorConvertido = null;
				
				if (componentConverter == EComponentConverter.DOUBLE_VALUE_TO_HEX_CONVERTER){
					converter = new DoubleValueToHexConverter();
				} else if (componentConverter == EComponentConverter.LECTOR_COMBOBOX_CONVERTER){
					converter 	= new LectorComboBoxConverter();
				} else if (componentConverter == EComponentConverter.LECTOR_FTP_FIELD_CONVERTER){
					converter 	= new LectorFtpFieldConverter();
				} else if (componentConverter == EComponentConverter.LECTOR_HEX_FIELD_CONVERTER){
					converter 	= new LectorHexFiedConverter();
				}
				
				if (isToDisplay){
					valorConvertido = converter.display(valor);
				}else{
					valorConvertido = converter.convert(valor);
				}
				
				configEquipamento.getValorConfigEquip().setValor(valorConvertido.toString());
				
			}
			
			return configEquipamento.getValorConfigEquip();
			
		}
		return null;
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Equipamento getEquipamento() {
		return equipamento;
	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

	public List<ConfigEquipamento> getConfigEquipamentoList() {
		return configEquipamentoList;
	}

	public void setConfigEquipamentoList(List<ConfigEquipamento> configEquipamentoList) {
		this.configEquipamentoList = configEquipamentoList;
	}
	
}
