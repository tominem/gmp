package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.TreeNode;

import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;
import br.com.prati.tim.collaboration.gmp.replicator.model.TableMap;

public class ColumnIgnoreNodeHandler implements NodeHandler<TableMap>{

	@Override
	public Map<String, Object> addNode(TreeNode selectedNode, ReplicatorParams params) {
		
		ReplicatorProp parentProp = (ReplicatorProp) selectedNode.getData();
		String identity			  = parentProp.getKey();
		
		if (identity.equals("columnsIgnore")) {
			
			List<TableMap> columnsIgnore = params.getColumnsIgnore();
			
			TableMap tableMap = new TableMap("", "");
			
			columnsIgnore.add(tableMap);
			
			HashMap<String, Object> hashMap = new HashMap<>();

			hashMap.put("elementName", "tableMap"      		 );
			hashMap.put("item" 		 , tableMap	   			 );
			hashMap.put("target"     , columnsIgnore         );
			hashMap.put("index"      , columnsIgnore.size()-1);
			
			return hashMap;
		}
		
		return null;
	}

	@Override
	public void removeNode(TreeNode selectedNode, int index, ReplicatorParams params) {
		
		TreeNode parent 				= selectedNode.getParent();
		ReplicatorProp parentProp 		= (ReplicatorProp) parent.getData();
		ReplicatorProp selectedProp 	= (ReplicatorProp) selectedNode.getData();
		
		String parentKey 				= parentProp.getKey();
		String selectedKey 				= selectedProp.getKey();
		
		if (parentKey.equals("columnsIgnore") && selectedKey.equals("tableMap")) {
			
			List<TableMap> columnsIgnore = params.getColumnsIgnore();
			columnsIgnore.remove(index);
			
		}
		
	}

}
