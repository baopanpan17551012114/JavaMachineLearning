package logitRegression;

public class Data {
	// �����߼��ع飨ʵ���Ƿ������⣩
	//��ʱЧ������������
	//��һ��ʹ��ȫ���˸����������飬����������Աȣ�������Ƿ���
	private String[][] datas = new String[][] { {"�ܶ�", "������", "���" },
			{ "0.697", "0.460", "1" }, { "0.774", "0.376", "1" },
			{ "0.634", "0.264", "1" }, { "0.608", "0.318", "1" },
			{ "0.556", "0.215", "1" }, { "0.403", "0.237", "1" },
			{ "0.481", "0.149", "1" }, { "0.437", "0.211", "1" },
			{ "0.666", "0.091", "0" }, { "0.243", "0.267", "0" },
			{ "0.245", "0.057", "0" }, { "0.343", "0.099", "0" },
			{ "0.639", "0.161", "0" }, { "0.657", "0.198", "0" },
			{ "0.360", "0.370", "0" }, { "0.593", "0.042", "0" },
			{ "0.719", "0.103", "0" } };

	public String[][] getDatas() {
		return datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
	}

	/**
	 * ���������ַ�����ת��Ϊ��ֵ���飬ȥ����ͷ
	 * 
	 * @param D
	 * @return
	 */
	public double[][] getDoubleData(String[][] D) {
		double[][] doubleData = new double[D.length - 1][D[0].length];
		for (int i = 1; i < D.length; i++) {
			for (int j = 0; j < D[0].length; j++) {
				doubleData[i - 1][j] = Double.valueOf(D[i][j]);
			}
		}
		return doubleData;
	}

	/**
	 * ��ӡ���������ݼ�
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
	 * �������������˻��õ��ľ���
	 * @param d1
	 * @param d2
	 * @return
	 */
	public double[][] getVectorToMatrix(double[] d1,double[] d2){
		double[][] result = new double[d1.length][d1.length];
		for(int i=0;i<d1.length;i++){
			for(int j=0;j<d1.length;j++){
				result[i][j] = d1[i]*d2[j];
			}
		}
		return result;
	}
	
	/**
	 * ���ظ����ķ��������ʽ,Ϊ���Գ�һ�壬��д��ĺ�����
	 * Ҫ�õ��ݹ�˼��
	 * @param D DΪ�������ڵ���2�ķ���
	 * @return
	 */
	public double getDetMatrix(double[][] D){
		if(D.length ==2){
			return D[0][0]*D[1][1]-D[0][1]*D[1][0];
		}
		double result = 0;
		for(int i=0;i<D.length;i++){
			double re = D[0][i];
			re = Math.pow(-1, i)*re;
			double[][] child = new double[D.length-1][D[0].length-1];
			for(int m=1;m<D.length;m++){
				for(int n=0;n<D[0].length;n++){
					if(n<i){
						child[m-1][n] = D[m][n];
					}
					if(n>i){
						child[m-1][n-1] = D[m][n];
					}
					
				}
			}
			result = result + re*getDetMatrix(child);
		}
		return result;
	}
	
	/**
	 * ����ָ����ά��ֵ�����ת�ã�ͨ�ã���һ��Ϊ����
	 * @param D
	 * @return
	 */
	public double[][] getTranspositionMatrix(double[][] D){
		double[][] result = new double[D[0].length][D.length];
		for(int i=0;i<D.length;i++){
			for(int j=0;j<D[0].length;j++){
				result[j][i] = D[i][j];
			}
		}
		return result;
	}
	
	/**
	 * ���ظ����Ķ�ά��ֵ���������������޷�����������Ҫ�õ���������ʽ
	 * @param D
	 * @return
	 */
	public double[][] getInverseMatrix(double[][] D){
		if(this.getDetMatrix(D) ==0){
			System.out.println("�þ��󲻿���");
			System.exit(0);
		}
		if(D.length == 2){
			double[][] result = new double[2][2];
			double value = this.getDetMatrix(D);
			result[0][0] = D[1][1]/value;
			result[0][1] = -D[0][1]/value;
			result[1][0] = -D[1][0]/value;
			result[1][1] = D[0][0]/value;
			return result;
		}
		double value = this.getDetMatrix(D);
		double[][] result = new double[D.length][D[0].length];
		for(int i=0;i<D.length;i++){
			for(int j=0;j<D[0].length;j++){
				double[][] child = new double[D.length-1][D[0].length-1];
				for(int p=0;p<D.length;p++){
					for(int q=0;q<D[0].length;q++){
						if(p<i){
							if(q<j){
								child[p][q] = D[p][q];
							}
							if(q>j){
								child[p][q-1] = D[p][q];
							}
						}
						if(p>i){
							if(q<j){
								child[p-1][q] = D[p][q];
							}
							if(q>j){
								child[p-1][q-1] = D[p][q];
							}
						}
					}
				}
				result[j][i] = Math.pow(-1, i+j)*this.getDetMatrix(child)/value;
			}
		}
		return result;
	}
	
	/**
	 * ���������������������ڻ�
	 * @param d1
	 * @param d2
	 * @return
	 */
	public double getVectorProject(double[] d1,double[] d2){
		double result = 0;
		for(int i=0;i<d1.length;i++){
			result = result + d1[i]*d2[i];
		}
		return result;
	}
	
	/**
	 * ����һ�������һ��������˵ĳ˻�
	 * @param D
	 * @param D2
	 * @return
	 */
	public double[] getMatrixVector(double[][] D,double[] D2){
		double[] result = new double[D.length];
		for(int i=0;i<D.length;i++){
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
			System.out.println();
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
					//System.out.println(once[n]);////////???????????????
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
	
	public static void main(String[] args){
		double[][] douData = new Data().getDoubleData(new Data().getDatas());
		double[] wei = new Data().getWeight(douData);
		for(int i=0;i<wei.length;i++){
			System.out.println(wei[i]);
		}
		for(int i=0;i<douData.length;i++){
			System.out.println(1/(1+(Math.pow(Math.E, -(new Data().getVectorProject(douData[i], wei))))));
		}
	}

}
