package cn.zeppin.product.utility;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XMLUtils {
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, Object> doXMLParse(String strxml)
			throws IOException, DocumentException {
//		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map<String, Object> m = new HashMap<String, Object>();

		Document doc = DocumentHelper.parseText(strxml);
		Element root = doc.getRootElement();
		m = getChildrenText(root);

		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getChildrenText(Element e) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<Element> iterator = e.elementIterator();
		while (iterator.hasNext()) {
			Element ee = iterator.next();
			if (ee.hasMixedContent()) {
				map.put(ee.getName(), getChildrenText(ee));
			} else {
				map.put(ee.getName(), ee.getTextTrim());
			}

		}

		return map;
	}

	/**
	 * map转xml
	 * @param dataMap
	 * @return
	 */
	public static String converter(Map<String, Object> dataMap) {
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<xml>");
		Set<String> objSet = dataMap.keySet();
		for (String key : objSet) {
			if (key == null) {
				continue;
			}
			strBuilder.append("<").append(key).append(">");
			Object value = dataMap.get(key);
			strBuilder.append(coverter(value));
			strBuilder.append("</").append(key).append(">");
		}
		strBuilder.append("</xml>");
		return strBuilder.toString();
	}

	public static String coverter(Object[] objects) {
		StringBuilder strBuilder = new StringBuilder();
		for (Object obj : objects) {
			strBuilder.append("<item className=")
					.append(obj.getClass().getName()).append(">");
			strBuilder.append(coverter(obj));
			strBuilder.append("</item>");
		}
		return strBuilder.toString();
	}

	public static String coverter(Collection<?> objects) {
		StringBuilder strBuilder = new StringBuilder();
		for (Object obj : objects) {
			strBuilder.append("<item className=")
					.append(obj.getClass().getName()).append(">");
			strBuilder.append(coverter(obj));
			strBuilder.append("</item>");
		}
		return strBuilder.toString();
	}

	public static String coverter(Object object) {
		if (object instanceof Object[]) {
			return coverter((Object[]) object);
		}
		if (object instanceof Collection) {
			return coverter((Collection<?>) object);
		}
		StringBuilder strBuilder = new StringBuilder();
		if (isObject(object)) {
			Class<? extends Object> clz = object.getClass();
			Field[] fields = clz.getDeclaredFields();

			for (Field field : fields) {
				field.setAccessible(true);
				String fieldName = field.getName();
				Object value = null;
				try {
					value = field.get(object);
				} catch (IllegalArgumentException e) {
					continue;
				} catch (IllegalAccessException e) {
					continue;
				}
				strBuilder.append("<").append(fieldName)
						.append(" className=\"")
						.append(value.getClass().getName()).append("\">");
				if (isObject(value)) {
					strBuilder.append(coverter(value));
				} else {
					strBuilder.append(value.toString() + "");
				}
				strBuilder.append("</").append(fieldName).append(">");
			}
		} else if (object == null) {
			strBuilder.append("null");
		} else {
			strBuilder.append(object.toString() + "");
		}
		return strBuilder.toString();
	}

	/**
	 * Checks if is object.
	 *
	 * @param obj
	 *            the obj
	 *
	 * @return true, if is object
	 */
	private static boolean isObject(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof String) {
			return false;
		}
		if (obj instanceof Integer) {
			return false;
		}
		if (obj instanceof Double) {
			return false;
		}
		if (obj instanceof Float) {
			return false;
		}
		if (obj instanceof Byte) {
			return false;
		}
		if (obj instanceof Long) {
			return false;
		}
		if (obj instanceof Character) {
			return false;
		}
		if (obj instanceof Short) {
			return false;
		}
		if (obj instanceof Boolean) {
			return false;
		}
		return true;
	}
	
	/**
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters  请求参数
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getRequestXml(Map<String,Object> parameters){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
}
