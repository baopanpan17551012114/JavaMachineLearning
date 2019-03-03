package naive_bayes_classifier;

public class NaiveBayesClassifier {
	//朴素贝叶斯分类器
	public void naiveClassifier(String[][] D,String[][] testExample){
		 double goodNumber = new Data().getSpeAttributeNumber(D, "结果", "1");
		 double badNumber = new Data().getSpeAttributeNumber(D, "结果", "0");
		 double goodPro = goodNumber/(D.length-1);
		 double badPro = badNumber/(D.length-1);
		 for(int i=0;i<D[0].length-3;i++){
			 String attributeName = testExample[0][i];
			 String attributeValue = testExample[1][i];
			 goodPro = goodPro*(new Data().getTwoSpeAttributeNumber(D, attributeName, attributeValue, "结果", "1")/goodNumber);
			 System.out.println((new Data().getTwoSpeAttributeNumber(D, attributeName, attributeValue, "结果", "1")/goodNumber));
			 badPro = badPro*(new Data().getTwoSpeAttributeNumber(D, attributeName, attributeValue, "结果", "0")/badNumber);
		 }
		 for(int i=D[0].length-3;i<D[0].length-1;i++){
			 String attributeName = testExample[0][i];
			 String attributeValue = testExample[1][i];
			 goodPro = goodPro*(new Data().getContinueAttributeNumber(D, attributeName, attributeValue, "结果", "1"));
			 badPro = badPro*(new Data().getContinueAttributeNumber(D, attributeName, attributeValue, "结果", "0"));
			 System.out.println(goodPro);
		 }
		
		 
		
		
		
		
	}
	public static void main(String[] args){
		String[][] tet = new String[][]{
				{ "色泽", "根蒂", "敲声", "纹理", "脐部", "触感","密度","含糖率", "结果" },
				{ "青绿", "蜷缩", "浊响", "清晰", "凹陷", "硬滑","0.697","0.460", "1" }};
		new NaiveBayesClassifier().naiveClassifier(new Data().getDatas(), tet);
	}

}
