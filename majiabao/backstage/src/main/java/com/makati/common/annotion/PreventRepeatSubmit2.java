package com.makati.common.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 阻止用户重复提交
 * 默认取header里面的userToken 加锁
 * field 可自定义
 * header已有字段 参考 HeaderInfo 信息
 * 也可选用 query方式
 * 可选用从request.getParameter(field)
 */
@Target(value = {ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PreventRepeatSubmit2 {
    /**
     * 是否取header中的字段
     * @return
     */
    public boolean header() default true;

    /**
     * 字段名称
     * @return
     */
    public String field() default "userToken";
}
