package br.com.prati.tim.collaboration.gmp.dao;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.EQUAL;
import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;

import com.uaihebert.uaicriteria.UaiCriteria;

public abstract class AbstractJPADAO<T> implements GenericDAO<T>{

	@Inject
	private EntityManager em;
	
	private Class<T> clazz;
	
	/**
	 * Attribute name of the flag that indicates if the entity is active or not
	 */
	protected String statusAttrName = "status";
	
	public AbstractJPADAO(Class<T> clazz) {
		this.clazz =  clazz;
	}

	@Override
	public Class<T> getEntityClass() {
		return clazz;
	}
	
	@Override
	public void delete(T item) throws PersistenceException {
		
		try {
			if (item == null) {
				throw new PersistenceException("Item may not be null");
			}

			em.remove(em.merge(item));
			em.flush();
			
		} catch (PersistenceException e) {
			
			isConstraintViolationException(e);
			
		}
	}
	
	/**
	 * Handle ConstraintViolatioException
	 * 
	 * @param e
	 * @throws PersistenceException
	 */
	private void isConstraintViolationException(PersistenceException e) throws PersistenceException{
		Throwable t = e.getCause();
		while ((t != null) && !(t instanceof ConstraintViolationException)) {
		    t = t.getCause();
		}
		if (t instanceof ConstraintViolationException) {
			throw new PersistenceException(t.getCause().getMessage());
		}
	}

	@Override
	public GenericDAO<T> setEntityManager(EntityManager em) {
		
		this.em = em;
		
		return this;
	}

	@Override
	public T persist(T item) {
		
		if (item == null) {
			throw new PersistenceException("Item may not be null");
		}

		em.persist(item);

		return item;
	}

	@Override
	public T update(T item) {
		
		if (item == null) {
			throw new PersistenceException("Item may not be null");
		}

		return em.merge(item);
	}

	@Override
	public List<T> findAll() {
		return createQueryCriteria(em, getEntityClass()).getResultList();
	}
	
	@Override
	public T activeOrInactive(T object) {
		
		try {
			
			Method getMethod = object.getClass().getMethod("get" + StringUtils.capitalize(statusAttrName));
			
			Boolean lastStatus = (Boolean) getMethod.invoke(object);
			
			Method setMethod = object.getClass().getMethod("set" + StringUtils.capitalize(statusAttrName), Boolean.class);
			
			setMethod.invoke(object, !lastStatus);
			
			return update(object);			
			
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return object;
	}

	@Override
	public T findById(Long id) {
		
		if (id == null || id < 1) {
			throw new PersistenceException("Id may not be null or negative");
		}

		return em.find(getEntityClass(), id);
	}
	
	@Override
	public List<T> findLikeOrNotLike(String pattern, Optional<Boolean> active, FilterParam<?> ... filterParams) {
		return findLikeOrNotLikeWithLimit(pattern, null, active, filterParams);
	}
	
	@Override
	public List<T> findAllWithLimit(Optional<Boolean> statusValue) {
		
		UaiCriteria<T> criteria = createQueryCriteria(em, getEntityClass());
				
		statusValue.ifPresent( sv -> criteria.andEquals(getStatusAttrName(), sv));
		
		List<T> resultList = criteria.getResultList();
		
		return resultList;
	}

	@Override
	public String getStatusAttrName() {
		return statusAttrName;
	}
	
	@Override
	public void setStatusAttrName(String statusAttrName) {
		this.statusAttrName = statusAttrName;
	}

	@Override
	public List<T> findActives() {
		UaiCriteria<T> criteria = createQueryCriteria(em, getEntityClass());
		
		if (statusAttrName != null) {
			criteria.andEquals(getStatusAttrName(), true);
		}
		
		return criteria.getResultList();
	}

	@Override
	public List<T> findLikeOrNotLikeWithLimit(String pattern, Optional<Integer> limit, Optional<Boolean> active, FilterParam<?> ... filterParams) {
		
		List<FilterParam<?>> filters = Arrays.asList(filterParams);
		
		UaiCriteria<T> criteria = createQueryCriteria(em, getEntityClass());
		
		filters.forEach(f -> {
			
			if (f.getCriteria() == EQUAL) {
				
				try {
					
					criteria.orEquals(f.getFieldName(), f.parsePattern(pattern));
				
				} catch (NumberFormatException e) {
					//do nothing
				}
				
			}
			
			else{
				criteria.orStringLike(f.getFieldName(), f.parsePattern(pattern).toString());
			}
			
		});

		active.ifPresent(b -> criteria.andEquals(getStatusAttrName(), b));
		
		limit.ifPresent(maxResults -> criteria.setMaxResults(maxResults));
		
		List<FilterParam<?>> ordernates = filters.stream().filter(f -> f.getOrder() != FilterOrder.NONE).collect(Collectors.toList());
		
		ordernates.forEach(f -> {
			
			if (f.getOrder() == FilterOrder.ASC) {
				criteria.orderByAsc(f.getFieldName());
			}
			
			else{
				criteria.orderByDesc(f.getFieldName());
			}
			
		});
		
		return criteria.getResultList();
	}

}