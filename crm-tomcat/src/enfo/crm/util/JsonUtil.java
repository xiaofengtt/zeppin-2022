/*
 * 创建日期 2009-12-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author qwert
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class JsonUtil {

	public static String object2json(Object obj) {
		StringBuffer json = new StringBuffer();
		if (obj == null) {
			json.append("\"\"");
		} else if (
			obj instanceof String
				|| obj instanceof Integer
				|| obj instanceof Float
				|| obj instanceof Boolean
				|| obj instanceof Short
				|| obj instanceof Double
				|| obj instanceof Long
				|| obj instanceof BigDecimal
				|| obj instanceof BigInteger
				|| obj instanceof Byte
				|| obj instanceof Timestamp
				) {
			json.append("\"").append(string2json(obj.toString())).append("\"");
		}else if (obj instanceof List) {
			json.append(list2json((List) obj));
		} else if (obj instanceof Map) {
			json.append(map2json((Map) obj));
		}else if(obj instanceof Blob){
			json.append("\"").append(string2json("")).append("\"");
		}		
		return json.toString();
	}


	public static String list2json(List list) {
		StringBuffer json = new StringBuffer();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (int i=0;i<list.size();i++) {
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
			while(it.hasNext()){
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
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
				case '"' :
					sb.append("\\\"");
					break;
				case '\\' :
					sb.append("\\\\");
					break;
				case '\b' :
					sb.append("\\b");
					break;
				case '\f' :
					sb.append("\\f");
					break;
				case '\n' :
					sb.append("\\n");
					break;
				case '\r' :
					sb.append("\\r");
					break;
				case '\t' :
					sb.append("\\t");
					break;
				case '/' :
					sb.append("\\/");
					break;
				default :
					if (ch >= '\u0000' && ch <= '\u001F') {
						String ss = Integer.toHexString(ch);
						sb.append("\\u");
						for (int k = 0; k < 4 - ss.length(); k++) {
							sb.append('0');
						}
						sb.append(ss.toUpperCase());
					} else {
						sb.append(ch);
					}
			}
		}
		return sb.toString();
	}
	
	public static void main(String args[]){
		Map map = new HashMap();
		
		map.put("BOTTOM_FLAG","1");
		
		System.out.println(object2json(map));
		
		
	}
}

