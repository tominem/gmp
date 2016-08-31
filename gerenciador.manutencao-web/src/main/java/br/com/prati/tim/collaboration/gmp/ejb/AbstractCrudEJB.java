package br.com.prati.tim.collaboration.gmp.ejb;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.com.prati.tim.collaboration.gmp.dao.FilterParam;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.login.SessionUtil;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

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
	public List<T> findAllWithLimit(Integer limit, Optional<Boolean> status, FilterParam<?> ... filterParams) {
		return getCrudDAO().findAllWithLimit(limit,  status, filterParams);
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
	public T activateOrInactivate(T entityBean) throws Exception {
		return getCrudDAO().activeOrInactive(entityBean);
	}

	@Override
	public List<T> checkIfExists(Map<String, Object> attributes) {
		return getCrudDAO().checkIfExists(attributes);
	}
	
	@Override
	public boolean checkIfExistsBoolean(Map<String, Object> params){
		return getCrudDAO().checkIfExistsBoolean(params);
	}
	
	protected void validatePermission(ETipoAcessoGUM tipoAcesso) throws Exception {
    	
		if (!SessionUtil.temPermissaoGUM(tipoAcesso)){
    		throw new Exception("Usuário sem permissão de " + tipoAcesso.getDescricao() + ".");
    	}
    }
	
}
