package br.com.prati.tim.collaboration.gmp.dao;

public interface CrudDAO<T> extends GenericDAO<T>{
	
	/**
	 * Returns the attribute name of the flag that indicates if the entity is active or not
	 * 
	 * @return
	 */
	String getStatusAttrName();

	void setStatusAttrName(String statusAttrName);
	
	T activeOrInactive(T object);

}
