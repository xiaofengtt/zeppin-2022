/** 
 * File Name:ActionParams.java 
 * 
 */  
package cn.product.worldmall.controller.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * ClassName: ActionParams <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * date: 2014年7月24日 下午9:16:35 <br/> 
 * 
 * @author Clark 
 * @version  
 * @since JDK 1.7 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionParams {
	ActionParam[] value();
}
