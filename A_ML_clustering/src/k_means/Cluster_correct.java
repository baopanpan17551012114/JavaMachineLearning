package k_means;

import java.util.LinkedList;
import java.util.List;

public class Cluster_correct {
	/**
	 * 第一次聚类，将将二维数据集聚类返回，聚类结果保存在一个大的list中（k取三）
	 * @param D
	 */
	public List<List<double[]>> getFirstCluster(String[][] D){
		List<double[]> list1 = new LinkedList<double[]>();
		List<double[]> list2 = new LinkedList<double[]>();
		List<double[]> list3 = new LinkedList<double[]>();
		List<double[]> list = new Data().getChangedData(D);
		list1.add(list.get(5));
		list2.add(list.get(11));
		list3.add(list.get(23));
		for(int i=0;i<list.size();i++){
			double[] value = list.get(i);
			double d1 = 0;
			double d2 = 0;
			double d3 = 0;
			d1 = new Data().distance(value, list1.get(0));
			d2 = new Data().distance(value, list2.get(0));
			d3 = new Data().distance(value, list3.get(0));
			System.out.println(d1+"&&"+d2+"&&"+d3);
			double min = 0;
			if(d2>=d1 && d3>=d1){
				min = d1;
				list1.add(value);
			}else{
				if(d3>=d2){
					min = d2;
					list2.add(value);
				}else{
					min = d3;
					list3.add(value);
				}
			}
			
		}
		
		List<List<double[]>> result = new LinkedList<List<double[]>>();
		result.add(list1);
		result.add(list2);
		result.add(list3);
		return result;
		
	}
	
	/**
	 * 得到最终的分类
	 * @param D
	 * @return
	 */
	public List<List<double[]>> getCluster(String[][] D){
		List<List<double[]>> listResult = new LinkedList<List<double[]>>();
		List<List<double[]>> list = new Cluster().getFirstCluster(D);//第一次聚类之后的结果
		//List<double[]> orderlist = new Data().getChangedData(D);//存放的是所有元素，按顺序
		int k = 0;
		int n = 0;
		while(k<1){
			List<double[]> orderlist = new Data().getChangedData(D);//存放的是所有元素，按顺序
			if(listResult.size() != 0){
				list.clear();
				for(List<double[]> l:listResult){
					list.add(l);
				}
				listResult.clear();
			}
			//System.out.println("list的长度："+list.size()+"listResult的长度："+listResult.size());
			List<double[]> list1 = new LinkedList<double[]>();
			List<double[]> list2 = new LinkedList<double[]>();
			List<double[]> list3 = new LinkedList<double[]>();
			list1.add(new Data().get_K_Value(list.get(0)));
			list2.add(new Data().get_K_Value(list.get(1)));
			list3.add(new Data().get_K_Value(list.get(2)));
			System.out.println(list1.get(0)[0]+"{}"+list2.get(0)[0]+"{}"+list3.get(0)[0]+"{}");
			for(int i=0;i<orderlist.size();i++){
				double[] value = orderlist.get(i);
				double d1 = 0;
				double d2 = 0;
				double d3 = 0;
				d1 = new Data().distance(value, list1.get(0));
				d2 = new Data().distance(value, list2.get(0));
				d3 = new Data().distance(value, list3.get(0));
				System.out.println(d1+"&&"+d2+"&&"+d3);
				double min = 0;
				if(d2>=d1 && d3>=d1){
					min = d1;
					list1.add(value);
				}else{
					if(d3>=d2){
						min = d2;
						list2.add(value);
					}else{
						min = d3;
						list3.add(value);
					}
				}
				
			}
			//list.clear();
			listResult.add(list1);
			listResult.add(list2);
			listResult.add(list3);
			System.out.println("list1中元素序号：");
			for(int m=1;m<list1.size();m++){
				System.out.print(list1.get(m)[2]+"\t");
			}
			System.out.println();
			System.out.println("list2中元素序号：");
			for(int m=1;m<list2.size();m++){
				System.out.print(list2.get(m)[2]+"\t");
			}
			System.out.println();
			System.out.println("list3中元素序号：");
			for(int m=1;m<list3.size();m++){
				System.out.print(list3.get(m)[2]+"\t");
			}
			System.out.println();
			//System.out.println("list1长度:"+list1.size()+"\tlist2长度:"+list2.size()+"\tlist3:"+list3.size());
			if(new Data().isEqualResult(listResult, list)){
				k = k+1;
			}else{
				k = k;
			}
			n++;
		}
		System.out.println(n+"^^^^^^^^^^^^^");
		return listResult;
		
	}
	
	public static void main(String[] args){
		List<List<double[]>> d = new Cluster_correct().getCluster(new Data().getDatas());
		//d = new Cluster().getFirstCluster(new Data().getDatas());
		for(List<double[]> l:d){
			System.out.println(l.size()+"%");
		}
		
		
		
		
		
		
		
		
		
		
	}
	
}

