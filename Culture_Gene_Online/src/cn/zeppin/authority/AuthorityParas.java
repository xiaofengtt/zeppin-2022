package cn.zeppin.authority;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorityParas
{
	
	public int[] user() default {};
	
	public String userGroupName();
	
	public int[] denyUser() default {};
	
	public String errMsg() default "无权访问";
	
}