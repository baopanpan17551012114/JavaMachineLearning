package laplacianCorrection;

import java.awt.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

public class Data {
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

	public String[][] getDatas() {
		return datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
	}
	
	/**
	 * 返回指定属性在指定属性值上的数量
	 * @param attribute 属性名
	 * @param attributeName  属性值
	 * @return
	 */
	public double getSpeAttributeNumber(String[][] D,String attributeName,String attributeValue){
		int speColumn = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
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
	 * 返回两个指定属性在两个指定属性值上的示例数量
	 * @param D
	 * @param attributeName1 属性1
	 * @param attributeValue1 属性1对应的值
	 * @param attributeName2属性2
	 * @param attributeValue2 属性2对应的值
	 * @return
	 */
	public double getTwoSpeAttributeNumber(String[][] D,String attributeName1,
			String attributeValue1,String attributeName2,String attributeValue2){
		int speColumn1 = 0;
		for(String str:D[0]){
			if(str.equals(attributeName1)){
				break;
			}
			speColumn1++;
		}
		int speColumn2 = 0;
		for(String str:D[0]){
			if(str.equals(attributeName2)){
				break;
			}
			speColumn2++;
		}
		double number = 0;
		for(int i=1;i<D.length;i++){
			if(D[i][speColumn1].equals(attributeValue1) && D[i][speColumn2].equals(attributeValue2)){
				number++;
			}
		}
		return number;
		
	}
	
	/**
	 * 得到连续属性1在结果为2下的概率
	 * @param D
	 * @param attributeName1
	 * @param attributeValue1
	 * @param attributeName2
	 * @param attributeValue2
	 * @return
	 */
	public double getContinueAttributeNumber(String[][] D,String attributeName1,
			String attributeValue1,String attributeName2,String attributeValue2){
		int speColumn1 = 0;
		for(String str:D[0]){
			if(str.equals(attributeName1)){
				break;
			}
			speColumn1++;
		}
		int speColumn2 = 0;
		for(String str:D[0]){
			if(str.equals(attributeName2)){
				break;
			}
			speColumn2++;
		}
		double number = 0;
		LinkedList<Double> values = new LinkedList<Double>();
		for(int i=1;i<D.length;i++){
			if(D[i][speColumn2].equals(attributeValue2)){
				number++;
				values.add(Double.valueOf(D[i][speColumn1]));
			}
		}
		double average = 0;
		double avera = 0;
		for(double d:values){
			average = average+d;
		}
		average = average/number;
		average = new BigDecimal(average).setScale(3, RoundingMode.HALF_UP)
		.doubleValue();
		for(double d:values){
			avera = avera+((average-d)*(average-d));
		}
		avera = avera/(number-1);
		avera = Math.sqrt(avera);
		avera = new BigDecimal(avera).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		double result = (1/avera/Math.sqrt(2*Math.PI))*Math.exp((Double.valueOf(attributeValue1)-average)*(Double.valueOf(attributeValue1)-average)/(-2*avera*avera));
		result = new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		//System.out.println(result);
		return result;
		
	}
	
	/**
	 * 返回给定的属性名在数据集上不同的取值的数量
	 * @param D
	 * @param attributeName
	 * @return
	 */
	public double getAttributeValueTypeNumber(String[][] D,String attributeName){
		int speColumn = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
				break;
			}
			speColumn++;
		}
		LinkedList<String> list = new LinkedList<String>();
		double number = 0;
		for(int i =1;i<D.length;i++){
			if(list.contains(D[i][speColumn])){
				continue;
			}else{
				list.add(D[i][speColumn]);
				number++;
			}
		}
		return number;
		
	}
	
	public static void main(String[] args){
		System.out.print(new Data().getAttributeValueTypeNumber(new Data().getDatas(), "结果"));
	}


}
