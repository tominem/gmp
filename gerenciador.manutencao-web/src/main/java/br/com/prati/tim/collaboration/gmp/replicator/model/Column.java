package br.com.prati.tim.collaboration.gmp.replicator.model;

public class Column {

	private String columnName;

	private String dataType;

	private String constraintType;
	
	private String columnDefault;

	private ColumnGenericType genericType;
	
	private boolean ignore;

	private int index;

	public Column() {}
	
//	public Column(String columnName, String dataType, String constraintType) {
//		this.columnName = columnName;
//		this.dataType = dataType;
//		this.constraintType = constraintType;
//	}
//	
//	public Column(String columnName, String dataType, String constraintType, String columnDefault) {
//		this.columnName = columnName;
//		this.dataType = dataType;
//		this.constraintType = constraintType;
//		this.columnDefault = columnDefault;
//	}

	public Column(String columnName, String dataType, String constraintType, String columnDefault, ColumnGenericType genericType, int index, boolean ignore) {
		this.columnName = columnName;
		this.dataType = dataType;
		this.constraintType = constraintType;
		this.columnDefault = columnDefault;
		this.setIndex(index);
		this.setGenericType(genericType);
		this.setIgnore(ignore);
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getConstraintType() {
		return constraintType;
	}

	public void setConstraintType(String constraintType) {
		this.constraintType = constraintType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((columnName == null) ? 0 : columnName.hashCode());
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
		Column other = (Column) obj;
		if (columnName == null) {
			if (other.columnName != null)
				return false;
		} else if (!columnName.equals(other.columnName))
			return false;
		return true;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	public ColumnGenericType getGenericType() {
		return genericType;
	}

	public void setGenericType(ColumnGenericType genericType) {
		this.genericType = genericType;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isIgnore() {
		return ignore;
	}

	public void setIgnore(boolean ignore) {
		this.ignore = ignore;
	}
	
}
