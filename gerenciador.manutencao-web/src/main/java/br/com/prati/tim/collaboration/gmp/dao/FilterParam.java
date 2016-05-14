package br.com.prati.tim.collaboration.gmp.dao;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCase.IGNORE_CASE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterCase.UPPER_CASE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.AFTER_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BEFORE_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.BOTH_LIKE;
import static br.com.prati.tim.collaboration.gmp.dao.FilterType.STRING;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.FetchType;

/**
 * This is a Filter to be used in dynamic query methods
 * to set up search parameters with or without <b>LIKE</b> clause
 * 
 * @author ewerton.costa
 *
 */
public class FilterParam<T> {

	private String name;
	
	private FilterCriteria criteria = BOTH_LIKE;

	private FilterCase filterCase = IGNORE_CASE;
	
	private FilterType type = STRING;

	private String fieldName;
	
	private FilterOrder order = FilterOrder.NONE;
	
	private FetchType fetchType = FetchType.LAZY;
	
	public FilterParam() {}
	
	public FilterParam(String name, String fieldName, FilterType type, FilterCase filterCase, FilterCriteria criteria, FilterOrder order) {
		this.name = name;
		this.fieldName = fieldName;
		this.type = type;
		this.filterCase = filterCase;
		this.criteria = criteria;
		this.order = order;
	}
	
	public FilterParam(String name, FilterCriteria criteria,
			FilterCase filterCase, FilterType type, String fieldName,
			FilterOrder order, FetchType fetchType) {
		this.name = name;
		this.criteria = criteria;
		this.filterCase = filterCase;
		this.type = type;
		this.fieldName = fieldName;
		this.order = order;
		this.fetchType = fetchType;
	}

	public FilterParam(String name, String fieldName, FilterCriteria criteria, FilterOrder order) {
		this.name = name;
		this.fieldName = fieldName;
		this.criteria = criteria;
		this.order = order;
	}
	
	public FilterParam(String name, String fieldName, FilterCriteria criteria, FilterType type) {
		this.name = name;
		this.fieldName = fieldName;
		this.criteria = criteria;
		this.type = type;
	}
	
	public FilterParam(String name, String fieldName, FilterCriteria criteria) {
		this.name = name;
		this.fieldName = fieldName;
		this.criteria = criteria;
	}

	public FilterParam(String name, String fieldName, FilterCriteria criteria, FetchType fetchType) {
		this.name = name;
		this.fieldName = fieldName;
		this.criteria = criteria;
		this.fetchType = fetchType;
	}
	
	public FetchType getFetchType() {
		return fetchType;
	}

	public void setFetchType(FetchType fetchType) {
		this.fetchType = fetchType;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public FilterCriteria getCriteria() {
		return criteria;
	}

	public void setCriteria(FilterCriteria criteria) {
		this.criteria = criteria;
	}

	public FilterCase getFilterCase() {
		return filterCase;
	}

	public void setFilterCase(FilterCase filterCase) {
		this.filterCase = filterCase;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public FilterOrder getOrder() {
		return order;
	}

	public void setOrder(FilterOrder order) {
		this.order = order;
	}
	
	public void setType(FilterType type) {
		this.type = type;
	}
	
	public FilterType getType() {
		return type;
	}
	
	public Object parsePattern(String pattern) throws NumberFormatException{
		
		if (type == STRING) {

			return new StringBuilder()		
					.append(criteria == BOTH_LIKE || criteria == AFTER_LIKE ? "%" : "")
					.append(filterCase == IGNORE_CASE ? pattern : filterCase == UPPER_CASE ? pattern.toUpperCase() : pattern.toLowerCase())
					.append(criteria == BOTH_LIKE || criteria == BEFORE_LIKE ? "%" : "")
					.toString();
			
		}
		
		else{
			
			if (type == FilterType.INTEGER) 	return Integer.parseInt(pattern);
			if (type == FilterType.LONG) 		return Long.valueOf(pattern);
			if (type == FilterType.BIGINT) 		return new BigInteger(pattern);
			if (type == FilterType.BIGDECIMAL) 	return new BigDecimal(pattern);
			
			return null; 
		}
		
	}

}
