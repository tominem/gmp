package br.com.prati.tim.collaboration.gmp.dao.produto;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.uaihebert.uaicriteria.UaiCriteria;

import br.com.prati.tim.collaboration.gmp.dao.AbstractJPADAO;
import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.ProdutoMaquina;
import br.prati.tim.collaboration.gp.jpa.ProdutoSubproduto;

public class ProdutoDAOImpl extends AbstractJPADAO<Produto> implements ProdutoDAO{

	public ProdutoDAOImpl() {
		super(Produto.class);
	}

	@Override
	public List<Produto> findByMaquina(Maquina maquina) throws Exception {
		List<ProdutoMaquina> resultList = createQueryCriteria(getEntityManager(), ProdutoMaquina.class)
				.andEquals("maquina", maquina)
				.innerJoinFetch("produto")
				.innerJoinFetch("maquina")
				.getResultList();
		
		List<Produto> produtos = resultList.stream().map(ProdutoMaquina::getProduto).collect(Collectors.toList());
		
		return produtos != null ? produtos : new ArrayList<Produto>();
	}

	@Override
	public List<ProdutoSubproduto> findProdutoSubproduto(Produto produto) throws Exception {
		
		List<ProdutoSubproduto> resultList = createQueryCriteria(getEntityManager(), ProdutoSubproduto.class)
				.andEquals("produto", produto)
				.innerJoinFetch("produto")
				.innerJoinFetch("subproduto")
				.getResultList();
		
		
		return resultList;
	}

	@Override
	public List<Produto> findByMaquinaFetchProdutoMaquina(Maquina maquina) throws Exception {
		
		List<Produto> resultList = createQueryCriteria(getEntityManager(), Produto.class)
				.setDistinctTrue()
				.innerJoinFetch("produtoMaquinas")
				.andEquals("produtoMaquinas.maquina", maquina)
				.getResultList();
		
		return resultList;
	}

	@Override
	public List<Produto> findByMaquinaWithLimit(
			int limit,
			Optional<Boolean> statusValue, 
			FilterParam<?>[] filterParams,
			Maquina maquina) {

		UaiCriteria<Produto> criteria = createQueryCriteria(getEntityManager(), getEntityClass());
		criteria.innerJoin("produtoMaquinas");
		
		if (filterParams != null) {
			Arrays.asList(filterParams).forEach(fp -> {
				
				handleJoinClause(criteria, fp);
				
			});
		}

		criteria.andEquals("produtoMaquinas.maquina", maquina);
				
		statusValue.ifPresent( sv -> criteria.andEquals(getStatusAttrName(), sv) );
		
		ordenate(Arrays.asList(filterParams), criteria);
		
		List<Produto> resultList = criteria.setMaxResults(limit).getResultList();
		
		return resultList;
	}

	@Override
	public List<Produto> findLikeOrNotLikeWithLimit(
			String pattern,
			Optional<Integer> limit, 
			Optional<Boolean> active,
			FilterParam<?>[] filterParams, 
			Maquina maquina) {
		
		List<FilterParam<?>> filters = Arrays.asList(filterParams);
		
		UaiCriteria<Produto> criteria = handleWhereClause(pattern, filters);
		
		criteria.innerJoin("produtoMaquinas");

		criteria.andEquals("produtoMaquinas.maquina", maquina);
		
		active.ifPresent(b -> criteria.andEquals(getStatusAttrName(), b));
		
		limit.ifPresent(maxResults -> criteria.setMaxResults(maxResults));
		
		ordenate(filters, criteria);
		
		return criteria.getResultList();
	}
	
	

}
