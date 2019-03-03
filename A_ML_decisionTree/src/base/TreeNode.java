package base;

public class TreeNode {
	//����һ����
	private TreeNode leftChild;//����
	private TreeNode middleChild;//�м亢��
	private TreeNode rightChild;//�Һ���
	private String attribute;//�ýڵ������
	private String attributeValue;//�ýڵ㻮�������ݵ�����ֵ
	private TreeNode root;//����
	
	//���캯��,˳��Ϊ����������
	public TreeNode(TreeNode root,String attribute,String attributeValue,TreeNode leftChild,TreeNode rightChild,TreeNode middleChild) {
		this.root = root;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.middleChild = middleChild;
		this.attribute = attribute;
		this.attributeValue = attributeValue;
	}
	
	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

		//���캯��,˳��Ϊ����������
		public TreeNode() {
			this.root = null;
			this.leftChild = null;
			this.rightChild = null;
			this.middleChild = null;
			this.attribute = null;
			this.attributeValue = null;
		}
	
	// ���캯��,˳��Ϊ����������
	public TreeNode(String attribute,String attributeValue,TreeNode root,TreeNode leftChild,
			TreeNode rightChild,TreeNode middleChild) {
		this.attributeValue = attributeValue;
		this.root = root;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.attribute = attribute;
		this.middleChild = middleChild;
	}
	
	// ���캯��,˳��Ϊ����������
		public TreeNode(String attribute,String attributeValue, TreeNode leftChild) {
			this.root = leftChild;
			this.attribute = attribute;
			this.attributeValue = attributeValue;
		}

	/*
	 * ��ȡ�������ǻ�ȡ�����ĸ�
	 */
	public TreeNode getTree(){
		return this.root;
	}
	
	public TreeNode getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(TreeNode leftChild) {
		this.leftChild = leftChild;
	}

	public TreeNode getMiddleChild() {
		return middleChild;
	}

	public void setMiddleChild(TreeNode middleChild) {
		this.middleChild = middleChild;
	}

	public TreeNode getRightChild() {
		return rightChild;
	}

	public void setRightChild(TreeNode rightChild) {
		this.rightChild = rightChild;
	}

	public String getattribute() {
		return attribute;
	}

	public void setattribute(String attribute) {
		this.attribute = attribute;
	}
	
	/**
	 * ���������ڵ��ӡ������
	 * @param root
	 */
	public void showTree(TreeNode root) {
		if (root.getLeftChild() == null && root.getRightChild() == null
				&& root.getMiddleChild() == null) {
			System.out.print(root.attribute + "--------" + root.attributeValue);
		} else {
			if (root.getRightChild() != null&&root.getLeftChild() != null&&root.getMiddleChild()!=null) {
				
				System.out.print(root.attribute + "--------"
						+ root.attributeValue);
				
				showTree(root.getLeftChild());
				showTree(root.getRightChild());
				showTree(root.getMiddleChild());
				System.out.println("############");
			} else if (root.getRightChild() != null&&root.getLeftChild() != null) {
				System.out.println("############");
				System.out.print(root.attribute + "--------"
						+ root.attributeValue);
				showTree(root.getLeftChild());
				showTree(root.getRightChild());
			} 
		}

	}
	public static void main(String[] args){
		TreeNode root = new TreeNode();
		root.setattribute("����");
		TreeNode node11 = new TreeNode();
		node11.setattribute("����");
		node11.setAttributeValue("����");
		TreeNode node12 = new TreeNode();
		node12.setattribute("����");
		node12.setAttributeValue("�Ժ�");
		TreeNode node13 = new TreeNode();
		node13.setattribute("����");
		node13.setAttributeValue("ģ��");
		TreeNode node21 = new TreeNode();
		node21.setattribute("�ù�");
		node21.setAttributeValue("Ӳ��");
		TreeNode node22 = new TreeNode();
		node22.setattribute("����");
		node22.setAttributeValue("��ճ");
		root.setLeftChild(node11);
		root.setRightChild(node13);
		root.setMiddleChild(node12);
		node13.setLeftChild(node21);
		node13.setRightChild(node22);
		new TreeNode().showTree(root);
	}

}
