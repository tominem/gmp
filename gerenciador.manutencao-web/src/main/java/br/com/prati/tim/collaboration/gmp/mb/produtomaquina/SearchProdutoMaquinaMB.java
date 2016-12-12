package br.com.prati.tim.collaboration.gmp.mb.produtomaquina;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.EQUAL;
import static br.com.prati.tim.collaboration.gmp.dao.FilterOrder.ASC;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.dao.FilterType;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;
import br.com.prati.tim.collaboration.gmp.ejb.produto.ProdutoEJB;
import br.com.prati.tim.collaboration.gmp.mb.SearchableMB;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.Produto;
import br.prati.tim.collaboration.gp.jpa.enumerator.ECategoriaProduto;

@Named("searchProdutoMaquinaMB")
@ViewScoped
public class SearchProdutoMaquinaMB extends SearchableMB<Produto> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 632087411912461081L;

	@Inject
	private ProdutoEJB ejb;
	
	private Maquina maquina;
	
	@Override
	@PostConstruct
	public void init() {
		
//		handleParameters();
		
	}
	
	@Override
	public String getTitle() {
		return "Pesquisar Produto";
	}

	@Override
	public String getFormName() {
		return "formPesquisaProduto";
	}

	@Override
	public String getEntityName() {
		return "Produto";
	}
	
	@Override
	public CrudEJB<Produto> getCrudEJB() {
		return ejb;
	}
	
	@Override
	public void search() {
		
		List<Produto> objectList;

		if (getPattern() == null || getPattern().trim().isEmpty()) {
			objectList = ejb.findByMaquinaWithLimit(MAX_RESULTS, Optional.ofNullable(getStatusSituation()), getFilterParams(), getMaquina());

		} else {

			objectList = getCrudEJB().findLikeOrNotLikeWithLimit(getPattern(), Optional.ofNullable(getMaxResults()), Optional.ofNullable(getStatusSituation()), getFilterParams());
		}

		setObjectList(objectList);
		
	}

	@Override
	public FilterParam<?>[] getFilterParams() {
		return new FilterParam<?>[]{
			
			new FilterParam<String>("Código", "codigoSap", EQUAL, FilterType.INTEGER),
			new FilterParam<String>("Descrição", "descricao", BOTH_LIKE, ASC),
			new FilterParam<String>("Categoria", "categoriaProduto", EQUAL, FilterType.ENUM){
				
				//=======================================================
				//	Override filter method because filtertype is an enum
				//=======================================================
				
				public Object parsePattern(String pattern) throws NumberFormatException {
					
					List<ECategoriaProduto> list = Arrays.asList(ECategoriaProduto.values());
					
					Optional<ECategoriaProduto> result = list.stream().filter(e -> e.getDesc().toLowerCase().contains(pattern.toLowerCase())).findFirst();
					
					return result.isPresent() ? result.get() : null;
				};
				
			}
			
		};
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

}
