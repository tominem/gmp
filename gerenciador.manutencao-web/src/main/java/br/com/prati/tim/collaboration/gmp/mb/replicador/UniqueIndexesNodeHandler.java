package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.TreeNode;

import br.com.prati.tim.collaboration.gmp.replicator.model.ColumnType;
import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;
import br.com.prati.tim.collaboration.gmp.replicator.model.UniqueIndex;

public class UniqueIndexesNodeHandler implements NodeHandler<UniqueIndex>{

	@Override
	public Map<String, Object> addNode(TreeNode selectedNode, ReplicatorParams params) {
		
		ReplicatorProp parentProp = (ReplicatorProp) selectedNode.getData();
		String identity			  = parentProp.getKey();
		
		if (identity.equals("uniqueIndexes")) {
			
			List<UniqueIndex> uniqueIndexes = params.getUniqueIndexes();
			
			UniqueIndex uniqueIndex = new UniqueIndex("", "", ColumnType.CHARACTER);
			uniqueIndexes.add(uniqueIndex);
			
			HashMap<String, Object> hashMap = new HashMap<>();

			hashMap.put("elementName", "uniqueIndex"       	 );
			hashMap.put("item" 		 , uniqueIndex		     );
			hashMap.put("target"     , uniqueIndexes         );
			hashMap.put("index"      , uniqueIndexes.size()-1);
			
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
		
		if (parentKey.equals("uniqueIndexes") && selectedKey.equals("uniqueIndex")) {
			
			List<UniqueIndex> uniqueIndexes = params.getUniqueIndexes();
			uniqueIndexes.remove(index);
			
		}
		
	}

}
