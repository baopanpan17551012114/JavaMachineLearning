package k_means;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

public class Data {
	private String[][] datas = new String[][] { { "密度", "含糖率", "编号" },
			{ "0.697", "0.460", "1" }, { "0.774", "0.376", "2" },
			{ "0.634", "0.264", "3" }, { "0.608", "0.318", "4" },
			{ "0.556", "0.215", "5" }, { "0.403", "0.237", "6" },
			{ "0.481", "0.149", "7" }, { "0.437", "0.211", "8" },
			{ "0.666", "0.091", "9" }, { "0.243", "0.267", "10" },
			{ "0.245", "0.057", "11" }, { "0.343", "0.099", "12" },
			{ "0.639", "0.161", "13" }, { "0.657", "0.198", "14" },
			{ "0.360", "0.370", "15" }, { "0.593", "0.042", "16" },
			{ "0.719", "0.103", "17" }, { "0.359", "0.188", "18" },
			{ "0.359", "0.241", "19" }, { "0.282", "0.257", "20" },
			{ "0.748", "0.232", "21" }, { "0.714", "0.346", "22" },
			{ "0.483", "0.312", "23" }, { "0.478", "0.437", "24" },
			{ "0.525", "0.369", "25" }, { "0.751", "0.489", "26" },
			{ "0.532", "0.472", "27" }, { "0.473", "0.376", "28" },
			{ "0.725", "0.445", "29" }, { "0.446", "0.459", "30" } };

	public String[][] getDatas() {
		return datas;
	}

	public void setDatas(String[][] datas) {
		this.datas = datas;
	}
	
	/**
	 * 给定两个示例，计算欧氏距离
	 * @param a
	 * @param b
	 * @return
	 */
	public double distance(double[] a,double[] b){
		double a1 = a[0];
		double a2 = a[1];
		double b1 = b[0];
		double b2 = b[1];
		double distance = 0;
		distance = Math.sqrt((a1-b1)*(a1-b1)+(a2-b2)*(a2-b2));
		distance = new BigDecimal(distance).setScale(3, RoundingMode.HALF_UP).doubleValue();
		return distance;
	}
	
	/**
	 * 给定一个簇（用list表示），计算k均值并返回(也是新的簇中心)
	 * @param list
	 * @return
	 */
	public double[] get_K_Value(List<double[]> list){
		double countNumber1 = 0;
		double countNumber2 = 0;
		for(int i=1;i<list.size();i++){//index=0存放簇标记
			double[] value = list.get(i);
			countNumber1 += value[0];
			countNumber2 += value[1];
		}
		countNumber1 = countNumber1 / (list.size()-1);
		countNumber1 = new BigDecimal(countNumber1).setScale(3, RoundingMode.HALF_UP).doubleValue();
		countNumber2 = countNumber2 / (list.size()-1);
		countNumber2 = new BigDecimal(countNumber2).setScale(3, RoundingMode.HALF_UP).doubleValue();
		double[] result = new double[2];
		result[0] = countNumber1;
		result[1] = countNumber2;
		return result;
	}
	
	/**
	 * 判断两个聚类是否相等。利用序号判定
	 * @param a
	 * @param b
	 * @return
	 */
	public boolean isEqualCluster(List<double[]> a,List<double[]> b){
		List<Double> listA = new LinkedList<Double>();
		List<Double> listB = new LinkedList<Double>();
		
		for(int i=1;i<a.size();i++){//index=0存放簇标记
			double a1 = a.get(i)[2];
			listA.add(a1);
		}
		for(int i=1;i<b.size();i++){//index=0存放簇标记
			double b1 = b.get(i)[2];
			listB.add(b1);
		}
		boolean result = true;
		for(double dou:listA){
			if(listB.contains(dou)){
				result = true;
			}else{
				result = false;
			}
		}
		return result;
	}
	
	/**
	 * 判断两次聚类结果是否相同，一个聚类结果存放在一个list，聚类的每条示例用double[]，
	 * 所有结果再存放在一个大的list
	 * @param list1
	 * @param list2
	 * @return
	 */	
	public boolean isEqualResult(List<List<double[]>> list1,List<List<double[]>> list2){
		List<List<double[]>> li1 = new LinkedList<List<double[]>>();
		List<List<double[]>> li2 = new LinkedList<List<double[]>>();
		for(List<double[]> ls1:list1){
			li1.add(ls1);
		}
		for(List<double[]> ls1:list2){
			li2.add(ls1);
		}
		for(List<double[]> ls1:list1){
			for(List<double[]> ls2:list2){
				if(new Data().isEqualCluster(ls1, ls2)){
					li1.remove(ls1);
					li2.remove(ls2);
				}
			}
		}
		if(li1.size() == 0&&li2.size() == 0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 *  将数据集变为数组列表
	 * @param D
	 * @return
	 */
	public List<double[]> getChangedData(String[][] D){
		List<double[]> list = new LinkedList<double[]>();
		for(int i =1;i<D.length;i++){
			double[] value = new double[3];
			value[0] = Double.valueOf(D[i][0]);
			value[1] = Double.valueOf(D[i][1]);
			value[2] = Double.valueOf(D[i][2]);
			list.add(value);
		}
		return list;
		
	}
	
	public static void main(String[] args){
		String[] a= new String[]{"3","4"};
		String[] b = new String[]{"4","5"};
		List<double[]> list1 = new LinkedList<double[]>();
		List<double[]> list2 = new LinkedList<double[]>();
		List<List<double[]>> list11 = new LinkedList<List<double[]>>();
		List<List<double[]>> list21 = new LinkedList<List<double[]>>();
		double[] d1 = new double[]{0.697, 0.460, 1};
		double[] d3 = new double[]{0.634, 0.264, 3};
		double[] d2 = new double[]{0.697, 0.460, 1};
		double[] d4 = new double[]{0.634, 0.264, 3};
		list1.add(d1);
		list1.add(d3);
		
		list2.add(d4);
		list2.add(d2);
		list11.add(list1);
		list11.add(list2);
		list21.add(list2);
		list21.add(list1);
		
		System.out.println(new Data().isEqualResult(list11, list21));
		
		
	}

}
