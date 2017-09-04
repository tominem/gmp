package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.TreeNode;

import br.com.prati.tim.collaboration.gmp.replicator.model.Filter;
import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;

public class FilterNodeHandler implements NodeHandler<Filter>{

	@Override
	public Map<String, Object> addNode(TreeNode selectedNode, ReplicatorParams params) {
		ReplicatorProp parentProp = (ReplicatorProp) selectedNode.getData();
		String identity			  = parentProp.getKey();
		
		if (identity.equals("filters")) {
			
			List<Filter> filters = params.getFilters();
			
			Filter filter = new Filter
					.Builder()
						.type("where")
						.tableName("")
						.value("")
						.filterColumn("")
						.parentPkColumn("")
						.build();
			
			filters.add(filter);
			
			HashMap<String, Object> hashMap = new HashMap<>();

			hashMap.put("elementName", "filter"        );
			hashMap.put("item" 		 , filter	       );
			hashMap.put("target"     , filters         );
			hashMap.put("index"      , filters.size()-1);
			
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
		
		if (parentKey.equals("filters") && selectedKey.equals("filter")) {
			
			List<Filter> filters = params.getFilters();
			filters.remove(index);
			
		}
		
	}

}
