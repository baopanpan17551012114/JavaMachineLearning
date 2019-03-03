package lose_value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Datas {
	private String[][] datas = new String[][] {
			{ "色泽", "根蒂", "敲声", "纹理", "脐部", "触感", "结果" },
			{ "-", "蜷缩", "浊响", "清晰", "凹陷", "硬滑", "1" },
			{ "乌黑", "蜷缩", "-", "清晰", "凹陷", "-", "1" },
			{ "乌黑", "蜷缩", "浊响", "清晰", "凹陷", "硬滑", "1" },
			{ "青绿", "蜷缩", "沉闷", "清晰", "凹陷", "硬滑", "1" },
			{ "-", "蜷缩", "浊响", "清晰", "凹陷", "硬滑", "1" },
			{ "青绿", "稍蜷", "浊响", "清晰", "-", "软粘", "1" },
			{ "乌黑", "稍蜷", "浊响", "稍糊", "稍凹", "软粘", "1" },
			{ "乌黑", "稍蜷", "浊响", "-", "稍凹", "硬滑", "1" },
			{ "乌黑", "-", "沉闷", "稍糊", "稍凹", "硬滑", "0" },
			{ "青绿", "硬挺", "清脆", "-", "平坦", "软粘", "0" },
			{ "浅白", "硬挺", "清脆", "模糊", "平坦", "-", "0" },
			{ "浅白", "蜷缩", "-", "模糊", "平坦", "软粘", "0" },
			{ "-", "稍蜷", "浊响", "稍糊", "凹陷", "硬滑", "0" },
			{ "浅白", "稍蜷", "沉闷", "稍糊", "凹陷", "硬滑", "0" },
			{ "乌黑", "稍蜷", "浊响", "清晰", "-", "软粘", "0" },
			{ "浅白", "蜷缩", "浊响", "模糊", "平坦", "硬滑", "0" },
			{ "青绿", "-", "沉闷", "稍糊", "稍凹", "硬滑", "0" } };
	private double[] weight = new double[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
	private List<Object> map = new LinkedList<Object>();
	
	public Datas(){}
	
	public Datas(String[][] D,double[] weight){
		map.add(D);
		map.add(weight);
	}
	
	
	
	public String[][] getDatas() {
		return datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
	}

	public double[] getWeight() {
		return weight;
	}

	public void setWeight(double[] weight) {
		this.weight = weight;
	}

	public List<Object> getMap() {
		return map;
	}


	public void setMap(List<Object> map) {
		this.map = map;
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
	 * 给定包含数据集和权重集的集合，返回去除缺失值一行的数据集合权重集，不用删除一列
	 * @param map
	 * @param attributeName
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List getRemoveLostValueChildData(List map,String attributeName){
		String[][] D = (String[][])map.get(0);
		double[] weight = (double[])map.get(1);
		int deleteColumn = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
				break;
			}
			deleteColumn++;
		}
		int columnNumber = 0;//计算子数据集的行数
		for(int i=1;i<D.length;i++){
			if(D[i][deleteColumn].equals("-")){
				columnNumber++;
			}
		}
		columnNumber = D.length-columnNumber;
		String[][] childData = new String[columnNumber][D[0].length];
		double[] weightChild = new double[columnNumber-1];
		for(int i=0;i<D[0].length;i++){
			childData[0][i] = D[0][i];
		}//数据集的第一行已经放入子集
		//将其他不为缺失的行放入
		int column = 0;
		for(int i=1;i<D.length;i++){
			if(D[i][deleteColumn].equals("-")){
				continue;
			}else{
				for(int j=0;j<D[0].length;j++){
					childData[column+1][j] = D[i][j];
				}
				weightChild[column] = weight[i-1];
				column++;
			}
		}
		List maps = new LinkedList<Object>();
		maps.add(childData);
		maps.add(weightChild);
		return maps;
	}
	
	/**
	 * 获得指定数据集的信息熵，原始数据集中有缺失值，此时计算信息熵要在具体的属性上 ,根据权重，而不是数量来计算
	 * @param D 指定的数据集（二维数组），带表头（属性名一行）
	 * @return
	 */
	public double getEnt(@SuppressWarnings("rawtypes") List map,String attributeName){
		String[][] D = (String[][])map.get(0);
		double[] weight = (double[])map.get(1);
		String[][] childData = (String[][])new Datas().getRemoveLostValueChildData(map, attributeName).get(0);
		List<String> attribute = new ArrayList<String>();
		for (String[] line : childData) {
			if (line == childData[0]) {
				continue;
			} else {
				attribute.add(line[line.length - 1]);
			}
		}
		double goodNumber = 0;
		double badNumber = 0;
		int count = 0;
		for (String re : attribute) {
			if (re.equals("1")) {
				goodNumber = goodNumber+weight[count];
				count++;
			} else {
				badNumber = badNumber+weight[count];
				count++;
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
	 * 获得指定数据集（二维数组），在指定属性下的信息增量,这个原始数据集带有缺失值
	 * @param D			指定的数据集
	 * @param attributeName	指定的属性
	 * @return
	 */
	public double getInfoGain(@SuppressWarnings("rawtypes") List map,String attributeName){
		String[][] D = (String[][])map.get(0);
		//new Datas().showData(D);
		double[] weight = (double[])map.get(1);
		String[][] childData = (String[][])new Datas().getRemoveLostValueChildData(map, attributeName).get(0);
		double result = new Datas().getEnt(map,attributeName);
		int column = 0;
		String[] arrNames = childData[0];
		for (String arr : arrNames) {
			if (arr.equals(attributeName)) {
				break;
			}
			column++;
		}
		int weidth = 0;
		List<String> attributes = new ArrayList<String>();
		for (String[] line : childData) {
			if (line == childData[0]) {
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
			double attributeGood = 0;
			double attributeBad = 0;
			int lineNow = 1;
			int count = 0;
			for (String att : attributes) {
				if (att.equals(attribute)) {
					if (childData[lineNow][weidth - 1].equals("1")) {
						attributeGood = attributeGood+weight[count];
						
					} else {
						attributeBad = attributeBad + weight[count];
					}
				}
				lineNow++;
				count++;
			}
			double total = attributeGood + attributeBad;
			double lineNumber = childData.length - 1;
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
		double number = 0;
		for(int i=1;i<D.length;i++){
			if(D[i][column].equals("-")){
				continue;
			}else{
				number++;
			}
		}
		double wei = number/(D.length-1);
		result = result*wei;
		result = new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		return result;

	}
	
	/**
	 * 属性值有缺失，给定一个包含数据集和权重数组的集合，返回对应信息增量最大的属性名
	 * @param map
	 * @return
	 */
	public String getLargestInfogainAtributeName(@SuppressWarnings("rawtypes") List map){
		String[][] D = (String[][])map.get(0);
		Map<String,Double> attributeValues = new LinkedHashMap<String, Double>();
		int count = 1;
		//new Datas().showData(D);
		for(String str:D[0]){
			if(count<D[0].length){
				
				attributeValues.put(str, new Datas().getInfoGain(map, str));
				//System.out.print(str+"  "+new Datas().getInfoGain(map,str)+";\t");
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
	 * 给定混合数据map，以及属性名和属性值，返回该属性值在属性所有值总数中所占比例
	 * @param map
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	public double getAttributeValueInTotal(@SuppressWarnings("rawtypes") List map,String attributeName,String attributeValue){
		String[][] D = (String[][])map.get(0);
		@SuppressWarnings("unused")
		double[] weight = (double[])map.get(1);
		int deleteColumn = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
				break;
			}
			deleteColumn++;
		}
		double attvalue = 0;
		double total = 0;
		for(int i=1;i<D.length;i++){
			if(D[i][deleteColumn].equals("-")){
				total++;
				
			}
			if(D[i][deleteColumn].equals(attributeValue)){
				attvalue++;
			}
		}
		
		System.out.print(attvalue+"%%%%"+(D.length-1-total));
		return attvalue/(D.length-1-total);
		
	}
	
	/**
	 * 给定一个list（包含数据集和权重集），根据给定的属性值和属性名，返回子数据集和子权重集的list
	 * @param map
	 * @param attribute
	 * @param attributeValue
	 * @return 返回的数据集要删除一列
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getChildDataAndWeight(List map,String attribute,String attributeValue){
		String[][] D = (String[][])map.get(0);
		double[] weight = (double[])map.get(1);
		int deleteColumn = 0;
		for(String str:D[0]){
			if(str.equals(attribute)){
				break;
			}
			deleteColumn++;
		}
		int columnNumber = 0;//计算子数据集的行数
		for(int i=1;i<D.length;i++){
			if(D[i][deleteColumn].equals(attributeValue) || D[i][deleteColumn].equals("-")){
				columnNumber++;
			}
		}
		String[][] childData = new String[columnNumber+1][D[0].length-1];
		double[] weightChild = new double[columnNumber];
		int column = 0;
		for(int i=0;i<D[0].length;i++){
			if(i == deleteColumn){
				continue;
			}else{
				childData[0][column] = D[0][i];
				column++;
			}
		}//数据集的第一行已经放入子集
		int lineCount = 1;
		for(int line=1;line<D.length;line++){
			if(D[line][deleteColumn].equals(attributeValue) || D[line][deleteColumn].equals("-")){
				column = 0;
				for(int i=0;i<D[0].length;i++){
					if(i == deleteColumn){
						continue;
					}else{
						childData[lineCount][column] = D[line][i];
						column++;
					}
				}
				if(D[line][deleteColumn].equals(attributeValue)){
					weightChild[lineCount-1] = weight[line-1]; 
				}else{
					weightChild[lineCount-1] = new Datas().getAttributeValueInTotal(map, attribute, attributeValue);
				}
				
				lineCount++;
				
			}
		}//数据集已经处理完毕
		List maps = new LinkedList<Object>();
		maps.add(childData);
		maps.add(weightChild);
		return maps;
	}
	
	/**
	 * 返回给定的属性在数据集中的可能取值（多个相同值只取一个）
	 * @param map 存放数据集和权重集的集合
	 * @param attribute 属性名
	 * @return
	 */
	public List<String> getDifferentAttributeValues(List map,String attribute){
		String[][] D = (String[][])map.get(0);
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
	 * 传入一个带有数据集的集合，返回数据集的结果情况，只有一个结果就返回（0或者1），不统一就返回null
	 * @param map
	 * @return
	 */
	public String getDatasResult(List map){
		String[][] D = (String[][])map.get(0);
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
	public String getDataMostValue(List map){
		String[][] D = (String[][])map.get(0);
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
		
		List map = new Datas(new Datas().getDatas(), new Datas().getWeight()).getMap();
		//double d = new Datas().getInfoGain(map, "敲声");
		String d = new Datas().getLargestInfogainAtributeName(map);
		
		//System.out.println(new Datas().getInfoGain(map, "色泽"));
		System.out.println(d);
	}
}
