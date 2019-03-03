package base;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Methods {
	/**
	 * 计算信息熵
	 * 
	 * @param D传入一个二维数组作为参数
	 * @return
	 */
	public static double ent(String[][] D) {
		List<String> attribute = new ArrayList<String>();
		// 循环遍历，结果的那一列放入list
		for (String[] line : D) {
			if(line == D[0]){
				continue;
			}else{
				attribute.add(line[line.length - 1]);
			}
		}
		// 计算结果中正反值的数量
		int goodNumber = 0;
		int badNumber = 0;
		for (String re : attribute) {
			if (re.equals("1")) {
				goodNumber++;
			} else {
				badNumber++;
			}
		}

		// 计算信息熵,不要使用int，会损失精度，甚至出错
		double total = goodNumber + badNumber;
		double result = 0;
		double lineNumber = D.length-1;
		// 四舍五入，保留三位小数
		if (goodNumber == 0.0) {
			result = -(badNumber / total)
					* ((Math.log((double) badNumber / total) / Math
							.log((double) 2)));

		} else if (badNumber == 0.0) {
			result = (-(goodNumber / total) * ((Math.log((double) goodNumber
							/ total) / Math.log((double) 2))));

		} else {
			result = (-(goodNumber / total) * ((Math.log((double) goodNumber / total) / Math.log((double) 2))) - (badNumber / total) * ((Math.log((double) badNumber / total) / Math.log((double) 2))));

		}
		return new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
	}

	/**
	 * 根据指定属性名计算指定数据集的信息增量（但是只适用于决策树的第一个例子）
	 * 
	 * @param D
	 *            指定的数据集
	 * @param arrName
	 *            指定的属性名
	 * @return
	 */
	public static double infoGain(String[][] D, String arrName) {
		// 根据属性名称arrName得到第几列，实际第几列，而不是数组中第几列
		int i = 1;

		String[] arrNames = D[0];
		for (String arr : arrNames) {
			if (arr.equals(arrName)) {
				break;
			}
			i++;
		}

		// 首先计算得到总的信息熵
		double result = Methods.ent(D);
		int weidth = 0;

		// 一个列表用于存放一列属性值
		List<String> attributes = new ArrayList<String>();
		// 循环遍历，将指定列的属性值放入list
		for (String[] line : D) {
			if (line == D[0]) {
				continue;
			} else {
				attributes.add(line[i - 1]);
				weidth = line.length;
			}
		}
		// 再建一个列表用于存放一列中不同的属性值
		List<String> differentValue = new ArrayList<String>();
		for (String att : attributes) {
			if (differentValue.contains(att)) {
				continue;
			} else {
				differentValue.add(att);
			}
		}
		// 对不同的属性值，计算其总数量及对应的正反结果数量
		String attribute = "";
		while (differentValue.size() != 0) {
			attribute = differentValue.remove(differentValue.size() - 1);

			// 计算属性值为attribute的正反结果数
			int attributeGood = 0;
			int attributeBad = 0;
			int lineNow = 1;//带表头，所以从第二行开始
			for (String att : attributes) {
				if (att.equals(attribute)) {// 属性值列,在该行和指定的属性值吻合
					if (D[lineNow][weidth - 1].equals("1")) {
						attributeGood++;
					} else {
						attributeBad++;
					}
				}
				lineNow++;
			}
			// 得到一组的好坏结果了
			// 以浅白为例，此时attributeGood=1，attributeBad=4
			double total = attributeGood + attributeBad;
			double lineNumber = D.length - 1;
			// attributeGood或者attributeBad为0的情况
			if (attributeGood == 0) {
				double results = (total / lineNumber)
						* -(attributeBad / total)
						* ((Math.log((double) attributeBad / total) / Math
								.log((double) 2)));
				results = new BigDecimal(results).setScale(3, RoundingMode.HALF_UP)
						.doubleValue();
				result = result - results;

			} else if (attributeBad == 0) {
				double results = (total / lineNumber)
						* (-(attributeGood / total) * ((Math
								.log((double) attributeGood / total) / Math
								.log((double) 2))));
				results = new BigDecimal(results).setScale(3, RoundingMode.HALF_UP)
						.doubleValue();
				result = result - results;

			} else {
				double results = (total / lineNumber)
						* (-(attributeGood / total)
								* ((Math.log((double) attributeGood / total) / Math
										.log((double) 2))) - (attributeBad / total)
								* ((Math.log((double) attributeBad / total) / Math
										.log((double) 2))));
				results = new BigDecimal(results).setScale(3, RoundingMode.HALF_UP)
						.doubleValue();
				result = result-results;

			}
		}
		result = new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		return result;
	}

	/**
	 * 给定一组属性名及其对应信息增量的键值对
	 * 返回信息增量值最大的属性名
	 * 
	 * @param maps
	 * @return
	 */
	public static String getLargestAttributeName(Map<String, Double> maps) {
		@SuppressWarnings("rawtypes")
		Set sets = maps.keySet();
		System.out.println(maps);
		String name = "";
		double value = 0.0;
		@SuppressWarnings("rawtypes")
		Iterator i = sets.iterator();// 要先构造迭代器

		// 防止引用出未知问题，逻辑尽量简洁
		while (i.hasNext()) {// 遍历 ，得到所有的属性名
			String str = i.next().toString();
			double val = maps.get(str);
			if (val > value) {
				value = val;
				name = str;
			}

		}
		return name;

	}
	
	/**
	 * 根据指定的属性名，返回给定数据集中该属性名不同的取值
	 * @param D			g给定的数据集
	 * @param attribute 数据集中指定的属性名
	 * @return
	 */
	public static List<String> getAttributeDifferentValues(String[][] D,String attribute){
		List<String> differentValue = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for(int i = 0;i<D[0].length;i++){
			if(D[0][i].equals(attribute)){
				//将第i列的所有元素放入list
				for(int j = 1;j<D.length;j++){
					differentValue.add(D[j][i]);
				}
			}
		}
		for (String att : differentValue) {
			if (values.contains(att)) {
				continue;
			} else {
				values.add(att);
			}
		}
		return values;
		
	}
	
	/**
	 * 根据指定属性的属性值返回子数据集
	 * @param D		给定的原数据集
	 * @param attributeValue  某个属性的属性值      比如（纹理 = “清晰”）
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[][] getAttributeValueDataChild(String[][] D,String attributeName,String attributeValue){
		//首相要得到原数据集中该属性值的数量，方便确定二维数组的行数
		int number = 0;
		
		// 根据属性名称arrName得到第几列，实际第几列，而不是数组中第几列
		int i = 1;

		String[] arrNames = D[0];
		for (String arr : arrNames) {
			if (arr.equals(attributeName)) {
				break;
			}
			i++;
		}
		
		List<String> attributes = new ArrayList<String>();
		// 循环遍历，将指定列的属性值放入list
		for (String[] line : D) {
			if (line == D[0]) {
				continue;
			} else {
				attributes.add(line[i - 1]);
			}
		}
		for(String value:attributes){
			if(value.equals(attributeValue)){
				number++;
			}
		}
		//求得该属性列在原数据集的列数
		int index = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
				break;
			}
			index++;
		}
		//新建一个D.weidth-1行，number列的二维数组
		String[][] dataChild = new String[number+1][D[0].length-1];//注意行列不要写反,行数要加上表头
		//将删除一列后的表头放入子数组
		String[] line = new String[D[0].length-1];
		List list = new ArrayList<String>();
		for(String str:D[0]){
			list.add(str);
		}
		list.remove(index);
		
		//这里只是利用列表删除一列，然后继续放入数组
		int j = 0;
		for(Object str:list){
			dataChild[0][j] = str.toString();
			j++;
		}
		//将剩下的和属性值相等的一行都放入子数据集
		//要获取对应的列数
		j = 1;
		for(int column = 0;column<attributes.size();column++){
			list.clear();
			if(attributes.get(column).equals(attributeValue)){//attributes中已经去除了表头
				for(String str:D[column+1]){
					list.add(str);
				}
				list.remove(index);//移除一列元素
				int lin = 0;
				for(Object str:list){
					dataChild[j][lin] = str.toString();
					lin++;
				}
				
				j++;
			}
		}
		//new Data().showData(dataChild);
		return dataChild;
		
	}
	
	/**
	 * 判断一个二维数据集的结果是否唯一，是就返回结果，否则返回null
	 * @param D
	 * @return
	 */
	public static String getChildResults(String[][] D){
		//从第二行开始
		String te = D[1][D[0].length-1];
		for (int i = 1; i < D.length; i++) {

			if (te.equals(D[i][D[0].length-1])) {
				continue;
			}else{
				return null;
			}

		}
		return te;
	}
	
	/**
	 
	 * @return
	 */

	public static boolean getChildResultsisEmpty(){
		return false;}

	public static void main(String[] args){
		double dou = Methods.infoGain(new Data().getData(), "触感");
		System.out.println(dou);
	}
}
