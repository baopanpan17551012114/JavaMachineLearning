package GiniIndex;

import java.util.List;

public class TreeBuild {
	public static void main(String[] args){
		TreeNode root = new TreeNode();
		TreeSet(new Data().getData(), root);
		new TreeNode().showTreeImprove(root);
	}
	
	/**
	 * 根据传入的根节点构造决策树。。。。。。流程和信息增益的一样？
	 * @param D
	 * @param node
	 */
	public static void TreeSet(String[][] D,TreeNode node){
		if(new Data().getDatasResult(D) == null){
			String attributeName = new Data().getMinAttributeName(D);
			node.setAttribute(attributeName);
			//List<String> list = new Data().getDifferentAttributeValues(D, attributeName);
			List<String> listAllAttributeValue = new Data().getDifferentAttributeValues(new Data().getData(), attributeName);
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
