package br.com.prati.tim.collaboration.gmp.replicator.model;

public class UniqueIndex {

	private String table;

	private String columnName;
	
	private ColumnType columnType;

	public UniqueIndex() {}
	
	public UniqueIndex(String table, String columnName, ColumnType columnType) {
		this.table = table;
		this.columnName = columnName;
		this.columnType = columnType;
	}
	
	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
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
		UniqueIndex other = (UniqueIndex) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		if (table == null) {
			if (other.table != null)
				return false;
		} else if (!table.equals(other.table))
			return false;
		return true;
	}

	public ColumnType getColumnType() {
		return columnType;
	}

	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}
	
	public String getValueFormat(String value, boolean withScape){
		String quote = withScape ? "'" : ""; 
		
		String result = value;
		
		if (columnType == ColumnType.CHARACTER) 
		{
			String res = quote + value + quote;
			return res;
		}
	
		return result;
	}
	
}
