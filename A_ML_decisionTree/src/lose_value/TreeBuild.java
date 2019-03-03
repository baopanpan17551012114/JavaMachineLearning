package lose_value;

import java.util.List;

import basez_optimization_continue.Data;
import basez_optimization_continue.TreeNode;

public class TreeBuild {
	public static void main(String[] args){
		TreeNode root = new TreeNode();
		TreeSet(new Datas(new Datas().getDatas(),new Datas().getWeight()).getMap(), root);
		new TreeNode().showTreeImprove(root);

	}
	
	/**
	 * 构造决策树，要使用递归
	 * @param D  传入的子数据集
	 * @param node 传入的当前节点，只构造了一半，该节点中只有子数据集分类时根据的属性值
	 */
	public static void TreeSet(List map,TreeNode node){
		if(new Datas().getDatasResult(map) == null){
			String attributeName = new Datas().getLargestInfogainAtributeName(map);
			System.out.println(attributeName);
			node.setAttribute(attributeName);
			//List<String> list = new Data().getDifferentAttributeValues(D, attributeName);
			List<String> listAllAttributeValue = new Datas().getDifferentAttributeValues(map, attributeName);
			for(String str:listAllAttributeValue){
				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue(str);
				List childMap = new Datas().getChildDataAndWeight(map, attributeName, str);
				String[][] childData = (String[][])childMap.get(0);
				//子集中不含这个属性值，那么子集就只有表头一行
				if(childData.length== 1){
					if(new Datas().getDataMostValue(map).equals("1")){
						childNode.setAttribute("好瓜");
					}else{
						childNode.setAttribute("坏瓜");
					}
				}
				if(node.getLeftChild() == null){
					node.setLeftChild(childNode);
				}else if(node.getRightChild() == null){
					node.setRightChild(childNode);
				}else{
					node.setMiddleChild(childNode);
				}
				if(childData.length == 1){
					break;
				}else{
					TreeSet(childMap, childNode);
				}
			}
		}else{
			if(new Datas().getDatasResult(map).equals("1")){
				node.setAttribute("好瓜");
			}else{
				node.setAttribute("坏瓜");
			}
		}
	}

}
