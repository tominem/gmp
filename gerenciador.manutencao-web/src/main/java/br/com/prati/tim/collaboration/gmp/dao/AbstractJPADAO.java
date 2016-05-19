package br.com.prati.tim.collaboration.gmp.dao;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCase.IGNORE_CASE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterCase.LOWER_CASE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.EQUAL;
import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createMultiSelectCriteria;
import static com.uaihebert.uaicriteria.UaiCriteriaFactory.createQueryCriteria;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;

import br.com.prati.tim.collaboration.gmp.utis.FacesUtis;

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
	
	
	public EntityManager getEntityManager(){
		return em;
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
			
			PersistenceException ex = isConstraintViolationException(e);
			
			if (ex != null) throw ex;
			
		}
	}
	
	/**
	 * Handle ConstraintViolatioException
	 * 
	 * @param e
	 * @throws PersistenceException
	 */
	private PersistenceException isConstraintViolationException(PersistenceException e) throws PersistenceException{
		Throwable t = e.getCause();
		while ((t != null) && !(t instanceof ConstraintViolationException)) {
		    t = t.getCause();
		}
		if (t instanceof ConstraintViolationException) {
			return new PersistenceException(FacesUtis.translateMessage(t.getCause().getMessage()));
		}
		
		return null;
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
	public T update(T item) throws Exception{
			
		try {
			
			if (item == null) {
				throw new PersistenceException("Item may not be null");
			}

			return em.merge(item);
			
		} catch (Exception e) {
			
			throw e;
			
		}
	}

	@Override
	public List<T> findAll() {
		return createQueryCriteria(em, getEntityClass()).getResultList();
	}
	
	@Override
	public T activeOrInactive(T object) throws Exception {
		
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
	public List<T> findAllWithLimit(Optional<Boolean> statusValue, FilterParam<?> ... filterParams) {
		
		UaiCriteria<T> criteria = createQueryCriteria(em, getEntityClass());
		
		if (filterParams != null) {
			Arrays.asList(filterParams).forEach(fp -> {
				
				handleJoinClause(criteria, fp);
				
			});
		}
				
		statusValue.ifPresent( sv -> criteria.andEquals(getStatusAttrName(), sv) );
		
		ordenate(Arrays.asList(filterParams), criteria);
		
		List<T> resultList = criteria.getResultList();
		
		return resultList;
	}


	private void ordenate(List<FilterParam<?>> filters, UaiCriteria<T> criteria) {
		
		List<FilterParam<?>> ordernates = filters.stream().filter(f -> f.getOrder() != FilterOrder.NONE).collect(Collectors.toList());
		
		ordernates.forEach(f -> {
			
			if (f.getOrder() == FilterOrder.ASC) {
				criteria.orderByAsc(f.getFieldName());
			}
			
			else{
				criteria.orderByDesc(f.getFieldName());
			}
			
		});
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
		
		if (getStatusAttrName() != null) {
			criteria.andEquals(getStatusAttrName(), true);
		}
		
		return criteria.getResultList();
	}

	@Override
	public List<T> findLikeOrNotLikeWithLimit(String pattern, Optional<Integer> limit, Optional<Boolean> active, FilterParam<?> ... filterParams) {
		
		List<FilterParam<?>> filters = Arrays.asList(filterParams);
		
		UaiCriteria<T> criteria = handleWhereClause(pattern, filters);

		active.ifPresent(b -> criteria.andEquals(getStatusAttrName(), b));
		
		limit.ifPresent(maxResults -> criteria.setMaxResults(maxResults));
		
		ordenate(filters, criteria);
		
		return criteria.getResultList();
	}

	private UaiCriteria<T> handleWhereClause(String pattern, List<FilterParam<?>> filters) {
		
		UaiCriteria<T> criteria = createQueryCriteria(em, getEntityClass());
		
		filters.forEach(f -> {
			
			handleJoinClause(criteria, f);
			
			try {
				
				Object parsePattern = f.parsePattern(pattern);
				
				if (parsePattern != null) {
					
					if (f.getCriteria() == EQUAL) {
						
						criteria.orEquals(f.getFieldName(), parsePattern);
						
					}
					
					else{
						
						if (f.getFilterCase() == IGNORE_CASE || f.getFilterCase() == LOWER_CASE) {
							criteria.orStringLike(true, f.getFieldName(),	parsePattern.toString());
						}
						
						else{
							criteria.orStringLike(f.getFieldName(),	parsePattern.toString());
						}
					}
					
				}
				
			} catch (NumberFormatException e) {
				//do nothing
			}
			
		});
		
		return criteria;
	}
	
	/**
	 * Makes auto the join clauses if character <b>.</b> is present in fieldName
	 * 
	 * @param criteria
	 * @param f
	 */
	protected void handleJoinClause(UaiCriteria<T> criteria, FilterParam<?> f) {
		
		if (f == null) return;
		
		if (f.getFieldName().contains(".")) {

			String[] split = f.getFieldName().split("\\.");
			
			if (f.getFetchType() == FetchType.EAGER) {
				criteria.leftJoinFetch(split[0]);
			}
			
			else{
				criteria.leftJoin(split[0]);
			}
			
		}
		
	}
	
	public List<T> findAllOrderByAsc(String attribute){
		return createQueryCriteria(em, getEntityClass())
					.orderByAsc(attribute)
					.getResultList();
	}
	
	public List<T> findActivesOrderByAsc(String attribute){
		return createQueryCriteria(em, getEntityClass())
				.andEquals(getStatusAttrName(), true)
				.orderByAsc(attribute)
				.getResultList();
	}
	
	@Override
	public boolean checkIfExistsBoolean(Map<String, Object> attributes) {

		UaiCriteria<T> criteria = createMultiSelectCriteria(em, getEntityClass())
				.countAttribute	(attributes.entrySet().stream().findFirst().get().getKey());
		
		handleWhereClause(attributes, criteria);
		
		return (Long) criteria.getMultiSelectResult().get(0) > 0;
	}


	private void handleWhereClause(Map<String, Object> attributes,	UaiCriteria<T> criteria) {
		
		attributes.forEach((k,v) -> {
			
			if (v instanceof String) {
				criteria.orEquals(true, k, v.toString());
			}
			
			else{
				criteria.orEquals(k, v);
			}
			
		});
	}
	
	@Override
	public List<T> checkIfExists(Map<String, Object> attributes) {

		UaiCriteria<T> criteria = createQueryCriteria(em, getEntityClass());
		
		handleWhereClause(attributes, criteria);
		
		return criteria.getResultList();
	}


}
