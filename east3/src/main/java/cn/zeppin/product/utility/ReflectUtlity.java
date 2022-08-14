package cn.zeppin.product.utility;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtlity {
	
	/** 
	    * java反射bean的get方法 
	    *  
	    * @param objectClass 
	    * @param fieldName 
	    * @return 
	    */  
	   @SuppressWarnings({ "unchecked", "rawtypes" })  
	   public static Method getGetMethod(Class objectClass, String fieldName) {  
	       StringBuffer sb = new StringBuffer();  
	       sb.append("get");  
	       sb.append(fieldName.substring(0, 1).toUpperCase());  
	       sb.append(fieldName.substring(1));  
	       try {  
	           return objectClass.getMethod(sb.toString());  
	       } catch (Exception e) {  
	       }  
	       return null;  
	   }
	   
	   
	   /** 
	    * java反射bean的set方法 
	    *  
	    * @param objectClass 
	    * @param fieldName 
	    * @return 
	    */  
	   @SuppressWarnings({ "unchecked", "rawtypes" })  
	   public static Method getSetMethod(Class objectClass, String fieldName) {  
	       try {  
	           Class[] parameterTypes = new Class[1];  
	           Field field = objectClass.getDeclaredField(fieldName);  
	           parameterTypes[0] = field.getType();  
	           StringBuffer sb = new StringBuffer();  
	           sb.append("set");  
	           sb.append(fieldName.substring(0, 1).toUpperCase());  
	           sb.append(fieldName.substring(1));  
	           Method method = objectClass.getMethod(sb.toString(), parameterTypes);  
	           return method;  
	       } catch (Exception e) {  
	           e.printStackTrace();  
	           return null;  
	       }
	   } 
	   
	   /** 
	    * 执行set方法 
	    *  
	    * @param o 执行对象 
	    * @param fieldName 属性 
	    * @param value 值 
	    */  
	   public static void invokeSet(Object o, String fieldName, Object value) {  
	       Method method = getSetMethod(o.getClass(), fieldName);  
	       try {  
	           method.invoke(o, new Object[] { value });  
	       } catch (Exception e) {  
	           e.printStackTrace();  
	       }  
	   }
	   
	   /** 
	    * 执行get方法 
	    *  
	    * @param o 执行对象 
	    * @param fieldName 属性 
	    */  
	   public static Object invokeGet(Object o, String fieldName) {  
	       Method method = getGetMethod(o.getClass(), fieldName);  
	       try {  
	           return method.invoke(o, new Object[0]);  
	       } catch (Exception e) {  
	           e.printStackTrace();
	           return null;  
	       }
	   }
	   
	   /**
	    * 获取成员常量
	    * @param o
	    * @param field
	    * @return
	    */
	   public static Object getConst(Class<? extends Object> o,String field){
	        try {
	            return o.getField(field).get(null);
	        } catch (Exception e) {
	        	return null;
	        }
	    }
}

