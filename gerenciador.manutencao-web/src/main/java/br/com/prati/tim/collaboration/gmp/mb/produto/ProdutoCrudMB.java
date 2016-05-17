package br.com.prati.tim.collaboration.gmp.mb.produto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.produto.ProdutoEJB;
import br.com.prati.tim.collaboration.gmp.ex.FacesValidateException;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.ordemproducao.OrdemSAPListener;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.enumerator.ECategoriaProduto;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducao;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoMateriais;

@Named("produtoMB")
@ViewScoped
public class ProdutoCrudMB extends AbstractCrudMB<Produto, Long>	implements Serializable, OrdemSAPListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6966525816419190286L;


	@Inject
	private TimeZone defaultTimeZone;
	
	@Inject
	private ProdutoEJB ejb;
	
	private boolean ordemImportada = false;
	
	private List<ProdutoSubproduto> produtoSubprodutos;
	
	//================ METHODS ========================//
	
	@PostConstruct
	@Override
	public void initObjects() {
		super.initObjects();
		
		load();
	}
	
	private void load() {
		
		produtoSubprodutos = new ArrayList<ProdutoSubproduto>();
		
		ordemImportada = false;
		
	}

	@Override
	public void clean() {
		
		load();
		
		super.clean();
	}
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/produto/searchProduto.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdProduto();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdProduto(entityId);
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
	public CrudEJB<Produto> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Produto> getEntityClass() {
		return Produto.class;
	}

	@Override
	public void retrieveOrdem(OrdemProducao ordemProducao) {
		entityBean.setDescricao(ordemProducao.getDescrMaterial());
		entityBean.setCodigoSap(ordemProducao.getCodMaterial());
		entityBean.setCodigo(ordemProducao.getEan() + "");
		
		addInfoMessage("Produto importado da ordem de produção com sucesso!");
	}
	
	public void selectSubproduto(SelectEvent event) {

		try {
			
			Object object = event.getObject();

			if (object != null	&& object.getClass().getName().equals(Subproduto.class.getName())) {

				addItem((Subproduto) object);
				
				addInfoMessage("Subproduto adicionado com sucesso.");
			}
			
		} catch (FacesValidateException e) {
			
			addErrorMessage(e.getMessage());
			
		}
			
	}
	
	@Override
	public void selectObjectAfterSearch(SelectEvent event) {
		super.selectObjectAfterSearch(event);
		setProdutoSubprodutos(entityBean.getProdutoSubprodutos());
	}
	
	private void addItem(Subproduto subproduto) throws FacesValidateException{
		
		validateAddItem(subproduto);

		if (produtoSubprodutos == null) {
			produtoSubprodutos = new ArrayList<ProdutoSubproduto>();
		}

		produtoSubprodutos.add(new ProdutoSubproduto(entityBean, subproduto, Calendar.getInstance(defaultTimeZone).getTime()));

	}
	
	public void removeItem(ProdutoSubproduto item){
		produtoSubprodutos.remove(item);
	}

	private void validateAddItem(Subproduto subproduto) throws FacesValidateException{
		
		if (getProdutoSubprodutos() != null && getProdutoSubprodutos()
				.stream().filter(s -> s.getSubproduto().getCodigoSap().equals(subproduto.getCodigoSap())).findFirst().isPresent()) {
			throw new FacesValidateException("Subproduto já vinculado!");
		}
		
	}
	
	@Override
	public ValidateComponent[] getValidaComponents() {
		return new ValidateComponent[] {
			
			new ValidateComponent("formCad:descricao", "Descrição", "descricao"),
			new ValidateComponent("formCad:codigoSAP", "Código SAP", "codigoSap")
				
		};
	}
	
	@Override
	public void save() {
		
		entityBean.setProdutoSubprodutos(new ArrayList<ProdutoSubproduto>());
		
		entityBean.setProdutoSubprodutos(getProdutoSubprodutos());
		
		super.save();
	}

	@Override
	public void retrieveMaterialSelected(OrdemProducaoMateriais material) {
		//do nothing
	}

	public List<ECategoriaProduto> getCategorias() {
		List<ECategoriaProduto> list = Arrays.asList(ECategoriaProduto.values());
		Collections.sort(list, (x1, x2) -> x1.getDesc().compareTo(x2.getDesc()));
		
		return list;
	}

	public List<ProdutoSubproduto> getProdutoSubprodutos() {
		return produtoSubprodutos;
	}

	public void setProdutoSubprodutos(List<ProdutoSubproduto> produtoSubprodutos) {
		this.produtoSubprodutos = produtoSubprodutos;
	}

	@Override
	public void retrieveMaterialSelecteds(List<OrdemProducaoMateriais> materiais) {
		try {
			
			if (materiais != null && materiais.size() > 0) {
				
				produtoSubprodutos = ejb.parseToProdutoSubprodutos(entityBean, materiais);
				
				ordemImportada = true;
			}
			
		} catch (Exception e) {
			
			if (e instanceof NoResultException) {
				addInfoMessage("Ordem importada com sucesso! Essa ordem não possui materiais ou subprodutos.");
			}
			else{
				addErrorMessage(e.getMessage());
			}
			
		}
	}

	public boolean isOrdemImportada() {
		return ordemImportada;
	}

	public void setOrdemImportada(boolean ordemImportada) {
		this.ordemImportada = ordemImportada;
	}
	
}
