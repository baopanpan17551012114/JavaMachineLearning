package EightLogitRegression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
public class Data {
	//�������������������������������Ҳû��������ĳ�ͻ����
	//���ڵ������ǣ�������Ϊʲô���г�ͻ���ݣ�����������
	private String[][] data = new String[][] {
			{ "ɫ��", "����", "����", "����", "�겿", "����", "�ܶ�", "������", "���" },
			{ "����", "����", "����", "����", "����", "Ӳ��", "0.697", "0.460", "1" },
			{ "�ں�", "����", "����", "����", "����", "Ӳ��", "0.774", "0.376", "1" },
			{ "�ں�", "����", "����", "����", "����", "Ӳ��", "0.634", "0.264", "1" },
			{ "����", "����", "����", "����", "����", "Ӳ��", "0.608", "0.318", "1" },
			{ "ǳ��", "����", "����", "����", "����", "Ӳ��", "0.556", "0.215", "1" },
			{ "����", "����", "����", "����", "�԰�", "��ճ", "0.403", "0.237", "1" },
			{ "�ں�", "����", "����", "�Ժ�", "�԰�", "��ճ", "0.481", "0.149", "1" },

			{ "�ں�", "����", "����", "����", "�԰�", "Ӳ��", "0.437", "0.211", "1" },
			{ "�ں�", "����", "����", "�Ժ�", "�԰�", "Ӳ��", "0.666", "0.091", "0" },
			{ "����", "Ӳͦ", "���", "����", "ƽ̹", "��ճ", "0.243", "0.267", "0" },
			{ "ǳ��", "Ӳͦ", "���", "ģ��", "ƽ̹", "Ӳ��", "0.245", "0.057", "0" },
			{ "ǳ��", "����", "����", "ģ��", "ƽ̹", "��ճ", "0.343", "0.099", "0" },
			{ "����", "����", "����", "�Ժ�", "����", "Ӳ��", "0.639", "0.161", "0" },
			{ "ǳ��", "����", "����", "�Ժ�", "����", "Ӳ��", "0.657", "0.198", "0" },
			{ "�ں�", "����", "����", "����", "�԰�", "��ճ", "0.360", "0.370", "0" },
			// { "�ں�", "����", "����", "����", "�԰�", "��ճ","0.360","0.370", "0" }����ԭʼ����
			{ "ǳ��", "����", "����", "ģ��", "ƽ̹", "Ӳ��", "0.593", "0.042", "0" },
			{ "����", "����", "����", "�Ժ�", "�԰�", "Ӳ��", "0.719", "0.103", "0" } };

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}

	/**
	 * ��ӡ���������ݼ�
	 * 
	 * @param D
	 */
	public void showData(double[][] D) {
		for (int line = 0; line < D.length; line++) {
			for (int column = 0; column < D[0].length; column++) {
				System.out.print(D[line][column] + "\t");
			}
			System.out.println("");
		}
	}

	/**
	 * ����һ���ַ������ж��Ƿ�����ֵ������Ǿͷ���true�����򷵻�false
	 * 
	 * @param attributeValue
	 * @return
	 */
	public boolean isNumber(String attributeValue) {
		try {
			Double.valueOf(attributeValue);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * ���ظ��������������ݼ��еĿ���ȡֵ�������ֵֻͬȡһ����
	 * 
	 * @param D
	 *            ���������ݼ�����ά����
	 * @param attribute
	 *            ����������
	 * @return
	 */
	public List<String> getDifferentAttributeValues(String[][] D,
			String attribute) {
		List<String> attributeValues = new LinkedList<String>();
		int column = 0;
		for (String str : D[0]) {
			if (str.equals(attribute)) {
				break;
			}
			column++;
		}
		for (int line = 1; line < D.length; line++) {
			if (attributeValues.contains(D[line][column])) {
				continue;
			} else {
				attributeValues.add(D[line][column]);
			}
		}
		return attributeValues;
	}

	/**
	 * �����������ݼ�������ֵ������ֵ�ı�Ϊ��ֵ������һ��Ҳȥ��
	 * 
	 * @param D���������ݼ�
	 * @return
	 */
	public double[][] getChangeDataType(String[][] D) {
		double[][] newData = new double[D.length - 1][D[0].length];
		// һ��һ�еĻ�������ֵ
		for (int i = 0; i < D[0].length; i++) {
			if (new Data().isNumber(D[1][i])) {
				for (int j = 1; j < D.length; j++) {
					newData[j - 1][i] = Double.valueOf(D[j][i]);
				}
			} else {
				@SuppressWarnings("rawtypes")
				List list = new Data().getDifferentAttributeValues(D, D[0][i]);
				List<Double> newValue = new LinkedList<Double>();
				for (double value = 1; value < list.size() + 1; value++) {
					newValue.add(new BigDecimal(value * 0.3).setScale(1,
							RoundingMode.HALF_UP).doubleValue());
				}
				double ne = 0;
				for (int j = 1; j < D.length; j++) {
					for (int k = 0; k < list.size(); k++) {
						if (list.get(k).equals(D[j][i])) {
							ne = newValue.get(k);
						}
					}
					newData[j - 1][i] = ne;
				}
			}
		}
		return newData;
	}

	/**
	 * �������������˻��õ��ľ���
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public double[][] getVectorToMatrix(double[] d1, double[] d2) {
		double[][] result = new double[d1.length][d1.length];
		for (int i = 0; i < d1.length; i++) {
			for (int j = 0; j < d1.length; j++) {
				result[i][j] = d1[i] * d2[j];
			}
		}
		return result;
	}

	/**
	 * ���ظ����ķ��������ʽ,Ϊ���Գ�һ�壬��д��ĺ����� Ҫ�õ��ݹ�˼��
	 * 
	 * @param D
	 *            DΪ�������ڵ���2�ķ���
	 * @return
	 */
	public double getDetMatrix(double[][] D) {
		if (D.length == 2) {
			return D[0][0] * D[1][1] - D[0][1] * D[1][0];
		}
		double result = 0;
		for (int i = 0; i < D.length; i++) {
			double re = D[0][i];
			re = Math.pow(-1, i) * re;
			double[][] child = new double[D.length - 1][D[0].length - 1];
			for (int m = 1; m < D.length; m++) {
				for (int n = 0; n < D[0].length; n++) {
					if (n < i) {
						child[m - 1][n] = D[m][n];
					}
					if (n > i) {
						child[m - 1][n - 1] = D[m][n];
					}

				}
			}
			result = result + re * getDetMatrix(child);
		}
		return result;
	}

	/**
	 * ����ָ����ά��ֵ�����ת�ã�ͨ�ã���һ��Ϊ����
	 * 
	 * @param D
	 * @return
	 */
	public double[][] getTranspositionMatrix(double[][] D) {
		double[][] result = new double[D[0].length][D.length];
		for (int i = 0; i < D.length; i++) {
			for (int j = 0; j < D[0].length; j++) {
				result[j][i] = D[i][j];
			}
		}
		return result;
	}

	/**
	 * ���ظ����Ķ�ά��ֵ���������������޷�����������Ҫ�õ���������ʽ
	 * 
	 * @param D
	 * @return
	 */
	public double[][] getInverseMatrix(double[][] D) {
		if (this.getDetMatrix(D) == 0) {
			System.out.println("�þ��󲻿���");
			System.exit(0);
		}
		if (D.length == 2) {
			double[][] result = new double[2][2];
			double value = this.getDetMatrix(D);
			result[0][0] = D[1][1] / value;
			result[0][1] = -D[0][1] / value;
			result[1][0] = -D[1][0] / value;
			result[1][1] = D[0][0] / value;
			return result;
		}
		double value = this.getDetMatrix(D);
		double[][] result = new double[D.length][D[0].length];
		for (int i = 0; i < D.length; i++) {
			for (int j = 0; j < D[0].length; j++) {
				double[][] child = new double[D.length - 1][D[0].length - 1];
				for (int p = 0; p < D.length; p++) {
					for (int q = 0; q < D[0].length; q++) {
						if (p < i) {
							if (q < j) {
								child[p][q] = D[p][q];
							}
							if (q > j) {
								child[p][q - 1] = D[p][q];
							}
						}
						if (p > i) {
							if (q < j) {
								child[p - 1][q] = D[p][q];
							}
							if (q > j) {
								child[p - 1][q - 1] = D[p][q];
							}
						}
					}
				}
				result[j][i] = Math.pow(-1, i + j) * this.getDetMatrix(child)
						/ value;
			}
		}
		return result;
	}

	/**
	 * ���������������������ڻ�
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public double getVectorProject(double[] d1, double[] d2) {
		double result = 0;
		for (int i = 0; i < d1.length; i++) {
			result = result + d1[i] * d2[i];
		}
		return result;
	}

	/**
	 * ����һ�������һ��������˵ĳ˻�
	 * 
	 * @param D
	 * @param D2
	 * @return
	 */
	public double[] getMatrixVector(double[][] D, double[] D2) {
		double[] result = new double[D.length];
		for (int i = 0; i < D.length; i++) {
			result[i] = new Data().getVectorProject(D[i], D2);
		}
		return result;
	}
	
	/**
	 * ���ظ������ݼ��Ķ��ʻع��Ȩ��
	 * @param data
	 * @return
	 */
	public double[] getWeight(double[][] data){
		double[] realResult = new double[data.length];
		double[] weights = new double[data[0].length];
		for(int i=0;i<data.length;i++){
			realResult[i] = data[i][data[0].length-1];
			data[i][data[0].length-1] = 1;
		}
		for(int i=0;i<weights.length;i++){
			weights[i] = 0.1;//ʼֵ��Ϊ0===��������Ϊ0����������ɷ�ĸΪ0��
		}
		//while(1 - ((Math.pow(Math.E, new Data().getVectorProject(data[5], weights)))/(1+(Math.pow(Math.E, new Data().getVectorProject(data[5], weights))))) >0.1){
		int num = 0;
		while(num<10000){
			num++;
			double[] once = new double[weights.length];
			for(int m=0;m<once.length;m++){
				once[m] = 0;
			}
			for(int k=0;k<data.length;k++){
				//���￪ʼ��һ�ε�
				double e = Math.pow(Math.E, new Data().getVectorProject(data[k], weights));
				double error = realResult[k]-(e/(1+e));
				for(int n=0;n<once.length;n++){
					once[n] = once[n] - error*data[k][n];
				}
			}
			//���￪ʼ����ε�,���ε���һ��������������������Ⱑ
			double twice = 0;
			for(int k=0;k<data.length;k++){
				double e = Math.pow(Math.E, new Data().getVectorProject(data[k], weights));
				double para = (e/(1+e))*(1-e/(1+e));
				double pro = new Data().getVectorProject(data[k],data[k]);
				twice = twice + pro*para;
			}
			for(int k=0;k<once.length;k++){
				weights[k] = weights[k]-(1/twice)*once[k];
			}
		}
		return weights;
	}

	public static void main(String[] args) {
		double[][] douData = new Data().getChangeDataType(new Data().getData());
		double[] wei = new Data().getWeight(douData);
		for(int i=0;i<wei.length;i++){
			System.out.println(wei[i]);
		}
		for(int i=0;i<douData.length;i++){
			System.out.println(1/(1+(Math.pow(Math.E, -(new Data().getVectorProject(douData[i], wei))))));
		}
	}

}
