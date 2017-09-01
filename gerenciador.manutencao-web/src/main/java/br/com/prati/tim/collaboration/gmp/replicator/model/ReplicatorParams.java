package br.com.prati.tim.collaboration.gmp.replicator.model;

import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReplicatorParams implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3105980585320471644L;
	

	private Integer uniqueIdentityID;
	
	/**
	 * Props Local Database
	 */
	@Order(value=1)
	private String localDatabaseName;
	@Order(value=2)
	private String localDatabaseHost;
	@Order(value=3)
	private String localDatabasePort;
	@Order(value=4)
	private String localDatabaseUser;
	@Order(value=5)
	private String localDatabasePass;
                                                                         
	/**                                                                  
	 * Props Remote Database                                           
	 */
	@Order(value=6)
	private String remoteDatabaseName;
	@Order(value=7)
	private String remoteDatabaseHost;
	@Order(value=8)
	private String remoteDatabasePort;
	@Order(value=9)
	private String remoteDatabaseUser;
	@Order(value=10)
	private String remoteDatabasePass;
	
	/**                                                                  
	 * Log, exclude and ignore import tables props                                      
	 */ 
	@Order(value=11)
	@XmlElement(name = "table")
	@XmlElementWrapper(name = "excludeTables")
	private List<String> excludeTable;
	
	@Order(value=12)
	@XmlElement(name = "table")
	@XmlElementWrapper(name = "logTables")
	private List<String> logTables;
	
	@Order(value=13)
	@XmlElement(name = "table")
	@XmlElementWrapper(name = "ignoreImportTables")
	private List<String> ignoreImportTables; 
	
	/**
	 * Ignore tables by regex
	 */
	@Order(value=14)
	private String ignoreImportTablesRegex;
	
	/**
	 * PARENT AUDIT TABLE
	 */
	@Order(value=15)
	private String auditParentTableName;
	
	/**
	 * PARENT AUDIT TABLE SEQUENCE
	 */
	@Order(value=16)
	private String auditParentTableSequence;

	/**
	 * DEFINE UNIQUE_INDEX props
	 */
	@Order(value=17)
	@XmlElement(name = "uniqueIndex")
	@XmlElementWrapper(name = "uniqueIndexes")
	private List<UniqueIndex> uniqueIndexes;
	
	/**
	 * Filters for registration tables
	 */
	@Order(value=18)
	@XmlElement(name = "filter")
	@XmlElementWrapper(name = "filters")
	private List<Filter> filters;
	
	/**
	 * Timer for dispatcher scheduling
	 * <ul>
	 *   <li>Default value is <b>0</b> that means that dispatcher scheduling is disabled</li>
	 * 	 <li>Value greater than <b>0</b> will enable dispatcher scheduling for the given time value delay</li>
	 * </ul>  
	 */
	@Order(value=19)
	private String scheduleDispatcherTimeMillis;
	
	/**
	 * containing the list of columns to ignore
	 */
	@Order(value=20)
	@XmlElement(name = "tableMap")
	@XmlElementWrapper(name = "columnsIgnore")
	private List<TableMap> columnsIgnore;
	
	public ReplicatorParams() {
	}

	public String getLocalDatabaseName() {
		return localDatabaseName;
	}

	public void setLocalDatabaseName(String localDatabaseName) {
		this.localDatabaseName = localDatabaseName;
	}

	public String getLocalDatabaseHost() {
		return localDatabaseHost;
	}

	public void setLocalDatabaseHost(String localDatabaseHost) {
		this.localDatabaseHost = localDatabaseHost;
	}

	public String getLocalDatabasePort() {
		return localDatabasePort;
	}

	public void setLocalDatabasePort(String localDatabasePort) {
		this.localDatabasePort = localDatabasePort;
	}

	public String getLocalDatabaseUser() {
		return localDatabaseUser;
	}

	public void setLocalDatabaseUser(String localDatabaseUser) {
		this.localDatabaseUser = localDatabaseUser;
	}

	public String getLocalDatabasePass() {
		return localDatabasePass;
	}

	public void setLocalDatabasePass(String localDatabasePass) {
		this.localDatabasePass = localDatabasePass;
	}

	public String getRemoteDatabaseName() {
		return remoteDatabaseName;
	}

	public void setRemoteDatabaseName(String remoteDatabaseName) {
		this.remoteDatabaseName = remoteDatabaseName;
	}

	public String getRemoteDatabaseHost() {
		return remoteDatabaseHost;
	}

	public void setRemoteDatabaseHost(String remoteDatabaseHost) {
		this.remoteDatabaseHost = remoteDatabaseHost;
	}

	public String getRemoteDatabasePort() {
		return remoteDatabasePort;
	}

	public void setRemoteDatabasePort(String remoteDatabasePort) {
		this.remoteDatabasePort = remoteDatabasePort;
	}

	public String getRemoteDatabaseUser() {
		return remoteDatabaseUser;
	}

	public void setRemoteDatabaseUser(String remoteDatabaseUser) {
		this.remoteDatabaseUser = remoteDatabaseUser;
	}

	public String getRemoteDatabasePass() {
		return remoteDatabasePass;
	}

	public void setRemoteDatabasePass(String remoteDatabasePass) {
		this.remoteDatabasePass = remoteDatabasePass;
	}

	public List<String> getExcludeTable() {
		return excludeTable;
	}

	public void setExcludeTable(List<String> excludeTable) {
		this.excludeTable = excludeTable;
	}

	public List<String> getLogTables() {
		return logTables;
	}

	public void setLogTables(List<String> logTables) {
		this.logTables = logTables;
	}

	public List<String> getIgnoreImportTables() {
		return ignoreImportTables;
	}

	public void ListIgnoreImportTables(List<String> ignoreImportTables) {
		this.ignoreImportTables = ignoreImportTables;
	}

	public String getIgnoreImportTablesRegex() {
		return ignoreImportTablesRegex;
	}

	public void setIgnoreImportTablesRegex(String ignoreImportTablesRegex) {
		this.ignoreImportTablesRegex = ignoreImportTablesRegex;
	}

	public String getAuditParentTableName() {
		return auditParentTableName;
	}

	public void setAuditParentTableName(String auditParentTableName) {
		this.auditParentTableName = auditParentTableName;
	}

	public String getAuditParentTableSequence() {
		return auditParentTableSequence;
	}

	public void setAuditParentTableSequence(String auditParentTableSequence) {
		this.auditParentTableSequence = auditParentTableSequence;
	}

	public List<UniqueIndex> getUniqueIndexes() {
		return uniqueIndexes;
	}

	public void setUniqueIndexes(List<UniqueIndex> uniqueIndexes) {
		this.uniqueIndexes = uniqueIndexes;
	}

	public List<Filter> getFilters() {
		return filters;
	}

	public void setFilters(List<Filter> filters) {
		this.filters = filters;
	}

	public String getScheduleDispatcherTimeMillis() {
		return scheduleDispatcherTimeMillis;
	}

	public void setScheduleDispatcherTimeMillis(String scheduleDispatcherTimeMillis) {
		this.scheduleDispatcherTimeMillis = scheduleDispatcherTimeMillis;
	}

	public List<TableMap> getColumnsIgnore() {
		return columnsIgnore;
	}

	public void setColumnsIgnore(List<TableMap> columnsIgnore) {
		this.columnsIgnore = columnsIgnore;
	}

	public Integer getUniqueIdentityID() {
		return uniqueIdentityID;
	}

	public void setUniqueIdentityID(Integer uniqueIdentityID) {
		this.uniqueIdentityID = uniqueIdentityID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uniqueIdentityID == null) ? 0 : uniqueIdentityID.hashCode());
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
		ReplicatorParams other = (ReplicatorParams) obj;
		if (uniqueIdentityID == null) {
			if (other.uniqueIdentityID != null)
				return false;
		} else if (!uniqueIdentityID.equals(other.uniqueIdentityID))
			return false;
		return true;
	}
	
