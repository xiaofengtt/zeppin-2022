package com.makati.common.util.lottery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 1、我们知道，排列个数的计算公式如下：
    A (n(下),m(上))= n!/(n-m)!

              组合个数的计算公式如下：
    C (n,m)=  n!/(m!(n-m)!)      

 * @author Hello
 *
 */

public class ComAndArrangeUtil {
	
	private static final String [] num=new String[] {"0","1", "2", "3", "4", "5", "6", "7", "8", "9"};
	
	/** 此方法为原始方法，
     * 排列选择   01 10
     * @param dataList 待选列表 
     * @param resultList 前面（resultIndex-1）个的排列结果 
     * @param resultIndex 选择索引，从0开始 
     */  
//    private static void arrangementSelect(String[] dataList, String[] resultList, int resultIndex) {  
//        int resultLen = resultList.length;  
//        if (resultIndex >= resultLen) { // 全部选择完时，输出排列结果  
//            System.out.println(Arrays.asList(resultList)+"==============");  
//            return ;  
//        }  
//        // 递归选择下一个  
//        for (int i = 0; i < dataList.length; i++) {  
//            // 判断待选项是否存在于排列结果中  
//            boolean exists = false;  
//            for (int j = 0; j < resultIndex; j++) {  
//                if (dataList[i].equals(resultList[j])) {  
//                    exists = true;  
//                    break;  
//                }  
//            }  
//            if (!exists) { // 排列结果不存在该项，才可选择  
//                resultList[resultIndex] = dataList[i]; 
//                arrangementSelect(dataList, resultList, resultIndex + 1);  
//            }  
//        }
//    }
	
  
    /** 
     * 排列选择，组选和值 ,此选择里不允许三个数字都一样
     * @param dataList 待选列表 
     * @param resultList 前面（resultIndex-1）个的排列结果 
     * @param resultIndex 选择索引，从0开始 
     */  
    private static void arrangementZuxHZ(String[] dataList, String[] resultList, int resultIndex,int specialValue,List<String> list,int intLength) {  
        int resultLen = resultList.length;  
        if (resultIndex >= resultLen) { // 全部选择完时，输出排列结果  
        	int count=0;
        	int [] itemp=new int[intLength];
        	for (int i = 0; i < resultList.length; i++) {
				count=Integer.parseInt(resultList[i])+count;
				itemp[i]=Integer.parseInt(resultList[i]);
			}
        	String temp="";
        	if(intLength==3){
        		if(itemp[0]==itemp[1]&&itemp[1]==itemp[2]){
            		return ;
            	}
            	Arrays.sort(itemp);
            	temp=""+itemp[0]+itemp[1]+itemp[2];	
        	}else if(intLength==2){
        		if(itemp[0]==itemp[1]){
            		return ;
            	}
            	Arrays.sort(itemp);
            	temp=""+itemp[0]+itemp[1];
        	}
        	
        	if(specialValue==count&&!list.contains(temp)){
        		list.add(temp);
        	}
            return ;  
        }  
        // 递归选择下一个  
        for (int i = 0; i < dataList.length; i++) {  
            resultList[resultIndex] = dataList[i]; 
            arrangementZuxHZ(dataList, resultList, resultIndex + 1,specialValue,list,intLength);  
        }
    }
    
    /** 
     * 排列选择 ,直选和值，此选择里允许有重复的数字
     * @param dataList 待选列表 
     * @param resultList 前面（resultIndex-1）个的排列结果 
     * @param resultIndex 选择索引，从0开始 
     */  
    private static void arrangementZhixHZ(String[] dataList, String[] resultList, int resultIndex,int specialValue,List<String> list) {  
        int resultLen = resultList.length;  
        if (resultIndex >= resultLen) { // 全部选择完时，输出排列结果  
        	int count=0;
        	String temp="";
        	for (int i = 0; i < resultList.length; i++) {
				count=Integer.parseInt(resultList[i])+count;
				temp=temp+resultList[i];
			}
        	if(specialValue==count&&!list.contains(temp)){
        		list.add(temp);
        	}
            return ;  
        }  
        // 递归选择下一个  
        for (int i = 0; i < dataList.length; i++) {  
               resultList[resultIndex] = dataList[i]; 
               arrangementZhixHZ(dataList, resultList, resultIndex + 1,specialValue,list);  
        }
    }
    
