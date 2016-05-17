package br.com.prati.tim.collaboration.gmp.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 * Generic interface 
 * @author ewerton.costa
 *
 * @param <T>
 */
public interface GenericDAO<T> {

	Class<T> getEntityClass();

	GenericDAO<T> setEntityManager(EntityManager em);

	T persist(T item);

	T update(T item) throws Exception;

	List<T> findAll();

	T findById(Long id);

	void delete(T item) throws PersistenceException;
	
	List<T> findAllWithLimit(Optional<Boolean> status, FilterParam<?> ... filterParams);

	/**
	 * Makes the search with a list filters using the LIKE clause or not
	 * 
	 * @param active
	 * 				Status entity activated or not
	 * @param filterParams
	 * 				Filters to set up the sql statement
	 * @return
	 */
	List<T> findLikeOrNotLike(String pattern, Optional<Boolean> active, FilterParam<?> ... filterParams);

	/**
	 * Retrieves only active entities
	 * 
	 * @return
	 */
	List<T> findActives();

	/**
	 * Retrieves elements with a limit number of register across the use of LIKE clause
	 * 
	 * @param limit
	 *            	limit amount of registers
	 * @param status
	 *            	registration status
	 * @param filterParams
	 * 				filters to set up the sql statement		
	 * @return
	 */
	List<T> findLikeOrNotLikeWithLimit(String pattern, Optional<Integer> limit, Optional<Boolean> active, FilterParam<?> ... filterParams);

	String getStatusAttrName();

	void setStatusAttrName(String statusAttrName);
	
	T activeOrInactive(T object) throws Exception;
	
	List<T> checkIfExists(Map<String, Object> attributes);

	boolean checkIfExistsBoolean(Map<String, Object> attributes);
	
}