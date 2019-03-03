package up_down;

import java.util.LinkedList;
import java.util.List;

public class Data {
	private String[][] data = new String[][] {
			{ "色泽", "根蒂", "敲声", "纹理", "脐部", "触感", "结果" },
			{ "青绿", "蜷缩", "浊响", "清晰", "凹陷", "硬滑", "1" },
			{ "乌黑", "蜷缩", "沉闷", "清晰", "凹陷", "硬滑", "1" },
			{ "乌黑", "蜷缩", "浊响", "清晰", "凹陷", "硬滑", "1" },
			{ "青绿", "稍蜷", "浊响", "清晰", "稍凹", "软粘", "1" },
			{ "乌黑", "稍蜷", "浊响", "稍糊", "稍凹", "软粘", "1" },
			{ "青绿", "硬挺", "清脆", "清晰", "平坦", "软粘", "0" },
			{ "浅白", "稍蜷", "沉闷", "稍糊", "凹陷", "硬滑", "0" },
			{ "乌黑", "稍蜷", "浊响", "清晰", "稍凹", "软粘", "0" },
			{ "浅白", "蜷缩", "浊响", "模糊", "平坦", "硬滑", "0" },
			{ "青绿", "蜷缩", "沉闷", "稍糊", "稍凹", "硬滑", "0" } };

	public String[][] getData() {
		return data;
	}

	public void setData(String[][] data) {
		this.data = data;
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
	 * 返回两个指定属性在两个指定属性值上的示例数量
	 * @param D
	 * @param attributeName1 属性1
	 * @param attributeValue1 属性1对应的值
	 * @param attributeName2属性2
	 * @param attributeValue2 属性2对应的值
	 * @return
	 */
	public double getTwoSpeAttributeNumber(String[][] D,String attributeName1,
			String attributeValue1,String attributeName2,String attributeValue2){
		int speColumn1 = 0;
		for(String str:D[0]){
			if(str.equals(attributeName1)){
				break;
			}
			speColumn1++;
		}
		int speColumn2 = 0;
		for(String str:D[0]){
			if(str.equals(attributeName2)){
				break;
			}
			speColumn2++;
		}
		double number = 0;
		for(int i=1;i<D.length;i++){
			if(D[i][speColumn1].equals(attributeValue1) && D[i][speColumn2].equals(attributeValue2)){
				number++;
			}
		}
		return number;
		
	}
	
	/**
	 * 根据当前原子规则返回子数据集，为下一步行为做准备
	 * @param D
	 * @param attributeName
	 * @param attributeValue
	 * @return
	 */
	public String[][] getChildData(String[][] D,String attributeName,String attributeValue){
		double lineNumber1 = new Data().getTwoSpeAttributeNumber(D, attributeName, attributeValue, "结果", "1");
		double lineNumber2 = new Data().getTwoSpeAttributeNumber(D, attributeName, attributeValue, "结果", "0");
		int lineNumber = (int)lineNumber1+(int)lineNumber2;
		String[][] childData = new String[lineNumber+1][D[0].length];
		int speline = 0;
		for(int i=0;i<D[0].length;i++){
			if(D[0][i].equals(attributeName)){
				break;
			}
			speline++;
		}
		for(int i=0;i<D[0].length;i++){
			childData[0][i] = D[0][i];
		}
		int line = 1;
		for(int i=1;i<D.length;i++){
			if(D[i][speline].equals(attributeValue)){
				for(int j=0;j<D[0].length;j++){
					childData[line][j] = D[i][j];
				}
				line++;
			}
		}
		
		String[][] againNewChild = new String[childData.length][childData[0].length-1];
		for(int i=0;i<childData.length;i++){
			int speLine = 0;
			for(int j=0;j<childData[0].length;j++){
				if(j == speline){
					continue;
				}
				againNewChild[i][speLine] = childData[i][j];
				speLine++;
			}
		}
		return againNewChild;
	}
	
	/**
	 * 打印给定的数据集
	 * @param D
	 */
	public void showData(String[][] D){
		for(int line = 0;line<D.length;line++){
			for(int column = 0;column<D[0].length;column++){
				System.out.print(D[line][column]+"\t");
			}
			System.out.println("");
		}
	}
	
	
	/**
	 * 在当前数据集中返回 一个命题
	 * @param D
	 * @return
	 */
	public String getRule(String[][] D){
		String result = "好瓜<---";
		while(D[0].length != 1){
			double maxPer = 0;
			double number = 0;
			String attributeName = "";
			String attributeValue = "";
			for(int i=0;i<D[0].length-1;i++){
				List<String> list = new Data().getDifferentAttributeValues(D, D[0][i]);
				for(String str:list){
					double good = new Data().getTwoSpeAttributeNumber(D, D[0][i], str, "结果", "1");
					double bad = new Data().getTwoSpeAttributeNumber(D, D[0][i], str, "结果", "0");
					double per = good/(good+bad);
					if(per>=maxPer){
						if(per == maxPer){
							if(number<good+number){
								maxPer = per;
								number = good+bad;
								attributeName = D[0][i];
								attributeValue = str;
							}
						}else{
							maxPer = per;
							number = good+bad;
							attributeName = D[0][i];
							attributeValue = str;
						}

					}
				}
			}
			String rule = "";
			if(result.equals("好瓜<---")){
				rule = "("+attributeName+"="+attributeValue+")";
			}else{
				rule = "&&"+"("+attributeName+"="+attributeValue+")";
			}
			result = result + rule;
			System.out.println(result);
			D = new Data().getChildData(D, attributeName, attributeValue);
		}
		System.out.println(result);
		return result;
		
	}

	public static void main(String[] args){
		//new Data().showData(new Data().getChildData(new Data().getData(), "色泽", "乌黑"));
		new Data().getRule(new Data().getData());
	}

}
