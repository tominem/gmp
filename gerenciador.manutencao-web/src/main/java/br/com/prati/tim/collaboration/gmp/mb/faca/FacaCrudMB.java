package br.com.prati.tim.collaboration.gmp.mb.faca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.faca.FacaEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractCrudMB;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.prati.tim.collaboration.gp.jpa.Faca;
import br.prati.tim.collaboration.gp.jpa.FacasProdMaq;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoMaquina;

@Named("facaMB")
@ViewScoped
public class FacaCrudMB extends AbstractCrudMB<Faca, Long>	implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5144587074129076687L;

	
	@Inject
	private FacaEJB ejb;
	
	private Maquina maquina;
	
	private Produto produto;
	
	private Boolean inspecionar;
	
	private List<FacasProdMaq> facaProdMaquinas;
	
	@Override
	@PostConstruct
	public void initObjects() {
		
		load();
		
	}
	
	private void load() {
		
		this.entityBean = new Faca();
		
		setMaquina(new Maquina());
		
		setProduto(new Produto());
		
		facaProdMaquinas = new ArrayList<>();
		
	}
	
	@Override
	public void selectObjectAfterSearch(SelectEvent event) {
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(getEntityClass().getName())) {
			this.entityBean = (Faca) object;
			
			setFacaProdMaquinas(entityBean.getFacasProdMaqs());
			
			showMensagemSucessoConsulta();
		}
	}
	
	@Override
	public String getResourceDialogPath() {
		return "/cadastros/faca/searchFaca.xhtml";
	}

	@Override
	public Long getEntityId() {
		return entityBean.getIdFaca();
	}

	@Override
	public void setEntityId(Long entityId) {
		entityBean.setIdFaca(entityId);
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
	public CrudEJB<Faca> getCrudEJB() {
		return ejb;
	}

	@Override
	public Class<Faca> getEntityClass() {
		return Faca.class;
	}
	
	@Override
	public ValidateComponent[] getValidaComponents() {
		return new ValidateComponent[]{
				
			new ValidateComponent("formCad:nome", "Nome", "descricao")
				
		};
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return super.validate(event);
	}
	
	public void select(SelectEvent event){
		
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(Maquina.class.getName())){
			
			Maquina maquinaSelecionada = (Maquina) object;
			
			if (maquinaSelecionada.equals(getMaquina())){
				return;
			}
			
			setMaquina(maquinaSelecionada);
			
			setProduto(null);
			
		}
		
		else if(object != null && object.getClass().getName().equals(Produto.class.getName())){
			
			Produto produtoSelecionado = (Produto) object;
			
			if (produtoSelecionado.equals(getProduto())){
				return;
			}
			
			setProduto(produtoSelecionado);
			
		}
		
	}
	
	@Override
	public void save() {
		
		if (getFacaProdMaquinas() != null && getFacaProdMaquinas().size() > 0) {
			
			entityBean.setFacasProdMaqs(new ArrayList<>());
			entityBean.setFacasProdMaqs(getFacaProdMaquinas());
			entityBean.getFacasProdMaqs().forEach(fpm -> fpm.setFaca(entityBean));
			
		}
		
		super.save();
		
	}
	
	@Override
	public void clean() {
			
		getRequestContext().reset(getFormName());
		
		this.entityBean = new Faca();
		
		setMaquina(new Maquina());
		
		setProduto(new Produto());
		
		facaProdMaquinas = new ArrayList<>();			
			
	}
	
	public void addVinculo(){
		
		FacasProdMaq item = new FacasProdMaq();
		
		ProdutoMaquina prodMaquina = ejb.findProdutoMaquinaByProdutoAndMaquina(getMaquina(), getProduto());
		
		item.setProdutoMaquina(prodMaquina);
		item.setInspecionar(getInspecionar());
		
		facaProdMaquinas.add(item);
		
		limpaPainelVinculos();
	}
	
	private void limpaPainelVinculos() {

		setMaquina(null);
		
		setProduto(null);
		
		setInspecionar(false);
		
	}

	public void removeVinculo(FacasProdMaq item){
		
		facaProdMaquinas.remove(item);
		
	}
	
	public void selectProduto(Produto produto){
		setProduto(produto);
		setInspecionar(true);
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<FacasProdMaq> getFacaProdMaquinas() {
		return facaProdMaquinas;
	}

	public void setFacaProdMaquinas(List<FacasProdMaq> facaProdMaquinas) {
		this.facaProdMaquinas = facaProdMaquinas;
	}

	public Boolean getInspecionar() {
		return inspecionar;
	}

	public void setInspecionar(Boolean inspecionar) {
		this.inspecionar = inspecionar;
	}

}
