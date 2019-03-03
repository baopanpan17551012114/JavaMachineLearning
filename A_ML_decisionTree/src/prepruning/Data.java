package prepruning;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Data {
	private String[][] data = new String[][] {
			{ "色泽", "根蒂", "敲声", "纹理", "脐部", "触感", "结果" },
			{ "青绿", "蜷缩", "浊响", "清晰", "凹陷", "硬滑", "1" },
			{ "乌黑", "蜷缩", "沉闷", "清晰", "凹陷", "硬滑", "1" },
			{ "乌黑", "蜷缩", "浊响", "清晰", "凹陷", "硬滑", "1" },
			{ "青绿", "稍蜷", "浊响", "清晰", "稍凹", "软粘", "1" },
			{ "乌黑", "稍蜷", "浊响", "稍糊", "稍凹", "软粘", "1" },
			{ "青绿", "硬挺", "清脆", "清晰", "平坦", "软粘", "0" },
			{ "浅白", "稍蜷", "沉闷", "稍糊", "凹陷", "硬滑", "0" },
			{ "乌黑", "稍蜷", "浊响", "清晰", "稍凹", "软粘", "0" },
			{ "浅白", "蜷缩", "浊响", "模糊", "平坦", "硬滑", "0" },
			{ "青绿", "蜷缩", "沉闷", "稍糊", "稍凹", "硬滑", "0" } };

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}
	/**
	 * 返回当前二维数组数据集的结果，结果不唯一时返回null,只有一个表头时返回-1
	 * @param D	D时带表头（即"色泽", "根蒂"......这个一行 ）的数据集（二维数组）
	 * @return
	 */
	public String getDatasResult(String[][] D){
		if(D.length == 1){
			return "-1";
		}
		String result = D[1][D[0].length - 1];
		for (int i = 1; i < D.length; i++) {
			if (result.equals(D[i][D[0].length - 1])) {
				continue;
			} else {
				return null;
			}
		}
		return result;
	}
	
	/**
	 * 获得指定数据集的信息熵，并且四舍五入保留三位
	 * @param D 指定的数据集（二维数组），带表头（属性名一行）
	 * @return
	 */
	public double getEnt(String[][] D){
		List<String> attribute = new ArrayList<String>();
		for (String[] line : D) {
			if (line == D[0]) {
				continue;
			} else {
				attribute.add(line[line.length - 1]);
			}
		}
		int goodNumber = 0;
		int badNumber = 0;
		for (String re : attribute) {
			if (re.equals("1")) {
				goodNumber++;
			} else {
				badNumber++;
			}
		}
		double total = goodNumber + badNumber;
		double result = 0;
		if (goodNumber == 0.0) {
			result = -(badNumber / total)
					* ((Math.log((double) badNumber / total) / Math
							.log((double) 2)));

		}else if (badNumber == 0.0) {
			result = (-(goodNumber / total) * ((Math.log((double) goodNumber
					/ total) / Math.log((double) 2))));

		} else {
			result = (-(goodNumber / total)
					* ((Math.log((double) goodNumber / total) / Math
							.log((double) 2))) - (badNumber / total)
					* ((Math.log((double) badNumber / total) / Math
							.log((double) 2))));

		}
		return new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
	}
	
	/**
	 * 获得指定数据集（二维数组），在指定属性下的信息增量
	 * @param D			指定的数据集
	 * @param attributeName	指定的属性
	 * @return
	 */
	public double getInfoGain(String[][] D,String attributeName){
		double result = new Data().getEnt(D);
		int column = 0;
		String[] arrNames = D[0];
		for (String arr : arrNames) {
			if (arr.equals(attributeName)) {
				break;
			}
			column++;
		}
		int weidth = 0;
		List<String> attributes = new ArrayList<String>();
		for (String[] line : D) {
			if (line == D[0]) {
				continue;
			} else {
				attributes.add(line[column]);
				weidth = line.length;
			}
		}
		List<String> differentValue = new ArrayList<String>();
		for (String att : attributes) {
			if (differentValue.contains(att)) {
				continue;
			} else {
				differentValue.add(att);
			}
		}
		String attribute = "";
		while (differentValue.size() != 0) {
			attribute = differentValue.remove(differentValue.size() - 1);
			int attributeGood = 0;
			int attributeBad = 0;
			int lineNow = 1;
			for (String att : attributes) {
				if (att.equals(attribute)) {
					if (D[lineNow][weidth - 1].equals("1")) {
						attributeGood++;
					} else {
						attributeBad++;
					}
				}
				lineNow++;
			}
			double total = attributeGood + attributeBad;
			double lineNumber = D.length - 1;
			if (attributeGood == 0) {
				double results = (total / lineNumber)
						* -(attributeBad / total)
						* ((Math.log((double) attributeBad / total) / Math
								.log((double) 2)));
				results = new BigDecimal(results).setScale(3,
						RoundingMode.HALF_UP).doubleValue();
				result = result - results;

			} else if (attributeBad == 0) {
				double results = (total / lineNumber)
						* (-(attributeGood / total) * ((Math
								.log((double) attributeGood / total) / Math
								.log((double) 2))));
				results = new BigDecimal(results).setScale(3,
						RoundingMode.HALF_UP).doubleValue();
				result = result - results;

			} else {
				double results = (total / lineNumber)
						* (-(attributeGood / total)
								* ((Math.log((double) attributeGood / total) / Math
										.log((double) 2))) - (attributeBad / total)
								* ((Math.log((double) attributeBad / total) / Math
										.log((double) 2))));
				results = new BigDecimal(results).setScale(3,
						RoundingMode.HALF_UP).doubleValue();
				result = result - results;

			}
		}
		result = new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		return result;

	}
	
	/**
	 * 给定一个数据集，返回这个数据集中信息增量最大时所对应的属性名
	 * @param D  给定的二维数组数据集
	 * @return
	 */
	public String getLargestInfogainAtributeName(String[][] D){
		Map<String,Double> attributeValues = new LinkedHashMap<String, Double>();
		int count = 1;
		for(String str:D[0]){
			if(count<D[0].length){
				attributeValues.put(str, new Data().getInfoGain(D, str));
			}
			count++;
		}
		@SuppressWarnings("rawtypes")
		Set attributeNames = attributeValues.keySet();
		@SuppressWarnings("rawtypes")
		Iterator iterator = attributeNames.iterator();
		double value = 0;
		String name = "";
		while(iterator.hasNext()){
			String str = iterator.next().toString();
			double val = attributeValues.get(str);
			if (val > value) {
				value = val;
				name = str;
			}
		}
		return name;
		
	}
	
	/**
	 * 返回给定的属性在数据集中的可能取值（多个相同值只取一个）
	 * @param D  给定的数据集，二维数组
	 * @param attribute  给定的属性
	 * @return
	 */
	public List<String> getDifferentAttributeValues(String[][] D,String attribute){
		List<String> attributeValues = new LinkedList<String>();
		int column = 0;
		for(String str:D[0]){
			if(str.equals(attribute)){
				break;
			}
			column++;
		}
		for(int line=1;line<D.length;line++){
			if(attributeValues.contains(D[line][column])){
				continue;
			}else{
				attributeValues.add(D[line][column]);
			}
		}
		return attributeValues;
	}
	
	/**
	 * 根据给定的数据集和属性值返回子数据集
	 * @param D 给定的数据集
	 * @param attribute 给定的属性（比如：色泽=青绿）色泽
	 * @param attributeValue 给定的属性值（比如：色泽=青绿）青绿
	 * @return 返回子数据集
	 */
	public String[][] getChildData(String[][] D,String attribute,String attributeValue){
		int deleteColumn = 0;
		for(String str:D[0]){
			if(str.equals(attribute)){
				break;
			}
			deleteColumn++;
		}
		int columnNumber = 0;
		for(int i=1;i<D.length;i++){
			if(D[i][deleteColumn].equals(attributeValue)){
				columnNumber++;
			}
		}
		String[][] childData = new String[columnNumber+1][D[0].length-1];
		int column = 0;
		for(int i=0;i<D[0].length;i++){
			if(i == deleteColumn){
				continue;
			}else{
				childData[0][column] = D[0][i];
				column++;
			}
		}
		int lineCount = 1;
		for(int line=1;line<D.length;line++){
			if(D[line][deleteColumn].equals(attributeValue)){
				column = 0;
				for(int i=0;i<D[0].length;i++){
					if(i == deleteColumn){
						continue;
					}else{
						childData[lineCount][column] = D[line][i];
						column++;
					}
				}
				lineCount++;
			}
		}
		return childData;
	}
	
	/**
	 * 打印给定的数据集
	 * @param D
	 */
	public void showData(String[][] D){
		for(int line = 0;line<D.length;line++){
			for(int column = 0;column<D[0].length;column++){
				System.out.print(D[line][column]+"\t");
			}
			System.out.println("");
		}
	}
	
	/**
	 * 根据给定的数据集，返回其中结果中的绝大多数，1多则返回1,0多则返回0
	 * @param D 给定的数据集
	 * @return 返回值
	 */
	public String getDataMostValue(String[][] D){
		int good = 0;
		int bad = 0;
		for(int i=1;i<D.length;i++){
			if(D[i][D[0].length-1].equals("1")){
				good++;
			}else{
				bad++;
			}
		}
		if(good>bad){
			return "1";
		}else{
			return "0";
		}
	}
	
	/**
	 * 构造决策树，要使用递归
	 * @param D  传入的子数据集
	 * @param node 传入的当前节点，只构造了一半，该节点中只有子数据集分类时根据的属性值
	 */
	public static void TreeSet(String[][] D,TreeNode node){
		if(new Data().getDatasResult(D) == null){
			String attributeName = new Data().getLargestInfogainAtributeName(D);
			node.setAttribute(attributeName);
			//List<String> list = new Data().getDifferentAttributeValues(D, attributeName);
			List<String> listAllAttributeValue = new Data().getDifferentAttributeValues(new Data().getData(), attributeName);
			for(String str:listAllAttributeValue){
				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue(str);
				String[][] childData = new Data().getChildData(D, attributeName, str);
				//子集中不含这个属性值，那么子集就只有表头一行
				if(childData.length == 1){
					if(new Data().getDataMostValue(D).equals("1")){
						childNode.setAttribute("好瓜");
					}else{
						childNode.setAttribute("坏瓜");
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
				node.setAttribute("好瓜");
			}else{
				node.setAttribute("坏瓜");
			}
		}
	}
	
	/**
	 * 对一棵树进行复制，产生两棵相同的树，但是根节点并不同
	 * 似乎也需要用到递归？？？？？？？？？？？？？？？？？？？？？？？？？？？
	 * @param node
	 * @param newRoot
	 * @return
	 */
	public void copyTree(TreeNode node,TreeNode newRoot){
		newRoot.setAttribute(node.getAttribute());
		newRoot.setAttributeValue(node.getAttributeValue());
		if(node.getLeftChild() == null){
			
		}else if(node.getRightChild() == null){
			
		}else{
		
		}
	}
	
	/**
	 * 构造预剪枝决策树,传入一个节点，该节点中有其父节点的一个属性值分类，并且已经确定对当前将要获取的属性进行展开比较合适
	 * 但是无法确定其1、仅仅展开当前比较好，2、进一步展开（对子节点）更加好
	 * @param D
	 * @param node
	 */
	public static void preTreeSet(String[][] D,TreeNode node){
		if(new Data().getDatasResult(D) == null){
			
			String attributeName = new Data().getLargestInfogainAtributeName(D);
			node.setAttribute(attributeName);
			
			double openNode = 0;//展开的正确率
			double closeNode = 0;//不展开的正确率（所谓不展开，就是对该属性对应属性值都分为叶子结点）
			
			//现在的问题在于：节点的进化具有不可逆转性
			
			List<String> listAllAttributeValue = new Data().getDifferentAttributeValues(new Data().getData(), attributeName);
			for(String str:listAllAttributeValue){
				TreeNode childNode = new TreeNode();
				childNode.setAttributeValue(str);
				String[][] childData = new Data().getChildData(D, attributeName, str);
				if(childData.length == 1){
					if(new Data().getDataMostValue(D).equals("1")){
						childNode.setAttribute("好瓜");
					}else{
						childNode.setAttribute("坏瓜");
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
				node.setAttribute("好瓜");
			}else{
				node.setAttribute("坏瓜");
			}
		}
	}
	
	
	/**
	 * 根据生成的决策树对测试集中的数据进行预测，正例返回“好瓜”，否则返回“坏瓜”
	 * 全部都是离散值
	 * @param D
	 * @param test
	 * @return
	 */
	public String getPredictValue(String[][] D,String[][] test){
		int good = 0;
		int bad = 0;
		TreeNode root = new TreeNode();
		new Data().TreeSet(D, root);
		//new TreeNode().showTreeImprove(root);
		// 根据训练出的决策树对要预测的数据进行预测
		List<String> para1 = new LinkedList<String>();
		List<String> para2 = new LinkedList<String>();
		for(int i=0;i<test[0].length;i++){
			para1.add(test[0][i]);
			para2.add(test[1][i]);
		}
		String result = "";
		while (!((root.getAttribute().equals("好瓜")) || (root.getAttribute()
				.equals("坏瓜")))) {
			String currentAttribute = root.getAttribute();
			String currentAttributeValue = "";
			for(int i=0;i<para1.size();i++){
				if(para1.get(i).equals(currentAttribute)){
					currentAttributeValue = para2.get(i);
				}
			}
			//System.out.println(currentAttributeValue);
			if (root.getLeftChild() != null) {
				if(root.getLeftChild().getAttributeValue().equals(currentAttributeValue)){
					root = root.getLeftChild();
				}
			}
			if (root.getRightChild() != null) {
				if(root.getRightChild().getAttributeValue().equals(currentAttributeValue)){
					root = root.getRightChild();
				}
			}
			if (root.getMiddleChild() != null) {
				if(root.getMiddleChild().getAttributeValue().equals(currentAttributeValue)){
					root = root.getMiddleChild();
				}
			}
			
		}
		result = root.getAttribute();
		if(result.equals("坏瓜")){
			return "0";
		}else{
			return "1";
		}
	}
	
	/**
	 * 给定指定属性在数据集上对指定的属性值判定，如果1多，就返回1；否则返回 0
	 * @param D
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	public String getMostAttributeValueResult(String[][] D,String attributeName,String attributeValue){
		double good = 0;
		double bad = 0;
		int speColumn = 0;
		for(int i=0;i<D[0].length;i++){
			if(D[0][i].equals(attributeName)){
				break;
			}
			speColumn++;
		}
		for(int i=1;i<D.length;i++){
			if(D[i][speColumn].equals(attributeValue)){
				if(D[i][D[0].length-1].equals("1")){
					good++;
				}else{
					bad++;
				}
			}
		}
		if(good>=bad){
			return "1";
		}else{
			return "0";
		}
	}
	
	/**
	 * 返回测试数据集的正确率
	 * @param D
	 * @param test
	 * @return
	 */
	public double getPredictionPercent(String[][] D,String[][] test){
		double rightPercent = 0;
		String[][] child = new String[2][test[0].length-1];
		double right = 0;
		double fault = 0;
		for(int i=1;i<test.length;i++){
			String realResult = test[i][test[0].length-1];
			for(int j=0;j<test[0].length-1;j++){
				child[1][j] = test[i][j];
			}
			String pre = new Data().getPredictValue(D, test);
			System.out.println(pre);
			if(pre.equals(realResult)){
				right++;
			}else{
				fault++;
			}
			
		}
		rightPercent = right/(right+fault);
		
		return new BigDecimal(rightPercent).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		
	}
	
	public static void main(String[] args){
		String[][] test = new String[][]{{ "色泽", "根蒂", "敲声", "纹理", "脐部", "触感","结果"},
				{"青绿", "蜷缩", "沉闷", "清晰", "凹陷", "硬滑","1"},
				{"浅白", "蜷缩", "浊响", "清晰", "凹陷", "硬滑","1"},
				{"乌黑", "稍蜷", "浊响", "清晰", "稍凹", "硬滑","1"},
				{"乌黑", "蜷缩", "沉闷", "稍糊", "稍凹", "硬滑","0"},
				{"浅白", "硬挺", "清脆", "模糊", "平坦", "硬滑","0"},
				{"浅白", "蜷缩", "浊响", "模糊", "平坦", "软粘","0"},
				{"青绿", "稍蜷", "浊响", "模糊", "凹陷", "硬滑","0"}};
		double value = new Data().getPredictionPercent(new Data().getData(), test);
		System.out.println(value);
	}

}
