package br.com.prati.tim.collaboration.gmp.replicator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class TableMap {

	@XmlAttribute(name="tableName")
	private String table;
	
	private ColumnList columns;
	
	public TableMap() {
	}
	
	public TableMap(String table, String ... columns) {
		this.table = table;
		
		this.columns = new ColumnList();
		
		Optional.ofNullable(columns)
			.map(Arrays::asList)
			.orElse(new ArrayList<String>())
			.forEach(s -> this.columns.add(s));
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public ColumnList getColumns() {
		return columns;
	}

	public void setColumns(ColumnList columns) {
		this.columns = columns;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((columns == null) ? 0 : columns.hashCode());
		result = prime * result + ((table == null) ? 0 : table.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableMap other = (TableMap) obj;
		if (columns == null) {
			if (other.columns != null)
				return false;
		} else if (!columns.equals(other.columns))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}

	public void set(String tableName, ColumnList columnList) {
		this.table = tableName;
		this.columns = columnList;
	}

}
