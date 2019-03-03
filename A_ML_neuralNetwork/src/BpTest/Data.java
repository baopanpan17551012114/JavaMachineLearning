package BpTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class Data {
	//�����繦�ܻ�����ʵ����
	//���� ����������ʾ���쳣������ֵ����������1......�������ֽ��ȡ0����1��������������1
	//����ȫ��ȡ0��ʱ����Ҳ��������0��
	
	//����ֵ��ǣ������ĵ����ڶ���ʾ�����ȡ0ʱ������Ч���ﵽ��á������������У��Է�������������Ӱ�����
	//���ң����д���������������������������������һ������Ӱ�컹�ǲ��ܽ��
	
	//�ı䵹������ʾ�����ܶȺͺ�����Ϊ������ĳһֵ����ǿ��ʹ���Ľ������Ԥ�ڣ�ֵ0.3��Զ�����������������������ڶ��ִ�
	//���ǽ�ǰ����������ֵ�ĳ����ⷴ���е�ֵ��������������Ԥ��
	
		private String[][] data = new String[][] {
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
				//{ "�ں�", "����", "����", "����", "�԰�", "��ճ","0.360","0.370", "0" }����ԭʼ����
				{ "ǳ��", "����", "����", "ģ��", "ƽ̹", "Ӳ��","0.593","0.042", "0" },
				{ "����", "����", "����", "�Ժ�", "�԰�", "Ӳ��","0.719","0.103", "0" }};
		
		public String[][] getData() {
			return data;
		}

		public void setData(String[][] data) {
			this.data = data;
		}
		
		/**
		 * ��ӡ���������ݼ�
		 * @param D
		 */
		public void showData(double[][] D){
			for(int line = 0;line<D.length;line++){
				for(int column = 0;column<D[0].length;column++){
					System.out.print(D[line][column]+"\t");
				}
				System.out.println("");
			}
		}
		
		/**
		 * ����һ���ַ������ж��Ƿ�����ֵ������Ǿͷ���true�����򷵻�false
		 * @param attributeValue
		 * @return
		 */
		public boolean isNumber(String attributeValue){
			try{
				Double.valueOf(attributeValue);
				return true;
			}catch(Exception e){
				return false;
			}
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
		 * �����������ݼ�������ֵ������ֵ�ı�Ϊ��ֵ������һ��Ҳȥ��
		 * @param D���������ݼ�
		 * @return
		 */
		public double[][] getChangeDataType(String[][] D){
			double[][] newData = new double[D.length-1][D[0].length];
			//һ��һ�еĻ�������ֵ
			for(int i=0;i<D[0].length;i++){
				if(new Data().isNumber(D[1][i])){
					for(int j=1;j<D.length;j++){
						newData[j-1][i] = Double.valueOf(D[j][i]);
					}
				}else{
					@SuppressWarnings("rawtypes")
					List list = new Data().getDifferentAttributeValues(D, D[0][i]);
					List<Double> newValue = new LinkedList<Double>();
					for(double value=1;value<list.size()+1;value++){
						newValue.add(new BigDecimal(value*0.3).setScale(1, RoundingMode.HALF_UP)
								.doubleValue());
					}
					double ne = 0;
					for(int j=1;j<D.length;j++){
						for(int k=0;k<list.size();k++){
							if(list.get(k).equals(D[j][i])){
								ne = newValue.get(k);
							}
						}
						newData[j-1][i] = ne;
					}
				}
			}
			return newData;
		}
		
		/**
		 * �õ������һ��
		 * @return
		 */
		public double[] getResults(double[][] D){
			double[] result = new double[D.length];
			for(int i=0;i<D.length;i++){
				result[i] = D[i][D[0].length];
			}
			return result;
			
		}
		
		public static void main(String[] args){
			new Data().showData(new Data().getChangeDataType(new Data().getData()));
		}


}
