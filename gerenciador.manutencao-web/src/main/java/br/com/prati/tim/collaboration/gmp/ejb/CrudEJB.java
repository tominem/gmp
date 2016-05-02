package br.com.prati.tim.collaboration.gmp.ejb;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;

public interface CrudEJB<T> {

	T save(T entityBean) throws Exception;
	
	GenericDAO<T> getCrudDAO();

	void exclude(T entityBean) throws Exception;
	
	List<T> findAllWithLimit(Optional<Boolean> statusValue);
	
	List<T> findLikeOrNotLikeWithLimit(String pattern, Optional<Integer> limit, Optional<Boolean> active, FilterParam<?> ... filterParams);

	T activateOrInactivate(T entityBean) throws Exception;
	
	boolean checkIfExists(Map<String, Object> attributes);
	
}
