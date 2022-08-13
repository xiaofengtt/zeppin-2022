package com.whaty.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONString;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.StringUtils;


/** *//**
 * JSON工具类，反射的方式转换整个对象
 * @author 
 *
 */
public class JsonUtil  {

    private static JsonUtil instance = null;
    
    private final static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    
    private static String dateformat = null;
    

	public JsonUtil() {}
    
    /** *//**
     * 代理类时做的检查.返回应该检查的对象.
     * @param bean
     * @return
     */
    protected Object proxyCheck(Object bean) {
//        if(bean instanceof HibernateProxy) {
//            LazyInitializer lazyInitializer = ((HibernateProxy)bean).getHibernateLazyInitializer();
//            if(lazyInitializer.isUninitialized()) {
//                return lazyInitializer.getIdentifier();
//            }else{
//            	return lazyInitializer.getIdentifier();
//            	//当延迟加载的对象被初始化后
//            }
//        }
//        if(bean instanceof PersistentSet) {
//            return new String[] {}; //忽略hibernate one-to-many
//        }
        return bean;

    }

    static public String toJSONString(Object obj) throws JSONException {
        return toJSONString(obj, false);
    }
    
    static public String toJSONString(Object obj, boolean useClassConvert) throws JSONException {
        if(instance == null)
            instance = new JsonUtil();
        return instance.getJSONObject(obj, useClassConvert).toString();
    }

    private String getJSONArray(Object arrayObj, boolean useClassConvert) throws JSONException {
        
        if(arrayObj == null)
            return "null";
        
        arrayObj = proxyCheck(arrayObj);
        
        JSONArray jSONArray = new JSONArray();
        if(arrayObj instanceof Collection) {
            Iterator iterator = ((Collection)arrayObj).iterator();
            while(iterator.hasNext()) {
                Object rowObj = iterator.next();
                if(rowObj == null)
                    jSONArray.add(new JSONStringObject(null));
                else if(rowObj.getClass().isArray() || rowObj instanceof Collection)
                    jSONArray.add(getJSONArray(rowObj, useClassConvert));
                else
                    jSONArray.add(getJSONObject(rowObj, useClassConvert).toJSONString());
            }
        }
        if(arrayObj.getClass().isArray()) {
            int arrayLength = Array.getLength(arrayObj);
            for(int i = 0; i < arrayLength; i ++) {
                Object rowObj = Array.get(arrayObj, i);
                if(rowObj == null)
                    jSONArray.add(new JSONStringObject(null));
                else if(rowObj.getClass().isArray() || rowObj instanceof Collection)
                    jSONArray.add(getJSONArray(rowObj, useClassConvert));
                else
                    jSONArray.add(getJSONObject(rowObj, useClassConvert).toJSONString());
            }
        }
        return jSONArray.toString();
    }

    JSONStringObject getJSONObject(Object value, boolean useClassConvert) throws JSONException {

        //处理原始类型
        if (value == null)  {
            return new JSONStringObject("null");
        }
        value = proxyCheck(value);
        if (value instanceof JSONString)  {
            Object o;
            try  {
                o = ((JSONString)value).toJSONString();
            } catch (Exception e)  {
                throw new JSONException(e);
            }
            throw new JSONException("Bad value from toJSONString: " + o);
        }
        if (value instanceof Number)  {
            return new JSONStringObject(JSONUtils.numberToString((Number) value));
        }
        if (value instanceof Boolean || value instanceof JSONObject ||
                value instanceof JSONArray)  {
            return new JSONStringObject(value.toString());
        }
        if (value instanceof String){
//        	return new JSONStringObject(JavaScriptUtils.javaScriptEscape(value.toString()));
        	return new JSONStringObject(value.toString());
        }
        	
        if (value instanceof Map)  {
            
            JSONObject jSONObject = new JSONObject();

            Iterator iterator = ((Map)value).keySet().iterator();
            while(iterator.hasNext()) {
                String key = iterator.next().toString();
                Object valueObj = ((Map)value).get(key);
                jSONObject.put(key, getJSONObject(valueObj, useClassConvert).toJSONString());
            }
            return new JSONStringObject(jSONObject.toString());
        }
        
        //date
        if(value instanceof Date || value instanceof Timestamp || value instanceof java.sql.Date){
        	SimpleDateFormat sf = null;
        	if(dateformat == null){
        		sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        	}else{
        		sf = new SimpleDateFormat(dateformat);
        	}
//        	return new JSONStringObject(JavaScriptUtils.javaScriptEscape(sf.format(value)));
        	return new JSONStringObject(sf.format(value));
        	
        	
        }

        //class
        if(value instanceof Class)
//            return new JSONStringObject(JavaScriptUtils.javaScriptEscape(((Class)value).getSimpleName()));
        	return new JSONStringObject(((Class)value).getSimpleName());
        
        //数组
        if (value instanceof Collection || value.getClass().isArray())  {
            return new JSONStringObject(getJSONArray(proxyCheck(value), useClassConvert));
        }

        return reflectObject(value, useClassConvert);
    }//value.equals(null)

