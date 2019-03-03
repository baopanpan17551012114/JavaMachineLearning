package base;

public class TreeNode {
	//建造一棵树
	private TreeNode leftChild;//左孩子
	private TreeNode middleChild;//中间孩子
	private TreeNode rightChild;//右孩子
	private String attribute;//该节点的属性
	private String attributeValue;//该节点划分所根据的属性值
	private TreeNode root;//树根
	
	//构造函数,顺序为根，左右中
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

		//构造函数,顺序为根，左右中
		public TreeNode() {
			this.root = null;
			this.leftChild = null;
			this.rightChild = null;
			this.middleChild = null;
			this.attribute = null;
			this.attributeValue = null;
		}
	
	// 构造函数,顺序为根，左右中
	public TreeNode(String attribute,String attributeValue,TreeNode root,TreeNode leftChild,
			TreeNode rightChild,TreeNode middleChild) {
		this.attributeValue = attributeValue;
		this.root = root;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.attribute = attribute;
		this.middleChild = middleChild;
	}
	
	// 构造函数,顺序为根，左右中
		public TreeNode(String attribute,String attributeValue, TreeNode leftChild) {
			this.root = leftChild;
			this.attribute = attribute;
			this.attributeValue = attributeValue;
		}

	/*
	 * 获取树，就是获取该树的根
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
	 * 传入数根节点打印决策树
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
		root.setattribute("纹理");
		TreeNode node11 = new TreeNode();
		node11.setattribute("根蒂");
		node11.setAttributeValue("清晰");
		TreeNode node12 = new TreeNode();
		node12.setattribute("触感");
		node12.setAttributeValue("稍糊");
		TreeNode node13 = new TreeNode();
		node13.setattribute("坏瓜");
		node13.setAttributeValue("模糊");
		TreeNode node21 = new TreeNode();
		node21.setattribute("好瓜");
		node21.setAttributeValue("硬滑");
		TreeNode node22 = new TreeNode();
		node22.setattribute("坏瓜");
		node22.setAttributeValue("软粘");
		root.setLeftChild(node11);
		root.setRightChild(node13);
		root.setMiddleChild(node12);
		node13.setLeftChild(node21);
		node13.setRightChild(node22);
		new TreeNode().showTree(root);
	}

}
