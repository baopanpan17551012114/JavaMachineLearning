package base;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Methods {
	/**
	 * ������Ϣ��
	 * 
	 * @param D����һ����ά������Ϊ����
	 * @return
	 */
	public static double ent(String[][] D) {
		List<String> attribute = new ArrayList<String>();
		// ѭ���������������һ�з���list
		for (String[] line : D) {
			if(line == D[0]){
				continue;
			}else{
				attribute.add(line[line.length - 1]);
			}
		}
		// ������������ֵ������
		int goodNumber = 0;
		int badNumber = 0;
		for (String re : attribute) {
			if (re.equals("1")) {
				goodNumber++;
			} else {
				badNumber++;
			}
		}

		// ������Ϣ��,��Ҫʹ��int������ʧ���ȣ���������
		double total = goodNumber + badNumber;
		double result = 0;
		double lineNumber = D.length-1;
		// �������룬������λС��
		if (goodNumber == 0.0) {
			result = -(badNumber / total)
					* ((Math.log((double) badNumber / total) / Math
							.log((double) 2)));

		} else if (badNumber == 0.0) {
			result = (-(goodNumber / total) * ((Math.log((double) goodNumber
							/ total) / Math.log((double) 2))));

		} else {
			result = (-(goodNumber / total) * ((Math.log((double) goodNumber / total) / Math.log((double) 2))) - (badNumber / total) * ((Math.log((double) badNumber / total) / Math.log((double) 2))));

		}
		return new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
	}

	/**
	 * ����ָ������������ָ�����ݼ�����Ϣ����������ֻ�����ھ������ĵ�һ�����ӣ�
	 * 
	 * @param D
	 *            ָ�������ݼ�
	 * @param arrName
	 *            ָ����������
	 * @return
	 */
	public static double infoGain(String[][] D, String arrName) {
		// ������������arrName�õ��ڼ��У�ʵ�ʵڼ��У������������еڼ���
		int i = 1;

		String[] arrNames = D[0];
		for (String arr : arrNames) {
			if (arr.equals(arrName)) {
				break;
			}
			i++;
		}

		// ���ȼ���õ��ܵ���Ϣ��
		double result = Methods.ent(D);
		int weidth = 0;

		// һ���б����ڴ��һ������ֵ
		List<String> attributes = new ArrayList<String>();
		// ѭ����������ָ���е�����ֵ����list
		for (String[] line : D) {
			if (line == D[0]) {
				continue;
			} else {
				attributes.add(line[i - 1]);
				weidth = line.length;
			}
		}
		// �ٽ�һ���б����ڴ��һ���в�ͬ������ֵ
		List<String> differentValue = new ArrayList<String>();
		for (String att : attributes) {
			if (differentValue.contains(att)) {
				continue;
			} else {
				differentValue.add(att);
			}
		}
		// �Բ�ͬ������ֵ������������������Ӧ�������������
		String attribute = "";
		while (differentValue.size() != 0) {
			attribute = differentValue.remove(differentValue.size() - 1);

			// ��������ֵΪattribute�����������
			int attributeGood = 0;
			int attributeBad = 0;
			int lineNow = 1;//����ͷ�����Դӵڶ��п�ʼ
			for (String att : attributes) {
				if (att.equals(attribute)) {// ����ֵ��,�ڸ��к�ָ��������ֵ�Ǻ�
					if (D[lineNow][weidth - 1].equals("1")) {
						attributeGood++;
					} else {
						attributeBad++;
					}
				}
				lineNow++;
			}
			// �õ�һ��ĺû������
			// ��ǳ��Ϊ������ʱattributeGood=1��attributeBad=4
			double total = attributeGood + attributeBad;
			double lineNumber = D.length - 1;
			// attributeGood����attributeBadΪ0�����
			if (attributeGood == 0) {
				double results = (total / lineNumber)
						* -(attributeBad / total)
						* ((Math.log((double) attributeBad / total) / Math
								.log((double) 2)));
				results = new BigDecimal(results).setScale(3, RoundingMode.HALF_UP)
						.doubleValue();
				result = result - results;

			} else if (attributeBad == 0) {
				double results = (total / lineNumber)
						* (-(attributeGood / total) * ((Math
								.log((double) attributeGood / total) / Math
								.log((double) 2))));
				results = new BigDecimal(results).setScale(3, RoundingMode.HALF_UP)
						.doubleValue();
				result = result - results;

			} else {
				double results = (total / lineNumber)
						* (-(attributeGood / total)
								* ((Math.log((double) attributeGood / total) / Math
										.log((double) 2))) - (attributeBad / total)
								* ((Math.log((double) attributeBad / total) / Math
										.log((double) 2))));
				results = new BigDecimal(results).setScale(3, RoundingMode.HALF_UP)
						.doubleValue();
				result = result-results;

			}
		}
		result = new BigDecimal(result).setScale(3, RoundingMode.HALF_UP)
				.doubleValue();
		return result;
	}

	/**
	 * ����һ�������������Ӧ��Ϣ�����ļ�ֵ��
	 * ������Ϣ����ֵ����������
	 * 
	 * @param maps
	 * @return
	 */
	public static String getLargestAttributeName(Map<String, Double> maps) {
		@SuppressWarnings("rawtypes")
		Set sets = maps.keySet();
		System.out.println(maps);
		String name = "";
		double value = 0.0;
		@SuppressWarnings("rawtypes")
		Iterator i = sets.iterator();// Ҫ�ȹ��������

		// ��ֹ���ó�δ֪���⣬�߼��������
		while (i.hasNext()) {// ���� ���õ����е�������
			String str = i.next().toString();
			double val = maps.get(str);
			if (val > value) {
				value = val;
				name = str;
			}

		}
		return name;

	}
	
	/**
	 * ����ָ���������������ظ������ݼ��и���������ͬ��ȡֵ
	 * @param D			g���������ݼ�
	 * @param attribute ���ݼ���ָ����������
	 * @return
	 */
	public static List<String> getAttributeDifferentValues(String[][] D,String attribute){
		List<String> differentValue = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for(int i = 0;i<D[0].length;i++){
			if(D[0][i].equals(attribute)){
				//����i�е�����Ԫ�ط���list
				for(int j = 1;j<D.length;j++){
					differentValue.add(D[j][i]);
				}
			}
		}
		for (String att : differentValue) {
			if (values.contains(att)) {
				continue;
			} else {
				values.add(att);
			}
		}
		return values;
		
	}
	
	/**
	 * ����ָ�����Ե�����ֵ���������ݼ�
	 * @param D		������ԭ���ݼ�
	 * @param attributeValue  ĳ�����Ե�����ֵ      ���磨���� = ����������
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String[][] getAttributeValueDataChild(String[][] D,String attributeName,String attributeValue){
		//����Ҫ�õ�ԭ���ݼ��и�����ֵ������������ȷ����ά���������
		int number = 0;
		
		// ������������arrName�õ��ڼ��У�ʵ�ʵڼ��У������������еڼ���
		int i = 1;

		String[] arrNames = D[0];
		for (String arr : arrNames) {
			if (arr.equals(attributeName)) {
				break;
			}
			i++;
		}
		
		List<String> attributes = new ArrayList<String>();
		// ѭ����������ָ���е�����ֵ����list
		for (String[] line : D) {
			if (line == D[0]) {
				continue;
			} else {
				attributes.add(line[i - 1]);
			}
		}
		for(String value:attributes){
			if(value.equals(attributeValue)){
				number++;
			}
		}
		//��ø���������ԭ���ݼ�������
		int index = 0;
		for(String str:D[0]){
			if(str.equals(attributeName)){
				break;
			}
			index++;
		}
		//�½�һ��D.weidth-1�У�number�еĶ�ά����
		String[][] dataChild = new String[number+1][D[0].length-1];//ע�����в�Ҫд��,����Ҫ���ϱ�ͷ
		//��ɾ��һ�к�ı�ͷ����������
		String[] line = new String[D[0].length-1];
		List list = new ArrayList<String>();
		for(String str:D[0]){
			list.add(str);
		}
		list.remove(index);
		
		//����ֻ�������б�ɾ��һ�У�Ȼ�������������
		int j = 0;
		for(Object str:list){
			dataChild[0][j] = str.toString();
			j++;
		}
		//��ʣ�µĺ�����ֵ��ȵ�һ�ж����������ݼ�
		//Ҫ��ȡ��Ӧ������
		j = 1;
		for(int column = 0;column<attributes.size();column++){
			list.clear();
			if(attributes.get(column).equals(attributeValue)){//attributes���Ѿ�ȥ���˱�ͷ
				for(String str:D[column+1]){
					list.add(str);
				}
				list.remove(index);//�Ƴ�һ��Ԫ��
				int lin = 0;
				for(Object str:list){
					dataChild[j][lin] = str.toString();
					lin++;
				}
				
				j++;
			}
		}
		//new Data().showData(dataChild);
		return dataChild;
		
	}
	
	/**
	 * �ж�һ����ά���ݼ��Ľ���Ƿ�Ψһ���Ǿͷ��ؽ�������򷵻�null
	 * @param D
	 * @return
	 */
	public static String getChildResults(String[][] D){
		//�ӵڶ��п�ʼ
		String te = D[1][D[0].length-1];
		for (int i = 1; i < D.length; i++) {

			if (te.equals(D[i][D[0].length-1])) {
				continue;
			}else{
				return null;
			}

		}
		return te;
	}
	
	/**
	 
	 * @return
	 */

	public static boolean getChildResultsisEmpty(){
		return false;}

	public static void main(String[] args){
		double dou = Methods.infoGain(new Data().getData(), "����");
		System.out.println(dou);
	}
}
