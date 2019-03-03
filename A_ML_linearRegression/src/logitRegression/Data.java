package logitRegression;

public class Data {
	// 试验逻辑回归（实际是分类问题）
	//暂时效果不尽如人意
	//下一步使用全部八个属性来试验，并和神经网络对比，看结果是否差不多
	private String[][] datas = new String[][] { {"密度", "含糖率", "结果" },
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
	 * 将给定的字符数组转换为数值数组，去掉表头
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
	 * 打印给定的数据集
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
	 * 返回两个向量乘积得到的矩阵
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
	 * 返回给定的方阵的行列式,为了自成一体，不写别的函数了
	 * 要用到递归思想
	 * @param D D为阶数大于等于2的方阵
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
	 * 给出指定二维数值矩阵的转置，通用，不一定为方阵
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
	 * 返回给定的二维数值数组的逆矩阵，这里无法独立，必须要用到矩阵行列式
	 * @param D
	 * @return
	 */
	public double[][] getInverseMatrix(double[][] D){
		if(this.getDetMatrix(D) ==0){
			System.out.println("该矩阵不可逆");
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
	 * 返回两个给定的向量的内积
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
	 * 返回一个矩阵和一个向量相乘的乘积
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
	 * 返回给定数据集的对率回归的权重
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
			weights[i] = 0.1;//始值设为0===，不能设为0啊，容易造成分母为0啊
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
				//这里开始求一次导
				double e = Math.pow(Math.E, new Data().getVectorProject(data[k], weights));
				double error = realResult[k]-(e/(1+e));
				for(int n=0;n<once.length;n++){
					once[n] = once[n] - error*data[k][n];
					//System.out.println(once[n]);////////???????????????
				}
			}
			//这里开始求二次导,二次导是一个常数？结果不尽如人意啊
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
