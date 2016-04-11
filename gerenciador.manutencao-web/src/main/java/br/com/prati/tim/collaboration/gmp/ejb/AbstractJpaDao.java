package br.com.prati.tim.collaboration.gmp.ejb;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.uaihebert.uaicriteria.UaiCriteria;
import com.uaihebert.uaicriteria.UaiCriteriaFactory;

public abstract class AbstractJpaDao<T> {

	@Inject
	protected EntityManager em;

	protected Class<T> entityClass;

	public AbstractJpaDao() {
	}

	public Class<T> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	public EntityManager getEm() {
		return em;
	}

	@SuppressWarnings("rawtypes")
	public AbstractJpaDao setEm(EntityManager em) {
		this.em = em;
		return this;
	}

	public T persist(T item) {

		if (item == null) {
			throw new PersistenceException("Item may not be null");
		}

		em.persist(item);

		return item;
	}

	public T update(T item) {

		if (item == null) {
			throw new PersistenceException("Item may not be null");
		}

		return em.merge(item);
	}

	public List<T> getAll() {

		UaiCriteria<T> uaiCriteria = UaiCriteriaFactory.createQueryCriteria(em, getEntityClass());

		return uaiCriteria.getResultList();

	}

	public T getById(Long id) {
		if (id == null || id < 1) {
			throw new PersistenceException("Id may not be null or negative");
		}

		return em.find(getEntityClass(), id);
	}

	public void delete(T item) {

		if (item == null) {
			throw new PersistenceException("Item may not be null");
		}

		em.remove(em.merge(item));
	}

	public List<T> findLikeByAttributes(Map<String, String> attributes) {

		UaiCriteria<T> easyCriteria = UaiCriteriaFactory.createQueryCriteria(em, getEntityClass());

		for (Entry<String, String> entry : attributes.entrySet()) {
			easyCriteria.andStringLike(true, entry.getKey(), "%" + entry.getValue() + "%");
		}

		return easyCriteria.getResultList();
	}

	public abstract List<T> listActives();

	
	public abstract List<T> getWithLimit(Integer limit, Boolean status);

	public abstract List<T> getWithQueryLike(String descQuery, Boolean status);

	public abstract T alterarSituacao(T object);

	public abstract void exluir(T object) throws Exception;

	public abstract T salvar(T object) throws Exception;
	
	public boolean validaCadastroExistente(String desc){
		return true;
	};

	public boolean validaCadastroExistente(Map<String, Object> attributes) {
		return true;
	}

}