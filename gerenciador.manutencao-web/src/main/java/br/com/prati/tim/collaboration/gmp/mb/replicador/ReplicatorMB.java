package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.TreeNode;

import br.com.prati.tim.collaboration.gmp.replicator.model.ColumnType;
import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;

@Named("replicatorMB")
@ViewScoped
public class ReplicatorMB implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2402453271637045442L;
	
	private TreeNode root;
	
	@Inject
	private ReplicatorParamsTreeMode model;
	
	private ReplicatorParams params;

	private TreeNode selectedNode;

	public ReplicatorMB() {}
	
	@PostConstruct
	public void init() {
		
		//TODO tem que carregar do banco de dados
		params = ReplicatorParams.loadFromXML();
		root = model.createDocuments(params);
		
	}
	
	public void save() {
		try {
			System.out.println(params.toXML(true));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<String> getColumnTypes(){
		return Arrays.asList(ColumnType.values())
				.stream()
				.map(Object::toString)
				.collect(Collectors.toList());
	}

	public void addNode() {
		model.addNode(getSelectedNode(), this.params);
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
	
}
