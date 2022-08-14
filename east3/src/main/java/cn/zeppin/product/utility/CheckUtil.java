package cn.zeppin.product.utility;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CheckUtil {
	/**
	 * 校验select参数
	 * @param type
	 * @param str
	 * @return
	 */
	public static Boolean checkValue(String type, String str){
		Object obj = ReflectUtlity.getConst(CheckValues.class, type);
		if(obj == null){
			return true;
		}
		
		String values = obj.toString();
		if(values.indexOf(str) != -1){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 校验日期参数
	 * @param type
	 * @param str
	 * @return
	 */
	public static Boolean checkDateValue(String type, String str) {
		Object obj = ReflectUtlity.getConst(CheckDateValues.class, type);
		if(obj == null){
			return true;
		}
		//校验时间格式是否符合YYYYMMDD
		try {
			String values = obj.toString();
			values = (values.split(","))[0];
			if(values.length() > 8) {
				if(str.length() != 14) {
					return false;
				}
				
				DateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
				formatter.setLenient(false);
				formatter.parse(str);
			} else {
				if(str.length() != 8) {
					return false;
				}
				
				DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
				formatter.setLenient(false);
				formatter.parse(str);
			}
			
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 校验小数位数
	 * @param decimal
	 * @param length
	 * @return
	 */
	public static Boolean checkDecimal (BigDecimal decimal, int length) {
		int scale = decimal.scale();
		if(scale > length) {
			return false;
		}
		return true;
	}
	
	/**
	 * 校验非空
	 * @param type（格式：表名_列名）
	 * @param str
	 * @return
	 */
	public static Boolean checkNullable (String type, String str) {
		if(CheckNullableValues.nullable.containsKey(type)) {
			if(Utlity.checkStringNull(str)) {
				return false;
			}
		} else {
			return true;
		}
		
		return true;
	}
}
