package br.com.prati.tim.collaboration.gmp.mb.subproduto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.subproduto.SubprodutoEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.ordemproducao.OrdemSAPListener;
import br.prati.tim.collaboration.gp.jpa.SubprodTipoInsp;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;
import br.prati.tim.collaboration.gp.jpa.Unidade;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoCalculoFator;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducao;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoMateriais;

@Named("subprodutoMB")
@ViewScoped
public class SubprodutoCrudMB extends AbstractCrudMB<Subproduto, Long>	implements Serializable, OrdemSAPListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;


//	private final String INPUT_CODIGO_INSPECIONADO_ID = "formCad:codigoInspecionado";
	
	private final String INPUT_TIPOINSPECAO_ID = "formCad:tpInspecao";
	
	@Inject
	private SubprodutoEJB ejb;
	
	private TipoInspecao tipoInspecao;
	
	private String codigoInspecionado;
	
	private List<SubprodTipoInsp> subprodTipoInsps;
	
	private String numeroOrdem = "";

	
	//================ METHODS ========================//
	
	@Override
	public void clean() {
		
		subprodTipoInsps = null;
		tipoInspecao = null;
		codigoInspecionado = "";
		numeroOrdem = "";
		
		super.clean();
	}
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/subproduto/searchSubproduto.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdSubproduto();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdSubproduto(entityId);
	}

	@Override
	public Boolean getEntityStatus() {
		return entityBean.getStatus();
	}

	@Override
	public void setEntityStatus(Boolean status) {
		entityBean.setStatus(status);
	}

	@Override
	public CrudEJB<Subproduto> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Subproduto> getEntityClass() {
		return Subproduto.class;
	}

	public List<Unidade> getUnidades(){
		List<Unidade> unidades =  ejb.findUnidades();
		
		if (unidades != null && unidades.size() > 0) {
			Collections.sort(unidades, (x1, x2) -> x1.getSigla().compareTo(x2.getSigla()));
		}
		
		return unidades;
	}
	
	@Override
	public void save() {
		
		entityBean.setSubprodTipoInsps(new ArrayList<SubprodTipoInsp>());
		
		entityBean.setSubprodTipoInsps(subprodTipoInsps);
		
		super.save();
	}
	

	public List<ETipoCalculoFator> getCalculos(){
		List<ETipoCalculoFator> calculos = Arrays.asList(ETipoCalculoFator.values());
		
		Collections.sort(calculos, (x1, x2) -> x1.getDescricao().compareTo(x2.getDescricao()));
		
		return calculos;
	}
	
	public void selectTipoInspecao(SelectEvent event) {

		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(TipoInspecao.class.getName())) {
			setTipoInspecao((TipoInspecao) object);

			addInfoMessage("Tipo de Inspeção informada com sucesso.");
		}
			
	}
	
	@Override
	public void selectObjectAfterSearch(SelectEvent event) {
		super.selectObjectAfterSearch(event);
		setSubprodTipoInsps(entityBean.getSubprodTipoInsps());
	}
	
	public void removeItem(SubprodTipoInsp item){
		subprodTipoInsps.remove(item);
	}
	
	public void addItem(){
		
		try {
			
			validateAddItem();
			
			if (subprodTipoInsps == null) {
				subprodTipoInsps = new ArrayList<SubprodTipoInsp>();
			}
			
			subprodTipoInsps.add(new SubprodTipoInsp(entityBean, getTipoInspecao(), getCodigoInspecionado()));
			
			cleanAddItem();
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}
		
	}

	private void validateAddItem() throws FacesValidateException {

		if (tipoInspecao == null) 
			throw new FacesValidateException("Tipo de Inspeção requerida", INPUT_TIPOINSPECAO_ID);

		// issues #31 - Solicitada remoção dessa validação 
		//	if (codigoInspecionado == null || codigoInspecionado.isEmpty()) 
		//		throw new FacesValidateException("Código requerido", INPUT_CODIGO_INSPECIONADO_ID);
		
		//seek if tipoinspecao is already present
		if (subprodTipoInsps != null && subprodTipoInsps.size() > 0) {
			Optional<SubprodTipoInsp> findFirst = subprodTipoInsps.stream().filter(e -> e.getTipoInspecao().equals(getTipoInspecao())).findFirst();
			if (findFirst.isPresent()) {
				throw new FacesValidateException("Tipo de inspeção já vinculado ao subproduto. Escolha outro!", INPUT_TIPOINSPECAO_ID);
			}
		}
		
	}
	
	@Override
	public ValidateComponent[] getValidaComponents() {
		return new ValidateComponent[]{
				
			new ValidateComponent("formCad:codSap", "Código Sap", "codigoSap"),
			new ValidateComponent("formCad:descricao", "Descrição", "descricao")
				
		};
	}

	private void cleanAddItem() {
		
		setTipoInspecao(null);
		setCodigoInspecionado("");
		
	}

	public TipoInspecao getTipoInspecao() {
		return tipoInspecao;
	}

	public void setTipoInspecao(TipoInspecao tipoInspecao) {
		this.tipoInspecao = tipoInspecao;
	}

	public String getCodigoInspecionado() {
		return codigoInspecionado;
	}

	public void setCodigoInspecionado(String codigoInspecionado) {
		this.codigoInspecionado = codigoInspecionado;
	}

	public List<SubprodTipoInsp> getSubprodTipoInsps() {
		return subprodTipoInsps;
	}

	public void setSubprodTipoInsps(List<SubprodTipoInsp> subprodTipoInsps) {
		this.subprodTipoInsps = subprodTipoInsps;
	}

	public String getNumeroOrdem() {
		return numeroOrdem;
	}

	public void setNumeroOrdem(String numeroOrdem) {
		this.numeroOrdem = numeroOrdem;
	}

	@Override
	public void retrieveOrdem(OrdemProducao ordemProducao) {
		
	}

	@Override
	public void retrieveMaterialSelected(OrdemProducaoMateriais material) {
		entityBean.setDescricao(material.getDescricao());
		entityBean.setCodigoSap(material.getCodigo());
		
		addInfoMessage("Subproduto importado da ordem de produção com sucesso!");
	}

	@Override
	public void retrieveMaterialSelecteds(List<OrdemProducaoMateriais> materiais) {
		// TODO Auto-generated method stub
		
	}
	
}