    /** 
     * 排列选择 ,此选择里允许有重复的数字
     * @param dataList 待选列表 
     * @param resultList 前面（resultIndex-1）个的排列结果 
     * @param resultIndex 选择索引，从0开始 
     */  
    private static void arrangementSelectKD(String[] dataList, String[] resultList, int resultIndex,int specialValue,List<String> list,int intLength) {  
        int resultLen = resultList.length;  
        if (resultIndex >= resultLen) { // 全部选择完时，输出排列结果  
        	int count=0;
        	int itemp[]=new int[intLength];
        	String temp="";
        	for (int i = 0; i < resultList.length; i++) {
        		itemp[i]=Integer.parseInt(resultList[i]);
				temp=temp+resultList[i];
			}
        	Arrays.sort(itemp);
        	count=itemp[intLength-1]-itemp[0];
        	if(specialValue==count&&!list.contains(temp)){
        		list.add(temp);
        	}
            return ;  
        }  
        // 递归选择下一个  
        for (int i = 0; i < dataList.length; i++) {  
            resultList[resultIndex] = dataList[i]; 
            arrangementSelectKD(dataList, resultList, resultIndex + 1,specialValue,list,intLength);  
        }
    }
  
    
    /** 
     * 组合选择  01
     * @param data 待选列表 
     * @param dataIndex 待选开始索引 
     * @param resultList 前面（resultIndex-1）个的组合结果 
     * @param resultIndex 选择索引，从0开始 
     */  
    private static void combinationSelect(List<String> list,String[] data, int dataIndex, String[] result, int resultIndex) {  
        int resultLen = result.length;  
        int resultCount = resultIndex + 1;  
        if (resultCount > resultLen) { // 全部选择完时，输出组合结果  
        	String temp="";
        	for (int i = 0; i < result.length; i++) {
				temp=temp+result[i]+",";
			}
        	list.add(temp.substring(0,temp.lastIndexOf(",")));
            return ;  
        }  
        // 递归选择下一个  
        for (int i = dataIndex; i < data.length + resultCount - resultLen; i++) {  
        	result[resultIndex] = data[i];  
            combinationSelect(list,data, i + 1, result, resultIndex + 1);  
        } 
    }  
  
    /** 
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1 
     * @param n 
     * @return 
     */  
    private static int factorial(int n) {  
        return (n > 1) ? n * factorial(n - 1) : 1;  
    }  
  
    /** 
     * 计算排列数，即A(n, m) = n!/(n-m)! 
     * @param n 
     * @param m 
     * @return 
     */  
    public static int arrangement(int n, int m) {  
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;  
    }  
  
    /** 
     * 计算组合数，即C(n, m) = n!/((n-m)! * m!) 
     * @param n :数字的长度
     * @param m ：组合几位数
     * @return 
     */  
    public static int combination(int n, int m) {  
        return (n >= m) ? factorial(n) / factorial(n - m) / factorial(m) : 0;  
    } 
    
    
    //--------------------------------------------------------------------
    /**
     * 直选和值
     * @param n:从数组里选择的个数
     * @param specialValue：特殊的值
     * @return
     */
    public static int combineZhixhz(int n,int specialValue ){
    	List<String> list=new ArrayList<String>();
    	arrangementZhixHZ(num, new String[n], 0,specialValue,list);
        return list.size();
    }
    
    /**
     * 直选跨度
     * @param n:从数组里选择的个数
     * @param specialValue：特殊的值
     * @param intLength :三星，二星
     * @return
     */
    public static int combineZhixKd(int n,int specialValue ,int intLength){
    	List<String> list=new ArrayList<String>();
    	arrangementSelectKD(num, new String[n], 0,specialValue,list,intLength);
        return list.size();
    }
    
    /**
     * 组选和值
     * @param n:从数组里选择的个数
     * @param specialValue：特殊的值
     * @param intLength :三星，二星
     * @return
     */
    public static int combineZuxhz(int n,int specialValue ,int intLength){
    	List<String> list=new ArrayList<String>();
    	arrangementZuxHZ(num, new String[n], 0,specialValue,list,intLength);
        return list.size();
    }
    
    /**
     * 获取给定数组的组合数
     * @param str：数组
     * @param n：组成几个数
     * @return  1,5,6    1,5,7
     */
    public static List<String>  getCombination(String[] str,int n){
    	List<String> list=new ArrayList<String>();
    	combinationSelect(list,str, 0, new String[n], 0);
    	return list;
    }
    
    
//    public static void main(String[] args) {
//    	List<String> list=new ArrayList<String>();
//    	combinationSelect(list,new String[]{"0","1","3","4","5","6","7"}, 0, new String[3], 0);
//    	for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i)+"----------");
//		}
//    }
}
