package up_down;

import java.util.LinkedList;
import java.util.List;

public class Data {
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
	 * ���ݵ�ǰԭ�ӹ��򷵻������ݼ���Ϊ��һ����Ϊ��׼��
	 * @param D
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	public String[][] getChildData(String[][] D,String attributeName,String attributeValue){
		double lineNumber1 = new Data().getTwoSpeAttributeNumber(D, attributeName, attributeValue, "���", "1");
		double lineNumber2 = new Data().getTwoSpeAttributeNumber(D, attributeName, attributeValue, "���", "0");
		int lineNumber = (int)lineNumber1+(int)lineNumber2;
		String[][] childData = new String[lineNumber+1][D[0].length];
		int speline = 0;
		for(int i=0;i<D[0].length;i++){
			if(D[0][i].equals(attributeName)){
				break;
			}
			speline++;
		}
		for(int i=0;i<D[0].length;i++){
			childData[0][i] = D[0][i];
		}
		int line = 1;
		for(int i=1;i<D.length;i++){
			if(D[i][speline].equals(attributeValue)){
				for(int j=0;j<D[0].length;j++){
					childData[line][j] = D[i][j];
				}
				line++;
			}
		}
		
		String[][] againNewChild = new String[childData.length][childData[0].length-1];
		for(int i=0;i<childData.length;i++){
			int speLine = 0;
			for(int j=0;j<childData[0].length;j++){
				if(j == speline){
					continue;
				}
				againNewChild[i][speLine] = childData[i][j];
				speLine++;
			}
		}
		return againNewChild;
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
	 * �ڵ�ǰ���ݼ��з��� һ������
	 * @param D
	 * @return
	 */
	public String getRule(String[][] D){
		String result = "�ù�<---";
		while(D[0].length != 1){
			double maxPer = 0;
			double number = 0;
			String attributeName = "";
			String attributeValue = "";
			for(int i=0;i<D[0].length-1;i++){
				List<String> list = new Data().getDifferentAttributeValues(D, D[0][i]);
				for(String str:list){
					double good = new Data().getTwoSpeAttributeNumber(D, D[0][i], str, "���", "1");
					double bad = new Data().getTwoSpeAttributeNumber(D, D[0][i], str, "���", "0");
					double per = good/(good+bad);
					if(per>=maxPer){
						if(per == maxPer){
							if(number<good+number){
								maxPer = per;
								number = good+bad;
								attributeName = D[0][i];
								attributeValue = str;
							}
						}else{
							maxPer = per;
							number = good+bad;
							attributeName = D[0][i];
							attributeValue = str;
						}

					}
				}
			}
			String rule = "";
			if(result.equals("�ù�<---")){
				rule = "("+attributeName+"="+attributeValue+")";
			}else{
				rule = "&&"+"("+attributeName+"="+attributeValue+")";
			}
			result = result + rule;
			System.out.println(result);
			D = new Data().getChildData(D, attributeName, attributeValue);
		}
		System.out.println(result);
		return result;
		
	}

	public static void main(String[] args){
		//new Data().showData(new Data().getChildData(new Data().getData(), "ɫ��", "�ں�"));
		new Data().getRule(new Data().getData());
	}

}
