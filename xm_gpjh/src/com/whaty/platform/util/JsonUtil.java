package com.whaty.platform.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.sql.Clob;
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

import org.hibernate.collection.PersistentSet;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.springframework.web.util.JavaScriptUtils;

import com.whaty.platform.entity.util.ColumnConfig;
import com.whaty.platform.entity.util.Page;

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
        if(bean instanceof HibernateProxy) {
            LazyInitializer lazyInitializer = ((HibernateProxy)bean).getHibernateLazyInitializer();
            if(lazyInitializer.isUninitialized()) {
                return lazyInitializer.getIdentifier();
            }else{
            	return lazyInitializer.getIdentifier();
            	//当延迟加载的对象被初始化后
            }
        }
        if(bean instanceof PersistentSet) {
            return new String[] {}; //忽略hibernate one-to-many
        }
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
        	return new JSONStringObject(JavaScriptUtils.javaScriptEscape(sf.format(value)));
        	
        	
        }

        //class
        if(value instanceof Class)
            return new JSONStringObject(JavaScriptUtils.javaScriptEscape(((Class)value).getSimpleName()));
        
        //数组
        if (value instanceof Collection || value.getClass().isArray())  {
            return new JSONStringObject(getJSONArray(proxyCheck(value), useClassConvert));
        }
//        if(value instanceof Clob){
//            return new JSONStringObject("");//zhaoyuxiao modified ,遇到一个clob类型错误，暂时的解决办法，
//        }
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
//System.out.println("elementObj is "+elementObj);
//System.out.println("useClassConvert is "+useClassConvert);
//System.out.println("key is "+key);
//System.out.println("name is "+name);
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
	public static Page JsonConversion(String [] key,Page page){
		List elementList = new ArrayList();
		List obj = page.getItems();
		elementList = JsonUtil.JsonConversion(key,obj);
		Page p = new Page(elementList,page.getTotalCount(),page.getPageSize(),page.getStartIndex());
		return p;
	}
	
	public static List ArrayToJsonObjects(List items, List<ColumnConfig> list) {
		List jsonObjectItems=new ArrayList();
		String[] columnString = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getDataIndex());
			columnString[i] = list.get(i).getDataIndex();
		}
		for (Object object : items) { 
			if(object.getClass().isArray()){
				JSONObject jsonObject = new JSONObject();
				Object[] valueObject = (Object[])object;
				for (int i = 0; i < columnString.length; i++) {
					String valueString = "";
					if (valueObject[i] instanceof String) {
						valueString = (String)valueObject[i];
						
					}else if(valueObject[i] instanceof Number){
						valueString = ((Number)valueObject[i]).toString();
						
					}else if(valueObject[i] instanceof Date || valueObject[i] instanceof Timestamp || valueObject[i] instanceof java.sql.Date){
			        	SimpleDateFormat sf = null;
			        	if(dateformat == null){
			        		sf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			        	}else{
			        		sf = new SimpleDateFormat(dateformat);
			        	}
			        	valueString = sf.format(valueObject[i]);
			        	
			        	
			        }
					jsonObject = getJSONObject(jsonObject, columnString[i], valueString);
				}
				jsonObjectItems.add(jsonObject);
			}else{
				break;
			}
		}
		if(jsonObjectItems.size() > 0){
			return jsonObjectItems;
		}
		return items;
	}

	private static JSONObject getJSONObject(JSONObject jsonObject, String columnString,
			String valueString) {
		if(columnString.indexOf(".") < 0){
			jsonObject.put(columnString, valueString);
		}else{
			JSONObject subJsonObject = new JSONObject();
			String key = columnString.substring(0, columnString.indexOf("."));
			if(!jsonObject.containsKey(key)){
				jsonObject.put(key, subJsonObject);
			}
			jsonObject.put(key,  getJSONObject(jsonObject.getJSONObject(key), columnString.substring(columnString.indexOf(".")+1), valueString));

		}
		return jsonObject;
	}
}
