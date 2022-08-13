package com.whaty.platform.entity.web.action;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeMap;

public class BigDecimalTest {
	 // 默认除法运算精度
	 private static final int DEF_DIV_SCALE = 10;
public static void main(String[] args) {
//	double   abc   =   1.234567890;   
//    System.out.println(new   java.math.BigDecimal(Double.toString(abc)).setScale(3,java.math.BigDecimal.ROUND_HALF_UP).doubleValue());  
//	double a = 0.7;
//	double b = 0.3;
//	double c = 0.1;
//	System.out.println(a/b);
//	System.out.println(BigDecimalTest.div(a, b,2));
//	Random rd = new Random();
//	int a = rd.nextInt(1000);
//	System.out.println(a);
//	TreeMap map = new TreeMap(); 
//	for(int i=0; i<100; i++) { 
//
//	int s = (int)(Math.random()*10000); 
//
//	map.put(s,s); 
//
//	} 
//
//	Collection col = map.values(); 
//
//	Iterator it = col.iterator(); 
//
//	while(it.hasNext()) { 

//	System.out.println(it.next()); 

//	} 
	double a = BigDecimalTest.add(-12345d, 1004d);
	System.out.println(a);
}
	private static int getRandInt(int max){
	 Random rd = new Random();
	 return Math.abs(rd.nextInt()%max);
	}
/**
 * 提供精确的加法运算。
 * 
 * @param v1 被加数
 * @param v2 加数
 * @return 两个参数的和
 */
public static double add(double v1, double v2) {
 BigDecimal b1 = new BigDecimal(Double.toString(v1));
 BigDecimal b2 = new BigDecimal(Double.toString(v2));
 return b1.add(b2).doubleValue();
}
/**
 * 提供精确的减法运算。
 * 
 * @param v1 被减数
 * @param v2 减数
 * @return 两个参数的差
 */
public static double sub(double v1, double v2) {
 BigDecimal b1 = new BigDecimal(Double.toString(v1));
 BigDecimal b2 = new BigDecimal(Double.toString(v2));
 return b1.subtract(b2).doubleValue();
}
/**
 * 提供精确的乘法运算。
 * 
 * @param v1 被乘数
 * @param v2 乘数
 * @return 两个参数的积
 */
public static double mul(double v1, double v2) {
 BigDecimal b1 = new BigDecimal(Double.toString(v1));
 BigDecimal b2 = new BigDecimal(Double.toString(v2));
 return b1.multiply(b2).doubleValue();
}
/**
 * 提供（相对）精确的除法运算，当发生除不尽的情况时，
 * 精确到小数点以后10位，以后的数字四舍五入。
 * 
 * @param v1 被除数
 * @param v2 除数
 * @return 两个参数的商
 */
public static double div(double v1, double v2) {
 return div(v1, v2, DEF_DIV_SCALE);
}
/**
 * 提供（相对）精确的除法运算。
 * 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
 * 
 * @param v1 被除数
 * @param v2 除数
 * @param scale 表示表示需要精确到小数点以后几位。
 * @return 两个参数的商
 */
public static double div(double v1, double v2, int scale) {
 if (scale < 0) {
  throw new IllegalArgumentException(
  "The scale must be a positive integer or zero");
 }
 BigDecimal b1 = new BigDecimal(Double.toString(v1));
 BigDecimal b2 = new BigDecimal(Double.toString(v2));
 return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
}
/**
 * 提供精确的小数位四舍五入处理。
 * 
 * @param v 需要四舍五入的数字
 * @param scale 小数点后保留几位
 * @return 四舍五入后的结果
 */
public static double round(double v, int scale) {
 if (scale < 0) {
  throw new IllegalArgumentException(
  "The scale must be a positive integer or zero");
 }
 BigDecimal b = new BigDecimal(Double.toString(v));
 BigDecimal one = new BigDecimal("1");
 return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
}

}
