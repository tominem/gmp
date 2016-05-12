package br.com.prati.tim.collaboration.gmp.ejb.subproduto;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.subproduto.SubprodutoDAO;
import br.com.prati.tim.collaboration.gmp.dao.unidade.UnidadeDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Subproduto;
import br.prati.tim.collaboration.gp.jpa.Unidade;

@Stateless
public class SubprodutoEJBImpl extends AbstractCrudEJB<Subproduto> implements SubprodutoEJB{

	@Inject
	private SubprodutoDAO subprodutoDAO;
	
	@Inject
	private UnidadeDAO unidadeDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public Subproduto save(Subproduto entityBean) throws Exception {
		if (entityBean.getIdSubproduto() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
		
		return subprodutoDAO.update(entityBean);
	}
	
	/**
	 * Foi necessário a sobrescrita do método porque no JPQ não é permitido casting de objetos e 
	 * e na tela de busca é necessário o filtro por códigoSap que é do tipo inteiro e não pode ser 
	 * utilizado na função LIKE do JPQL.
	 */
//	@Override
//	public List<Subproduto> findLikeOrNotLikeWithLimit(String pattern, Optional<Integer> limit, Optional<Boolean> active, FilterParam<?>... filterParams) {
//		
//		FilterParam<?> descricaoFilter = filterParams[1];
//		
//		List<Subproduto> result = super.findLikeOrNotLikeWithLimit(pattern, limit, active, descricaoFilter);
//		
//		if (result != null && result.size() > 0) {
//			result = result.stream().filter(e -> Integer.toString(e.getCodigoSap()).contains(pattern)).collect(Collectors.toList());
//		}
//		
//		return result;
//	}
	
	@Override
	public GenericDAO<Subproduto> getCrudDAO() {
		return subprodutoDAO;
	}

	@Override
	public List<Unidade> findUnidades() {
		return unidadeDAO.findActives();
	}


}
