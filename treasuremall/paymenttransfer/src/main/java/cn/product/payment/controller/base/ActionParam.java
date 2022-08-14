package cn.product.payment.controller.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
//import java.lang.annotation.Repeatable;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(ActionParams.class)
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
