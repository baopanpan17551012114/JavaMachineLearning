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
	 * �����������Ҫʹ�õݹ�
	 * @param D  ����������ݼ�
	 * @param node ����ĵ�ǰ�ڵ㣬ֻ������һ�룬�ýڵ���ֻ�������ݼ�����ʱ���ݵ�����ֵ
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
				//�Ӽ��в����������ֵ����ô�Ӽ���ֻ�б�ͷһ��
				if(childData.length== 1){
					if(new Datas().getDataMostValue(map).equals("1")){
						childNode.setAttribute("�ù�");
					}else{
						childNode.setAttribute("����");
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
				node.setAttribute("�ù�");
			}else{
				node.setAttribute("����");
			}
		}
	}

}
