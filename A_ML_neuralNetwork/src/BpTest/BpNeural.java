package BpTest;

public class BpNeural {
	//���幫ʽʹ�������ھ��
	//����˼��ʹ�û���ѧϰ��

	public void BpDeep(double[][] D){
		new Data().showData(D);
		int inNumber = 8;//����ڵ����Ϊ���Ը���
		int middleNumber = 8;//����ڵ�������Ϊ������ڵ���+1
		double[][] inWeight = new double[8][8];//���������֮���Ȩ�أ�����Ϊ����ڵ���
		double[] outWeight = new double[8];//���������ڵ�֮��Ȩ�أ�����Ϊ������
		double[] midSpeValues = new double[8];//����������ֵ
		double outSpe = 0.5;//�������ֵҲֻ��һ��

		//����Ȩ�غ���ֵȫ����Ϊ0.5
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
		//�ظ�ִ�еĴ���������Ϊ5000��
		for(int doNumber=0;doNumber<1000;doNumber++){
			for(int m = 0; m < 17; m++){//�����ݼ��е�ÿһ��ʾ��
				
				//���㵱ǰ������ʾ�����������������
				double[] MidValues = new double[middleNumber];//���ڴ������ڵ�����
				
				double result = 0;//���ڴ洢һ��ʾ�����л���������
				for(int i=0;i<middleNumber;i++){
					double results = 0;
					for(int j=0;j<inNumber;j++){
						results = results+inWeight[j][i]*D[m][j];
					}
					results = results - midSpeValues[i];//��ȥ��ֵ
					results = 1/(Math.expm1(-results)+2);
					MidValues[i] = results;
					
					result = result+results*outWeight[i];
				}
				
				//�õ���ǰʾ��������ڵ�����룬��һ��Ҫ����Ծֵ����
				result = 1/(Math.expm1(outSpe-result)+2);
				System.out.println(result);
				//�����������Ԫ���ݶ���
				double outGradientTerm = result*(1-result)*(D[m][8]-result);
				//����������Ԫ�ݶ���
				double midGradientTerm[] = new double[MidValues.length];
				for(int i=0;i<midGradientTerm.length;i++){
					midGradientTerm[i] = MidValues[i]*(1-MidValues[i])*outWeight[i]*outGradientTerm;
				}
				//��������Ȩ�غ���ֵ
				outSpe = outSpe-0.1*outGradientTerm;//�������ֵ����
				
				//������ֵ����
				for(int i=0;i<middleNumber;i++){
					midSpeValues[i] = midSpeValues[i]-0.1*midGradientTerm[i];
				}
				
				//���㵽�����֮��Ȩ�ظ��� 
				for(int i=0;i<middleNumber;i++){
					outWeight[i] = outWeight[i] + 0.8* midGradientTerm[i]*MidValues[i];
				}
				
				// ����㵽�����Ȩ�ظ���
				for (int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {// ���и���
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
