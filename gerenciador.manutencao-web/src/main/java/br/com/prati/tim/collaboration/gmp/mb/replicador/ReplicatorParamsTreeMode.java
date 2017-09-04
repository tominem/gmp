package br.com.prati.tim.collaboration.gmp.mb.replicador;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.enterprise.inject.Model;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.beanutils.PropertyUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.prati.tim.collaboration.gmp.replicator.model.ColumnList;
import br.com.prati.tim.collaboration.gmp.replicator.model.ColumnType;
import br.com.prati.tim.collaboration.gmp.replicator.model.Order;
import br.com.prati.tim.collaboration.gmp.replicator.model.ReplicatorParams;

@Model
public class ReplicatorParamsTreeMode {
	
	private List<NodeHandler<?>> nodeHandlers = new ArrayList<>();
	
	public ReplicatorParamsTreeMode() {

		List<NodeHandler<?>> addedNodes = Arrays.asList
		(
			new ExcludeTablesNodeHandle(),
			new LogTablesNodeHandler(),
			new IgnoreTablesNodehandler(),
			new UniqueIndexesNodeHandler(),
			new FilterNodeHandler(),
			new ColumnIgnoreNodeHandler(),
			new ColumnListHandler()
		);
			
		
		nodeHandlers.addAll(addedNodes);
		
	}
	
	public TreeNode createDocuments(ReplicatorParams params) {
		
		TreeNode root = new DefaultTreeNode(new ReplicatorProp("Configuração", "-", params), null);
		
		List<Field> fields = getDeclaredFields(params);
		
		loadTreeNodes(1, params, root, fields);
		
        return root;
	}


	private List<Field> getDeclaredFields(Object target) {
		return Arrays.asList(target.getClass().getDeclaredFields())
				.stream()
				.filter(f -> f.getDeclaredAnnotationsByType(XmlTransient.class) == null 
								|| f.getDeclaredAnnotationsByType(XmlTransient.class).length == 0)
				.collect(Collectors.toList());
	}


