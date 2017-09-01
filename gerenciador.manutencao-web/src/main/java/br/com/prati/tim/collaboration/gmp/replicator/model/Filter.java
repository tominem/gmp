package br.com.prati.tim.collaboration.gmp.replicator.model;

import javax.xml.bind.annotation.XmlTransient;

public class Filter {
	
	/**
	 * possible values: where and left_join
	 */
	private String type = "where";
	
	@XmlTransient
	private Table table;
	
	private String tableName = "";
	
	@XmlTransient
	private Table parentTable;
	
	private String value = "";
	
	private String filterColumn = "";
	
	private String parentPkColumn = "";
	
	public Filter() {
		// TODO Auto-generated constructor stub
	}
	
	public Filter(String type, String tableName, String value,
			String filterColumn) {
		this.type = type;
		this.tableName = tableName;
		this.value = value;
		this.filterColumn = filterColumn;
	}

	public Filter(String type, String tableName, Table parentTable, String value,
			String filterColumn, String parentPkColumn, Table table) {
		this.type = type;
		this.tableName = tableName;
		this.parentTable = parentTable;
		this.value = value;
		this.filterColumn = filterColumn;
		this.parentPkColumn = parentPkColumn;
		this.table = table;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public String getFilterColumn() {
		return filterColumn;
	}

	public void setFilterColumn(String filterColumn) {
		this.filterColumn = filterColumn;
	}

	public String getParentPkColumn() {
		return parentPkColumn;
	}

	public void setParentPkColumn(String parentPkColumn) {
		this.parentPkColumn = parentPkColumn;
	}

	public Table getParentTable() {
		return parentTable;
	}

	public void setParentTable(Table parentTable) {
		this.parentTable = parentTable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	@Override
	public String toString() {
		return "Filter [type=" + type + ", table=" + table + ", tableName="
				+ tableName + ", parentTable=" + parentTable + ", value="
				+ value + ", filterColumn=" + filterColumn
				+ ", parentPkColumn=" + parentPkColumn + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((parentTable == null) ? 0 : parentTable.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Filter other = (Filter) obj;
		if (parentTable == null) {
			if (other.parentTable != null)
				return false;
		} else if (!parentTable.equals(other.parentTable))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}



	/**
	 * ===========================================================================================
	 * 
	 *	BUILDER 
	 * 
	 * ===========================================================================================
	 * 
	 * @author ewerton.costa
	 *
	 */
	
	public static class Builder{
		
		private String type = "where";
		
		private String tableName;

		private Table table;
		
		private Table parentTable;
		
		private String value;
		
		private String filterColumn;
		
		private String parentPkColumn;
		
		public Builder type(String type){
			this.type = type;
			return this;
		}
		
		public Builder tableName(String tableName){
			this.tableName = tableName;
			return this;
		}
		
		public Builder parentTable(Table parentTable){
			this.parentTable = parentTable;
			return this;
		}
		
		public Builder value(String value){
			this.value = value;
			return this;
		}
		
		public Builder filterColumn(String filterColumn){
			this.filterColumn = filterColumn;
			return this;
		}

		public Builder parentPkColumn(String parentPkColumn){
			this.parentPkColumn = parentPkColumn;
			return this;
		}

		public Builder table(Table table){
			this.table = table;
			return this;
		}
		
		public Filter build(){
			return new Filter(type, tableName, parentTable, value, filterColumn, parentPkColumn, table);
		}
		
	}

}
