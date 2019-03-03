package basez_optimization_continue;

import java.util.List;

public class TreeBuild {
	public static void main(String[] args){
		TreeNode root = new TreeNode();
		TreeSet(new Data().getDatas(), root);
		new TreeNode().showTreeImprove(root);
	}
	
	/**
	 * 构造决策树，要使用递归
	 * @param D  传入的子数据集
	 * @param node 传入的当前节点，只构造了一半，该节点中只有子数据集分类时根据的属性值
	 */
	public static void TreeSet(String[][] D,TreeNode node){
		if(new Data().getDatasResult(D) == null){
			String attributeName = new Data().getLargestInfogainAtributeName(D);
			node.setAttribute(attributeName);
			//List<String> list = new Data().getDifferentAttributeValues(D, attributeName);
			List<String> listAllAttributeValue = new Data().getDifferentAttributeValues(new Data().getDatas(), attributeName);
			for(String str:listAllAttributeValue){
				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue(str);
				String[][] childData = new Data().getChildData(D, attributeName, str);
				//子集中不含这个属性值，那么子集就只有表头一行
				if(childData.length == 1){
					if(new Data().getDataMostValue(D).equals("1")){
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
					TreeSet(childData, childNode);
				}
			}
		}else{
			if(new Data().getDatasResult(D).equals("1")){
				node.setAttribute("好瓜");
			}else{
				node.setAttribute("坏瓜");
			}
		}
	}

}
