package br.com.prati.tim.collaboration.gmp.ejb.tipocomponente;

import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createMultiSelectCriteria;
import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.prati.tim.collaboration.gmp.ejb.AbstractJpaDao;
import br.com.prati.tim.collaboration.gmp.ex.tipocomponente.VinculoTipoComponenteEhFuncaoConfigException;
import br.prati.tim.collaboration.gp.jpa.TipoComponente;

import com.uaihebert.uaicriteria.UaiCriteria;

@Stateless
public class CadTipoComponenteEJB extends AbstractJpaDao<TipoComponente>{
	
	@Inject
	private TimeZone defaultTimeZone;

	@Override
	public List<TipoComponente> listActives() {
		
		UaiCriteria<TipoComponente> criteria = createQueryCriteria(em, TipoComponente.class);

		criteria.andEquals("status", Boolean.TRUE);
		
		return criteria.getResultList();
	}

	@Override
	public List<TipoComponente> getWithLimit(Integer limit, Boolean status) {
		
		UaiCriteria<TipoComponente> criteria = createQueryCriteria(em, TipoComponente.class);

		if (status != null) {
			criteria.andEquals("status", status);
		}

		criteria.setMaxResults	(limit);
		
		criteria.orderByAsc		("descricao");
		
		return criteria.getResultList();
	}

	@Override
	public List<TipoComponente> getWithQueryLike(String descQuery, Boolean status) {

		UaiCriteria<TipoComponente> criteria = createQueryCriteria(em, TipoComponente.class);

		criteria.andStringLike(true, "descricao", "%" + descQuery + "%");

		if (status != null) {
			criteria.andEquals("status", status);
		}
		
		criteria.orderByAsc("descricao");
		
		return criteria.getResultList();
	}

	@Override
	public TipoComponente alterarSituacao(TipoComponente tpComponente) {

		tpComponente.setStatus(!tpComponente.getStatus());

		return update(tpComponente);

	}

	@Override
	public void exluir(TipoComponente object) throws VinculoTipoComponenteEhFuncaoConfigException {
		
		validarVinculoComFuncaoConfig(object);
		
		delete(object);
		
	}
	
	private void validarVinculoComFuncaoConfig(TipoComponente object) throws VinculoTipoComponenteEhFuncaoConfigException {
		
		UaiCriteria<TipoComponente> criteria = createMultiSelectCriteria(em, TipoComponente.class);
		
		criteria.innerJoin		("funcaoConfigs");
		criteria.countAttribute	("funcaoConfigs.tipoComponente");
		
		criteria.andEquals		("idTipoComponente", object.getIdTipoComponente());
	
        Long result = (Long) criteria.getMultiSelectResult().get(0);

        if(result > 0) {
        	throw new VinculoTipoComponenteEhFuncaoConfigException();
        }
		
	}

	@Override
	public Class<TipoComponente> getEntityClass() {
		return TipoComponente.class;
	}

	@Override
	public TipoComponente salvar(TipoComponente cadTipoComponente) {

		if (cadTipoComponente.getIdTipoComponente() == null) {
			cadTipoComponente.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			cadTipoComponente.setStatus(Boolean.TRUE);
		}

		return update(cadTipoComponente);
	}

	@Override
	public boolean validaCadastroExistente(Map<String, Object> attributes) {

		UaiCriteria<TipoComponente> criteria = createMultiSelectCriteria(em, TipoComponente.class)
				.countAttribute	("idTipoComponente");
		
		attributes.forEach((k,v) -> criteria.orEquals(true, k, v.toString().toLowerCase()));
		
		return (Long) criteria.getMultiSelectResult().get(0) > 0;
	}

}
