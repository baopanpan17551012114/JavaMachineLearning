package base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BuildDecisionTree {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		List list = new ArrayList<TreeNode>();
		// 将根节点和划分过的子集传入
		String[][] D = new Data().getDatas();
		String[] attruibuteNames = new String[D[0].length - 1];
		// 通过遍历数据集的第一行得到属性集
		int i = 0;
		for (i = 0; i < D[0].length - 1; i++) {
			attruibuteNames[i] = D[0][i];
		}
		if (attruibuteNames.length == 0) {
			return;
		}
		// 找到信息增量最大的属性作为根节点
		Map maps = new LinkedHashMap<String, Double>();// 用于存放属性及其对应的信息增量
		for (String attName : attruibuteNames) {
			double informationGain = Methods.infoGain(D, attName);
			maps.put(attName, informationGain);
			
		}
		String rootAttribute = Methods.getLargestAttributeName(maps);// 得到信息增量最大的属性作为根节点
		System.out.println(rootAttribute+"$$属性");
		List<String> rootDifferentValues = Methods.getAttributeDifferentValues(
				D, rootAttribute);
		TreeNode root = new TreeNode();
		root.setattribute(rootAttribute);
		for (String str : rootDifferentValues) {
			TreeNode childNode = new TreeNode();
			childNode.setAttributeValue(str);// 将属性值填入子节点
			String[][] child = Methods.getAttributeValueDataChild(D,
					rootAttribute, str);
			if (root.getLeftChild() == null) {
				root.setLeftChild(childNode);
			} else if (root.getRightChild() == null) {
				root.setRightChild(childNode);
			} else {
				root.setMiddleChild(childNode);
			}
			TreeBuild(child, childNode);
		}
		new TreeNode().showTree(root);
		
	}
	/**
	 * 构造决策树
	 * 
	 * @param D
	 *            传入的数据集，已经分化过的子数据集
	 * @param root
	 *            当前节点
	 * @param list
	 *            用于存储节点的列表
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void TreeSet(String[][] D, TreeNode node) {
		new Data().showData(D);
		// 通过遍历数据集的第一行得到属性集
		String[] attruibuteNames = new String[D[0].length - 1];
		int i = 0;
		for (i = 0; i < D[0].length - 1; i++) {
			attruibuteNames[i] = D[0][i];
		}
		if (attruibuteNames.length == 0) {
			return;
		}
		Map maps = new LinkedHashMap<String, Double>();//从添加顺序来说，只有LinkedHashMap才是按添加顺序，添加
		// 计算所有属性的信息增量
		for (String attName : attruibuteNames) {
			double informationGain = Methods.infoGain(D, attName);
			maps.put(attName, informationGain);
		}
		String attributeName = Methods.getLargestAttributeName(maps);// 得到信息增量最大的属性作为根节点
		System.out.println(attributeName+"$$属性");
		node.setattribute(attributeName);// 将该节点的分类属性填入

		// 该属性所有可能 取值，建立子节点
		List<String> attributeDifferentValues = Methods
				.getAttributeDifferentValues(D, attributeName);// 得到根节点（属性）的可能取值
		for (String str : attributeDifferentValues) {
			TreeNode childNode = new TreeNode();
			childNode.setAttributeValue(str);// 将属性值填入子节点
			// 将数据集划分子集
			String[][] child = Methods.getAttributeValueDataChild(D,
					attributeName, str);

			// 判断子集结果是否统一，是则为叶子节点
			String re = Methods.getChildResults(child);
			if (re == null) {
				// 继续划分，将划分后的数据集和

				// 按左右中孩子的顺序将子节点连接上父节点
				if (node.getLeftChild() == null) {
					node.setLeftChild(childNode);
				} else if (node.getRightChild() == null) {
					node.setRightChild(childNode);
				} else {
					node.setMiddleChild(childNode);
				}
				TreeSet(child, childNode);

			} else {// 最后终归是到达叶子节点
				if (re.equals("1")) {// 根据该属性值分类得到的结果全为正确的
					node.setattribute("好瓜");
					System.out.println("@@@@@@"+str+"好瓜");
					// return;
				} else {
					node.setattribute("坏瓜");
					System.out.println("@@@@@@"+str+"坏瓜");
					// return;
				}
			}
		}
	}
	
	/**
	 * 构造决策树的函数
	 * @param D
	 * @param node
	 */
	public static void TreeBuild(String[][] D, TreeNode node){
		//首先要判断当前节点是否是叶子结点
		String re = Methods.getChildResults(D);
		if(re == null){//不是叶子节点
			new Data().showData(D);
			// 通过遍历数据集的第一行得到属性集
			String[] attruibuteNames = new String[D[0].length - 1];
			int i = 0;
			for (i = 0; i < D[0].length - 1; i++) {
				attruibuteNames[i] = D[0][i];
			}
			if (attruibuteNames.length == 0) {
				return;
			}
			Map maps = new LinkedHashMap<String, Double>();//从添加顺序来说，只有LinkedHashMap才是按添加顺序，添加
			// 计算所有属性的信息增量
			for (String attName : attruibuteNames) {
				double informationGain = Methods.infoGain(D, attName);
				maps.put(attName, informationGain);
			}
			String attributeName = Methods.getLargestAttributeName(maps);// 得到信息增量最大的属性作为根节点
			System.out.println(attributeName+"$$属性");
			node.setattribute(attributeName);// 将该节点的分类属性填入

			// 该属性所有可能 取值，建立子节点
			List<String> attributeDifferentValues = Methods
					.getAttributeDifferentValues(D, attributeName);// 得到根节点（属性）的可能取值
			
			for (String str : attributeDifferentValues) {
				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue(str);// 将属性值填入子节点
				// 将数据集划分子集
				String[][] child = Methods.getAttributeValueDataChild(D,
						attributeName, str);
				// 按左右中孩子的顺序将子节点连接上父节点
				if (node.getLeftChild() == null) {
					node.setLeftChild(childNode);
				} else if (node.getRightChild() == null) {
					node.setRightChild(childNode);
				} else {
					node.setMiddleChild(childNode);
				}
				TreeBuild(child, childNode);
			}
		}else{
			if (re.equals("1")) {//是叶子节点
				node.setattribute("好瓜");
			} else {
				node.setattribute("坏瓜");
			}
		}
	}
}
