package br.com.prati.tim.collaboration.gmp.ejb;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;

public abstract class AbstractCrudEJB<T> implements CrudEJB<T> {


	@Override
	public void exclude(T entityBean) throws Exception{
		try {
			getCrudDAO().delete(entityBean);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<T> findAllWithLimit(Optional<Boolean> statusValue) {
		return getCrudDAO().findAllWithLimit(statusValue);
	}

	@Override
	public List<T> findLikeOrNotLikeWithLimit(
			String pattern,
			Optional<Integer> limit, 
			Optional<Boolean> active,
			FilterParam<?>... filterParams) {
		return getCrudDAO().findLikeOrNotLikeWithLimit(pattern, limit, active, filterParams);
	}

	@Override
	public T activateOrInactivate(T entityBean) {
		return getCrudDAO().activeOrInactive(entityBean);
	}

	@Override
	public boolean checkIfExists(Map<String, Object> attributes) {
		return getCrudDAO().checkIfExists(attributes);
	}
	
}
