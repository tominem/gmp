package br.com.prati.tim.collaboration.gmp.ejb.produto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.produto.ProdutoDAO;
import br.com.prati.tim.collaboration.gmp.dao.subproduto.SubprodutoDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;
import br.prati.tim.gmp.ws.ordemproducao.OrdemProducaoMateriais;

@Stateless
public class ProdutoEJBImpl extends AbstractCrudEJB<Produto> implements ProdutoEJB{

	@Inject
	private ProdutoDAO produtoDAO;
	
	@Inject
	private SubprodutoDAO subprodutoDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public Produto save(Produto entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdProduto() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		if (entityBean.getIdProduto() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return produtoDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Produto> getCrudDAO() {
		return produtoDAO;
	}

	@Override
	public List<ProdutoSubproduto> parseToProdutoSubprodutos(Produto produto, List<OrdemProducaoMateriais> materiais) throws Exception {

		List<Integer> codigosSap = materiais.stream().map(OrdemProducaoMateriais::getCodigo).collect(Collectors.toList());
		
		List<Subproduto> encontrados = subprodutoDAO.findByCodigoSap(codigosSap);
		
		return handleProdutoSubprodutos(produto, materiais, encontrados);
	}

	private List<ProdutoSubproduto> handleProdutoSubprodutos(Produto produto, List<OrdemProducaoMateriais> materiais, List<Subproduto> encontrados) {

		ArrayList<ProdutoSubproduto> result = new ArrayList<ProdutoSubproduto>();
		
		materiais.forEach( m -> {
			
			//================================================================
			//	Compara o código sap do objeto vindo do banco com o da O.P.
			//================================================================
			
			Optional<Subproduto> jaCadastrado = encontrados.stream().filter(e -> e.getCodigoSap().equals(m.getCodigo())).findFirst();

			if (jaCadastrado.isPresent()) {
				
				result.add(new ProdutoSubproduto(produto, jaCadastrado.get()));
				
			} 
			
			//================================================================
			//	Se não encontrar suproduto cadastrado ele vincula um para ser 
			//	cadastrado posteriormente no formulário de produto
			//================================================================
			
			else{
				
				Subproduto subproduto = new Subproduto();
				subproduto.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
				subproduto.setDescricao(m.getDescricao());
				subproduto.setCodigoSap(m.getCodigo());
				subproduto.setStatus(true);
				
				result.add(new ProdutoSubproduto(produto, subproduto));
				
			}
			
		});	
		
		return result;
	}

	@Override
	public List<Produto> findByMaquina(Maquina maquina) throws Exception {
		return produtoDAO.findByMaquina(maquina);
	}

	@Override
	public List<ProdutoSubproduto> findProdutoSubproduto(Produto produto) throws Exception {
		return produtoDAO.findProdutoSubproduto(produto);
	}

	@Override
	public List<Produto> findByMaquinaWithLimit(
			int maxResults,
			Optional<Boolean> optStatus, FilterParam<?>[] filterParams,
			Maquina maquina) {
		
		return produtoDAO.findByMaquinaWithLimit(maxResults, optStatus, filterParams, maquina);
	}

	@Override
	public List<Produto> findLikeOrNotLikeWithLimit(String pattern, Optional<Integer> limit, Optional<Boolean> active, FilterParam<?>[] filterParams, Maquina maquina) {
		
		return produtoDAO.findLikeOrNotLikeWithLimit(pattern, limit, active, filterParams, maquina);
	}

	

}
