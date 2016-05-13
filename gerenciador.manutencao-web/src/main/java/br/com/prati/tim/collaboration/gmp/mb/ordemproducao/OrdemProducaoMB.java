package br.com.prati.tim.collaboration.gmp.mb.ordemproducao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CloseEvent;

import br.com.prati.tim.collaboration.gmp.ejb.ordemproducao.OrdemProducaoEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.BaseMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducao;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoMateriais;

@Named("ordemProducaoMB")
@ViewScoped
public class OrdemProducaoMB extends AbstractBaseMB implements BaseMB, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3520074732472389063L;
	
	
	@Inject
	private OrdemProducaoEJB ordemProducaoEJB;
	
	private String numOrdem;
	
	private OrdemProducao ordemProducao;
	
	private List<OrdemProducaoMateriais> materiais;
	
	private OrdemProducaoMateriais materialSelected;
	
	
	// ==================================================
	//  METHODS
	// ==================================================
	
	@PostConstruct
	public void init(){
		
		setNumOrdem("");
		
	}
	
	@Override
	public void clean() {
		
		getRequestContext().reset("formOrdem");
		
		numOrdem = "";

		materiais = null; 

		materialSelected = null;
		
		ordemProducao = null;
		
	}
	
	public void close(CloseEvent event){
		clean();
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}
	
	public void consultaOrdem(OrdemSAPListener listener){
		
		boolean invalidate = false;
		
		try {
			
			if (numOrdem == null || numOrdem.isEmpty()) {
				throw new FacesValidateException("Número da ordem deve ser informado!", "formOrdem:ordem");
			}
			
			ordemProducao = ordemProducaoEJB.carregarOrdemProducao(Integer.parseInt(getNumOrdem()));
			
			setMateriais(ordemProducao.getMaterias());
			
			if (listener != null && listener instanceof OrdemSAPListener) {
				listener.retrieveOrdem(ordemProducao);
			}
			
		} catch (Exception e) {
			
			invalidate = true;
			
			getRequestContext().addCallbackParam("invalidate", invalidate);
			
			addErrorMessage(e.getMessage());
			
		}
	}

	public List<OrdemProducaoMateriais> getMateriais() {
		return materiais;
	}

	public void setMateriais(List<OrdemProducaoMateriais> materiais) {
		this.materiais = materiais;
	}

	public OrdemProducaoMateriais getMaterialSelected() {
		return materialSelected;
	}

	public void setMaterialSelected(OrdemProducaoMateriais materialSelected) {
		this.materialSelected = materialSelected;
	}

	public String getNumOrdem() {
		return numOrdem;
	}

	public void setNumOrdem(String numOrdem) {
		this.numOrdem = numOrdem;
	}

	public OrdemProducao getOrdemProducao() {
		return ordemProducao;
	}

	public void setOrdemProducao(OrdemProducao ordemProducao) {
		this.ordemProducao = ordemProducao;
	}

}
