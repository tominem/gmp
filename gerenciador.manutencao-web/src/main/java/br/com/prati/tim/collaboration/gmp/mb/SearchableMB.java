package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

public abstract class SearchableMB<T extends Serializable> implements Serializable {

	private static final long	serialVersionUID	= 201119042011L;
	private static final int	MAX_RESULTS			= 20;

	private T					objectSelected;

	private List<T>				objectList;
	
	private Integer				intSituacao	= null;
	
	private String				pattern;
	
	private List<SelectItem>	itemsSituacao;
	
	private Searchable<T> 		searchable;

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void init() {
		
		this.searchable = (Searchable<T>) RequestContext.getCurrentInstance().getAttributes().get("searchable");
		
		search();
		
	}

	public void search() {

		List<T> objectList;

		if (getPattern() == null || getPattern().trim().isEmpty()) {
			objectList = searchable.getCrudEJB().findAllWithLimit(Optional.of(getStatusSituation()));

		} else {

			objectList = searchable.getCrudEJB().findLikeOrNotLikeWithLimit(getPattern(), Optional.of(getMaxResults()), Optional.of(getStatusSituation()), searchable.getFilterParams());
		}

		setObjectList(objectList);

	}

	/**
	 * 
	 * @return O objeto que será retornado o objeto para managed bean que
	 *         invocou a pesquisa
	 */
	public T getObjetoSelecionado() {
		return objectSelected;
	}

	public void setObjetoSelecionado(T objetoSelecionado) {
		this.objectSelected = objetoSelecionado;
	}

	public List<T> getObjectList() {
		return objectList;
	}

	public void setObjectList(List<T> objectList) {
		this.objectList = objectList;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	public void limpar() {

		objectList.clear();

		pattern = null;
		objectSelected = null;

	}

	/**
	 * Métod invocado ao selecionar um objeto na tabela de buscas
	 */
	public void setSelectedObject() {
		RequestContext.getCurrentInstance().closeDialog(objectSelected);
	}

	public Boolean getStatusSituation() {

		return (getIntSituacao() != null && getIntSituacao() != 0) ? intSituacao == 1: null;

	}

	public Integer getIntSituacao() {
		return intSituacao;
	}

	public void setIntSituacao(Integer intSituacao) {
		this.intSituacao = intSituacao;
		search();
	}

	/**
	 * Items da situação
	 * 
	 * @return lista de SelectItem
	 */
	public List<SelectItem> getItemSituacao() {

		if (itemsSituacao == null) {

			itemsSituacao = new ArrayList<SelectItem>();

			itemsSituacao.add(new SelectItem(0, "Ambas"));
			itemsSituacao.add(new SelectItem(1, "Ativo"));
			itemsSituacao.add(new SelectItem(2, "Inativo"));
		}

		return itemsSituacao;
	}

	public Integer getMaxResults() {
		return MAX_RESULTS;
	}
	
}
