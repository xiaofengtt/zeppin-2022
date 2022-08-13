package com.zixueku.util;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * 类说明
 * 
 * @author Email: huangweidong@zeppin.cn
 * @version V1.0 创建时间：2015-2-9 下午2:51:11
 */
public class JSONUtil {
	private static GsonBuilder builder;
	static {
		builder = new GsonBuilder();
	}

	public static <T> T jsonToObject(String s, Class<T> c) {
		T obj = null;
		if (isNotNull(s)) {
			Gson gson = builder.create();
			obj = gson.fromJson(s, c);
		}
		return obj;
	}

	public static <T> T jsonToObject(String s, Object objType) {
		T obj = null;
		if (isNotNull(s)) {
			Gson gson = builder.create();
			Type typeClass = objType.getClass().getGenericSuperclass();
			obj = gson.fromJson(s, typeClass);
		}
		return obj;
	}

	public static Map<Object, Object> jsonToMap(String s) {
		Map<Object, Object> map = null;
		if (isNotNull(s)) {
			map = new HashMap<Object, Object>();
			Type type = new TypeToken<Map<Object, Object>>() {
			}.getType(); // 指定集合对象属性
			Gson gson = builder.create();
			map = gson.fromJson(s, type);
		}
		return map;
	}

	public static List<?> jsonToList(String s) {
		List<?> list = null;
		if (isNotNull(s)) {
			list = new ArrayList<Object>();
			Type type = new TypeToken<List<Object>>() {
			}.getType(); // 指定集合对象属性
			Gson gson = builder.create();
			list = gson.fromJson(s, type);
		}
		return list;
	}

	public static List<?> jsonToList(String json, Class<?> type) {
		List<Object> target = null;
		if (isNotNull(json)) {
			target = new ArrayList<Object>();
			Gson gson = builder.create();
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonElement = jsonParser.parse(json); // 将json字符串转换成JsonElement
			JsonArray jsonArray = jsonElement.getAsJsonArray(); // 将JsonElement转换成JsonArray
			Iterator<?> it = jsonArray.iterator(); // Iterator处理
			while (it.hasNext()) { // 循环
				jsonElement = (JsonElement) it.next(); // 提取JsonElement
				json = jsonElement.toString(); // JsonElement转换成String
				Object object = gson.fromJson(json, type); // String转化成JavaBean
				target.add(object); // 加入List
			}
		}
		return target;
	}

	public static String objectToJson(Object object) {
		String s = new String();
		Gson gson = builder.create();
		if (object != null) {
			s = gson.toJson(object);
		}
		return s;
	}

	public static String getRecords(String src) throws IOException {
		String records = null;
		// ActionResult actionResult = jsonToObject(src,ActionResult.class);
		// records = objectToJson(actionResult.getRecords());
		return records;
	}

	protected static Boolean isNotNull(String s) {
		Boolean mark = true;
		if ("".equals(s.trim())) {
			mark = false;
		}
		return mark;
	}

}