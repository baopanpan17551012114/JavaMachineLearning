package GD_linearGression;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Data {
	// 用梯度下降实现线性回归
	/**
	 * 从Excel中读取数据，初始格式为String类型，包括表头
	 * 
	 * @param file
	 * @return
	 */
	public String[][] readExcel(File file) {
		String[][] data = new String[12][5];
		try {
			InputStream inputStream = new FileInputStream(file);
			@SuppressWarnings("unused")
			String fileName = file.getName();
			Workbook wb = null;
			wb = new XSSFWorkbook(inputStream);// 解析xls格式
			Sheet sheet = wb.getSheetAt(0);// 第一个工作表 ，第二个则为1，以此类推...
			int firstRowIndex = sheet.getFirstRowNum();
			int lastRowIndex = sheet.getLastRowNum();
			for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
				Row row = sheet.getRow(rIndex);
				if (row != null) {
					int firstCellIndex = row.getFirstCellNum();
					// cIndex表示当前列
					for (int cIndex = firstCellIndex; cIndex < 5; cIndex++) {
						Cell cell = row.getCell(cIndex);
						String value = "";
						if (cell != null) {
							value = cell.toString();
							data[rIndex][cIndex] = value;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
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
	 * 返回给定的数据集的最终权重，通过梯度下降算法
	 * @param data
	 * @return
	 */
	public double[] getWeightByGD(double[][] data){
		double[] weights = new double[data[0].length];
		double[] realReult = new double[data.length];//用于保存所有实例的真实值
		for(int i=0;i<data.length;i++){
			realReult[i] = data[i][data[0].length-1];
			data[i][data[0].length-1] = 1;
		}
		for(int i=0;i<weights.length;i++){
			weights[i] = 0;//初始值全部设为一
		}
		//第一个实例的误差超过1就一直迭代
		while(Math.abs(new Data().getVectorProject(weights, data[0])-realReult[0])>0.11){
			//System.out.println(new Data().getVectorProject(weights, data[0])-realReult[0]);
			for(int i=0;i<data.length;i++){
				double error = new Data().getVectorProject(weights, data[i])-realReult[i];
				for(int j=1;j<weights.length;j++){
					//这里可以看出，该方法受到步长的影响很大，一般要取步长使得步长*属性值*误差在一个个位数范围才好
					//步长选择不当会导致结果直接无法测出
					//比如，这里的步长如果选择0.1甚至0.01都是没有用的，会导致每一次修正之后的误差越来越大（正反误差交替）
					weights[j] = weights[j]-0.0001*error*data[i][j];
				}
			}
		}
		return weights;
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
	
	
	public static void main(String[] args){
		String[][] D = new Data().readExcel(new File("C:/Users/包盼盼/Desktop/001.xlsx"));
		double[][] data = new Data().getDoubleData(D);
		double[] wei = new Data().getWeightByGD(data);
		double[] pre = new Data().getMatrixVector(data, wei);
		for(int i=0;i<pre.length;i++){
			System.out.println(pre[i]);
		}
	}
}