	private void loadTreeNodes(int level, Object target, TreeNode root, List<Field> fields) {
		
		sortFields(fields);
		
		fields.forEach(f-> {
			
			try {
				
				f.setAccessible(true);
				
				if (!f.getName().equals("serialVersionUID")) {
					
					Object fieldValue = Optional.ofNullable(f.get(target)).orElse("");

					XmlElement xmlElement 		 		= f.getDeclaredAnnotation(XmlElement.class);
					XmlElementWrapper xmlElementWrapper = f.getDeclaredAnnotation(XmlElementWrapper.class);
					
					if (xmlElement != null && xmlElementWrapper != null) {
						
						String wrapperName = xmlElementWrapper.name();
						String elementName = xmlElement.name();
						
						DefaultTreeNode wrapper = new DefaultTreeNode("type" + (level+1), new ReplicatorProp(wrapperName, "-"), root);
						
						addChildrenTreeNode(wrapper, elementName, fieldValue, target);
						
						
					} else if (xmlElement != null){
						
						String elementName = xmlElementWrapper.name();
						
						DefaultTreeNode wrapper = new DefaultTreeNode("type" + (level+1), new ReplicatorProp(elementName, "-", fieldValue), root);
						
						addChildrenTreeNode(wrapper, elementName, fieldValue, target);
						
					} else if(fieldValue instanceof ColumnList) {
						
						String elementName = f.getName();
						
						DefaultTreeNode wrapper = new DefaultTreeNode("type" + (level+1), new ReplicatorProp(elementName, "-", fieldValue), root);
						
						addChildrenTreeNode(wrapper, elementName, fieldValue, target);
						
					} else if(fieldValue instanceof ColumnType) {
						
						Consumer<ReplicatorProp> consumer = s->{
							
							try {
								ColumnType selected = ColumnType.valueOf(s.getValue());
								PropertyUtils.setProperty(target, f.getName(), selected);
							} catch (Exception e) {
								throw new RuntimeException(e);
							}
							
						};
						
						new DefaultTreeNode("type" + level, new ReplicatorProp(f.getName(), fieldValue.toString(), target, consumer), root);
						
						
					} else {
						
						new DefaultTreeNode("type" + level, new ReplicatorProp(f.getName(), fieldValue.toString(), target), root);
						
					}
					
				}
				
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			
		});
	}


	private void addChildrenTreeNode(TreeNode wrapper, String elementName, Object fieldValue, Object target) {
		
		if (fieldValue instanceof Collection) {
			
			Collection<?> list = (Collection<?>) fieldValue;
			int i = 0;
			for (Object item : list) {
				addChild(wrapper, elementName, item, list, i++);
			}
			
		} else if(fieldValue instanceof ColumnList) {
			
			ColumnList list = (ColumnList) fieldValue;
			int i = 0;
			for (Object item : list.toList()) {
				addChild(wrapper, "column", item, list.toList(), i++);
			}
			
		}
		
	}


	@SuppressWarnings("unchecked")
	private void addChild(TreeNode wrapper, String elementName, Object item, Object target, Integer index) {
		
		if (item instanceof String) {
			
			new DefaultTreeNode
			(
				"type3",
				new ReplicatorProp
				(
					elementName, 
					item.toString(), 
					target, 
					obj ->
					{
						
						Collection<String> sourceColl = (Collection<String>) target;
						List<String> list = new ArrayList<>(sourceColl);
						
						if (list.size() > 0) {
							
							list.set(obj.getIndex(), obj.getValue());
							sourceColl.clear();
							sourceColl.addAll(list);
								
						}
							
							
					},
					index
				), 
				wrapper
			);
			
		}
		
		else {

			loadChildren("type3", wrapper, elementName, item, index);
			
		}
		
		
	}
	
	private void loadChildren(String type, TreeNode wrapper, String elementName, Object item, int index) {
		
		DefaultTreeNode element = new DefaultTreeNode(type, new ReplicatorProp(elementName, "-", index), wrapper);
		
		loadTreeNodes(4, item, element, getDeclaredFields(item));
		
	}


	private void sortFields(List<Field> fields) {
		fields.stream()
			  .sorted((x1,x2)-> 
			  {
					
				Order or1 = x1.getAnnotation(Order.class);
	            Order or2 = x2.getAnnotation(Order.class);
	            
	            if (or1 != null && or2 != null) {
	                return or1.value() - or2.value();
	            } else
	            if (or1 != null && or2 == null) {
	                return -1;
	            } else
	            if (or1 == null && or2 != null) {
	                return 1;
	            }
	            return x1.getName().compareTo(x2.getName());
			  });
	}


	public void addNode(TreeNode selectedNode, ReplicatorParams params) {
		
		for (NodeHandler<?> nodeBuilder : nodeHandlers) {
			Map<String, Object> addNode = nodeBuilder.addNode(selectedNode, params);
			
			if (addNode != null) {
				
				addChild
				(
					selectedNode,
					(String) addNode.get("elementName"), 
					(Object) addNode.get("item"), 
					(Object) addNode.get("target"), 
					(int) addNode.get("index")
				);
				
			}
		}
		
	}
	
	public void deleteNode(TreeNode selectedNode, ReplicatorParams params) {
		
		int index = getIndexFromNode(selectedNode);
		
		for (NodeHandler<?> nodeBuilder : nodeHandlers) {
			nodeBuilder.removeNode(selectedNode, index, params);
		}
		
		List<TreeNode> children = selectedNode.getParent().getChildren();
		
		List<TreeNode> brothers = children.stream()
			.filter(n -> !n.equals(selectedNode))
			.collect(Collectors.toList());
		
		if (brothers != null) {
			for (int i = 0; i < brothers.size(); i++) {
				ReplicatorProp prop = (ReplicatorProp) brothers.get(i).getData();
				prop.setIndex(i);
			}
		}
		
	}

	private int getIndexFromNode(TreeNode selectedNode) {
		TreeNode parent = selectedNode.getParent();
		for (int i = 0; i < parent.getChildCount(); i++) {
			TreeNode item = parent.getChildren().get(i);
			if (item.equals(selectedNode)) {
				return i;
			}
		}
		
		return -1;
	}

}
