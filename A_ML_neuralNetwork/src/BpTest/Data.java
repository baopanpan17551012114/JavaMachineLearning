package BpTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class Data {
	//神经网络功能基本上实现了
	//但是 倒数第三个示例异常，测试值无限趋近于1......后来发现结果取0或者1他还都是趋近于1
	//但是全部取0的时候，它也是趋近于0的
	
	//更奇怪的是：正例的倒数第二个示例结果取0时，整体效果达到最好。。。。。其中，对反例倒数第三个影响最大
	//而且，进行次数调整，将反例倒数第三调到倒数第一，这种影响还是不能解决
	
	//改变倒数第三示例的密度和含糖率为反例中某一值，勉强能使它的结果符合预期（值0.3，远大其它反例），正例倒数第二又错
	//但是将前面六个属性值改成任意反例中的值，都能完美符合预期
	
		private String[][] data = new String[][] {
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
				//{ "乌黑", "稍蜷", "浊响", "清晰", "稍凹", "软粘","0.360","0.370", "0" }倒三原始数据
				{ "浅白", "蜷缩", "浊响", "模糊", "平坦", "硬滑","0.593","0.042", "0" },
				{ "青绿", "蜷缩", "沉闷", "稍糊", "稍凹", "硬滑","0.719","0.103", "0" }};
		
		public String[][] getData() {
			return data;
		}

		public void setData(String[][] data) {
			this.data = data;
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
		 * 将给定的数据集中属性值不是数值的变为数值，将第一行也去掉
		 * @param D给定的数据集
		 * @return
		 */
		public double[][] getChangeDataType(String[][] D){
			double[][] newData = new double[D.length-1][D[0].length];
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
						newValue.add(new BigDecimal(value*0.3).setScale(1, RoundingMode.HALF_UP)
								.doubleValue());
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
		 * 得到结果的一列
		 * @return
		 */
		public double[] getResults(double[][] D){
			double[] result = new double[D.length];
			for(int i=0;i<D.length;i++){
				result[i] = D[i][D[0].length];
			}
			return result;
			
		}
		
		public static void main(String[] args){
			new Data().showData(new Data().getChangeDataType(new Data().getData()));
		}


}
