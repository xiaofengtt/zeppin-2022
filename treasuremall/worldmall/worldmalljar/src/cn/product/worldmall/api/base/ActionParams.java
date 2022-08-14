/** 
 * 
 */  
package cn.product.worldmall.api.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * ClassName: ActionParams <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * 
 * @version  
 * @since JDK 1.7 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionParams {
	ActionParam[] value();
}
