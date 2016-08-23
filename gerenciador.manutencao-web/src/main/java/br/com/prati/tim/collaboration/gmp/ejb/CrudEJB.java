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
	
	List<T> findAllWithLimit(Integer limit, Optional<Boolean> status, FilterParam<?> ... filterParams);
	
	List<T> findLikeOrNotLikeWithLimit(String pattern, Optional<Integer> limit, Optional<Boolean> active, FilterParam<?> ... filterParams);

	T activateOrInactivate(T entityBean) throws Exception;
	
	List<T> checkIfExists(Map<String, Object> attributes);

	boolean checkIfExistsBoolean(Map<String, Object> params);

}
