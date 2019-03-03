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
			{ "ɫ��", "����", "����", "����", "�겿", "����", "���" },
			{ "-", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "�ں�", "����", "-", "����", "����", "-", "1" },
			{ "�ں�", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "����", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "-", "����", "����", "����", "����", "Ӳ��", "1" },
			{ "����", "����", "����", "����", "-", "��ճ", "1" },
			{ "�ں�", "����", "����", "�Ժ�", "�԰�", "��ճ", "1" },
			{ "�ں�", "����", "����", "-", "�԰�", "Ӳ��", "1" },
			{ "�ں�", "-", "����", "�Ժ�", "�԰�", "Ӳ��", "0" },
			{ "����", "Ӳͦ", "���", "-", "ƽ̹", "��ճ", "0" },
			{ "ǳ��", "Ӳͦ", "���", "ģ��", "ƽ̹", "-", "0" },
			{ "ǳ��", "����", "-", "ģ��", "ƽ̹", "��ճ", "0" },
			{ "-", "����", "����", "�Ժ�", "����", "Ӳ��", "0" },
			{ "ǳ��", "����", "����", "�Ժ�", "����", "Ӳ��", "0" },
			{ "�ں�", "����", "����", "����", "-", "��ճ", "0" },
			{ "ǳ��", "����", "����", "ģ��", "ƽ̹", "Ӳ��", "0" },
			{ "����", "-", "����", "�Ժ�", "�԰�", "Ӳ��", "0" } };
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
	 * �����������ݼ���Ȩ�ؼ��ļ��ϣ�����ȥ��ȱʧֵһ�е����ݼ���Ȩ�ؼ�������ɾ��һ��
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
		int columnNumber = 0;//���������ݼ�������
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
		}//���ݼ��ĵ�һ���Ѿ������Ӽ�
		//��������Ϊȱʧ���з���
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
	 * ���ָ�����ݼ�����Ϣ�أ�ԭʼ���ݼ�����ȱʧֵ����ʱ������Ϣ��Ҫ�ھ���������� ,����Ȩ�أ�����������������
	 * @param D ָ�������ݼ�����ά���飩������ͷ��������һ�У�
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
	 * ���ָ�����ݼ�����ά���飩����ָ�������µ���Ϣ����,���ԭʼ���ݼ�����ȱʧֵ
	 * @param D			ָ�������ݼ�
	 * @param attributeName	ָ��������
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
	 * ����ֵ��ȱʧ������һ���������ݼ���Ȩ������ļ��ϣ����ض�Ӧ��Ϣ��������������
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
	 * �����������map���Լ�������������ֵ�����ظ�����ֵ����������ֵ��������ռ����
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
	 * ����һ��list���������ݼ���Ȩ�ؼ��������ݸ���������ֵ�������������������ݼ�����Ȩ�ؼ���list
	 * @param map
	 * @param attribute
	 * @param attributeValue
	 * @return ���ص����ݼ�Ҫɾ��һ��
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
		int columnNumber = 0;//���������ݼ�������
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
		}//���ݼ��ĵ�һ���Ѿ������Ӽ�
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
		}//���ݼ��Ѿ��������
		List maps = new LinkedList<Object>();
		maps.add(childData);
		maps.add(weightChild);
		return maps;
	}
	
	/**
	 * ���ظ��������������ݼ��еĿ���ȡֵ�������ֵֻͬȡһ����
	 * @param map ������ݼ���Ȩ�ؼ��ļ���
	 * @param attribute ������
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
	 * ����һ���������ݼ��ļ��ϣ��������ݼ��Ľ�������ֻ��һ������ͷ��أ�0����1������ͳһ�ͷ���null
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
	 * ���ݸ��������ݼ����������н���еľ��������1���򷵻�1,0���򷵻�0
	 * @param D ���������ݼ�
	 * @return ����ֵ
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
		//double d = new Datas().getInfoGain(map, "����");
		String d = new Datas().getLargestInfogainAtributeName(map);
		
		//System.out.println(new Datas().getInfoGain(map, "ɫ��"));
		System.out.println(d);
	}
}
