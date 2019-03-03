package bagging;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Data {
	private String[][] datas = new String[][] {
			{"�ܶ�","������", "���" },
			{"0.697","0.460", "1" },
			{"0.774","0.376", "1" },
			{"0.634","0.264", "1" },
			{"0.608","0.318", "1" },
			{"0.556","0.215", "1" },
			{"0.403","0.237", "1" },
			{"0.481","0.149", "1" },
			{"0.437","0.211", "1" },
			{"0.666","0.091", "0" },
			{"0.243","0.267", "0" },
			{"0.245","0.057", "0" },
			{"0.343","0.099", "0" },
			{"0.639","0.161", "0" },
			{"0.657","0.198", "0" },
			{"0.360","0.370", "0" },
			{"0.593","0.042", "0" },
			{"0.719","0.103", "0" } };

	public String[][] getDatas() {
		return datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
	}
	
	/**
	 * ��ȡD�Ĳ�����������������number��ʾ��
	 * @param D
	 * @param number
	 * @return
	 */
	public String[][] getRandomChildData(String[][] D,int number){
		String[][] randomChildData = new String[number+1][D[0].length];
		for(int i=0;i<D[0].length;i++){
			randomChildData[0][i] = D[0][i];
		}
		int max = D.length-1;
		Random random = new Random();
		for(int i=0;i<number;i++){
			int randomNumber = random.nextInt(max)+1;//1-17
			for(int j=0;j<D[0].length;j++){
				randomChildData[i+1][j] = D[randomNumber][j];
			}
		}
		
		return randomChildData;
		
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
	 * ���㺬��������ֵ�����ݼ�����Ϣ�أ��������䣬�����������뱣����λ
	 * @param D ָ�������ݼ�����ά���飩������ͷ��������һ�У�
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
	 * ����ָ�����ݼ���ָ�������������Ϸ��ص���Ϣ����,�����Ӧ�Ļ��ֵ�
	 * @param D
	 * @param attributeName
	 * @return <k,v>k����Ϣ������v�����Ӧ�Ļ��ֵ�,map�����㡣ʹ��һ��list��0������Ϣ������1���Ż��ֵ�
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
	 * ����һ�����ݼ�������������ݼ�����Ϣ�������ʱ����Ӧ��������,����ֵ��������
	 * @param D  �����Ķ�ά�������ݼ�
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
	 * ���ݴ�����������ж����ݼ��ڸ����������Ƿ�����������ֵ
	 * @param D
	 * @param attributeName
	 * @return �Ǿͷ���true�����򷵻�false
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
	 * ���ݸ�����ֵ���������ݼ���ָ��������������ֵ���ϵ��Ӽ�,����Ӽ����ڼ�����Ϣ���������ü�һ��
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
	 * ���ݸ�����ֵ���������ݼ���ָ��������������ֵ���ϵ��Ӽ�,����Ӽ����ڼ�����Ϣ���������ü�һ��
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
	 * �������ݼ���ָ������������ֵΪ�����������ذ���С�����õ�����ֵ
	 * @param D  ���������ݼ�
	 * @param attributeName ������������
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
	
	
	public static void main(String[] args){
		String name = new Data().getLargestInfogainAtributeName(new Data().getDatas());
		List list = new Data().getContinueInfoGain(new Data().getDatas(), "������");
		List list1 = new Data().getContinueInfoGain(new Data().getDatas(), "�ܶ�");
		
		System.out.println(list);
		System.out.println(list1);
	}
	


}
