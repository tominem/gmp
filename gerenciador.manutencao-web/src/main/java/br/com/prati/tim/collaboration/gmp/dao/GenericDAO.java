package br.com.prati.tim.collaboration.gmp.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;

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

	T update(T item);

	List<T> findAll();

	T findById(Long id);

	void delete(T item);
	
	List<T> findAllWithLimit(Optional<Boolean> status);

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

}