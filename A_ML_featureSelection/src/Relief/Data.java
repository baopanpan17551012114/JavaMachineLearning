package Relief;

import java.util.LinkedList;
import java.util.List;

public class Data {
	//���ݼ����࣬������صĺ���
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
		 * �����������ݼ�������ֵ������ֵ�ı�Ϊ��ֵ������һ��Ҳȥ��
		 * @param D���������ݼ�
		 * @return
		 */
		public double[][] getChangeDataType(String[][] D){
			double[][] newData = new double[D.length-1][D[0].length];
			double difValue = 0;
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
						newValue.add(value+difValue);
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
		 * �ж�����ʾ���Ƿ����
		 * @param a
		 * @param b
		 * @return
		 */
		public boolean isSameExample(double[] a,double[] b){
			boolean result = true;
			for(int i=0;i<a.length;i++){
				if(a[i] == b[i]){
					result = true;
				}else{
					result = false;
					break;
				}
			}
			return result;
			
		}
		
		/**
		 * ��������ʾ��֮��ľ��루���������һ�У�
		 * @param a
		 * @param b
		 * @return
		 */
		public double getExampleDistance(double[] a,double[] b){
			double distance = 0;
			for(int i=0;i<a.length-1;i++){
				distance += (a[i]-b[i])*(a[i]-b[i]);
			}
			return distance;
			
		}
		
		/**
		 * ����ͬһ�������к͸���ʾ�������ʾ��
		 * @param a
		 * @return
		 */
		public double[] getSameLDistance(double[] a){
			double[][] D = this.getChangeDataType(this.getDatas());
			double[] result = new double[a.length];
			double distance = 100;
			double sign = a[a.length-1];
			@SuppressWarnings("unused")
			int n = 0;
			for(int i=0;i<D.length;i++){
				if(D[i][a.length-1] == sign){
					if(new Data().isSameExample(a, D[i])){
						continue;
					}
					double newDis = this.getExampleDistance(a, D[i]);
					if(newDis<distance){
						distance = newDis;
						for(int j=0;j<a.length;j++){
							result[j] = D[i][j];
						}
						n = i;
					}
				}
			}
			return result;
			
		}
		
		/**
		 * ����ͬһ�������к͸���ʾ�������ʾ��
		 * @param a
		 * @return
		 */
		public double[] getDifLDistance(double[] a){
			double[][] D = this.getChangeDataType(this.getDatas());
			double[] result = new double[a.length];
			double distance = 100;
			double sign = a[a.length-1];
			@SuppressWarnings("unused")
			int n = 0;
			for(int i=0;i<D.length;i++){
				if(D[i][a.length-1] != sign){
					if(new Data().isSameExample(a, D[i])){
						continue;
					}
					double newDis = this.getExampleDistance(a, D[i]);
					if(newDis<distance){
						distance = newDis;
						for(int j=0;j<a.length;j++){
							result[j] = D[i][j];
						}
						n = i;
					}
				}
			}
			return result;
			
		}
		
		/**
		 * �õ���ǰʾ������ָ��������Ȩ��
		 * @param d
		 * @return
		 */
		public double getOneWeight(double[] d,int speColumn){
			//String[] attributes = new String[]{ "ɫ��", "����", "����", "����", "�겿", "����","�ܶ�","������"};
			double weightValue = 0;
			double[] same = new Data().getSameLDistance(d);
			double[] dif = new Data().getDifLDistance(d);
			double v1 = same[speColumn];
			double v2 = dif[speColumn];
			double v = d[speColumn];
			double bri1 = 0;
			double bri2 = 0;
			
			if(speColumn==6 || speColumn==7){
				weightValue = (v2-v)*(v2-v)-(v1-v)*(v1-v);
			}else{
				if(v1 == v ){
					bri1 = 0;
				}else{
					bri1 = 1;
				}
				if(v2 == v){
					bri2 = 0;
				}else{
					bri2 = 1;
				}
				weightValue = bri2-bri1;
			}
			
			return weightValue;
			
		}
		
		/**
		 * �õ���������������ڶ�ά������
		 * @return
		 */
		public double[] getLastResult(){
			double[][] D = new Data().getChangeDataType(this.getDatas());
			double[] attributeValue = new double[D[0].length-1];
			double dou= 0;
			for(int i=0;i<D[0].length-1;i++){
				for(int j=0;j<D.length;j++){
					dou = dou + new Data().getOneWeight(D[j], i);
				}
				attributeValue[i] = dou;
			}
			
			return attributeValue;
			
		}
		
		public static void main(String[] args){
			double[][] d = new Data().getChangeDataType(new Data().getDatas());
			double[] dou = new Data().getLastResult();
			for(double di:dou){
				System.out.println(di);
			}
			
		}

}
