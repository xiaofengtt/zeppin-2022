/** 
 * Project Name:Self_Cool  
 * File Name:ParamCheck.java 
 * Package Name:cn.zeppin.action.base 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.product.itic.core.controller.base;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
//import java.lang.annotation.Repeatable;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** 
 * ClassName: ActionParam <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月24日 下午7:39:28 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(ActionParams.class) //请升级为JDK1.8, rjf-20140724
public @interface ActionParam {

	public String key();
	public DataType type();
	public boolean required() default false;
	public int minLength() default 0;
	public int maxLength() default 99999;
	public long minValue() default 0;
	public long maxValue() default Long.MAX_VALUE;  
	public String validate() default "";
	public String message() default "";
	//内部类
	
	/**
	 * 在牛投邦项目中还缺少如下参数值验证
	 * 1、货币（含有二位小数）  
	 * 2、正数货币（含有二位小数）
	 * 3、正整数货币
	 * 4、十倍数正整数货币值
	 * 5、百倍数正整数货币
	 * 6、千倍数正整数货币
	 * 7、万倍数正整数货币
	 * 8、货币值验证（无限制小数位数，无限制正负值）
	 * 
	 * NUMBER_ARRAY: 数值数组，主要用于提交表单时有一些字段是多个值，当参数数值类型为NUMBER_ARRAY时，则数组中每一个值都需要经过验证
	 * STRING_ARRAY: 字符串数组，主要用于提交表单时有一些字段是多个值（如UUID），当参数数值类型为STRING_ARRAY时，则数组中每一个值都需要经过验证
	 */
    public enum DataType{
    	NUMBER,                                      // 任意数字
    	BOOLEAN,                                     // 布尔值
    	DATE,										 // 日期
    	INTEGER,                                     // 整数
    	POSITIVE_INTEGER,                            // 正整数
    	CURRENCY,                                    // 货币（含有二位小数）
    	POSITIVE_CURRENCY,                           // 正数货币（含有二位小数）
    	TEN_TIMES_POSITIVE_INTEGER,                  // 十倍正整数
    	HUNDRED_TIMES_POSITIVE_INTEGER,              // 百倍正整数
    	THOUSAND_TIMES_POSITIVE_INTEGER,             // 千倍正整数
    	TENTHOUSAND_TIMES_POSITIVE_INTEGER,          // 万倍正整数
    	PHONE,                                       // 手机号
    	EMAIL,                                       // 电子邮箱
    	IDCARD,                                      // 身份证
    	STRING,                                      // 字符串
    	NUMBER_ARRAY,                                // 数值数组
    	STRING_ARRAY                                 // 字符串数组
    	}        
}
