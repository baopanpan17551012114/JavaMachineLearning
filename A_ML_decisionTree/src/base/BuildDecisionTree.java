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
		// �����ڵ�ͻ��ֹ����Ӽ�����
		String[][] D = new Data().getDatas();
		String[] attruibuteNames = new String[D[0].length - 1];
		// ͨ���������ݼ��ĵ�һ�еõ����Լ�
		int i = 0;
		for (i = 0; i < D[0].length - 1; i++) {
			attruibuteNames[i] = D[0][i];
		}
		if (attruibuteNames.length == 0) {
			return;
		}
		// �ҵ���Ϣ��������������Ϊ���ڵ�
		Map maps = new LinkedHashMap<String, Double>();// ���ڴ�����Լ����Ӧ����Ϣ����
		for (String attName : attruibuteNames) {
			double informationGain = Methods.infoGain(D, attName);
			maps.put(attName, informationGain);
			
		}
		String rootAttribute = Methods.getLargestAttributeName(maps);// �õ���Ϣ��������������Ϊ���ڵ�
		System.out.println(rootAttribute+"$$����");
		List<String> rootDifferentValues = Methods.getAttributeDifferentValues(
				D, rootAttribute);
		TreeNode root = new TreeNode();
		root.setattribute(rootAttribute);
		for (String str : rootDifferentValues) {
			TreeNode childNode = new TreeNode();
			childNode.setAttributeValue(str);// ������ֵ�����ӽڵ�
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
	 * ���������
	 * 
	 * @param D
	 *            ��������ݼ����Ѿ��ֻ����������ݼ�
	 * @param root
	 *            ��ǰ�ڵ�
	 * @param list
	 *            ���ڴ洢�ڵ���б�
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void TreeSet(String[][] D, TreeNode node) {
		new Data().showData(D);
		// ͨ���������ݼ��ĵ�һ�еõ����Լ�
		String[] attruibuteNames = new String[D[0].length - 1];
		int i = 0;
		for (i = 0; i < D[0].length - 1; i++) {
			attruibuteNames[i] = D[0][i];
		}
		if (attruibuteNames.length == 0) {
			return;
		}
		Map maps = new LinkedHashMap<String, Double>();//�����˳����˵��ֻ��LinkedHashMap���ǰ����˳�����
		// �����������Ե���Ϣ����
		for (String attName : attruibuteNames) {
			double informationGain = Methods.infoGain(D, attName);
			maps.put(attName, informationGain);
		}
		String attributeName = Methods.getLargestAttributeName(maps);// �õ���Ϣ��������������Ϊ���ڵ�
		System.out.println(attributeName+"$$����");
		node.setattribute(attributeName);// ���ýڵ�ķ�����������

		// ���������п��� ȡֵ�������ӽڵ�
		List<String> attributeDifferentValues = Methods
				.getAttributeDifferentValues(D, attributeName);// �õ����ڵ㣨���ԣ��Ŀ���ȡֵ
		for (String str : attributeDifferentValues) {
			TreeNode childNode = new TreeNode();
			childNode.setAttributeValue(str);// ������ֵ�����ӽڵ�
			// �����ݼ������Ӽ�
			String[][] child = Methods.getAttributeValueDataChild(D,
					attributeName, str);

			// �ж��Ӽ�����Ƿ�ͳһ������ΪҶ�ӽڵ�
			String re = Methods.getChildResults(child);
			if (re == null) {
				// �������֣������ֺ�����ݼ���

				// �������к��ӵ�˳���ӽڵ������ϸ��ڵ�
				if (node.getLeftChild() == null) {
					node.setLeftChild(childNode);
				} else if (node.getRightChild() == null) {
					node.setRightChild(childNode);
				} else {
					node.setMiddleChild(childNode);
				}
				TreeSet(child, childNode);

			} else {// ����չ��ǵ���Ҷ�ӽڵ�
				if (re.equals("1")) {// ���ݸ�����ֵ����õ��Ľ��ȫΪ��ȷ��
					node.setattribute("�ù�");
					System.out.println("@@@@@@"+str+"�ù�");
					// return;
				} else {
					node.setattribute("����");
					System.out.println("@@@@@@"+str+"����");
					// return;
				}
			}
		}
	}
	
	/**
	 * ����������ĺ���
	 * @param D
	 * @param node
	 */
	public static void TreeBuild(String[][] D, TreeNode node){
		//����Ҫ�жϵ�ǰ�ڵ��Ƿ���Ҷ�ӽ��
		String re = Methods.getChildResults(D);
		if(re == null){//����Ҷ�ӽڵ�
			new Data().showData(D);
			// ͨ���������ݼ��ĵ�һ�еõ����Լ�
			String[] attruibuteNames = new String[D[0].length - 1];
			int i = 0;
			for (i = 0; i < D[0].length - 1; i++) {
				attruibuteNames[i] = D[0][i];
			}
			if (attruibuteNames.length == 0) {
				return;
			}
			Map maps = new LinkedHashMap<String, Double>();//�����˳����˵��ֻ��LinkedHashMap���ǰ����˳�����
			// �����������Ե���Ϣ����
			for (String attName : attruibuteNames) {
				double informationGain = Methods.infoGain(D, attName);
				maps.put(attName, informationGain);
			}
			String attributeName = Methods.getLargestAttributeName(maps);// �õ���Ϣ��������������Ϊ���ڵ�
			System.out.println(attributeName+"$$����");
			node.setattribute(attributeName);// ���ýڵ�ķ�����������

			// ���������п��� ȡֵ�������ӽڵ�
			List<String> attributeDifferentValues = Methods
					.getAttributeDifferentValues(D, attributeName);// �õ����ڵ㣨���ԣ��Ŀ���ȡֵ
			
			for (String str : attributeDifferentValues) {
				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue(str);// ������ֵ�����ӽڵ�
				// �����ݼ������Ӽ�
				String[][] child = Methods.getAttributeValueDataChild(D,
						attributeName, str);
				// �������к��ӵ�˳���ӽڵ������ϸ��ڵ�
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
			if (re.equals("1")) {//��Ҷ�ӽڵ�
				node.setattribute("�ù�");
			} else {
				node.setattribute("����");
			}
		}
	}
}
