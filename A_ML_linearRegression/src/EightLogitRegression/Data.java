package EightLogitRegression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;
public class Data {
	//这里的试验很完美，比神经网络更完美，也没有神经网络的冲突数据
	//现在的问题是：神经网络为什么会有冲突数据？？？？？？
	private String[][] data = new String[][] {
			{ "色泽", "根蒂", "敲声", "纹理", "脐部", "触感", "密度", "含糖率", "结果" },
			{ "青绿", "蜷缩", "浊响", "清晰", "凹陷", "硬滑", "0.697", "0.460", "1" },
			{ "乌黑", "蜷缩", "沉闷", "清晰", "凹陷", "硬滑", "0.774", "0.376", "1" },
			{ "乌黑", "蜷缩", "浊响", "清晰", "凹陷", "硬滑", "0.634", "0.264", "1" },
			{ "青绿", "蜷缩", "沉闷", "清晰", "凹陷", "硬滑", "0.608", "0.318", "1" },
			{ "浅白", "蜷缩", "浊响", "清晰", "凹陷", "硬滑", "0.556", "0.215", "1" },
			{ "青绿", "稍蜷", "浊响", "清晰", "稍凹", "软粘", "0.403", "0.237", "1" },
			{ "乌黑", "稍蜷", "浊响", "稍糊", "稍凹", "软粘", "0.481", "0.149", "1" },

			{ "乌黑", "稍蜷", "浊响", "清晰", "稍凹", "硬滑", "0.437", "0.211", "1" },
			{ "乌黑", "稍蜷", "沉闷", "稍糊", "稍凹", "硬滑", "0.666", "0.091", "0" },
			{ "青绿", "硬挺", "清脆", "清晰", "平坦", "软粘", "0.243", "0.267", "0" },
			{ "浅白", "硬挺", "清脆", "模糊", "平坦", "硬滑", "0.245", "0.057", "0" },
			{ "浅白", "蜷缩", "浊响", "模糊", "平坦", "软粘", "0.343", "0.099", "0" },
			{ "青绿", "稍蜷", "浊响", "稍糊", "凹陷", "硬滑", "0.639", "0.161", "0" },
			{ "浅白", "稍蜷", "沉闷", "稍糊", "凹陷", "硬滑", "0.657", "0.198", "0" },
			{ "乌黑", "稍蜷", "浊响", "清晰", "稍凹", "软粘", "0.360", "0.370", "0" },
			// { "乌黑", "稍蜷", "浊响", "清晰", "稍凹", "软粘","0.360","0.370", "0" }倒三原始数据
			{ "浅白", "蜷缩", "浊响", "模糊", "平坦", "硬滑", "0.593", "0.042", "0" },
			{ "青绿", "蜷缩", "沉闷", "稍糊", "稍凹", "硬滑", "0.719", "0.103", "0" } };

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}

	/**
	 * 打印给定的数据集
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
	 * 给定一个字符串，判定是否是数值，如果是就返回true，否则返回false
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
	 * 返回给定的属性在数据集中的可能取值（多个相同值只取一个）
	 * 
	 * @param D
	 *            给定的数据集，二维数组
	 * @param attribute
	 *            给定的属性
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
	 * 将给定的数据集中属性值不是数值的变为数值，将第一行也去掉
	 * 
	 * @param D给定的数据集
	 * @return
	 */
	public double[][] getChangeDataType(String[][] D) {
		double[][] newData = new double[D.length - 1][D[0].length];
		// 一列一列的换掉属性值
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
	 * 返回两个向量乘积得到的矩阵
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
	 * 返回给定的方阵的行列式,为了自成一体，不写别的函数了 要用到递归思想
	 * 
	 * @param D
	 *            D为阶数大于等于2的方阵
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
	 * 给出指定二维数值矩阵的转置，通用，不一定为方阵
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
	 * 返回给定的二维数值数组的逆矩阵，这里无法独立，必须要用到矩阵行列式
	 * 
	 * @param D
	 * @return
	 */
	public double[][] getInverseMatrix(double[][] D) {
		if (this.getDetMatrix(D) == 0) {
			System.out.println("该矩阵不可逆");
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
	 * 返回两个给定的向量的内积
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
	 * 返回一个矩阵和一个向量相乘的乘积
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
