package br.com.prati.tim.collaboration.gmp.mb;

import java.io.Serializable;

import br.com.prati.tim.collaboration.gmp.ejb.CrudEJB;

public interface CrudMB<T extends Serializable, P extends Serializable> {
	
	String getResourceDialogPath();
	
	P getEntityId();
	
	void setEntityId(P entityId);
	
	Boolean getEntityStatus();

	void setEntityStatus(Boolean status);
	
	CrudEJB<T> getCrudEJB();
	
	Class<T> getEntityClass();

}
