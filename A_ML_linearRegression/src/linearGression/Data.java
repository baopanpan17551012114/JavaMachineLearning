package linearGression;

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
	//Ҫ����дһ��������Excel�е�������
	/**
	 * ��Excel�ж�ȡ���ݣ���ʼ��ʽΪString���ͣ�������ͷ
	 * @param file
	 * @return
	 */
	public String[][] readExcel(File file){ 
		String[][] data = new String[12][5];
        try { 
            InputStream inputStream = new FileInputStream(file); 
            @SuppressWarnings("unused")
			String fileName = file.getName(); 
            Workbook wb = null; 
            wb = new XSSFWorkbook(inputStream);//����xls��ʽ 
            Sheet sheet = wb.getSheetAt(0);//��һ��������  ���ڶ�����Ϊ1���Դ�����...
            int firstRowIndex = sheet.getFirstRowNum(); 
            int lastRowIndex = sheet.getLastRowNum(); 
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex ++){ 
                Row row = sheet.getRow(rIndex); 
                if(row != null){
                    int firstCellIndex = row.getFirstCellNum(); 
                    //cIndex��ʾ��ǰ��
                    for(int cIndex = firstCellIndex; cIndex < 5; cIndex ++){ 
                        Cell cell = row.getCell(cIndex); 
                        String value = ""; 
                        if(cell != null){ 
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
	 * ���������ַ�����ת��Ϊ��ֵ���飬ȥ����ͷ
	 * @param D
	 * @return
	 */
	public double[][] getDoubleData(String[][] D){
		double[][] doubleData = new double[D.length-1][D[0].length];
		for(int i=1;i<D.length;i++){
			for(int j=0;j<D[0].length;j++){
				doubleData[i-1][j] = Double.valueOf(D[i][j]);
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
	 * �������������Ķ�ά��ֵ����ĳ˻�
	 * @param d1
	 * @param d2
	 * @return
	 */
	public double[][] getMatrixProject(double[][] d1,double[][] d2){
		double[][] result = new double[d1.length][d2[0].length];
		for(int i=0;i<d1.length;i++){
			for(int j=0;j<d2[0].length;j++){
				double[] colu = new double[d2.length];
				for(int m=0;m<d2.length;m++){
					colu[m] = d2[m][j];
				}
				result[i][j] = this.getVectorProject(d1[i], colu);
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
	
	public static void main(String[] args){
		String[][] D = new Data().readExcel(new File("C:/Users/������/Desktop/001.xlsx"));
		double[][] data = new Data().getDoubleData(D);
		//new Data().showData(data);
		double[] realValues = new double[data.length];
		for(int i=0;i<data.length;i++){
			realValues[i] = data[i][data[0].length-1];
			data[i][data[0].length-1] = 1;
		}
		double[][] pass = new Data().getInverseMatrix(new Data().getMatrixProject(new Data().getTranspositionMatrix(data), data));
		double[][] n1 = new Data().getMatrixProject(pass, new Data().getTranspositionMatrix(data));
		double[] n2 = new Data().getMatrixVector(n1, realValues);//n2�����յõ���Ȩ��
		double[] pre = new Data().getMatrixVector(data, n2);
		for(int i=0;i<pre.length;i++){
			System.out.println(pre[i]);//���ۣ����ڳ���һ�����Իع��Ԥ���������������Ȩ�ط���
			//��һ������������ݶ��½��㷨ʵ��
		}
	}
	
}
