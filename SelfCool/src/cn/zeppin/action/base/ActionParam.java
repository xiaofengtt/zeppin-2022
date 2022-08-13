/** 
 * Project Name:Self_Cool  
 * File Name:ParamCheck.java 
 * Package Name:cn.zeppin.action.base 
 * Copyright (c) 2014, Zeppin All Rights Reserved. 
 * 
 */  
package cn.zeppin.action.base;

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
	public ValueType type();
	public boolean nullable() default true;
	public boolean emptyable() default true;
	//内部类
    public enum ValueType{NUMBER, PHONE, EMAIL, IDCARD, STRING, NUMBER_ARRAY}
}
