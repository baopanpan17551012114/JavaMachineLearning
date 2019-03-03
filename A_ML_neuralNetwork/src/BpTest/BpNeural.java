package BpTest;

public class BpNeural {
	//具体公式使用数据挖掘的
	//大体思想使用机器学习的

	public void BpDeep(double[][] D){
		new Data().showData(D);
		int inNumber = 8;//输入节点个数为属性个数
		int middleNumber = 8;//隐层节点数设置为比输入节点数+1
		double[][] inWeight = new double[8][8];//输入和隐层之间的权重，行数为输入节点数
		double[] outWeight = new double[8];//隐层和输出节点之间权重，行数为隐层数
		double[] midSpeValues = new double[8];//隐层所有阈值
		double outSpe = 0.5;//输出层阈值也只有一个

		//所有权重和阈值全部设为0.5
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				inWeight[i][j] = 0.5;
			}
		}

		for (int j = 0; j <8; j++) {
			outWeight[j] = 0.5;
		}

		for(int i=0;i<8;i++){
			midSpeValues[i] = 0.5;
		}
		//重复执行的次数，设置为5000次
		for(int doNumber=0;doNumber<1000;doNumber++){
			for(int m = 0; m < 17; m++){//对数据集中的每一个示例
				
				//计算当前样本（示例）的输出（机器）
				double[] MidValues = new double[middleNumber];//用于存放隐层节点的输出
				
				double result = 0;//用于存储一次示例的中机器输出结果
				for(int i=0;i<middleNumber;i++){
					double results = 0;
					for(int j=0;j<inNumber;j++){
						results = results+inWeight[j][i]*D[m][j];
					}
					results = results - midSpeValues[i];//减去阈值
					results = 1/(Math.expm1(-results)+2);
					MidValues[i] = results;
					
					result = result+results*outWeight[i];
				}
				
				//得到当前示例在输出节点的输入，下一步要经过跃值函数
				result = 1/(Math.expm1(outSpe-result)+2);
				System.out.println(result);
				//计算输出层神经元的梯度项
				double outGradientTerm = result*(1-result)*(D[m][8]-result);
				//计算隐层神经元梯度项
				double midGradientTerm[] = new double[MidValues.length];
				for(int i=0;i<midGradientTerm.length;i++){
					midGradientTerm[i] = MidValues[i]*(1-MidValues[i])*outWeight[i]*outGradientTerm;
				}
				//更新所有权重和阈值
				outSpe = outSpe-0.1*outGradientTerm;//输出层阈值更新
				
				//隐层阈值更新
				for(int i=0;i<middleNumber;i++){
					midSpeValues[i] = midSpeValues[i]-0.1*midGradientTerm[i];
				}
				
				//隐层到输出层之间权重更新 
				for(int i=0;i<middleNumber;i++){
					outWeight[i] = outWeight[i] + 0.8* midGradientTerm[i]*MidValues[i];
				}
				
				// 输入层到隐层的权重更新
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {// 按行更新
						inWeight[j][i] = inWeight[j][i] + 0.8* midGradientTerm[i] * D[m][j];
					}
				}
				
			}
		}
	
	}
	
	public static void main(String[] args){
		//new Data().showData(new Data().getChangeDataType(new Data().getData()));
		new BpNeural().BpDeep(new Data().getChangeDataType(new Data().getData()));
	}

}
