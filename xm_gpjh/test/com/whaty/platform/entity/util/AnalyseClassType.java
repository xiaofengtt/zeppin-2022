package com.whaty.platform.entity.util;

import java.lang.reflect.Method;

import com.whaty.platform.entity.exception.EntityException;

public class AnalyseClassType {
	public static Class getClassType(Class clazz, String expression){
		
		String[] fields;
		fields = expression.split("\\.");
		Class c = clazz;
		for (int i = 0; i < fields.length; i++) {
			try {
				Method m = c.getMethod("get"+fields[i].substring(0, 1).toUpperCase()+fields[i].substring(1), null);
				c = m.getReturnType();
			} catch (SecurityException e) {
				throw new RuntimeException("SecurityException--"+fields[i]);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException("没有这个成员变量--"+fields[i]);
			}
		}
		return c;
	}

}
