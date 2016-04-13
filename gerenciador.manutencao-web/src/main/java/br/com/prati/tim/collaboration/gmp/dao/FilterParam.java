package br.com.prati.tim.collaboration.gmp.dao;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCriteria.*;
import static br.com.prati.tim.collaboration.gmp.dao.FilterType.CHARACTER;

import java.math.BigDecimal;

import static br.com.prati.tim.collaboration.gmp.dao.FilterCase.*;

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
	
	private FilterType type = CHARACTER;

	private String fieldName;
	
	private FilterOrder order = FilterOrder.NONE;
	
	public FilterParam() {}
	
	public FilterParam(String name, String fieldName, FilterType type, FilterCase filterCase, FilterCriteria criteria, FilterOrder order) {
		this.name = name;
		this.fieldName = fieldName;
		this.type = type;
		this.filterCase = filterCase;
		this.criteria = criteria;
		this.order = order;
	}
	
	public FilterParam(String name, String fieldName, FilterCriteria criteria, FilterOrder order) {
		this.name = name;
		this.fieldName = fieldName;
		this.criteria = criteria;
		this.order = order;
	}
	
	public FilterParam(String name, String fieldName, FilterCriteria criteria) {
		this.name = name;
		this.fieldName = fieldName;
		this.criteria = criteria;
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
		
		if (type == CHARACTER) {

			return new StringBuilder()		
					.append(criteria == BOTH_LIKE || criteria == AFTER_LIKE ? "%" : "")
					.append(filterCase == IGNORE_CASE ? pattern : filterCase == UPPER_CASE ? pattern.toUpperCase() : pattern.toLowerCase())
					.append(criteria == BOTH_LIKE || criteria == BEFORE_LIKE ? "%" : "")
					.toString();
			
		}
		
		else{
			
			return new BigDecimal(pattern);
			
		}
		
	}

}