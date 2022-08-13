/**  
 * This class is used for 访问权限注解接口
 * @author suijing
 * @version  
 *       1.0, 2014年7月25日 下午3:58:53  
 */
package cn.zeppin.authority;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sj
 *
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorityParas {

	public int[] user() default {};

	public String userGroupName();

	public int[] denyUser() default {};

	public String errMsg() default "无权访问";

}
