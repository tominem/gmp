package br.com.prati.tim.collaboration.gmp.dao;

/**
 * 
 * @author ewerton.costa
 *
 */
public enum FilterCriteria {
	
	/**
	 * Defines the wildcard both before and after the pattern. 
	 * &nbsp&nbsp<b>e.g. '%<i>pattern</i>%'</b> 
	 */
	BOTH_LIKE,
	
	/**
	 * Defines the wildcard before the pattern.
	 *  &nbsp&nbsp<b>e.g. '%<i>pattern</i>'</b> 
	 */
	AFTER_LIKE,
	
	/**
	 * Defines the wildcard after the pattern.
	 *  &nbsp&nbsp<br><b>e.g. '<i>pattern</i>%'</b> 
	 */
	BEFORE_LIKE,
	
	/**
	 * Finds by the same value you looking for.
	 * &nbsp&nbsp<b>e.g. columnName='<i>pattern</i>'</b>
	 */
	EQUAL

}
