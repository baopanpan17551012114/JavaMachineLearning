package AODE;

public class test {
	public void classifier(String[][] D){
		double goodPro = 0;
		double badPro = 1;
		double p0= 0;
		for (int i = 0; i < D[0].length - 1; i++){
			String attributeName1 = D[0][i];
			String[] attributeValues = new Data().getDifferentValue(D, attributeName1);
			for(int j = 0; j < attributeValues.length - 1; j++){
				
				double p1 = 1;
				
				String attributeValue1 = attributeValues[j];
				double d = (1+(new Data().getTwoSpeAttributeNumber(D, "结果", "1", attributeName1, attributeValue1)))/(17+2*(new Data().getAttributeValueTypeNumber(D, attributeName1)));
				
				double q0= 1;
				double q1 = 1;
				for (int k = 0; k < D[0].length - 1; k++){
					String attributeName2 = D[0][k];
					String[] attributeValues2 = new Data().getDifferentValue(D, attributeName2);
					for(int p = 0; p < attributeValues.length - 1; p++){
						String attributeValue2 = attributeValues[j];
						double d2 = (1+(new Data().getThreeSpeAttributeNumber(D, "结果", "1", attributeName2, attributeValue2,attributeName1, attributeValue1)))/((new Data().getTwoSpeAttributeNumber(D, "结果", "1", attributeName1, attributeValue1))+(new Data().getAttributeValueTypeNumber(D, attributeName2)));
					
						q0 = q0*d2;
					}
				}
				d = d*q0;
				p0 = p0+d;
				
			}
		}
		System.out.println(p0);
		
	}
	public static void main(String[] args){
		new test().classifier(new Data().getDatas());
	}
}
