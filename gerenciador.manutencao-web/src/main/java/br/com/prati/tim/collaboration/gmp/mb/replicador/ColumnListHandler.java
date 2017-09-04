package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.TreeNode;

import br.com.prati.tim.collaboration.gmp.replicator.model.ColumnList;
import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;

public class ColumnListHandler implements NodeHandler<ColumnList> {

	@Override
	public Map<String, Object> addNode(TreeNode selectedNode, ReplicatorParams params) {
		
		ReplicatorProp parentNode = (ReplicatorProp) selectedNode.getParent().getData();
		ReplicatorProp node 	  = (ReplicatorProp) selectedNode.getData();
		String parentKey 		  = parentNode.getKey();
		String identity			  = node.getKey();
		
		if (parentKey.equals("tableMap") && identity.equals("columns")) {
			
			ColumnList columnList = (ColumnList) node.getTarget();
			List<String> columns = columnList.toList(); 
			columns.add("");
			
			HashMap<String, Object> hashMap = new HashMap<>();

			hashMap.put("elementName", "column"        			);
			hashMap.put("item" 		 , ""	       			    );
			hashMap.put("target"     , columns         			);
			hashMap.put("index"      , columns.size()-1);
			
			return hashMap;
		}
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void removeNode(TreeNode selectedNode, int index, ReplicatorParams params) {

		TreeNode parent 				= selectedNode.getParent();
		TreeNode parentFromParent		= parent.getParent();
		
		ReplicatorProp parentFromParentProp = (ReplicatorProp) parentFromParent.getData();
		ReplicatorProp parentProp 			= (ReplicatorProp) parent.getData();
		ReplicatorProp selectedProp 		= (ReplicatorProp) selectedNode.getData();
		
		String parentFromParentKey 		= parentFromParentProp.getKey();
		String parentKey 				= parentProp.getKey();
		
		if (parentFromParentKey.equals("tableMap") && parentKey.equals("columns")) {
			
			List<String> columns = (List<String>) selectedProp.getTarget();
			columns.remove(index);

		}
		
	}

}
