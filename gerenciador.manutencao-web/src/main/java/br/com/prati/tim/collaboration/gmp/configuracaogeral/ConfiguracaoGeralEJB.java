package br.com.prati.tim.collaboration.gmp.configuracaogeral;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.prati.tim.collaboration.gmp.ejb.AbstractJpaDao;
import br.prati.tim.collaboration.gp.jpa.ConfiguracaoGeral;

import com.uaihebert.uaicriteria.UaiCriteria;
import com.uaihebert.uaicriteria.UaiCriteriaFactory;

@Stateless
public class ConfiguracaoGeralEJB extends AbstractJpaDao<ConfiguracaoGeral>{

	@Override
	public List<ConfiguracaoGeral> listActives() {

		UaiCriteria<ConfiguracaoGeral> criteria = UaiCriteriaFactory.createQueryCriteria(em, ConfiguracaoGeral.class);
		
		criteria.orEquals	("tagSistema", "PCP");
		criteria.orIsNull	("tagSistema");

		criteria.orderByAsc	("chaveConfig");
		
		return criteria.getResultList();
	}

	@Override
	public List<ConfiguracaoGeral> getWithLimit(Integer limit, Boolean status) {
		
		UaiCriteria<ConfiguracaoGeral> criteria = UaiCriteriaFactory.createQueryCriteria(em, ConfiguracaoGeral.class);

		criteria.orderByAsc		("chaveConfig");
		criteria.setMaxResults	(limit);
		
		return criteria.getResultList();
	}

	@Override
	public List<ConfiguracaoGeral> getWithQueryLike(String descQuery, Boolean status) {

		UaiCriteria<ConfiguracaoGeral> criteria = UaiCriteriaFactory.createQueryCriteria(em, ConfiguracaoGeral.class);
		
		criteria.andStringLike	(true, "chaveConfig", "%" + descQuery + "%");
		
		criteria.orderByAsc		("chaveConfig");
		
		return criteria.getResultList();
	}

	@Override
	public ConfiguracaoGeral alterarSituacao(ConfiguracaoGeral object) {
		throw new UnsupportedOperationException("Não é possível alterar a situação desta entidade.");
	}

	@Override
	public void exluir(ConfiguracaoGeral object){
		delete(object);
	}

	@Override
	public ConfiguracaoGeral salvar(ConfiguracaoGeral object)  {
		
		if (object.getIdConfiguracao() == null) {
			
			object.setDataRegistro	(new Date());
			object.setTagSistema	("PCP");
			
		}

		return update(object);
	}

	@Override
	public boolean validaCadastroExistente(String desc) {
		
		UaiCriteria<ConfiguracaoGeral> criteria = UaiCriteriaFactory.createMultiSelectCriteria(em, ConfiguracaoGeral.class);
		
		criteria.countAttribute	("idConfiguracao");
		
		criteria.andEquals		(true, "chaveConfig", desc.toLowerCase());
        
		Long result = (Long) criteria.getMultiSelectResult().get(0);

		return result > 0;
	}

	public ConfiguracaoGeral getByChave(String chave) throws Exception {
		
		UaiCriteria<ConfiguracaoGeral> criteria = UaiCriteriaFactory.createQueryCriteria(em, ConfiguracaoGeral.class);

		criteria.andEquals		("chaveConfig", chave);
		criteria.orderByAsc		("chaveConfig");
		
		try {
			return criteria.getSingleResult();
		} catch (NoResultException e) {
			throw new Exception("Propriedade não encontrada: " + chave);
		}
	}

	public void salvar(List<ConfiguracaoGeral> configuracoes, List<ConfiguracaoGeral> configuracoesRemover) {
		
		configuracoesRemover.forEach(config -> {
			exluir(config);

		});
		

		configuracoes.forEach(config -> {
			salvar(config);

		});
	}

}