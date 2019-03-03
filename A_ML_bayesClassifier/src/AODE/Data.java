package AODE;

import java.awt.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

public class Data {
	private String[][] datas = new String[][] {
			{ "ɫ��", "����", "����", "����", "�겿", "����", "���" },
			{ "����", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "�ں�", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "�ں�", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "����", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "ǳ��", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "����", "����", "����", "����", "�԰�", "��ճ", "1" },
			{ "�ں�", "����", "����", "�Ժ�", "�԰�", "��ճ", "1" },
			{ "�ں�", "����", "����", "����", "�԰�", "Ӳ��", "1" },
			{ "�ں�", "����", "����", "�Ժ�", "�԰�", "Ӳ��", "0" },
			{ "����", "Ӳͦ", "���", "����", "ƽ̹", "��ճ", "0" },
			{ "ǳ��", "Ӳͦ", "���", "ģ��", "ƽ̹", "Ӳ��", "0" },
			{ "ǳ��", "����", "����", "ģ��", "ƽ̹", "��ճ", "0" },
			{ "����", "����", "����", "�Ժ�", "����", "Ӳ��", "0" },
			{ "ǳ��", "����", "����", "�Ժ�", "����", "Ӳ��", "0" },
			{ "�ں�", "����", "����", "����", "�԰�", "��ճ", "0" },
			{ "ǳ��", "����", "����", "ģ��", "ƽ̹", "Ӳ��", "0" },
			{ "����", "����", "����", "�Ժ�", "�԰�", "Ӳ��", "0" } };

	public String[][] getDatas() {
		return datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
	}
	
	/**
	 * ����ָ��������ָ������ֵ�ϵ�����
	 * @param attribute ������
	 * @param attributeName  ����ֵ
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
	 * ��������ָ������������ָ������ֵ�ϵ�ʾ������
	 * @param D
	 * @param attributeName1 ����1
	 * @param attributeValue1 ����1��Ӧ��ֵ
	 * @param attributeName2����2
	 * @param attributeValue2 ����2��Ӧ��ֵ
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
	 * ����ͬʱ������������Ҫ���ʾ��ֵ
	 * @param D
	 * @param attributeName1
	 * @param attributeValue1
	 * @param attributeName2
	 * @param attributeValue2
	 * @param attributeName3
	 * @param attributeValue3
	 * @return
	 */
	public double getThreeSpeAttributeNumber(String[][] D,String attributeName1,
			String attributeValue1,String attributeName2,String attributeValue2,
			String attributeName3,String attributeValue3){
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
		int speColumn3 = 0;
		for(String str:D[0]){
			if(str.equals(attributeName3)){
				break;
			}
			speColumn3++;
		}
		double number = 0;
		for(int i=1;i<D.length;i++){
			if(D[i][speColumn1].equals(attributeValue1) && D[i][speColumn2].equals(attributeValue2)&& D[i][speColumn3].equals(attributeValue3)){
				number++;
			}
		}
		return number;
		
	}
	
	/**
	 * �õ���������1�ڽ��Ϊ2�µĸ���
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
	 * ���ظ����������������ݼ��ϲ�ͬ��ȡֵ������
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
	
	/**
	 * �õ�ָ�����ԵĲ�ͬȡֵ
	 * @param D
	 * @param attributeName
	 * @return
	 */
	public String[] getDifferentValue(String[][] D,String attributeName){
		int n = (int)new Data().getAttributeValueTypeNumber(D, attributeName);
		String[] value = new String[n];
		LinkedList<String> list = new LinkedList<String>();
		int speColumn = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
				break;
			}
			speColumn++;
		}
		for(int i=1;i<D.length;i++){
			if(list.contains(D[i][speColumn])){
				continue;
			}else{
				list.add(D[i][speColumn]);
			}
		}
		for(int i=0;i<n-1;i++){
			value[i] = list.get(i);
		}
		
		return value;
		
	}
	
	public static void main(String[] args){
		System.out.print(new Data().getThreeSpeAttributeNumber(new Data().getDatas(), "�겿", "����", "���", "1", "����", "����"));
	}


}
