/*
 * 创建日期 2009-12-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.tools;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	public static String get3LevelMenuJson(List list) {
		StringBuffer json = new StringBuffer();
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (((Map) list.get(i)).get("menuId").toString().length() == 3) {
					list1.add(list.get(i));
				} else if (((Map) list.get(i)).get("menuId").toString()
						.length() == 5) {
					list2.add(list.get(i));
				} else {
					list3.add(list.get(i));
				}
			}
		}
		int flag2 = 0;
		if (list2 == null || list2.size() == 0) {
			Iterator it3 = list3.iterator();
			while (it3.hasNext()) {
				Map map3 = (Map) it3.next();
				json.append(map2json(map3));
				if (!map2json(map3).equals("")) {
					json.append("}");
				}
			}
			flag2++;
		} else if (list1 == null || list1.size() == 0) {
			Iterator it2 = list2.iterator();
			while (it2.hasNext()) {
				Map map2 = (Map) it2.next();
				json.append(map2json(map2) + ",");
				if (!get3rdJson(map2, list3).equals("")) {
					json.setCharAt(json.length() - 2, ' ');
				}
				json.append(get3rdJson(map2, list3));
			}
			flag2++;
		}
		Iterator it1 = list1.iterator();
		while (it1.hasNext()) {
			Map map1 = (Map) it1.next();
			json.append(map2json(map1));
			json.setCharAt(json.length() - 1, ',');
			String menuId1 = map1.get("menuId").toString();
			Iterator it2 = list2.iterator();
			int flag = 0;
			flag2 = 0;
			while (it2.hasNext()) {
				Map map2 = (Map) it2.next();
				if (map2.get("menuId").toString().startsWith(menuId1)) {
					if (flag == 0) {
						json.append("\"children\":[");
						flag++;
					}
					json.append(map2json(map2) + ",");
					if (!get3rdJson(map2, list3).equals("")) {
						json.setCharAt(json.length() - 2, ' ');
					}
					json.append(get3rdJson(map2, list3));
				}
				flag2++;
			}
			if (flag2 == list2.size()) {
				json.setCharAt(json.length() - 1, ' ');
			}
			if (flag != 0) {
				json.append("]},");
			} else {
				json.setCharAt(json.length() - 1, '}');
				json.append(",");
			}
		}
		if (flag2 == list2.size()) {
			json.setCharAt(json.length() - 1, ' ');
		}
		if (list1 == null || list1.size() == 0) {
			json.setCharAt(json.length() - 1, ' ');
		}
		if (json.toString().equals(" ")) {
			json.append("[");
		}
		json.append("]");
		return json.toString();
	}

	public static String get3rdJson(Map map, List list) {
		StringBuffer sb = new StringBuffer();
		// sb.append("{");
		String menuId = map.get("menuId").toString();
		Iterator it = list.iterator();
		int flag = 0;
		while (it.hasNext()) {
			Map map3 = (Map) it.next();
			if (map3.get("menuId").toString().startsWith(menuId)) {
				if (flag == 0) {
					sb.append("\"children\":[");
					flag++;
				}
				sb.append(map2json(map3));
				sb.append(",");
			}
		}
		if (sb.toString().equals("")) {// equals("{")) {
			return "";
		} else {
			sb.setCharAt(sb.length() - 1, ']');
			sb.append("},");
		}
		return sb.toString();
	}

	public static String object2json(Object obj) {
		StringBuffer json = new StringBuffer();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof String || obj instanceof Integer
				|| obj instanceof Float || obj instanceof Boolean
				|| obj instanceof Short || obj instanceof Double
				|| obj instanceof Long || obj instanceof BigDecimal
				|| obj instanceof BigInteger || obj instanceof Byte
				|| obj instanceof Timestamp) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		} else if (obj instanceof List) {
			json.append(list2json((List) obj));
		} else if (obj instanceof Map) {
			json.append(map2json((Map) obj));
		} else if (obj instanceof Blob) {
			json.append("\"").append(string2json("")).append("\"");
		}
		return json.toString();
	}

	public static String list2json(List list) {
		StringBuffer json = new StringBuffer();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				json.append(object2json(list.get(i)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	public static String map2json(Map map) {
		StringBuffer json = new StringBuffer();
		Set keySet = map.keySet();
		Iterator it = keySet.iterator();
		String key = "";
		json.append("{");
		if (map != null && map.size() > 0) {
			while (it.hasNext()) {
				key = it.next().toString();
				json.append(object2json(key));
				json.append(":");
				json.append(object2json(map.get(key)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}

	public static String string2json(String s) {
		if (s == null)
			return "";
		return s;
	}

	public static String mapToJson(Map map) {
		JSONObject json = JSONObject.fromObject(map);
		return json.toString();
	}

	// MapForString
	public static Map parseJSON2Map(String jsonStr) {
		if (jsonStr == null || jsonStr.length() == 0) {
			return null;
		}

		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map valueMap = new HashMap();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueMap.put(key, value);
		}

		return valueMap;
	}

	/**
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static java.util.Properties parseJSON2Proper(String jsonStr) {
		if (jsonStr == null || jsonStr.length() == 0) {
			return null;
		}

		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		java.util.Properties valueProperty = new java.util.Properties();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			valueProperty.put(key, value);
		}

		return valueProperty;
	}

	public static Hashtable parseJSON2Hashtable(String jsonStr) {
		if (jsonStr == null || jsonStr.length() == 0) {
			return null;
		} else {
			jsonStr = "[" + jsonStr + "]";
		}

		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		JSONObject jsonObject;
		java.util.Properties pojoValue;
		Hashtable table = new Hashtable();
		Iterator keyIter = null;
		String key;

		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			keyIter = jsonObject.keys();

			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				pojoValue = parseJSON2Proper(jsonObject.get(key).toString());
				table.put(key, pojoValue);
			}
		}

		return table;
	}
	
	public static ArrayList parseJSON2ArrayList(String jsonStr) {
		if (jsonStr == null || jsonStr.length() == 0) {
			return null;
		} else if (!jsonStr.startsWith("[")){
			jsonStr = "[" + jsonStr + "]";
		}

		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		JSONObject jsonObject;
		//java.util.Properties pojoValue;
		Map map=null;
		ArrayList list = new ArrayList();
		Iterator keyIter = null;
		String key;
		String value;

		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			keyIter = jsonObject.keys();
			map=new HashMap();
			while (keyIter.hasNext()) {
				key = (String) keyIter.next();
				value = jsonObject.get(key).toString();
				map.put(key,jsonObject.get(key).toString());
				//pojoValue = parseJSON2Proper(jsonObject.get(key).toString());
			}
			list.add(map);
		}
		return list;
	}
	
	
}

