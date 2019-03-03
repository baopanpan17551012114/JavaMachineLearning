package Relief;

import java.util.LinkedList;
import java.util.List;

public class Data {
	//数据集的类，包括相关的函数
		private String[][] datas = new String[][] {
				{ "色泽", "根蒂", "敲声", "纹理", "脐部", "触感","密度","含糖率", "结果" },
				{ "青绿", "蜷缩", "浊响", "清晰", "凹陷", "硬滑","0.697","0.460", "1" },
				{ "乌黑", "蜷缩", "沉闷", "清晰", "凹陷", "硬滑","0.774","0.376", "1" },
				{ "乌黑", "蜷缩", "浊响", "清晰", "凹陷", "硬滑","0.634","0.264", "1" },
				{ "青绿", "蜷缩", "沉闷", "清晰", "凹陷", "硬滑","0.608","0.318", "1" },
				{ "浅白", "蜷缩", "浊响", "清晰", "凹陷", "硬滑","0.556","0.215", "1" },
				{ "青绿", "稍蜷", "浊响", "清晰", "稍凹", "软粘","0.403","0.237", "1" },
				{ "乌黑", "稍蜷", "浊响", "稍糊", "稍凹", "软粘","0.481","0.149", "1" },
				{ "乌黑", "稍蜷", "浊响", "清晰", "稍凹", "硬滑","0.437","0.211", "1" },
				{ "乌黑", "稍蜷", "沉闷", "稍糊", "稍凹", "硬滑","0.666","0.091", "0" },
				{ "青绿", "硬挺", "清脆", "清晰", "平坦", "软粘","0.243","0.267", "0" },
				{ "浅白", "硬挺", "清脆", "模糊", "平坦", "硬滑","0.245","0.057", "0" },
				{ "浅白", "蜷缩", "浊响", "模糊", "平坦", "软粘","0.343","0.099", "0" },
				{ "青绿", "稍蜷", "浊响", "稍糊", "凹陷", "硬滑","0.639","0.161", "0" },
				{ "浅白", "稍蜷", "沉闷", "稍糊", "凹陷", "硬滑","0.657","0.198", "0" },
				{ "乌黑", "稍蜷", "浊响", "清晰", "稍凹", "软粘","0.360","0.370", "0" },
				{ "浅白", "蜷缩", "浊响", "模糊", "平坦", "硬滑","0.593","0.042", "0" },
				{ "青绿", "蜷缩", "沉闷", "稍糊", "稍凹", "硬滑","0.719","0.103", "0" } };

		public String[][] getDatas() {
			return datas;
		}

