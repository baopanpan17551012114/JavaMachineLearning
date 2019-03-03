package GiniIndex;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class Data {
	//���ڱ�4.2�����ݼ��ͻ���ָ�����ɾ�����
	private String[][] data = new String[][] {
			{ "ɫ��", "����", "����", "����", "�겿", "����", "���" },
			{ "����", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "�ں�", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "�ں�", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "����", "����", "����", "����", "�԰�", "��ճ", "1" },
			{ "�ں�", "����", "����", "�Ժ�", "�԰�", "��ճ", "1" },
			{ "����", "Ӳͦ", "���", "����", "ƽ̹", "��ճ", "0" },
			{ "ǳ��", "����", "����", "�Ժ�", "����", "Ӳ��", "0" },
			{ "�ں�", "����", "����", "����", "�԰�", "��ճ", "0" },
			{ "ǳ��", "����", "����", "ģ��", "ƽ̹", "Ӳ��", "0" },
			{ "����", "����", "����", "�Ժ�", "�԰�", "Ӳ��", "0" } };

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}
	
	/**
	 * ���ظ��������������ݼ��еĿ���ȡֵ�������ֵֻͬȡһ����
	 * @param D  ���������ݼ�����ά����
	 * @param attribute  ����������
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
	 * ���ݸ��������ݼ�������ֵ���������ݼ�
	 * @param D ���������ݼ�
	 * @param attribute ���������ԣ����磺ɫ��=���̣�ɫ��
	 * @param attributeValue ����������ֵ�����磺ɫ��=���̣�����
	 * @return ���������ݼ�
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
	 * ��ӡ���������ݼ�
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
	 * ����ָ��������ָ�����ݼ���ָ������ֵ������
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
	 * ���ظ������ݼ��Ļ���ֵ
	 * @param D
	 * @return
	 */
	public double giniValue(String[][] D){
		double goodNumber = new Data().getAttributeValueNumber(D, "���", "1");
		double badNumber = new Data().getAttributeValueNumber(D, "���", "0");
		double total = goodNumber+badNumber;
		double result = (goodNumber/total)*(goodNumber/total)+(badNumber/total)*(badNumber/total);
		result = 1-result;
		return new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		
	}
	
	/**
	 * ����ָ����������Ӧ�Ļ���ָ��ֵ
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
	 * ����������ݼ������Ļ���ָ����Ӧ��������
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
	 * ���ص�ǰ��ά�������ݼ��Ľ���������Ψһʱ����null,ֻ��һ����ͷʱ����-1
	 * @param D	Dʱ����ͷ����"ɫ��", "����"......���һ�� �������ݼ�����ά���飩
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
	 * ���ݸ��������ݼ����������н���еľ��������1���򷵻�1,0���򷵻�0
	 * @param D ���������ݼ�
	 * @return ����ֵ
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
