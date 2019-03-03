package base_optimization;


public class TreeNode {
	//树节点类
	private String attribute;//用于存放当前节点的分类属性或者瓜的属性
	private String attributeValue;//用于存放当前节点之前分类所取的属性值
	private TreeNode leftChild;
	private TreeNode rightChild;
	private TreeNode middleChild;
	
	public TreeNode(){
		this.attribute = null;
		this.attributeValue = null;
		this.leftChild = null;
		this.rightChild = null;
		this.middleChild = null;
	}
	
	//set()get()函数
	
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public TreeNode getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(TreeNode leftChild) {
		this.leftChild = leftChild;
	}
	public TreeNode getRightChild() {
		return rightChild;
	}
	public void setRightChild(TreeNode rightChild) {
		this.rightChild = rightChild;
	}
	public TreeNode getMiddleChild() {
		return middleChild;
	}
	public void setMiddleChild(TreeNode middleChild) {
		this.middleChild = middleChild;
	}
	
	/**
	 * 传入根节点，打印整棵树
	 * @param root
	 */
	public void showTreeImprove(TreeNode root){
		if(root.getLeftChild() != null||root.getRightChild() != null||root.getMiddleChild() != null){
			if (root.getLeftChild() != null) {
				System.out.print(root.getLeftChild().getAttribute() + "--------"
						+ root.getLeftChild().getAttributeValue() + "\t");

			}
			if (root.getRightChild() != null) {
				System.out.print(root.getRightChild().getAttribute() + "--------"
						+ root.getRightChild().getAttributeValue() + "\t");

			}
			if (root.getMiddleChild() != null) {
				System.out.print(root.getMiddleChild().getAttribute() + "--------"
						+ root.getMiddleChild().getAttributeValue() + "\t");
			}
			System.out.println("");
			System.out.println("############");
			if (root.getLeftChild() != null) {
				showTreeImprove(root.getLeftChild());
			}
			if (root.getRightChild() != null) {
				showTreeImprove(root.getRightChild());
			}
			if (root.getMiddleChild() != null) {
				showTreeImprove(root.getMiddleChild());
			}
		}
		
	}
	public void showTree(TreeNode root) {
		if (root.getLeftChild() == null && root.getRightChild() == null
				&& root.getMiddleChild() == null) {
			System.out.print(root.getAttribute() + "--------"
					+ root.getAttributeValue());
		} else {
			if (root.getRightChild() != null&&root.getLeftChild() != null&&root.getMiddleChild()!=null) {
				
				System.out.print(root.getAttribute() + "--------"
						+ root.getAttributeValue());
				
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

}
