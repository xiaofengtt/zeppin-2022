package com.whaty.platform.entity.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author liweixin 
 *
 */
public class JsonConversionUtil {
	
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
	 * 把包令元素为对象数组的list 转成json适用的list
	 * @param key
	 * @param value
	 * @return
	 */
	public static List JsonConversion(String name,List obj){
		List elementList = new ArrayList();
		Iterator it = obj.iterator();
		List list = new ArrayList();
		while(it.hasNext()){
			Object [] value = (Object[])it.next();
			list = Arrays.asList(value);
			elementList.add(list);
		}
		return elementList;
	}
}
