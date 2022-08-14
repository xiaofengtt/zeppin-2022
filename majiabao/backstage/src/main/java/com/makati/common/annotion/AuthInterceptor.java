package com.makati.common.annotion;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthInterceptor {


    /**  1.此值若为true，则必须用登录的md5_salt才能校验
     *   2.若为false，则既可以登录md5_salt值校验又可以用默认值来校验
     *   3.若不带此注解，则无需校验
     * @return
     */
    boolean needAuthTokenVerify() default true;
}
