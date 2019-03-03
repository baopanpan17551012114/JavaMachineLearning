package bagging;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Ensemble {
	/**
	 * 构造决策树，要使用递归
	 * @param D  传入的子数据集
	 * @param node 传入的当前节点，只构造了一半，该节点中只有子数据集分类时根据的属性值
	 */
	public static void treeSet(String[][] D,TreeNode node){
		if (new Data().getDatasResult(D) == null) {
			String attributeName = new Data().getLargestInfogainAtributeName(D);
			//System.out.println(attributeName);
			if (new Data().isContinueAttributeName(D, attributeName)) {
				// 是连续型属性值，获取划分点（为划分子数据集做准备）
				double point = new Data().getContinueInfoGain(D, attributeName)
						.get(1);
				node.setAttribute(attributeName + "<" + point);

				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue("是");
				String[][] childData = new Data().getChildDataLittlerThan(D,
						attributeName, point);
				node.setLeftChild(childNode);
				treeSet(childData, childNode);
				TreeNode childNodeRight = new TreeNode();
				childNodeRight.setAttributeValue("否");
				String[][] childDataRight = new Data().getChildDataBiggerThan(
						D, attributeName, point);
				node.setRightChild(childNodeRight);
				treeSet(childDataRight, childNodeRight);
				return;

			}

		} else {
			if (new Data().getDatasResult(D).equals("1")) {
				node.setAttribute("好瓜");
			} else {
				node.setAttribute("坏瓜");
			}
		}
	}
	
	/**
	 * 组合多个预测结果来最终预测，D是训练集，test是测试
	 * @param D
	 * @param test
	 * @return
	 */
	public String ensemblePrediction(String[][] D,String[][] test){
		int good = 0;
		int bad = 0;
		for(int i = 0;i<100;i++){//重复训练500次
			String[][] realLearingData = new Data().getRandomChildData(D, D.length-1);
			TreeNode root = new TreeNode();
			new Ensemble().treeSet(realLearingData, root);
			//new TreeNode().showTreeImprove(root);
			//根据训练出的决策树对要预测的数据进行预测
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
			while(!((root.getAttribute().equals("好瓜")) ||(root.getAttribute().equals("坏瓜")))){
				String attributes = root.getAttribute();
				String[] list = attributes.split("<");
				String attribute = list[0];
				double attributeValue = Double.valueOf(list[1]);
				String sign = "";
				if(attribute.equals((String)para1.get(0))){
					double d = (double)para1.get(1);
					if(d<attributeValue){
						sign = "是";
						
					}else{
						sign = "否";
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
						sign = "是";
					}else{
						sign = "否";
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
			if(result.equals("好瓜")){
				good++;
			}else{
				bad++;
			}
		}
		if(good>bad){
			return "好瓜";
		}else{
			return "坏瓜";
		}
		
	}
	
	public static void main(String[] args){
		String[][] test = new String[][]{{"密度","含糖率", "结果"},{"0.719","0.103", "?" }};
		String result = new Ensemble().ensemblePrediction(new Data().getDatas(), test);
		System.out.println(result);
	}

}
