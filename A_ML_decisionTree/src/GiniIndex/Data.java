package GiniIndex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class Data {
	//基于表4.2的数据集和基尼指数生成决策树
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
	 * 返回指定属性在指定数据集上指定属性值的数量
	 * @param D
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	public double getAttributeValueNumber(String[][] D,String attributeName,String attributeValue){
		int speColumn = 0;
		for(int i=0;i<D[0].length;i++){
			if(D[0][i].equals(attributeName)){
				break;
			}
			speColumn++;
		}
		double number = 0;
		for(int i=1;i<D.length;i++){
			if(D[i][speColumn].equals(attributeValue)){
				number++;
			}
		}
		return number;
		
	}
	
	/**
	 * 返回给定数据集的基尼值
	 * @param D
	 * @return
	 */
	public double giniValue(String[][] D){
		double goodNumber = new Data().getAttributeValueNumber(D, "结果", "1");
		double badNumber = new Data().getAttributeValueNumber(D, "结果", "0");
		double total = goodNumber+badNumber;
		double result = (goodNumber/total)*(goodNumber/total)+(badNumber/total)*(badNumber/total);
		result = 1-result;
		return new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		
	}
	
	/**
	 * 返回指定属性名对应的基尼指数值
	 * @param D
	 * @param attributeName
	 * @return
	 */
	public double giniIndexValue(String[][] D,String attributeName){
		List<String> lists = new Data().getDifferentAttributeValues(D, attributeName);
		//new Data().getChildData(D, attributeName, lists.get(0));
		//new Data().showData(new Data().getChildData(D, attributeName, lists.get(0)));
		double result = 0;
		for(String value:lists){
			String[][] child = new Data().getChildData(D, attributeName, value);
			double dou = (double)(child.length-1)/(double)(D.length-1)*(new Data().giniValue(child));
			result = result + dou;
		}
		return new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		
	}
	
	/**
	 * 返回最大数据集中最大的基尼指数对应的属性名
	 * @param D
	 * @return
	 */
	public String getMinAttributeName(String[][] D){
		LinkedList<String> names = new LinkedList<String>();
		for(String name:D[0]){
			names.add(name);
		}
		LinkedList<Double> values = new LinkedList<Double>();
		for(int i=0;i<names.size()-1;i++){
			String name = names.get(i);
			double value = new Data().giniIndexValue(D, name);
			values.add(value);
		}
//		System.out.println(names);
//		System.out.println(values);
		double min = 10;
		int index = 0;
		for(int i=0;i<values.size();i++){
			if(values.get(i)<min){
				min = values.get(i);
				index = i;
			}
		}
		return names.get(index);
		
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
	
	public static void main(String[] args){
		String name = new Data().getMinAttributeName(new Data().getData());
		System.out.println(name);
	}

}
