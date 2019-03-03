package base_optimization;


public class TreeNode {
	//���ڵ���
	private String attribute;//���ڴ�ŵ�ǰ�ڵ�ķ������Ի��߹ϵ�����
	private String attributeValue;//���ڴ�ŵ�ǰ�ڵ�֮ǰ������ȡ������ֵ
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
	
	//set()get()����
	
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
	 * ������ڵ㣬��ӡ������
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
