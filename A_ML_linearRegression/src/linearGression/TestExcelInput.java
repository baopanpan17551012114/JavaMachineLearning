package linearGression;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream; 
import java.io.FileNotFoundException; 
import java.io.IOException;  
import org.apache.poi.ss.usermodel.Cell; 
import org.apache.poi.ss.usermodel.Row; 
import org.apache.poi.ss.usermodel.Sheet; 
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class TestExcelInput {
	public static void readExcel(File file){ 
        try { 
            InputStream inputStream = new FileInputStream(file); 
            String fileName = file.getName(); 
            Workbook wb = null; 
           // poi-3.9.jar  只可以读取2007以下的版本，后缀为：xsl
            wb = new XSSFWorkbook(inputStream);//解析xls格式 
           
            Sheet sheet = wb.getSheetAt(0);//第一个工作表  ，第二个则为1，以此类推...
              
            int firstRowIndex = sheet.getFirstRowNum(); 
            int lastRowIndex = sheet.getLastRowNum(); 
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex ++){ 
                Row row = sheet.getRow(rIndex); 
                if(row != null){ 
                    int firstCellIndex = row.getFirstCellNum(); 
                   // int lastCellIndex = row.getLastCellNum(); 
                    //此处参数cIndex决定可以取到excel的列数。
                    for(int cIndex = firstCellIndex; cIndex < 3; cIndex ++){ 
                        Cell cell = row.getCell(cIndex); 
                        String value = ""; 
                        if(cell != null){ 
                            value = cell.toString(); 
                            System.out.print(value+"\t"); 
                        } 
                    } 
                    System.out.println(); 
                } 
            } 
        } catch (FileNotFoundException e) { 
            // TODO 自动生成 catch 块 
            e.printStackTrace(); 
        } catch (IOException e) { 
            // TODO 自动生成 catch 块 
            e.printStackTrace(); 
        } 
    } 
 public static void main(String[] args) {
 File file = new File("C:/Users/包盼盼/Desktop/001.xlsx");//C:\Users\包盼盼\Desktop
 readExcel(file);
}
}
