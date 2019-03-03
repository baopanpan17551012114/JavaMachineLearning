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
	//要单独写一个方法从Excel中导入数据
	/**
	 * 从Excel中读取数据，初始格式为String类型，包括表头
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
            wb = new XSSFWorkbook(inputStream);//解析xls格式 
            Sheet sheet = wb.getSheetAt(0);//第一个工作表  ，第二个则为1，以此类推...
            int firstRowIndex = sheet.getFirstRowNum(); 
            int lastRowIndex = sheet.getLastRowNum(); 
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex ++){ 
                Row row = sheet.getRow(rIndex); 
                if(row != null){
                    int firstCellIndex = row.getFirstCellNum(); 
                    //cIndex表示当前列
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
	 * 打印给定的数据集
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
	 * 将给定的字符数组转换为数值数组，去掉表头
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
	 * 返回两个给定的二维数值矩阵的乘积
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
	
	public static void main(String[] args){
		String[][] D = new Data().readExcel(new File("C:/Users/包盼盼/Desktop/001.xlsx"));
		double[][] data = new Data().getDoubleData(D);
		//new Data().showData(data);
		double[] realValues = new double[data.length];
		for(int i=0;i<data.length;i++){
			realValues[i] = data[i][data[0].length-1];
			data[i][data[0].length-1] = 1;
		}
		double[][] pass = new Data().getInverseMatrix(new Data().getMatrixProject(new Data().getTranspositionMatrix(data), data));
		double[][] n1 = new Data().getMatrixProject(pass, new Data().getTranspositionMatrix(data));
		double[] n2 = new Data().getMatrixVector(n1, realValues);//n2是最终得到的权重
		double[] pre = new Data().getMatrixVector(data, n2);
		for(int i=0;i<pre.length;i++){
			System.out.println(pre[i]);//哇哇，终于出了一个线性回归的预测结果啦，矩阵计算权重方法
			//下一步考虑用随机梯度下降算法实现
		}
	}
	
}
