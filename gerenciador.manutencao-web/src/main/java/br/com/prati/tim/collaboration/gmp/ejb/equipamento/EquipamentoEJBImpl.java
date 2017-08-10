package br.com.prati.tim.collaboration.gmp.ejb.equipamento;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.uaihebert.uaicriteria.UaiCriteria;
import com.uaihebert.uaicriteria.UaiCriteriaFactory;

import br.com.prati.tim.collaboration.gmp.dao.GenericDAO;
import br.com.prati.tim.collaboration.gmp.dao.equipamento.EquipamentoDAO;
import br.com.prati.tim.collaboration.gmp.dao.itemconfig.FuncaoConfigDAO;
import br.com.prati.tim.collaboration.gmp.ejb.AbstractCrudEJB;
import br.prati.tim.collaboration.gp.jpa.Equipamento;
import br.prati.tim.collaboration.gp.jpa.FuncaoConfig;
import br.prati.tim.collaboration.gp.jpa.enumerator.ETipoAcessoGUM;

@Stateless
public class EquipamentoEJBImpl extends AbstractCrudEJB<Equipamento> implements EquipamentoEJB{

	@Inject
	private EquipamentoDAO equipamentoDAO;
	
	@Inject
	private FuncaoConfigDAO funcaoConfigDAO;
	
	@Inject
	private TimeZone defaultTimeZone;
	
	@Override
	public Equipamento save(Equipamento entityBean) throws Exception {
		
		ETipoAcessoGUM tipoAcesso = entityBean.getIdEquipamento() == null ? ETipoAcessoGUM.INCLUSAO : ETipoAcessoGUM.ALTERACAO;
		
		validatePermission(tipoAcesso);
		
		if (entityBean.getIdEquipamento() == null) {
			entityBean.setDataRegistro(Calendar.getInstance(defaultTimeZone).getTime());
			entityBean.setStatus(Boolean.TRUE);
		}
	
		return equipamentoDAO.update(entityBean);
	}

	@Override
	public GenericDAO<Equipamento> getCrudDAO() {
		return equipamentoDAO;
	}

	@Override
	public List<FuncaoConfig> findAllFuncaoConfig() {
		return funcaoConfigDAO.findAll();
	}

	@Override
	public List<Equipamento> getWithQueryLike(String descQuery, Boolean status) {

		UaiCriteria<Equipamento> criteria = UaiCriteriaFactory.createQueryCriteria(equipamentoDAO.getEntityManager(),
				Equipamento.class);

		if (status != null) {
			criteria.andEquals("status", status);
		}

		criteria.orStringLike(true, "tag", "%" + descQuery + "%");
		criteria.orStringLike(true, "nome", "%" + descQuery + "%");

		criteria.orderByAsc("descricao");
		criteria.setDistinctTrue();

		return criteria.getResultList();

	}

}
