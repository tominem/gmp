package br.com.prati.tim.collaboration.gmp.configuracaogeral;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.uaihebert.uaicriteria.UaiCriteria;
import com.uaihebert.uaicriteria.UaiCriteriaFactory;

import br.com.prati.tim.collaboration.gmp.ejb.AbstractJpaDao;
import br.prati.tim.collaboration.gp.jpa.ConfiguracaoGeral;
import br.prati.tim.collaboration.gp.jpa.Maquina;

@Stateless
public class ConfiguracaoGeralEJB extends AbstractJpaDao<ConfiguracaoGeral>{

	@Override
	public List<ConfiguracaoGeral> listActives() {

		UaiCriteria<ConfiguracaoGeral> criteria = UaiCriteriaFactory.createQueryCriteria(em, ConfiguracaoGeral.class);
		
		List<String> sistemas = new ArrayList<String>();
		sistemas.add("IEM");
		sistemas.add("GMP");
		
		criteria.andStringIn	("tagSistema", 	sistemas);

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
		
		List<String> sistemas = new ArrayList<String>();
		sistemas.add("IEM");
		sistemas.add("GMP");
		
		criteria.andStringLike	(true, "chaveConfig", "%" + descQuery + "%");
		criteria.andStringIn	("tagSistema", 	sistemas);
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
		}

		return update(object);
	}

	@Override
	public boolean validaCadastroExistente(String desc) {
		
		UaiCriteria<ConfiguracaoGeral> criteria = UaiCriteriaFactory.createMultiSelectCriteria(em, ConfiguracaoGeral.class);
		
		criteria.countAttribute	("idConfiguracao");
		
		List<String> sistemas = new ArrayList<String>();
		sistemas.add("IEM");
		sistemas.add("GMP");
		
		criteria.andStringIn	("tagSistema", 	sistemas);
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
	
	public ConfiguracaoGeral getByChaveAndSistemas(String chave) throws Exception {
		
		UaiCriteria<ConfiguracaoGeral> criteria = UaiCriteriaFactory.createQueryCriteria(em, ConfiguracaoGeral.class);

		List<String> sistemas = new ArrayList<String>();
		sistemas.add("IEM");
		sistemas.add("GMP");
		
		criteria.andEquals		("chaveConfig", chave);
		criteria.andStringIn	("tagSistema", 	sistemas);
		criteria.orderByAsc		("chaveConfig");
		
		try {
			return criteria.getSingleResult();
		} catch (NoResultException e) {
			throw new Exception("Propriedade não encontrada: " + chave);
		}
	}
	
	public List<ConfiguracaoGeral> getByMaquina(Maquina maquina) throws Exception {
		
		UaiCriteria<ConfiguracaoGeral> criteria = UaiCriteriaFactory.createQueryCriteria(em, ConfiguracaoGeral.class);
		
		List<String> sistemas = new ArrayList<String>();
		sistemas.add("IEM");
		sistemas.add("GMP");
		
		if (maquina == null || maquina.getIdMaquina() == null){
			criteria.andIsNull	("maquina");
		}else{			
			criteria.andEquals	("maquina", 	maquina);
		}
		
		criteria.andStringIn	("tagSistema", 	sistemas);
		criteria.orderByAsc		("chaveConfig");
		
		try {
			return criteria.getResultList();
		} catch (NoResultException e) {
			throw new Exception("Configurações não encontradas para: " + maquina.getTag());
		}
	}
	
	public List<ConfiguracaoGeral> getByMaquinaAndSistema(Maquina maquina, String sistema) throws Exception {
		
		UaiCriteria<ConfiguracaoGeral> criteria = UaiCriteriaFactory.createQueryCriteria(em, ConfiguracaoGeral.class);
		
		if (maquina == null || maquina.getIdMaquina() == null){
			criteria.andIsNull	("maquina");
		}else{			
			criteria.andEquals	("maquina", 	maquina);
		}
		
		if (sistema == null || sistema.isEmpty()){
			criteria.andIsNull	("tagSistema");
		}else{
			criteria.andEquals	("tagSistema", 	sistema);
		}
		
		
		criteria.orderByAsc		("chaveConfig");
		
		try {
			return criteria.getResultList();
		} catch (NoResultException e) {
			throw new Exception("Configurações não encontradas para: " + maquina.getTag());
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

	public void excluir (List<ConfiguracaoGeral> configuracoes){
		for (ConfiguracaoGeral configuracaoGeral : configuracoes) {
			delete(configuracaoGeral);
		}
	}
	
}