package br.com.prati.tim.collaboration.gmp.mb.variaveisclp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.variaveisclp.VariaveisClpEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.VariaveisClp;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoValorVariaveis;

@Named("variaveisClpMB")
@ViewScoped
public class VariaveisClpMB extends AbstractBaseMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;

	
	@Inject
	private VariaveisClpEJB ejb;
	
	private VariaveisClp variaveisClp;
	
	private List<VariaveisClp> variaveis;
	
	@PostConstruct
	public void init(){
		load();
	}

	private void load() {
		VariaveisClp var = new VariaveisClp();
		
		var.setMaquina(new Maquina());
		var.setEquipamento(new Equipamento());
		
		setVariaveisClp(var);
		
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}


	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public VariaveisClp getVariaveisClp() {
		return variaveisClp;
	}

	public void setVariaveisClp(VariaveisClp variaveisClp) {
		this.variaveisClp = variaveisClp;
	}
	
	public List<ETipoValorVariaveis> getTiposValor() {
		return Arrays.asList(ETipoValorVariaveis.values());
	}

	public void select(SelectEvent event){
		
		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(Maquina.class.getName())) {
			
			variaveisClp.setMaquina((Maquina) object);
			
			addInfoMessage("Linha informada com sucesso.");
		}

		else if (object != null	&& object.getClass().getName().equals(Equipamento.class.getName())) {
			
			variaveisClp.setEquipamento((Equipamento) object);
			
			addInfoMessage("Equipamento informado com sucesso.");
		}
		
	}
	
	public void add(){
		
		try {
			
			if (variaveis == null) {
				variaveis = new ArrayList<VariaveisClp>();
			}

			validateAdd();
			
			variaveis.add(variaveisClp);
			
			load();
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
	}
	
	private void validateAdd() throws FacesValidateException {

		if (variaveis.stream().filter(e -> e.getDescricao().equalsIgnoreCase(variaveisClp.getDescricao())).findFirst().isPresent()){
			throw new FacesValidateException("Já existe um item com a mesma descrição na tabela, vinculado a mesma máquina e CLP!", "formCad:descricao");
		}
		
	}

	public void remove(VariaveisClp variaveisClp){
		variaveis.remove(variaveisClp);
	}

	public List<VariaveisClp> getVariaveis() {
		return variaveis;
	}

	public void setVariaveis(List<VariaveisClp> variaveis) {
		this.variaveis = variaveis;
	}

}
