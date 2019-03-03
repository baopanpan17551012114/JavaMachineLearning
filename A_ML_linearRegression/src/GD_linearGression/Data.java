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
	// ���ݶ��½�ʵ�����Իع�
	/**
	 * ��Excel�ж�ȡ���ݣ���ʼ��ʽΪString���ͣ�������ͷ
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
			wb = new XSSFWorkbook(inputStream);// ����xls��ʽ
			Sheet sheet = wb.getSheetAt(0);// ��һ�������� ���ڶ�����Ϊ1���Դ�����...
			int firstRowIndex = sheet.getFirstRowNum();
			int lastRowIndex = sheet.getLastRowNum();
			for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
				Row row = sheet.getRow(rIndex);
				if (row != null) {
					int firstCellIndex = row.getFirstCellNum();
					// cIndex��ʾ��ǰ��
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
	 * ���ظ��������ݼ�������Ȩ�أ�ͨ���ݶ��½��㷨
	 * @param data
	 * @return
	 */
	public double[] getWeightByGD(double[][] data){
		double[] weights = new double[data[0].length];
		double[] realReult = new double[data.length];//���ڱ�������ʵ������ʵֵ
		for(int i=0;i<data.length;i++){
			realReult[i] = data[i][data[0].length-1];
			data[i][data[0].length-1] = 1;
		}
		for(int i=0;i<weights.length;i++){
			weights[i] = 0;//��ʼֵȫ����Ϊһ
		}
		//��һ��ʵ��������1��һֱ����
		while(Math.abs(new Data().getVectorProject(weights, data[0])-realReult[0])>0.11){
			//System.out.println(new Data().getVectorProject(weights, data[0])-realReult[0]);
			for(int i=0;i<data.length;i++){
				double error = new Data().getVectorProject(weights, data[i])-realReult[i];
				for(int j=1;j<weights.length;j++){
					//������Կ������÷����ܵ�������Ӱ��ܴ�һ��Ҫȡ����ʹ�ò���*����ֵ*�����һ����λ����Χ�ź�
					//����ѡ�񲻵��ᵼ�½��ֱ���޷����
					//���磬����Ĳ������ѡ��0.1����0.01����û���õģ��ᵼ��ÿһ������֮������Խ��Խ���������棩
					weights[j] = weights[j]-0.0001*error*data[i][j];
				}
			}
		}
		return weights;
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
	
	
	public static void main(String[] args){
		String[][] D = new Data().readExcel(new File("C:/Users/������/Desktop/001.xlsx"));
		double[][] data = new Data().getDoubleData(D);
		double[] wei = new Data().getWeightByGD(data);
		double[] pre = new Data().getMatrixVector(data, wei);
		for(int i=0;i<pre.length;i++){
			System.out.println(pre[i]);
		}
	}
}
