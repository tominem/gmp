package br.com.prati.tim.collaboration.gmp.ejb;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import br.com.prati.tim.collaboration.gmp.dao.CrudDAO;
import br.com.prati.tim.collaboration.gmp.dao.FilterParam;

public interface CrudEJB<T extends Serializable> {

	T save(T entityBean);
	
	CrudDAO<T> getCrudDAO();

	void exclude(T entityBean);
	
	List<T> findAllWithLimit(Optional<Boolean> statusValue);
	
	List<T> findLikeOrNotLikeWithLimit(String pattern, Optional<Integer> limit, Optional<Boolean> active, FilterParam<?> ... filterParams);
	
}