//	public void validate() throws ReplicatorValidationParameterException {
//		
//		//TODO implementar método de validação
//		
//	}
//
	public static ReplicatorParams loadFromXML() {
		
		try {
			
			JAXBContext jc = JAXBContext.newInstance(ReplicatorParams.class);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			
			InputStream resourceAsStream = ReplicatorParams.class.getResourceAsStream("/replicator-params.xml");
			ReplicatorParams params = (ReplicatorParams) unmarshaller.unmarshal(resourceAsStream);
			
			return params;
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
//
//	public static ReplicatorParams loadFromXML(String xmlString) {
//		
//		try {
//			
//			JAXBContext jc = JAXBContext.newInstance(ReplicatorParams.class);
//			Unmarshaller unmarshaller = jc.createUnmarshaller();
//			
//			StringReader reader = new StringReader(xmlString);
//			ReplicatorParams params = (ReplicatorParams) unmarshaller.unmarshal(reader);
//			
//			return params;
//			
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		
//	}
//	
//	public void storeXML() throws Exception {
//		storeXML(null);
//	}
//	
//	public void storeXML(String file) throws Exception {
//		
//		file = Optional.ofNullable(file)
//					.orElse(AppSettings.getConfigPath() + "/replicator-params.xml");
//		
//		JAXBContext jc = JAXBContext.newInstance(ReplicatorParams.class);
//		
//		Marshaller marshaller = jc.createMarshaller();
//        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//        
//        try(FileOutputStream fos = new FileOutputStream(new File(file))){
//        	marshaller.marshal(this, fos);
//        }
//		
//	}
//	
	public String toXML(boolean formattedOutput) throws Exception {
		
		JAXBContext jc = JAXBContext.newInstance(ReplicatorParams.class);
		
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formattedOutput);
		
		StringWriter sw = new StringWriter();
		marshaller.marshal(this, sw);
			
		return sw.toString();
	}
	
}
