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
           // poi-3.9.jar  ֻ���Զ�ȡ2007���µİ汾����׺Ϊ��xsl
            wb = new XSSFWorkbook(inputStream);//����xls��ʽ 
           
            Sheet sheet = wb.getSheetAt(0);//��һ��������  ���ڶ�����Ϊ1���Դ�����...
              
            int firstRowIndex = sheet.getFirstRowNum(); 
            int lastRowIndex = sheet.getLastRowNum(); 
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex ++){ 
                Row row = sheet.getRow(rIndex); 
                if(row != null){ 
                    int firstCellIndex = row.getFirstCellNum(); 
                   // int lastCellIndex = row.getLastCellNum(); 
                    //�˴�����cIndex��������ȡ��excel��������
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
            // TODO �Զ����� catch �� 
            e.printStackTrace(); 
        } catch (IOException e) { 
            // TODO �Զ����� catch �� 
            e.printStackTrace(); 
        } 
    } 
 public static void main(String[] args) {
 File file = new File("C:/Users/������/Desktop/001.xlsx");//C:\Users\������\Desktop
 readExcel(file);
}
}
