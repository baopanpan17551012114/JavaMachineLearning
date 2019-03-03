package base_optimization;

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
			List<String> list = new Data().getDifferentAttributeValues(D, attributeName);
			for(String str:list){
				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue(str);
				String[][] childData = new Data().getChildData(D, attributeName, str);
				if(node.getLeftChild() == null){
					node.setLeftChild(childNode);
				}else if(node.getRightChild() == null){
					node.setRightChild(childNode);
				}else{
					node.setMiddleChild(childNode);
				}
				TreeSet(childData, childNode);
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
