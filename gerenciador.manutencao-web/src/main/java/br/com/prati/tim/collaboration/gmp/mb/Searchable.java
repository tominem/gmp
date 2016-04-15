package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;

import org.primefaces.event.SelectEvent;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;

public interface Searchable<T extends Serializable> {
	
	void search();
	
	void selectObjectAfterSearch(SelectEvent event);
	
	String getResourceDialogPath();
	
	CrudEJB<T> getCrudEJB();
	
}
