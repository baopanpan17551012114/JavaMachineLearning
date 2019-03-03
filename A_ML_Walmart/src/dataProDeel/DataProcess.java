package dataProDeel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataProcess {
	//将所有数据中非数值的属性转化为数值，并且将缺失值的示例删除
	//从CSV文件读入，最终也写入CSV文件
	/**
	 * 从Excel中读取数据，转换属性值类型后写入Excel文件
	 * @param file
	 * @return
	 */
	public String[][] readExcel(File file){ 
        try { 
            InputStream inputStream = new FileInputStream(file); 
            @SuppressWarnings("unused")
			String fileName = file.getName(); 
            Workbook wb = null; 
            wb = new XSSFWorkbook(inputStream);//解析xls格式 
            Sheet sheet = wb.getSheetAt(0);//第一个工作表  ，第二个则为1，以此类推...
            int firstRowIndex = sheet.getFirstRowNum(); 
            //System.out.println(firstRowIndex);
            int lastRowIndex = sheet.getLastRowNum();//行数-1
            //System.out.println(lastRowIndex);
            int lastCellIndex = sheet.getRow(1).getPhysicalNumberOfCells();//列数
            //System.out.println(lastCellIndex);
            String[][] data = new String[lastRowIndex+1][lastCellIndex];
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex ++){ 
                Row row = sheet.getRow(rIndex); 
                if(row != null){
                    int firstCellIndex = row.getFirstCellNum(); 
                    //cIndex表示当前列
                    for(int cIndex = firstCellIndex; cIndex < lastCellIndex; cIndex ++){ 
                        Cell cell = row.getCell(cIndex); 
                        String value = ""; 
                        if(cell != null){ 
                            value = cell.toString(); 
                           data[rIndex][cIndex] = value;
                        }else{
                        	data[rIndex][cIndex] = "";
                        } 
                    } 
                } 
            } 
            return data;
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
		 return null;
    } 
	
	/**
	 * 判断给定的字符串是否是数值
	 * @param s
	 * @return
	 */
	public boolean isNumber(String s){
		try{
			Double.valueOf(s);
			return true;
		}catch (Exception e){
			return false;
		}
	}
	
	/**
	 * 将读入变为二维数组的数据处理（数值化，删除缺失实例）后写入Excel
	 * @param D
	 */
	public void writeToExcel(String[][] D){
		//第一步，创建一个workbook对应一个excel文件
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFWorkbook workbook2 = new HSSFWorkbook();
        //第二部，在workbook中创建一个sheet对应excel中的sheet
        HSSFSheet sheet = workbook.createSheet();//"C:/Users/包盼盼/Desktop/002.xlsx"
        HSSFSheet sheet2 = workbook2.createSheet();
        //第三部，在sheet表中添加表头第0行，老版本的poi对sheet的行列有限制
        HSSFRow row1 = sheet.createRow(0);
        //第四步，创建单元格，设置表头
        //太多了，要循环创建
        HSSFCell cell = row1.createCell(0);
        cell.setCellValue(D[0][0]);
        for(int i=1;i<D[0].length;i++){
        	cell = row1.createCell(i);
            cell.setCellValue(D[0][i]);
        }
        //2
        HSSFRow row2 = sheet2.createRow(0);
        HSSFCell cell1 = row2.createCell(0);
        cell1.setCellValue(D[0][0]);
        for(int i=1;i<D[0].length;i++){
        	cell1 = row2.createCell(i);
            cell1.setCellValue(D[0][i]);
        }
      //第五步，写入实体数据，实际应用中这些数据从数据库得到,对象封装数据，集合包对象。对象的属性值对应表的每行的值
        biaoji:for (int i = 1; i < D.length; i++) {//D.length
            String[] line = D[i];
            //判断该行数据是否确实值，是就删除（跳过）
            for(int k=0;k<line.length;k++){
            	if(line[k].equals("")){
            		continue biaoji;
            	}
            }

            //Excel中行数不能根据i来设
            if((i+1)%6 ==0){
            	int lastRowIndex = sheet.getLastRowNum();//行数-1
                row1 = sheet.createRow(lastRowIndex+1);
        	}else{
                int lastRowIndex2 = sheet2.getLastRowNum();//行数-1
                row2 = sheet2.createRow(lastRowIndex2+1);
        	}
            
            //创建单元格设值
            for(int k=0;k<D[0].length;k++){
            	//判断该行中单元格中转换为数值
            	double value = 0;
            	if(!new DataProcess().isNumber(line[k])){//不是数
            		value = (double)line[k].hashCode();
            		//row1.createCell(k).setCellValue((double)line[k].hashCode());
            	}else{
            		value = Double.parseDouble(line[k]);
            		//row1.createCell(k).setCellValue(Double.parseDouble(line[k]));
            	}
            	if((i+1)%6 ==0){
            		
            		row1.createCell(k).setCellValue(value);
            	}else{
            		
            		row2.createCell(k).setCellValue(value);
            	}
            	
            }
        }

        //将文件保存到指定的位置
        try {
            FileOutputStream fos = new FileOutputStream("C:/Users/包盼盼/Desktop/002.xlsx");//("C:/Users/包盼盼/Desktop/002.xlsx");
            workbook.write(fos);
            FileOutputStream fos1 = new FileOutputStream("C:/Users/包盼盼/Desktop/003.xlsx");//("C:/Users/包盼盼/Desktop/002.xlsx");
            workbook2.write(fos1);
            System.out.println("写入成功");
            fos.close();
            fos1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	
	/**
	 * 打印给定的数据集
	 * @param D
	 */
	public void showData(String[][] D){
		for(int line = 0;line<D.length;line++){
			for(int column = 0;column<D[0].length;column++){
				System.out.print(D[line][column]+"\t");
			}
			System.out.println("");
		}
	}
	
	public static void main(String[] args){
		String[][] D = new DataProcess().readExcel(new File("E:/研究生文件/ML/第一个数据/train.csv/train.xlsx"));
		//new DataProcess().showData(D);
		new DataProcess().writeToExcel(D);
	}

}
