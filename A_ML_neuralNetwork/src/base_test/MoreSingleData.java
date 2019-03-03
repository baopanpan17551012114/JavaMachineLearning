package base_test;

public class MoreSingleData {
	//两个示例，基本上也能实现，但是效果很差，下一步用两个示例，但是加上阈值
	private String[][] data = new String[][] {
			{ "色泽", "根蒂", "敲声", "纹理", "脐部", "触感","密度","含糖率", "结果" },
			{ "0.25", "0.25", "0.5", "0.75", "0.5", "0.3","0.719","0.103", "0" } };

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}
	
	/**
	 * 不用传入数据集，就是用该示例
	 */
	public void oneBp(){
		double[][] exam = new double[2][3];
		exam[0][0] = 0.8;
		exam[0][1] = 1.0;
		exam[0][2] = 0.4;
		
		exam[1][0] = 0.5;
		exam[1][1] = 1.5;
		exam[1][2] = 0.9;
		double[] outRe = new double[2]; 
		outRe[0] = 0.67;
		outRe[1] = 0.85;
		
		
		int inNumber = 3;//输入节点个数为属性个数
		int middleNumber = inNumber;//隐层节点数设置为比输入节点数+1
		double[][] inWeight = new double[3][3];//输入和隐层之间的权重，每一行依次连接一个输入节点和每个中间节点的权重
		double[] outWeight = new double[middleNumber];//隐层和输出节点之间权重，行数为隐层数

		//所有权重和阈值全部设为0-1之间随机数
		inWeight[0][0] = 0.1;
		inWeight[1][0] = 0.2;
		inWeight[2][0] = 0.3;
		inWeight[0][1] = -0.2;
		inWeight[1][1] = -0.1;
		inWeight[2][1] = 0.1;
		inWeight[0][2] = 0.1;
		inWeight[1][2] = -0.1;
		inWeight[2][2] = 0.2;
		outWeight[0] = 0.3;
		outWeight[1] = 0.5;
		outWeight[2] = 0.4;
		for(int k=0;k<10000;k++){//循环十次
			//计算当前样本（示例）的输出（机器）
			for(int m=0;m<2;m++){
				double[] MidValues = new double[middleNumber];//用于存放隐层节点的输出
				
				double result = 0;//用于存储一次示例的中机器输出结果
				for(int i=0;i<middleNumber;i++){
					double results = 0;
					for(int j=0;j<inNumber;j++){
						results = results+inWeight[j][i]*exam[m][j];
					}
					results = results;//减去阈值,没有阈值可减
					results = 1/(Math.expm1(-results)+2);
					MidValues[i] = results;
					
					result = result+results*outWeight[i];
				}
				
				//得到当前示例在输出节点的输入，下一步要经过跃值函数
				result = 1/(Math.expm1(-result)+2);
				//System.out.println(result);
				System.out.println(result);
				//计算输出层神经元的梯度项
				double outGradientTerm = result*(1-result)*(outRe[m]-result);
				//计算隐层神经元梯度项
				double midGradientTerm[] = new double[MidValues.length];
				for(int i=0;i<midGradientTerm.length;i++){
					midGradientTerm[i] = MidValues[i]*(1-MidValues[i])*outWeight[i]*outGradientTerm;
				}
				//隐层到输出层之间权重更新 
				for(int i=0;i<middleNumber;i++){
					outWeight[i] = outWeight[i] + 0.8*midGradientTerm[i]*MidValues[i];
					//System.out.println(outWeight[i]);
				}
				//输入层到隐层的权重更新
				for(int i=0;i<inWeight.length;i++){
					for(int j=0;j<inWeight[0].length;j++){//按行更新
						inWeight[j][i] = inWeight[j][i]+0.3*midGradientTerm[i]*exam[m][j];
						//System.out.println(inWeight[j][i]);
					}
				}		
			}
			
		}
	}
	
	public static void main(String[] args){
		new MoreSingleData().oneBp();
		//System.out.println(1/(Math.expm1(0)+2));
		
	}


}
