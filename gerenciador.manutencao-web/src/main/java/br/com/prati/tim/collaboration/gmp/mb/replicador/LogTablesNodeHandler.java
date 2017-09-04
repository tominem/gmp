package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.TreeNode;

import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;

public class LogTablesNodeHandler implements NodeHandler<String>{

	@Override
	public Map<String, Object> addNode(TreeNode selectedNode, ReplicatorParams params) {
		
		ReplicatorProp parentProp = (ReplicatorProp) selectedNode.getData();
		String identity			  = parentProp.getKey();
		
		if (identity.equals("logTables")) {
			
			List<String> logTables = params.getLogTables();
			logTables.add("");
			
			HashMap<String, Object> hashMap = new HashMap<>();

			hashMap.put("elementName", "table"           );
			hashMap.put("item" 		 , ""         		 );
			hashMap.put("target"     , logTables         );
			hashMap.put("index"      , logTables.size()-1);
			
			return hashMap;
		}
		
		return null;
	}
	
	@Override
	public void removeNode(TreeNode selectedNode, int index, ReplicatorParams params) {
		
		TreeNode parent 				= selectedNode.getParent();
		ReplicatorProp parentProp 		= (ReplicatorProp) parent.getData();
		ReplicatorProp selectedProp 	= (ReplicatorProp) selectedNode.getData();
		
		String parentKey 	= parentProp.getKey();
		String selectedKey 	= selectedProp.getKey();
		
		if (parentKey.equals("logTables") && selectedKey.equals("table")) {
			
			List<String> logTables = params.getLogTables();
			logTables.remove(index);
			
		}
		
	}

}
