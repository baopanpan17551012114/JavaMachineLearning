package basez_optimization_continue;

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
	//���ݼ����࣬������صĺ���
	private String[][] datas = new String[][] {
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

	private String[][] data = new String[][] {
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

	
	//get()set()����
	public String[][] getDatas() {
		return datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
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
	 * ���ָ�����ݼ�����Ϣ�أ������������뱣����λ
	 * @param D ָ�������ݼ�����ά���飩������ͷ��������һ�У�
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
	 * ���ָ�����ݼ�����ά���飩����ָ�������µ���Ϣ����
	 * @param D			ָ�������ݼ�
	 * @param attributeName	ָ��������
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
	 * ����һ�����ݼ�������������ݼ�����Ϣ�������ʱ����Ӧ��������
	 * @param D  �����Ķ�ά�������ݼ�
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
	/**
	 * �����������Ը�������
	 * @param args
	 */
	public static void main(String[] args){
		//double d = new Data().getInfoGain(new Data().getDatas(), "�겿");
		String str = new Data().getLargestInfogainAtributeName(new Data().getDatas());
		//System.out.println(new Data().getDifferentAttributeValues(new Data().getDatas(), "���"));
		String stri = new Data().getDatasResult(new Data().getDatas());
		System.out.println(new Data().getDataMostValue(new Data().getDatas()));
	}
	
}
