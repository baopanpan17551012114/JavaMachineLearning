package laplacianCorrection;

import java.awt.List;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;

public class Data {
	private String[][] datas = new String[][] {
			{ "ɫ��", "����", "����", "����", "�겿", "����","�ܶ�","������", "���" },
			{ "����", "����", "����", "����", "����", "Ӳ��","0.697","0.460", "1" },
			{ "�ں�", "����", "����", "����", "����", "Ӳ��","0.774","0.376", "1" },
			{ "�ں�", "����", "����", "����", "����", "Ӳ��","0.634","0.264", "1" },
			{ "����", "����", "����", "����", "����", "Ӳ��","0.608","0.318", "1" },
			{ "ǳ��", "����", "����", "����", "����", "Ӳ��","0.556","0.215", "1" },
			{ "����", "����", "����", "����", "�԰�", "��ճ","0.403","0.237", "1" },
			{ "�ں�", "����", "����", "�Ժ�", "�԰�", "��ճ","0.481","0.149", "1" },
			{ "�ں�", "����", "����", "����", "�԰�", "Ӳ��","0.437","0.211", "1" },
			{ "�ں�", "����", "����", "�Ժ�", "�԰�", "Ӳ��","0.666","0.091", "0" },
			{ "����", "Ӳͦ", "���", "����", "ƽ̹", "��ճ","0.243","0.267", "0" },
			{ "ǳ��", "Ӳͦ", "���", "ģ��", "ƽ̹", "Ӳ��","0.245","0.057", "0" },
			{ "ǳ��", "����", "����", "ģ��", "ƽ̹", "��ճ","0.343","0.099", "0" },
			{ "����", "����", "����", "�Ժ�", "����", "Ӳ��","0.639","0.161", "0" },
			{ "ǳ��", "����", "����", "�Ժ�", "����", "Ӳ��","0.657","0.198", "0" },
			{ "�ں�", "����", "����", "����", "�԰�", "��ճ","0.360","0.370", "0" },
			{ "ǳ��", "����", "����", "ģ��", "ƽ̹", "Ӳ��","0.593","0.042", "0" },
			{ "����", "����", "����", "�Ժ�", "�԰�", "Ӳ��","0.719","0.103", "0" } };

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
	
	public static void main(String[] args){
		System.out.print(new Data().getAttributeValueTypeNumber(new Data().getDatas(), "���"));
	}


}
