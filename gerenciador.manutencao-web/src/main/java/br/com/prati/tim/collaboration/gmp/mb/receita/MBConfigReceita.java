package br.com.prati.tim.collaboration.gmp.mb.receita;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.produto.ProdutoEJB;
import br.com.prati.tim.collaboration.gmp.ejb.receita.ConfigReceitaEJB;
import br.com.prati.tim.collaboration.gmp.ejb.receita.ReceitaEJB;
import br.com.prati.tim.collaboration.gmp.ejb.tipoinspecao.TipoInspecaoEJB;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.EquipamentoMaquina;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;
import br.prati.tim.collaboration.gp.jpa.Receita;
import br.prati.tim.collaboration.gp.jpa.SubprodTipoInsp;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.TipoInspecao;
import br.prati.tim.collaboration.gp.jpa.ValorReceita;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Named("mbConfigReceita")
@ViewScoped
public class MBConfigReceita extends AbstractBaseMB implements Serializable {
	
	private static final long serialVersionUID = -5144587074129076687L;

	@Inject
	private ConfigReceitaEJB			ejbConfigReceita;

	@Inject
	private ProdutoEJB					ejbProduto;

	@Inject
	private ReceitaEJB					ejbReceita;

	@Inject
	private TipoInspecaoEJB				ejbTipoInspecao;

	private Maquina						maquina;

	private TipoInspecao				tipoInspecao;

	private List<TipoInspecao>			tiposInspecao;

	private Produto						produto;

	private List<Produto>				produtos;

	private Subproduto					subproduto;

	private List<Subproduto>			subprodutosReceita;

	private List<Receita>				receitas;

	private List<ValorReceita>			valoresReceita;

	private Map<Long, Region>			mapRegiao;

	private List<ProdutoSubproduto>		produtoSubprodutos;

	private List<ProdutoSubproduto>		produtoSubprodutosSelected;

	private List<Produto>				produtosExport;

	@PostConstruct
	public void init(){
		
		setSubprodutosReceita	(new ArrayList<Subproduto>());
		setValoresReceita		(new ArrayList<ValorReceita>());
		setReceitas				(new ArrayList<Receita>());
		setMapRegiao			(new HashMap<Long, Region>());
		setProdutoSubprodutos	(new ArrayList<ProdutoSubproduto>());
		setProdutosExport		(new ArrayList<Produto>());
		setTiposInspecao		(ejbTipoInspecao.getCrudDAO().findActives());
		setProdutos				(new ArrayList<Produto>());
		
	}
	
