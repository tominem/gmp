package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;

import br.com.prati.tim.collaboration.gmp.dao.replicador.ReplicatorParamsDAO;
import br.com.prati.tim.collaboration.gmp.mb.AbstractBaseMB;
import br.com.prati.tim.collaboration.gmp.mb.UtilsMessage;
import br.com.prati.tim.collaboration.gmp.mb.ValidateComponent;
import br.com.prati.tim.collaboration.gmp.replicator.model.ColumnType;
import br.com.prati.tim.collaboration.gmp.replicator.model.Filter;
import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;
import br.com.prati.tim.collaboration.gmp.replicator.model.TableMap;
import br.com.prati.tim.collaboration.gmp.replicator.model.UniqueIndex;
import br.prati.tim.collaboration.gp.jpa.Maquina;
import br.prati.tim.collaboration.gp.jpa.ReplicatorParamsEntity;

@Named("replicatorMB")
@ViewScoped
public class ReplicatorMB extends AbstractBaseMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2402453271637045442L;
	
	private TreeNode root;
	
	@Inject
	private ReplicatorParamsTreeMode model;
	
	@Inject
	private ReplicatorParamsDAO  dao;
	
	private ReplicatorParams params;
	
	private ReplicatorParamsEntity entity;

	private TreeNode selectedNode;
	
	private Maquina maquina;
	
	public ReplicatorMB() {}
	
	@PostConstruct
	public void init() {
		
		
		
	}
	
	public void selectMaquina(SelectEvent event){
		
		Object object = event.getObject();

		if (object != null	&& object.getClass().getName().equals(Maquina.class.getName())) {
			
			Maquina maquinaSelecionada = (Maquina) object;
			
			if (maquinaSelecionada.equals(getMaquina())) return;
			
			loadTree(maquinaSelecionada.getIdMaquina());
			
			setMaquina(maquinaSelecionada);
			
		} 
	}
	
	public void limparMaquina() {
		
		setMaquina(null);
		
		getRoot().getChildren().clear();
		
		setRoot(null);
		
		setEntity(null);
		
	}
	
	private void loadTree(Long idMaquina) {
		
		ReplicatorParams params = null;
		
		ReplicatorParamsEntity entity = dao.findById(idMaquina);
		
		if (entity != null) {
			
			params = ReplicatorParams.loadFromXML(entity.getXml());
			
		} else {
			
			params = new ReplicatorParams();
			
			params.setUniqueIdentityID(idMaquina.intValue());
			
			params.setExcludeTable(new ArrayList<>());
			params.getExcludeTable().add("");
			
			params.setLogTables(new ArrayList<>());
			params.getLogTables().add("");
			
			params.setIgnoreImportTables(new ArrayList<>());
			params.getIgnoreImportTables().add("");
			
			params.setIgnoreImportTablesRegex("\\w*?._aud");
			params.setAuditParentTableName("revinfo");
			//TODO setar o nome da sequência como padrão
			params.setAuditParentTableSequence("");
			
			params.setUniqueIndexes(new ArrayList<>());
			params.getUniqueIndexes().add(new UniqueIndex());
			
			params.setFilters(new ArrayList<>());
			params.getFilters().add(new Filter());
			
			
			params.setColumnsIgnore(new ArrayList<>());
			TableMap tableMap = new TableMap();
			tableMap.getColumns().toList().add("");
			
			params.getColumnsIgnore().add(tableMap);
			
			params.setScheduleDispatcherTimeMillis("30000");
		}
		
		this.params = params;
		
		setRoot(model.createDocuments(params));
		
		setEntity(entity);
		
	}

	public void save() throws Exception {
		
		ReplicatorParamsEntity entity = getEntity();
		
		if (entity == null) {
			entity = new ReplicatorParamsEntity();
			entity.setXml(params.toXML(false));
		}
		
		if (entity.getUniqueId() == null) {
			entity.setUniqueId(Long.valueOf(params.getUniqueIdentityID()));
			dao.persist(entity);
		}
		else {
			dao.update(entity);
		}
		
		setEntity(entity);
		
		UtilsMessage.addInfoMessage("Configuração salva com sucesso.");
		
	}
	
	public List<String> getColumnTypes(){
		return Arrays.asList(ColumnType.values())
				.stream()
				.map(Object::toString)
				.collect(Collectors.toList());
	}

	public void addNode() {
		model.addNode(getSelectedNode(), this.params);
		getSelectedNode().setExpanded(true);
		selectLastNode();
	}

	private void selectLastNode() {
		
		unSelectAllNodes(getRoot());
		
		TreeNode lastnode = getSelectedNode()
				.getChildren()
				.get(getSelectedNode().getChildCount()-1);
		
		lastnode.setSelected(true);
		
		if (lastnode.getChildCount() > 0) {
			lastnode.setExpanded(true);
		}

		requestFocusUI(lastnode);
		
	}

	private void requestFocusUI(TreeNode lastnode) {
		if (lastnode.getChildCount() > 0) {
			RequestContext.getCurrentInstance().execute("forceSelReqFocusType3();");
		}else {
			RequestContext.getCurrentInstance().execute("forceSelReqFocusType();");
		}
	}

	private void unSelectAllNodes(TreeNode node) {
		for (TreeNode n : node.getChildren()) {
			if(n.getChildCount() > 0) {
				n.setSelected(false);
				unSelectAllNodes(n);
			}else {
				n.setSelected(false);
			}
		}
	}

	public void deleteNode() {
		model.deleteNode(getSelectedNode(), this.params);
		deleteNodeFromUI();
	}
	
	private void deleteNodeFromUI() {
		
		getSelectedNode().getChildren().clear();
		getSelectedNode().getParent().getChildren().remove(selectedNode);
		getSelectedNode().setParent(null);
         
        selectedNode = null;
        
	}

	public TreeNode getRoot() {
        return root;
    }

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public Maquina getMaquina() {
		return maquina;
	}

	public void setMaquina(Maquina maquina) {
		this.maquina = maquina;
	}

	@Override
	public boolean validate(ComponentSystemEvent event) {
		return false;
	}

	@Override
	public ValidateComponent[] getValidaComponents() {
		return null;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public ReplicatorParamsEntity getEntity() {
		return entity;
	}

	public void setEntity(ReplicatorParamsEntity entity) {
		this.entity = entity;
	}

}
