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
	//�����������з���ֵ������ת��Ϊ��ֵ�����ҽ�ȱʧֵ��ʾ��ɾ��
	//��CSV�ļ����룬����Ҳд��CSV�ļ�
	/**
	 * ��Excel�ж�ȡ���ݣ�ת������ֵ���ͺ�д��Excel�ļ�
	 * @param file
	 * @return
	 */
	public String[][] readExcel(File file){ 
        try { 
            InputStream inputStream = new FileInputStream(file); 
            @SuppressWarnings("unused")
			String fileName = file.getName(); 
            Workbook wb = null; 
            wb = new XSSFWorkbook(inputStream);//����xls��ʽ 
            Sheet sheet = wb.getSheetAt(0);//��һ��������  ���ڶ�����Ϊ1���Դ�����...
            int firstRowIndex = sheet.getFirstRowNum(); 
            //System.out.println(firstRowIndex);
            int lastRowIndex = sheet.getLastRowNum();//����-1
            //System.out.println(lastRowIndex);
            int lastCellIndex = sheet.getRow(1).getPhysicalNumberOfCells();//����
            //System.out.println(lastCellIndex);
            String[][] data = new String[lastRowIndex+1][lastCellIndex];
            for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex ++){ 
                Row row = sheet.getRow(rIndex); 
                if(row != null){
                    int firstCellIndex = row.getFirstCellNum(); 
                    //cIndex��ʾ��ǰ��
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
	 * �жϸ������ַ����Ƿ�����ֵ
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
	 * �������Ϊ��ά��������ݴ�����ֵ����ɾ��ȱʧʵ������д��Excel
	 * @param D
	 */
	public void writeToExcel(String[][] D){
		//��һ��������һ��workbook��Ӧһ��excel�ļ�
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFWorkbook workbook2 = new HSSFWorkbook();
        //�ڶ�������workbook�д���һ��sheet��Ӧexcel�е�sheet
        HSSFSheet sheet = workbook.createSheet();//"C:/Users/������/Desktop/002.xlsx"
        HSSFSheet sheet2 = workbook2.createSheet();
        //����������sheet������ӱ�ͷ��0�У��ϰ汾��poi��sheet������������
        HSSFRow row1 = sheet.createRow(0);
        //���Ĳ���������Ԫ�����ñ�ͷ
        //̫���ˣ�Ҫѭ������
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
      //���岽��д��ʵ�����ݣ�ʵ��Ӧ������Щ���ݴ����ݿ�õ�,�����װ���ݣ����ϰ����󡣶��������ֵ��Ӧ���ÿ�е�ֵ
        biaoji:for (int i = 1; i < D.length; i++) {//D.length
            String[] line = D[i];
            //�жϸ��������Ƿ�ȷʵֵ���Ǿ�ɾ����������
            for(int k=0;k<line.length;k++){
            	if(line[k].equals("")){
            		continue biaoji;
            	}
            }

            //Excel���������ܸ���i����
            if((i+1)%6 ==0){
            	int lastRowIndex = sheet.getLastRowNum();//����-1
                row1 = sheet.createRow(lastRowIndex+1);
        	}else{
                int lastRowIndex2 = sheet2.getLastRowNum();//����-1
                row2 = sheet2.createRow(lastRowIndex2+1);
        	}
            
            //������Ԫ����ֵ
            for(int k=0;k<D[0].length;k++){
            	//�жϸ����е�Ԫ����ת��Ϊ��ֵ
            	double value = 0;
            	if(!new DataProcess().isNumber(line[k])){//������
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

        //���ļ����浽ָ����λ��
        try {
            FileOutputStream fos = new FileOutputStream("C:/Users/������/Desktop/002.xlsx");//("C:/Users/������/Desktop/002.xlsx");
            workbook.write(fos);
            FileOutputStream fos1 = new FileOutputStream("C:/Users/������/Desktop/003.xlsx");//("C:/Users/������/Desktop/002.xlsx");
            workbook2.write(fos1);
            System.out.println("д��ɹ�");
            fos.close();
            fos1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	
	/**
	 * ��ӡ���������ݼ�
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
		String[][] D = new DataProcess().readExcel(new File("E:/�о����ļ�/ML/��һ������/train.csv/train.xlsx"));
		//new DataProcess().showData(D);
		new DataProcess().writeToExcel(D);
	}

}
