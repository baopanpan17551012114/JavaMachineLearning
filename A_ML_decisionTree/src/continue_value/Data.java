package continue_value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Data {
	//数据集的类，包括相关的函数
	private String[][] datas = new String[][] {
			{ "色泽", "根蒂", "敲声", "纹理", "脐部", "触感","密度","含糖率", "结果" },
			{ "青绿", "蜷缩", "浊响", "清晰", "凹陷", "硬滑","0.697","0.460", "1" },
			{ "乌黑", "蜷缩", "沉闷", "清晰", "凹陷", "硬滑","0.774","0.376", "1" },
			{ "乌黑", "蜷缩", "浊响", "清晰", "凹陷", "硬滑","0.634","0.264", "1" },
			{ "青绿", "蜷缩", "沉闷", "清晰", "凹陷", "硬滑","0.608","0.318", "1" },
			{ "浅白", "蜷缩", "浊响", "清晰", "凹陷", "硬滑","0.556","0.215", "1" },
			{ "青绿", "稍蜷", "浊响", "清晰", "稍凹", "软粘","0.403","0.237", "1" },
			{ "乌黑", "稍蜷", "浊响", "稍糊", "稍凹", "软粘","0.481","0.149", "1" },
			{ "乌黑", "稍蜷", "浊响", "清晰", "稍凹", "硬滑","0.437","0.211", "1" },
			{ "乌黑", "稍蜷", "沉闷", "稍糊", "稍凹", "硬滑","0.666","0.091", "0" },
			{ "青绿", "硬挺", "清脆", "清晰", "平坦", "软粘","0.243","0.267", "0" },
			{ "浅白", "硬挺", "清脆", "模糊", "平坦", "硬滑","0.245","0.057", "0" },
			{ "浅白", "蜷缩", "浊响", "模糊", "平坦", "软粘","0.343","0.099", "0" },
			{ "青绿", "稍蜷", "浊响", "稍糊", "凹陷", "硬滑","0.639","0.161", "0" },
			{ "浅白", "稍蜷", "沉闷", "稍糊", "凹陷", "硬滑","0.657","0.198", "0" },
			{ "乌黑", "稍蜷", "浊响", "清晰", "稍凹", "软粘","0.360","0.370", "0" },
			{ "浅白", "蜷缩", "浊响", "模糊", "平坦", "硬滑","0.593","0.042", "0" },
			{ "青绿", "蜷缩", "沉闷", "稍糊", "稍凹", "硬滑","0.719","0.103", "0" } };

	
	//get()set()函数
	public String[][] getDatas() {
		return datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
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
	 * 计算含有连续数值的数据集的信息熵，方法不变，并且四舍五入保留三位
	 * @param D 指定的数据集（二维数组），带表头（属性名一行）
	 * @return
	 */
	public double getEnt(String[][] D){
		if(D.length == 1){
			return 0;
		}
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
	 * 给定数据集和指定的属性名（值为连续），返回按大小排序后得到的中值
	 * @param D  给定的数据集
	 * @param attributeName 给定的属性名
	 * @return
	 */
	public List<Double> getOrderMiddleAttributeValues(String[][] D,String attributeName){
		int columnNumber = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
				break;
			}
			columnNumber++;
		}
		double[] order = new double[D.length-1];
		for(int i=1;i<D.length;i++){
			order[i-1] = Double.valueOf(D[i][columnNumber]);
		}
		for(int i=0;i<order.length;i++){
			double min = order[i];
			int sign = 0;
			for(int j=i;j<order.length;j++){
				if(order[j]<min){
					min = order[j];
					sign = j;
				}
			}
			if(sign != 0){
				double bridge = order[i];
				order[i] = min;
				order[sign] = bridge;
			}
		}
		List<Double> list = new LinkedList<Double>();
		for(int i=1;i<order.length;i++){
			double an = order[i]+order[i-1];
			an = an / 2;
			an = new BigDecimal(an).setScale(3, RoundingMode.DOWN).doubleValue();
			list.add(an);
		}
		return list;
	}
	
	/**
	 * 根据给定的值，返回数据集在指定属性名（连续值）上的子集,这个子集用于计算信息增量，不用减一列
	 * @param D
	 * @param d
	 * @return
	 */
	public String[][] getChildDataBiggerThan(String[][] D,String attributeName,double d){
		int deleteColumn = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
				break;
			}
			deleteColumn++;
		}
		int columnNumber = 0;
		for(int i=1;i<D.length;i++){
			if(Double.valueOf(D[i][deleteColumn])>d){
				columnNumber++;
			}
		}
		String[][] childData = new String[columnNumber+1][D[0].length];
		int column = 0;
		for(int i=0;i<D[0].length;i++){
			if(i == deleteColumn){
				childData[0][column] = D[0][i];
				column++;
			}else{
				childData[0][column] = D[0][i];
				column++;
			}
		}
		int lineCount = 1;
		for(int line=1;line<D.length;line++){
			if(Double.valueOf(D[line][deleteColumn])>d){
				column = 0;
				for(int i=0;i<D[0].length;i++){
					if(i == deleteColumn){
						childData[lineCount][column] = D[line][i];
						column++;
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
	 * 根据给定的值，返回数据集在指定属性名（连续值）上的子集,这个子集用于计算信息增量，不用减一列
	 * @param D
	 * @param d
	 * @return
	 */
	public String[][] getChildDataLittlerThan(String[][] D,String attributeName,double d){
		int deleteColumn = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
				break;
			}
			deleteColumn++;
		}
		int columnNumber = 0;
		for(int i=1;i<D.length;i++){
			if(Double.valueOf(D[i][deleteColumn])<d){
				columnNumber++;
			}
		}
		String[][] childData = new String[columnNumber+1][D[0].length];
		int column = 0;
		for(int i=0;i<D[0].length;i++){
			if(i == deleteColumn){
				childData[0][column] = D[0][i];
				column++;
			}else{
				childData[0][column] = D[0][i];
				column++;
			}
		}
		int lineCount = 1;
		for(int line=1;line<D.length;line++){
			if(Double.valueOf(D[line][deleteColumn])<d){
				column = 0;
				for(int i=0;i<D[0].length;i++){
					if(i == deleteColumn){
						childData[lineCount][column] = D[line][i];
						column++;
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
	 * 返回指定数据集在指定的连续属性上返回的信息增量,及其对应的划分点
	 * @param D
	 * @param attributeName
	 * @return <k,v>k是信息增量，v是其对应的划分点,map不方便。使用一个list，0处放信息增量，1处放划分点
	 */
	public List<Double> getContinueInfoGain(String[][] D,String attributeName){
		List<Double> list = new Data().getOrderMiddleAttributeValues(D, attributeName);
		List<Double> values = new LinkedList<Double>();
		for(double d:list){
			String[][] bigger = new Data().getChildDataBiggerThan(D, attributeName, d);
			String[][] little = new Data().getChildDataLittlerThan(D, attributeName, d);
			double infoGain = new Data().getEnt(D)-((double)(bigger.length-1)/(double)(D.length-1))*new Data().getEnt(bigger)-((double)(little.length-1)/(double)(D.length-1))*new Data().getEnt(little);
			 infoGain = new BigDecimal(infoGain).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
			values.add(infoGain);
		}
		double bridge = 0;
		int index = 0;
		int sign = 0;
		for(double d:values){
			if(d>bridge){
				bridge = d;
				sign = index;
			}
			index++;
		}

		List<Double> map = new LinkedList<Double>();
		//map.put(bridge, list.get(sign));
		map.add(bridge);
		map.add(list.get(sign));
		return map;
		
	}
	
	/**
	 * 给定一个数据集，返回这个数据集中信息增量最大时所对应的属性名,包括连续属性
	 * @param D  给定的二维数组数据集
	 * @return
	 */
	public String getLargestInfogainAtributeName(String[][] D){
		Map<String,Double> attributeValues = new LinkedHashMap<String, Double>();
		int count = 1;
		for(String str:D[0]){
			if(count<D[0].length){
				if(new Data().isContinueAttributeName(D, str)){
					attributeValues.put(str, new Data().getContinueInfoGain(D, str).get(0));
					count++;
					continue;
				}
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
	 * 根据传入的属性名判断数据集在该属性名下是否是连续属性值
	 * @param D
	 * @param attributeName
	 * @return 是就返回true，否则返回false
	 */
	public boolean isContinueAttributeName(String[][] D,String attributeName){
		int columnNumber = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
				break;
			}
			columnNumber++;
		}
		String str = D[1][columnNumber];
		try{
			Double.valueOf(str);
			return true;
		}catch(Exception e){
			return false;
		}
		
	}
	
	/**
	 * 主方法，测试各个方法
	 * @param args
	 */
	public static void main(String[] args){
		System.out.println(new Data().isContinueAttributeName(new Data().getDatas(), "敲声"));
	}
	
}
