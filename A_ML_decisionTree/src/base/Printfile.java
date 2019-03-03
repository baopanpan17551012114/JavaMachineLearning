package base;

import java.io.File;
import java.util.Scanner;

//利用递归打印文件夹层次图


public class Printfile {
	public static void main(String[] args){
		System.out.println("请输入你要打印的文件夹：");
		Scanner s = new Scanner(System.in);
		String a = s.nextLine();
		File f = new File(a);//////////////////////////
		printFile(f,0);
		
	}
	static void printFile(File file,int level){
		for(int i=0;i<level;i++){
			System.out.print("_");
		}
		System.out.println(file.getName());
		
		if(file.isDirectory()){
			File[] files = file.listFiles();
			for(File temp : files){
				printFile(temp,level+1);
			}
			
		}
	}

}
