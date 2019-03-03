package BP_deep;

import java.util.Arrays;

public class BpDeepTest{
    public static void main(String[] args){
        //��ʼ��������Ļ�������
        //��һ��������һ���������飬��ʾ������Ĳ�����ÿ��ڵ���������{3,10,10,10,10,2}��ʾ�������3���ڵ㣬�������2���ڵ㣬�м���4�������㣬ÿ��10���ڵ�
        //�ڶ���������ѧϰ�����������������Ƕ���ϵ��
        BpDeep bp = new BpDeep(new int[]{2,10,2}, 0.15, 0.8);

        //�����������ݣ���Ӧ�����4����ά��������
        double[][] data = new double[][]{{1,2},{2,2},{1,1},{2,1}};
        //����Ŀ�����ݣ���Ӧ4���������ݵķ���
        double[][] target = new double[][]{{1,0},{0,1},{0,1},{1,0}};

        //����ѵ��5000��
        for(int n=0;n<5000;n++)
            for(int i=0;i<data.length;i++)
                bp.train(data[i], target[i]);

        //����ѵ�������������������
        for(int j=0;j<data.length;j++){
            double[] result = bp.computeOut(data[j]);
            System.out.println(Arrays.toString(data[j])+":"+Arrays.toString(result));
        }

        //����ѵ�������Ԥ��һ�������ݵķ���
        double[] x = new double[]{3,1};
        double[] result = bp.computeOut(x);
        System.out.println(Arrays.toString(x)+":"+Arrays.toString(result));
    }
}
