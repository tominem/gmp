package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;

public abstract class SearchableMB<T extends Serializable> implements Serializable {

	public  static final int	MAX_RESULTS			= 20;

	private static final long	serialVersionUID	= 201119042011L;

	private T					objectSelected;

	private List<T>				objectList;
	
	protected Integer			intSituacao	= null;
	
	private String				pattern;
	
	private List<SelectItem>	itemsSituacao;
	
	@PostConstruct
	public void init() {
		
		handleParameters();
		
		search();
	}
	
	protected void handleParameters() {
		
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String onlyActives = params.get("onlyActives");
		
		if (onlyActives != null && Boolean.valueOf(onlyActives)) {
			
			setIntSituacao(1);
			
		}
		
	}

	public abstract String getTitle();
	
	public abstract String getFormName();

	public abstract String getEntityName();

	public void search() {

		List<T> objectList;

		if (getPattern() == null || getPattern().trim().isEmpty()) {
			objectList = getCrudEJB().findAllWithLimit(MAX_RESULTS, Optional.ofNullable(getStatusSituation()), getFilterParams());

		} else {

			objectList = getCrudEJB().findLikeOrNotLikeWithLimit(getPattern(), Optional.ofNullable(getMaxResults()), Optional.ofNullable(getStatusSituation()), getFilterParams());
		}

		setObjectList(objectList);

	}

	public abstract CrudEJB<T> getCrudEJB();

	public abstract FilterParam<?>[] getFilterParams();

	public T getObjectSelected() {
		return objectSelected;
	}

	public void setObjectSelected(T objectSelected) {
		this.objectSelected = objectSelected;
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
