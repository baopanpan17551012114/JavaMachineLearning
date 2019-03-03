package naive_bayes_classifier;

public class NaiveBayesClassifier {
	//���ر�Ҷ˹������
	public void naiveClassifier(String[][] D,String[][] testExample){
		 double goodNumber = new Data().getSpeAttributeNumber(D, "���", "1");
		 double badNumber = new Data().getSpeAttributeNumber(D, "���", "0");
		 double goodPro = goodNumber/(D.length-1);
		 double badPro = badNumber/(D.length-1);
		 for(int i=0;i<D[0].length-3;i++){
			 String attributeName = testExample[0][i];
			 String attributeValue = testExample[1][i];
			 goodPro = goodPro*(new Data().getTwoSpeAttributeNumber(D, attributeName, attributeValue, "���", "1")/goodNumber);
			 System.out.println((new Data().getTwoSpeAttributeNumber(D, attributeName, attributeValue, "���", "1")/goodNumber));
			 badPro = badPro*(new Data().getTwoSpeAttributeNumber(D, attributeName, attributeValue, "���", "0")/badNumber);
		 }
		 for(int i=D[0].length-3;i<D[0].length-1;i++){
			 String attributeName = testExample[0][i];
			 String attributeValue = testExample[1][i];
			 goodPro = goodPro*(new Data().getContinueAttributeNumber(D, attributeName, attributeValue, "���", "1"));
			 badPro = badPro*(new Data().getContinueAttributeNumber(D, attributeName, attributeValue, "���", "0"));
			 System.out.println(goodPro);
		 }
		
		 
		
		
		
		
	}
	public static void main(String[] args){
		String[][] tet = new String[][]{
				{ "ɫ��", "����", "����", "����", "�겿", "����","�ܶ�","������", "���" },
				{ "����", "����", "����", "����", "����", "Ӳ��","0.697","0.460", "1" }};
		new NaiveBayesClassifier().naiveClassifier(new Data().getDatas(), tet);
	}

}
