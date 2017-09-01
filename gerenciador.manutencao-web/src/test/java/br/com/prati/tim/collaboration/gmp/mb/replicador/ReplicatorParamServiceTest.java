package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.primefaces.model.TreeNode;

import br.com.prati.tim.collaboration.gmp.replicator.model.ColumnType;
import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;
import br.com.prati.tim.collaboration.gmp.replicator.model.TableMap;
import br.com.prati.tim.collaboration.gmp.replicator.model.UniqueIndex;

public class ReplicatorParamServiceTest {
	
	@Test
	public void loadTest() throws Exception {
		
		ReplicatorParams params  = ReplicatorParams.loadFromXML();
		ReplicatorParams params2 = ReplicatorParams.loadFromXML();
		
		TableMap tableMap = params.getColumnsIgnore().get(0);
		tableMap.setTable("new_table");
		tableMap.getColumns().getColumns().set(0, "new_column");
		
		ArrayList<UniqueIndex> uniqueIndexes = new ArrayList<>(params.getUniqueIndexes());
		UniqueIndex uniqueIndex = uniqueIndexes.get(8);
		uniqueIndex.setColumnName("new_column_name");
		uniqueIndex.setColumnType(ColumnType.CHARACTER);
		uniqueIndex.setTable("new_table");
		
		params.setScheduleDispatcherTimeMillis("2000");
		
		ReplicatorParamsTreeMode rps = new ReplicatorParamsTreeMode();
		TreeNode root = rps.createDocuments(params2);
		
		TreeNode tabMapNode 	= getTreeNode(root, "tableMap");
		TreeNode columnListNode = getTreeNode(tabMapNode, "column");
		
		ReplicatorProp tabMapProp = (ReplicatorProp) tabMapNode.getChildren().get(0).getData();
		tabMapProp.setValue("new_table");

		ReplicatorProp columnListProp = (ReplicatorProp) columnListNode.getData();
		columnListProp.setValue("new_column");
		
		TreeNode uniqueindexesNode 	= getTreeNode(root, "uniqueIndexes");
		TreeNode uniqueIndexNode = uniqueindexesNode.getChildren().get(8);
		
		ReplicatorProp columnProp 		= (ReplicatorProp) getTreeNode(uniqueIndexNode, "columnName").getData();
		ReplicatorProp columnTypeProp 	= (ReplicatorProp) getTreeNode(uniqueIndexNode, "columnType").getData();
		ReplicatorProp tableProp 		= (ReplicatorProp) getTreeNode(uniqueIndexNode, "table").getData();
		
		columnProp 		.setValue("new_column_name");
		columnTypeProp 	.setValue(ColumnType.CHARACTER.toString());
		tableProp 		.setValue("new_table");
		
		ReplicatorProp scheduleDispatcherTimeMillisProp = getPropData(root, "scheduleDispatcherTimeMillis");
		scheduleDispatcherTimeMillisProp.setValue("2000");
		
		System.out.println(params.toXML(true));
		System.out.println("");
		System.out.println(params2.toXML(true));
		
		Assert.assertEquals(params.toXML(false), params2.toXML(false));
		
	}
	
	private ReplicatorProp getPropData(TreeNode node, String key) {
		TreeNode treeNode = getTreeNode(node, key);
		return (ReplicatorProp)treeNode.getData();
	}
	
	private TreeNode getTreeNode(TreeNode parent, String key) {
		
		if (parent.getChildCount() == 0) return null;
		
		for (TreeNode node : parent.getChildren()) {
			
			ReplicatorProp prop = (ReplicatorProp) node.getData();
			
			if (prop.getKey().equals(key)) {
				return node;
			}
			else {
				
				TreeNode treeNode = getTreeNode(node, key);
				
				if (treeNode != null) {
					return treeNode;
				}
				
			}
			
		}
		
		return null;
	}

}
