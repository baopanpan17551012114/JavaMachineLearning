package bagging;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Ensemble {
	/**
	 * �����������Ҫʹ�õݹ�
	 * @param D  ����������ݼ�
	 * @param node ����ĵ�ǰ�ڵ㣬ֻ������һ�룬�ýڵ���ֻ�������ݼ�����ʱ���ݵ�����ֵ
	 */
	public static void treeSet(String[][] D,TreeNode node){
		if (new Data().getDatasResult(D) == null) {
			String attributeName = new Data().getLargestInfogainAtributeName(D);
			//System.out.println(attributeName);
			if (new Data().isContinueAttributeName(D, attributeName)) {
				// ������������ֵ����ȡ���ֵ㣨Ϊ���������ݼ���׼����
				double point = new Data().getContinueInfoGain(D, attributeName)
						.get(1);
				node.setAttribute(attributeName + "<" + point);

				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue("��");
				String[][] childData = new Data().getChildDataLittlerThan(D,
						attributeName, point);
				node.setLeftChild(childNode);
				treeSet(childData, childNode);
				TreeNode childNodeRight = new TreeNode();
				childNodeRight.setAttributeValue("��");
				String[][] childDataRight = new Data().getChildDataBiggerThan(
						D, attributeName, point);
				node.setRightChild(childNodeRight);
				treeSet(childDataRight, childNodeRight);
				return;

			}

		} else {
			if (new Data().getDatasResult(D).equals("1")) {
				node.setAttribute("�ù�");
			} else {
				node.setAttribute("����");
			}
		}
	}
	
	/**
	 * ��϶��Ԥ����������Ԥ�⣬D��ѵ������test�ǲ���
	 * @param D
	 * @param test
	 * @return
	 */
	public String ensemblePrediction(String[][] D,String[][] test){
		int good = 0;
		int bad = 0;
		for(int i = 0;i<100;i++){//�ظ�ѵ��500��
			String[][] realLearingData = new Data().getRandomChildData(D, D.length-1);
			TreeNode root = new TreeNode();
			new Ensemble().treeSet(realLearingData, root);
			//new TreeNode().showTreeImprove(root);
			//����ѵ�����ľ�������ҪԤ������ݽ���Ԥ��
			List<Object> para1 = new LinkedList<Object>();
			List<Object> para2 = new LinkedList<Object>();
			String name = test[0][0];
			double value = Double.valueOf(test[1][0]);
			para1.add(name);
			para1.add(value);
			name = test[0][1];
			value = Double.valueOf(test[1][1]);
			para2.add(name);
			para2.add(value);
			String result = "";
			while(!((root.getAttribute().equals("�ù�")) ||(root.getAttribute().equals("����")))){
				String attributes = root.getAttribute();
				String[] list = attributes.split("<");
				String attribute = list[0];
				double attributeValue = Double.valueOf(list[1]);
				String sign = "";
				if(attribute.equals((String)para1.get(0))){
					double d = (double)para1.get(1);
					if(d<attributeValue){
						sign = "��";
						
					}else{
						sign = "��";
					}
					if (root.getLeftChild() != null) {
						if(root.getLeftChild().getAttributeValue().equals(sign)){
							root = root.getLeftChild();
						}
					}
					if (root.getRightChild() != null) {
						if(root.getRightChild().getAttributeValue().equals(sign)){
							root = root.getRightChild();
						}
					}
					if (root.getMiddleChild() != null) {
						if(root.getMiddleChild().getAttributeValue().equals(sign)){
							root = root.getMiddleChild();
						}
					}
				}else{
					double d = (double)para2.get(1);
					if(d<attributeValue){
						sign = "��";
					}else{
						sign = "��";
					}
					if (root.getLeftChild() != null) {
						if(root.getLeftChild().getAttributeValue().equals(sign)){
							root = root.getLeftChild();
						}
					}
					if (root.getRightChild() != null) {
						if(root.getRightChild().getAttributeValue().equals(sign)){
							root = root.getRightChild();
						}
					}
					if (root.getMiddleChild() != null) {
						if(root.getMiddleChild().getAttributeValue().equals(sign)){
							root = root.getMiddleChild();
						}
					}
				}
				
			}
			result = root.getAttribute();
			//System.out.println(result);
			if(result.equals("�ù�")){
				good++;
			}else{
				bad++;
			}
		}
		if(good>bad){
			return "�ù�";
		}else{
			return "����";
		}
		
	}
	
	public static void main(String[] args){
		String[][] test = new String[][]{{"�ܶ�","������", "���"},{"0.719","0.103", "?" }};
		String result = new Ensemble().ensemblePrediction(new Data().getDatas(), test);
		System.out.println(result);
	}

}
