package br.com.prati.tim.collaboration.gmp.replicator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Table implements Cloneable{

	private String name;

	private SequencialColumn sequencialColumn;

	private List<String> pkColumns;
	
	private String alias;
	
	private String sequenceName;
	
	private String schema;
	
	private Map<String, Table> parents = new HashMap<String, Table>();
	
	private Map<String, Table> children = new HashMap<String, Table>();
	
	private Map<String, String> foreignColumn = new UnHashMap<String, String>();
	
	private Map<String, Column> columns = new LinkedHashMap<>();
	
	private Set<Filter> filters = new LinkedHashSet<Filter>();

	private List<UniqueIndex> uniqueIndexes = new ArrayList<UniqueIndex>();
	
	private boolean isSynchroninized;
	
	private boolean isAudit;
	
	private boolean isParentAuditTable;

	public Table() {}
	
	
	public Table(String name, String pkColumn) 
	{
		this.name = name;
		this.addPkColumns(pkColumn);
	}

	public Table(String name) 
	{
		this.name = name;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public SequencialColumn getSequencialColumn() 
	{
		return sequencialColumn;
	}

	public void setSequencialColumn(SequencialColumn sequencialColumn) 
	{
		this.sequencialColumn = sequencialColumn;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() 
	{
		return name;
	}
	
	public Object clone() throws CloneNotSupportedException 
	{
        return super.clone();
    }

	public Map<String, Table> getParents() 
	{
		return parents;
	}

	public void setParents(Map<String, Table> parents) 
	{
		this.parents = parents;
	}

	public Map<String, Table> getChildren() 
	{
		return children;
	}

	public void setChildren(Map<String, Table> children) 
	{
		this.children = children;
	}

	public Set<Filter> getFilters() 
	{
		return filters;
	}

	public void setFilters(Set<Filter> filters) 
	{
		this.filters = filters;
	}

	public List<String> getPkColumns() 
	{
		if (pkColumns == null) {
			pkColumns = new ListSet<>();
		}
		
		return pkColumns;
	}

	public void setPkColumns(List<String> pkColumn) 
	{
		this.pkColumns = pkColumn;
	}
	
	public void addPkColumns(String pkColumn)
	{
		getPkColumns().add(pkColumn);
	}

	public Map<String, String> getForeignColumn() 
	{
		return foreignColumn;
	}

	public void setForeignColumn(Map<String, String> foreignColumn) 
	{
		this.foreignColumn = foreignColumn;
	}

	public boolean isSynchroninized() {
		return isSynchroninized;
	}

	public void setSynchroninized(boolean isSynchroninized) 
	{
		this.isSynchroninized = isSynchroninized;
	}

	public String getAlias() 
	{
		if (alias == null && name!= null && !name.isEmpty()) {
			alias = name.substring(0, 1) + new Random().nextInt(999);
		}
		
		return alias;
	}
	
	public String getSequenceName() 
	{
		return sequenceName;
	}

	public void setSequenceName(String sequenceName) 
	{
		this.sequenceName = sequenceName;
	}

	public Map<String, Column> getColumns() 
	{
		return columns;
	}

	public void setColumns(Map<String, Column> columns) 
	{
		this.columns = columns;
	}

	public String getSchema() 
	{
		return schema;
	}

	public void setSchema(String schema) 
	{
		this.schema = schema;
	}
	
	public String getFullTableName()
	{
		String name = null;
		
		if (this.name != null && !this.name.isEmpty()) {
			
			if (schema != null && !schema.isEmpty()) 
			{
				name = schema.concat(".").concat(this.name);
			}
			else
			{
				name = this.name;
			}
		}
		
		return name;
	}

	public boolean isAudit() 
	{
		return isAudit;
	}

	public void setAudit(boolean isAudit) 
	{
		this.isAudit = isAudit;
	}

	public void addUniqueIndexes(UniqueIndex uniqueIndex) 
	{
		
		if (uniqueIndexes == null) {
			uniqueIndexes = new ArrayList<UniqueIndex>();
		}
		
		uniqueIndexes.add(uniqueIndex);
	}
	
	public List<UniqueIndex> getUniqueIndexes() 
	{
		return uniqueIndexes;
	}

	public void setUniqueIndexes(List<UniqueIndex> uniqueIndexes) 
	{
		this.uniqueIndexes = uniqueIndexes;
	}


	public boolean isParentAuditTable() {
		return isParentAuditTable;
	}


	public void setParentAuditTable(boolean isParentAuditTable) {
		this.isParentAuditTable = isParentAuditTable;
	}

}
