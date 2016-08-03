package br.com.prati.tim.collaboration.gmp.mb.receita;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.receita.ConfigReceitaEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;
import br.prati.tim.collaboration.gp.jpa.ValorReceita;

@Named("configReceitaMB")
@ViewScoped
public class ConfigReceitaMB extends AbstractBaseMB implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;

	
	@Inject
	private ConfigReceitaEJB ejb;
	
	private TipoInspecao tipoInspecao;
	
	private Subproduto subproduto;
	
	private List<ValorReceita> valoresReceita;
	
	@PostConstruct
	public void init(){
		
		Receita receita = ejb.findReceitaById(2L);
		
		receita.getConfigEquipamento().getFuncaoConfig().getConverter();
		
		List<ValorReceita> valorReceitas = ejb.findValorReceitaByReceita(receita);
		
		
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

	public void select(SelectEvent event){
		
		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(TipoInspecao.class.getName())) {
			
			TipoInspecao tipoInspecao = (TipoInspecao) object;
			
			if (tipoInspecao.equals(getTipoInspecao())) return;
			
			selectTipoInspecao(tipoInspecao);
		}

		else if (object != null	&& object.getClass().getName().equals(Subproduto.class.getName())) {
			
			Subproduto subproduto = (Subproduto) object;
			
			if (subproduto.equals(getSubproduto())) return;
			
			selectSubproduto(subproduto);
		}
		
		
	}

	private void selectSubproduto(Subproduto subproduto) {
		setSubproduto(subproduto);
	}

	private void selectTipoInspecao(TipoInspecao tipoInspecao) {
		setTipoInspecao(tipoInspecao);
	}

	public TipoInspecao getTipoInspecao() {
		return tipoInspecao;
	}

	public void setTipoInspecao(TipoInspecao tipoInspecao) {
		this.tipoInspecao = tipoInspecao;
	}

	public Subproduto getSubproduto() {
		return subproduto;
	}

	public void setSubproduto(Subproduto subproduto) {
		this.subproduto = subproduto;
	}

	public List<ValorReceita> getValoresReceita() {
		return valoresReceita;
	}

	public void setValoresReceita(List<ValorReceita> valoresReceita) {
		this.valoresReceita = valoresReceita;
	}

}
