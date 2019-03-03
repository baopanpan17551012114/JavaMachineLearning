package base_test;

public class MoreSingleData {
	//����ʾ����������Ҳ��ʵ�֣�����Ч���ܲ��һ��������ʾ�������Ǽ�����ֵ
	private String[][] data = new String[][] {
			{ "ɫ��", "����", "����", "����", "�겿", "����","�ܶ�","������", "���" },
			{ "0.25", "0.25", "0.5", "0.75", "0.5", "0.3","0.719","0.103", "0" } };

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
	}
	
	/**
	 * ���ô������ݼ��������ø�ʾ��
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
		
		
		int inNumber = 3;//����ڵ����Ϊ���Ը���
		int middleNumber = inNumber;//����ڵ�������Ϊ������ڵ���+1
		double[][] inWeight = new double[3][3];//���������֮���Ȩ�أ�ÿһ����������һ������ڵ��ÿ���м�ڵ��Ȩ��
		double[] outWeight = new double[middleNumber];//���������ڵ�֮��Ȩ�أ�����Ϊ������

		//����Ȩ�غ���ֵȫ����Ϊ0-1֮�������
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
		for(int k=0;k<10000;k++){//ѭ��ʮ��
			//���㵱ǰ������ʾ�����������������
			for(int m=0;m<2;m++){
				double[] MidValues = new double[middleNumber];//���ڴ������ڵ�����
				
				double result = 0;//���ڴ洢һ��ʾ�����л���������
				for(int i=0;i<middleNumber;i++){
					double results = 0;
					for(int j=0;j<inNumber;j++){
						results = results+inWeight[j][i]*exam[m][j];
					}
					results = results;//��ȥ��ֵ,û����ֵ�ɼ�
					results = 1/(Math.expm1(-results)+2);
					MidValues[i] = results;
					
					result = result+results*outWeight[i];
				}
				
				//�õ���ǰʾ��������ڵ�����룬��һ��Ҫ����Ծֵ����
				result = 1/(Math.expm1(-result)+2);
				//System.out.println(result);
				System.out.println(result);
				//�����������Ԫ���ݶ���
				double outGradientTerm = result*(1-result)*(outRe[m]-result);
				//����������Ԫ�ݶ���
				double midGradientTerm[] = new double[MidValues.length];
				for(int i=0;i<midGradientTerm.length;i++){
					midGradientTerm[i] = MidValues[i]*(1-MidValues[i])*outWeight[i]*outGradientTerm;
				}
				//���㵽�����֮��Ȩ�ظ��� 
				for(int i=0;i<middleNumber;i++){
					outWeight[i] = outWeight[i] + 0.8*midGradientTerm[i]*MidValues[i];
					//System.out.println(outWeight[i]);
				}
				//����㵽�����Ȩ�ظ���
				for(int i=0;i<inWeight.length;i++){
					for(int j=0;j<inWeight[0].length;j++){//���и���
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