	public void save(){
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.ALTERACAO)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.ALTERACAO.getDescricao() + ".");
			return;
		}
		
		try {
			ejbConfigReceita.saveValoresReceita(valoresReceita, mapRegiao);
		} catch (Exception e) {
			addErrorMessage(e.getMessage());
			return;
		}
		
		clean();
		
		addInfoMessage("Configuração salva com sucessos.");
		
	}

	public void clean(){
		
		init();
		FacesContext.getCurrentInstance().getViewRoot().getViewMap().clear();
	}
	
	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public void select(SelectEvent event){
		
		Object object = event.getObject();

		if (object != null && object.getClass().getName().equals(Maquina.class.getName())){
			
			Maquina maquinaSelecionada = (Maquina) object;
			
			if (maquinaSelecionada.equals(getMaquina())){
				return;
			}
			
			selectMaquina(maquinaSelecionada);
			
			loadReceita();
		}
		
	}
	
	public void selectTipoInspecao(final AjaxBehaviorEvent event)  {
		selectTipoInspecao(tipoInspecao);
	}
	
	public void selectProduto(final AjaxBehaviorEvent event)  {
		loadReceita();
	}
	
	public void selectSubproduto(final AjaxBehaviorEvent event)  {
		
		if (!SessionUtil.temPermissaoGUM(ETipoAcessoGUM.CONSULTA)){
			UtilsMessage.addErrorMessage("Usuário sem permissão de " + ETipoAcessoGUM.CONSULTA.getDescricao() + ".");
			return;
		}
		
		setValoresReceita(ejbConfigReceita.retornaValoresReceitas(receitas, mapRegiao, subproduto));
		
		try {
			loadProdutoSubprod();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}	
	
	private void selectMaquina(Maquina maquina){
		
		setMaquina(maquina);
		setProduto(null);
		setTipoInspecao(null);
		setSubproduto(null);
		
		try {
			setProdutos(ejbProduto.findByMaquina(maquina));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		loadReceita();
	}
	
	private void selectTipoInspecao(TipoInspecao tipoInspecao) {
		setTipoInspecao(tipoInspecao);
		loadReceita();
	}
	
	private void loadReceita(){
		
		setValoresReceita(new ArrayList<>());
		
		if (maquina != null && tipoInspecao != null){
			
			if (tipoInspecao.getEquipamentoMaquinas() == null || tipoInspecao.getEquipamentoMaquinas().isEmpty()){
				return;
			}
			
			for (EquipamentoMaquina equipamentoMaquina : tipoInspecao.getEquipamentoMaquinas()){
				if (equipamentoMaquina.getMaquina().equals(maquina)){
					setReceitas(ejbReceita.findReceitasFetchByMaquinaAndEquipamentoAndTipoInspecao(maquina, equipamentoMaquina.getEquipamento(), tipoInspecao));
					break;
				}
			}
			
			loadSubprodutos();
			loadComponentes();
			
		}
		
	}
	
	private void loadComponentes(){
		
		for (Receita receita : receitas) {
			receita.getConfigEquipamento().getValorConfigEquip();
		}
		
	}
	
	private void loadSubprodutos(){
		
		produtoSubprodutos.clear();
		setSubproduto(null);
		setSubprodutosReceita(new ArrayList<Subproduto>());
		setMapRegiao(new HashMap<Long, Region>());
		
		if (getReceitas() != null && !getReceitas().isEmpty()){
			
			for (Receita receita : receitas) {
			
				List<Subproduto> subprodutosDoProduto = new ArrayList<>();
				
				try {
					
					List<ProdutoSubproduto> subprodutoProduto 	= ejbProduto.findProdutoSubproduto(produto);
					
					for (ProdutoSubproduto produtoSubproduto : subprodutoProduto) {
						if (!subprodutosDoProduto.contains(subprodutoProduto)){
							subprodutosDoProduto.add(produtoSubproduto.getSubproduto());
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				List<SubprodTipoInsp> subprodTipoInsps = receita.getTipoInspecao().getSubprodTipoInsps();
				
				for (SubprodTipoInsp subprodTipoInsp : subprodTipoInsps) {
					
					if (!subprodutosReceita.contains(subprodTipoInsp.getSubproduto()) &&
							subprodutosDoProduto.contains(subprodTipoInsp.getSubproduto()) &&
							subprodTipoInsp.getTipoInspecao().getIdTipoInspecao() == getTipoInspecao().getIdTipoInspecao()){
						subprodutosReceita.add(subprodTipoInsp.getSubproduto());
					}
				}
				
			}
			
		}
	}
	
	public void OpenTreeSubproduto() throws Exception{
		
		loadProdutoSubprod();
		
		RequestContext.getCurrentInstance().execute("PF('exportDialog').show()");
		
	}
	
	private void loadProdutoSubprod() throws Exception{
		
		produtosExport.clear();
		produtoSubprodutos.clear();
		
		
		List<Subproduto> subprodReceita = new ArrayList<>();
		
		if (getReceitas() != null && !getReceitas().isEmpty()){
			
			for (Receita receita : receitas) {
			
				List<SubprodTipoInsp> subprodTipoInsps = receita.getTipoInspecao().getSubprodTipoInsps();
				
				for (SubprodTipoInsp subprodTipoInsp : subprodTipoInsps) {
					
					if (!subprodReceita.contains(subprodTipoInsp.getSubproduto())){
						subprodReceita.add(subprodTipoInsp.getSubproduto());
					}
				}
				
			}
			
		}
		
		List<Produto> produtosByMaquina = ejbProduto.findByMaquina(maquina);
		
		for (Produto produto : produtosByMaquina) {
			
			List<ProdutoSubproduto> findProdutoSubproduto = ejbProduto.findProdutoSubproduto(produto);
			
			if (findProdutoSubproduto != null && !findProdutoSubproduto.isEmpty()){
				produtosExport.add(produto);
			}
			for (ProdutoSubproduto produtoSubproduto : findProdutoSubproduto) {
				
				if (subprodReceita.contains(produtoSubproduto.getSubproduto()) &&
						!produtoSubproduto.getSubproduto().getIdSubproduto().equals(subproduto.getIdSubproduto())){
					produtoSubprodutos.add(produtoSubproduto);
				}
			}
						
		}
		
	}
	
	public void exportar(){
		
		try {
			ejbConfigReceita.exportarConfiguracao(produtoSubprodutosSelected, mapRegiao, receitas, valoresReceita);
		} catch (Exception e) {
			addErrorMessage("Não foi possível exportar configuração. " + e.getMessage());
			return;
		}
		
		setProdutoSubprodutosSelected(new ArrayList<>());
		
		addInfoMessage("Exportação realizada com sucesso!");
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
	
	public Maquina getMaquina() {
		return maquina;
	}
	
	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}
	
	public List<Subproduto> getSubprodutosReceita() {
		return subprodutosReceita;
	}
	
	public void setSubprodutosReceita(List<Subproduto> subprodutosReceita) {
		this.subprodutosReceita = subprodutosReceita;
	}
	
	public List<Receita> getReceitas() {
		return receitas;
	}	
	
	public void setReceitas(List<Receita> receitas) {
		this.receitas = receitas;
	}
	
	public List<ValorReceita> getValoresReceita() {
		return valoresReceita;
	}

	public void setValoresReceita(List<ValorReceita> valoresReceita) {
		this.valoresReceita = valoresReceita;
	}
	
	public Map<Long, Region> getMapRegiao() {
		return mapRegiao;
	}
	
	public void setMapRegiao(Map<Long, Region> mapRegiao) {
		this.mapRegiao = mapRegiao;
	}
	
	public List<ProdutoSubproduto> getProdutoSubprodutos() {
		return produtoSubprodutos;
	}
	
	public void setProdutoSubprodutos(List<ProdutoSubproduto> produtoSubprodutos) {
		this.produtoSubprodutos = produtoSubprodutos;
	}
	
	public List<ProdutoSubproduto> getProdutoSubprodutosSelected() {
		return produtoSubprodutosSelected;
	}
	
	public void setProdutoSubprodutosSelected(List<ProdutoSubproduto> produtoSubprodutosSelected) {
		this.produtoSubprodutosSelected = produtoSubprodutosSelected;
	}
	
	public List<Produto> getProdutosExport() {
		return produtosExport;
	}
	
	public void setProdutosExport(List<Produto> produtosExport) {
		this.produtosExport = produtosExport;
	}

	public List<TipoInspecao> getTiposInspecao() {
		return tiposInspecao;
	}
	
	public void setTiposInspecao(List<TipoInspecao> tiposInspecao) {
		this.tiposInspecao = tiposInspecao;
	}
	
	public Produto getProduto() {
		return produto;
	}
	
	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
}
