package continue_value;

import java.util.List;

public class TreeBuild {
	public static void main(String[] args){
		TreeNode root = new TreeNode();
		TreeSet(new Data().getDatas(), root);
		new TreeNode().showTreeImprove(root);
	}
	
	/**
	 * �����������Ҫʹ�õݹ�
	 * @param D  ����������ݼ�
	 * @param node ����ĵ�ǰ�ڵ㣬ֻ������һ�룬�ýڵ���ֻ�������ݼ�����ʱ���ݵ�����ֵ
	 */
	public static void TreeSet(String[][] D,TreeNode node){
		if(new Data().getDatasResult(D) == null){
			String attributeName = new Data().getLargestInfogainAtributeName(D);
			if(new Data().isContinueAttributeName(D, attributeName)){
				//������������ֵ����ȡ���ֵ㣨Ϊ���������ݼ���׼����
				double point = new Data().getContinueInfoGain(D, attributeName).get(1);
				node.setAttribute(attributeName+"<"+point);

				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue("��");
				String[][] childData = new Data().getChildDataLittlerThan(D,
						attributeName, point);
				node.setLeftChild(childNode);
				TreeSet(childData, childNode);
				TreeNode childNodeRight = new TreeNode();
				childNodeRight.setAttributeValue("��");
				String[][] childDataRight = new Data().getChildDataBiggerThan(
						D, attributeName, point);
				node.setRightChild(childNodeRight);
				TreeSet(childDataRight, childNodeRight);
				return;

			}
			node.setAttribute(attributeName);
			//List<String> list = new Data().getDifferentAttributeValues(D, attributeName);
			List<String> listAllAttributeValue = new Data().getDifferentAttributeValues(new Data().getDatas(), attributeName);
			for(String str:listAllAttributeValue){
				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue(str);
				String[][] childData = new Data().getChildData(D, attributeName, str);
				//�Ӽ��в����������ֵ����ô�Ӽ���ֻ�б�ͷһ��
				if(childData.length == 1){
					if(new Data().getDataMostValue(D).equals("1")){
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
					TreeSet(childData, childNode);
				}
			}
		}else{
			if(new Data().getDatasResult(D).equals("1")){
				node.setAttribute("�ù�");
			}else{
				node.setAttribute("����");
			}
		}
	}

}