    private JSONStringObject reflectObject(Object bean, boolean useClassConvert) {
        JSONObject jSONObject = new JSONObject();

        Class klass = bean.getClass();
        Method[] methods = klass.getMethods();
        for (int i = 0; i < methods.length; i += 1)  {
            try  {
                Method method = methods[i];
                String name = method.getName();
                String key = "";
                if (name.startsWith("get"))  {
                    key = name.substring(3);
                } else if (name.startsWith("is"))  {
                    key = name.substring(2);
                }
                if (key.length() > 0 &&
                        Character.isUpperCase(key.charAt(0)) &&
                        method.getParameterTypes().length == 0)  {
                    if (key.length() == 1)  {
                        key = key.toLowerCase();
                    } else if (!Character.isUpperCase(key.charAt(1)))  {
                        key = key.substring(0, 1).toLowerCase() +
                            key.substring(1);
                    }
                    Object elementObj = method.invoke(bean, null);
                    if(!useClassConvert && elementObj instanceof Class)
                        continue;

                    jSONObject.put(key, getJSONObject(elementObj, useClassConvert).toJSONString());
                }
            } catch (Exception e)  {
                /**//* forget about it */
            }
        }
        return new JSONStringObject(jSONObject.toString());
    }

	public static String getDateformat() {
		return dateformat;
	}

	public static void setDateformat(String dateformat) {
		JsonUtil.dateformat = dateformat;
	}
	
	/**
	 * 把转入的键以及包令元素为对象数组的list 转成json适用的list
	 * @param key
	 * @param value
	 * @return
	 */
	public static List JsonConversion(String [] key,List obj){
		List elementList = new ArrayList();
		Iterator it = obj.iterator();
		while(it.hasNext()){
			Map map = new HashMap();
			Object [] value = (Object[])it.next();
			int num = value.length>key.length ? key.length :value.length;
			for(int i =0 ;i<num ;i++){
				map.put(key[i],value[i]);
			}
			elementList.add(map);
		}
		return elementList;
	}
	
	/**
	 * 把转入的键以及包令元素为对象数组的page 转成json适用的page
	 * @param key
	 * @param page
	 * @return
	 */
//	public static Page JsonConversion(String [] key,Page page){
//		List elementList = new ArrayList();
//		List obj = page.getItems();
//		elementList = JsonUtil.JsonConversion(key,obj);
//		Page p = new Page(elementList,page.getTotalCount(),page.getPageSize(),page.getStartIndex());
//		return p;
//	}
	
	
	/**
	    * 将单条记录的明细信息，转化为json数据
	    * row的数据格式为：[{key:value,key:value...}]
	    * @param row
	    * @return
	    */
	   public static String rowJson(String row){
		   if(StringUtils.isNotEmpty(row)){
			   if(row.startsWith("[")){
				   return "{\"success\":true,\"rows\":" + row  + "}";
			   }else{
				   return "{\"success\":true,\"rows\":[" + row  + "]}";
			   }
		   }else{
			   return null;
		   }
		   
	   }
	   
	   /**
	    * 将单条记录的明细信息，转化为json数据,为离线课件使用；
	    * row的数据格式为：[{key:value,key:value...}]
	    * @param row
	    * @return
	    */
	   public static String rowJson(String row,String callback){
		   if(StringUtils.isNotEmpty(row)){
			   if(row.startsWith("[")){
				   return "{\"success\":true,\"rows\":" + row  + "}";
			   }else{
				   return "{\"success\":true,\"rows\":[" + row  + "]}";
			   }
		   }else{
			   return null;
		   }
		   
	   }
	   
	   /**
	    * 构造错误信息
	    */
	   public static String rowErrorJson(String error){
		   return "{\"failure\":true,\"errorInfo\":\"" + error  + "\"}";
	   }
	   /**
	    * 构造错误信息,为离线课件使用
	    */
	   public static String rowErrorJson(String error,String callback){
		   return callback+"({\"failure\":true,\"errorInfo\":\"" + error  + "\"})";
	   }
	   
	   /**
	    * 构造成功信息
	    */
	   public static String rowSuccessJson(String info){
		   return "{\"success\":true,\"info\":\"" + info  + "\"}";
	   }
	   
	   /**
		 * 转化为分页所需要的json数据结构。
		 * root是一个对像数组
		 * [{...},{...}...]
		 * @param total
		 * @param root
		 * @return
		 */
	   public static String pageJson(int total,String root){
		   String json="";
		   if(total==0){
			   json="{}";
		   }
		   json="{\"total\":"+total+",\"root\":"+root+"}";
		   return json;
	   }
	   
	   
	   //=================================把json字符串转成java类型;
	   /**
	    *  把json字符串转换成为java对象;
	    *  字符串是符合json规则的;
	    *  {"...":"...","...":"..."}
	    */
	   public static Object fromStrToObject(String str,Class clazz,Map classMap){
		   JSONObject json = JSONObject.fromObject(str);
		   Object bean = null;
		   if(clazz == null){
			   bean = JSONObject.toBean(json);
		   }else if(clazz != null){
			   if(classMap != null){
				   bean = JSONObject.toBean(json,clazz,classMap);
			   }else{
				   bean = JSONObject.toBean(json,clazz);
			   }
		   }
		   return bean;
	   }
	   
	   
	   public static List fromStrToList(String str, Class clazz, Map classMap) {

		JSONArray array = JSONArray.fromObject(str);
		List list = new ArrayList();
		for (int i = 0; i < array.size(); i++) {
			JSONObject obj = array.getJSONObject(i);
			Object bean = null;
			if (clazz == null) {
				bean = JSONObject.toBean(obj);
			} else if (clazz != null) {
				if (classMap != null) {
					bean = JSONObject.toBean(obj, clazz, classMap);
				} else {
					bean = JSONObject.toBean(obj, clazz);
				}
			}
			list.add(bean);
		}
		return list;
	}
	   
}