		public void setDatas(String[][] datas) {
			this.datas = datas;
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
		 * 给定一个字符串，判定是否是数值，如果是就返回true，否则返回false
		 * @param attributeValue
		 * @return
		 */
		public boolean isNumber(String attributeValue){
			try{
				Double.valueOf(attributeValue);
				return true;
			}catch(Exception e){
				return false;
			}
		}
		
		/**
		 * 将给定的数据集中属性值不是数值的变为数值，将第一行也去掉
		 * @param D给定的数据集
		 * @return
		 */
		public double[][] getChangeDataType(String[][] D){
			double[][] newData = new double[D.length-1][D[0].length];
			double difValue = 0;
			//一列一列的换掉属性值
			for(int i=0;i<D[0].length;i++){
				if(new Data().isNumber(D[1][i])){
					for(int j=1;j<D.length;j++){
						newData[j-1][i] = Double.valueOf(D[j][i]);
					}
				}else{
					@SuppressWarnings("rawtypes")
					List list = new Data().getDifferentAttributeValues(D, D[0][i]);
					List<Double> newValue = new LinkedList<Double>();
					for(double value=1;value<list.size()+1;value++){
						newValue.add(value+difValue);
					}
					double ne = 0;
					for(int j=1;j<D.length;j++){
						for(int k=0;k<list.size();k++){
							if(list.get(k).equals(D[j][i])){
								ne = newValue.get(k);
							}
						}
						newData[j-1][i] = ne;
					}
				}
			}
			return newData;
		}
		
		/**
		 * 返回给定的属性在数据集中的可能取值（多个相同值只取一个）
		 * @param D  给定的数据集，二维数组
		 * @param attribute  给定的属性
		 * @return
		 */
		public List<String> getDifferentAttributeValues(String[][] D,String attribute){
			List<String> attributeValues = new LinkedList<String>();
			int column = 0;
			for(String str:D[0]){
				if(str.equals(attribute)){
					break;
				}
				column++;
			}
			for(int line=1;line<D.length;line++){
				if(attributeValues.contains(D[line][column])){
					continue;
				}else{
					attributeValues.add(D[line][column]);
				}
			}
			return attributeValues;
		}
		
		/**
		 * 判断两个示例是否相等
		 * @param a
		 * @param b
		 * @return
		 */
		public boolean isSameExample(double[] a,double[] b){
			boolean result = true;
			for(int i=0;i<a.length;i++){
				if(a[i] == b[i]){
					result = true;
				}else{
					result = false;
					break;
				}
			}
			return result;
			
		}
		
		/**
		 * 返回两个示例之间的距离（不包括结果一列）
		 * @param a
		 * @param b
		 * @return
		 */
		public double getExampleDistance(double[] a,double[] b){
			double distance = 0;
			for(int i=0;i<a.length-1;i++){
				distance += (a[i]-b[i])*(a[i]-b[i]);
			}
			return distance;
			
		}
		
		/**
		 * 返回同一个分类中和给定示例最近的示例
		 * @param a
		 * @return
		 */
		public double[] getSameLDistance(double[] a){
			double[][] D = this.getChangeDataType(this.getDatas());
			double[] result = new double[a.length];
			double distance = 100;
			double sign = a[a.length-1];
			@SuppressWarnings("unused")
			int n = 0;
			for(int i=0;i<D.length;i++){
				if(D[i][a.length-1] == sign){
					if(new Data().isSameExample(a, D[i])){
						continue;
					}
					double newDis = this.getExampleDistance(a, D[i]);
					if(newDis<distance){
						distance = newDis;
						for(int j=0;j<a.length;j++){
							result[j] = D[i][j];
						}
						n = i;
					}
				}
			}
			return result;
			
		}
		
		/**
		 * 返回同一个分类中和给定示例最近的示例
		 * @param a
		 * @return
		 */
		public double[] getDifLDistance(double[] a){
			double[][] D = this.getChangeDataType(this.getDatas());
			double[] result = new double[a.length];
			double distance = 100;
			double sign = a[a.length-1];
			@SuppressWarnings("unused")
			int n = 0;
			for(int i=0;i<D.length;i++){
				if(D[i][a.length-1] != sign){
					if(new Data().isSameExample(a, D[i])){
						continue;
					}
					double newDis = this.getExampleDistance(a, D[i]);
					if(newDis<distance){
						distance = newDis;
						for(int j=0;j<a.length;j++){
							result[j] = D[i][j];
						}
						n = i;
					}
				}
			}
			return result;
			
		}
		
		/**
		 * 得到当前示例的在指定属性上权重
		 * @param d
		 * @return
		 */
		public double getOneWeight(double[] d,int speColumn){
			//String[] attributes = new String[]{ "色泽", "根蒂", "敲声", "纹理", "脐部", "触感","密度","含糖率"};
			double weightValue = 0;
			double[] same = new Data().getSameLDistance(d);
			double[] dif = new Data().getDifLDistance(d);
			double v1 = same[speColumn];
			double v2 = dif[speColumn];
			double v = d[speColumn];
			double bri1 = 0;
			double bri2 = 0;
			
			if(speColumn==6 || speColumn==7){
				weightValue = (v2-v)*(v2-v)-(v1-v)*(v1-v);
			}else{
				if(v1 == v ){
					bri1 = 0;
				}else{
					bri1 = 1;
				}
				if(v2 == v){
					bri2 = 0;
				}else{
					bri2 = 1;
				}
				weightValue = bri2-bri1;
			}
			
			return weightValue;
			
		}
		
		/**
		 * 得到最后计算结果，存放在二维数组中
		 * @return
		 */
		public double[] getLastResult(){
			double[][] D = new Data().getChangeDataType(this.getDatas());
			double[] attributeValue = new double[D[0].length-1];
			double dou= 0;
			for(int i=0;i<D[0].length-1;i++){
				for(int j=0;j<D.length;j++){
					dou = dou + new Data().getOneWeight(D[j], i);
				}
				attributeValue[i] = dou;
			}
			
			return attributeValue;
			
		}
		
		public static void main(String[] args){
			double[][] d = new Data().getChangeDataType(new Data().getDatas());
			double[] dou = new Data().getLastResult();
			for(double di:dou){
				System.out.println(di);
			}
			
		}

}
