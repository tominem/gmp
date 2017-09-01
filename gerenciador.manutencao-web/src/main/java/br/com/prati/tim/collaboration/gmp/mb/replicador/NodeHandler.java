package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.util.Map;

import org.primefaces.model.TreeNode;

import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;

public interface NodeHandler<T> {
	
	Map<String, Object> addNode(TreeNode selectedNode, ReplicatorParams params);

	void removeNode(TreeNode selectedNode, int index, ReplicatorParams params);

}
