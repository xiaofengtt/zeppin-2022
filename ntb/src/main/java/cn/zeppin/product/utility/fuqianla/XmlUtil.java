package cn.zeppin.product.utility.fuqianla;

import java.io.StringReader;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class XmlUtil {

	/**
	 * 解析xml字符串
	 * 
	 * @param xmlDoc
	 *            调用wsdl返回的数据
	 * @return 请求结果，如果网络连接失败，返回null
	 */
	@SuppressWarnings("unchecked")
	public static JSONArray parseXML(String xmlDoc) {
		long start = System.currentTimeMillis();
		JSONArray resultArray = null;
		StringReader read = new StringReader(xmlDoc);
		// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		InputSource source = new InputSource(read);
		// 创建一个新的SAXBuilder
		SAXReader sb = new SAXReader();
		// List infoList = new ArrayList();
		try {
			// 通过输入源构造一个Document
			Document doc = sb.read(source);
			// 取的根元素
			Element root = doc.getRootElement();
			// 得到根元素所有子元素的集合
			// Namespace ns = root.getNamespace();
			List<Element> bodyList = root.elements();
			if (bodyList != null && bodyList.size() > 0) {
				Element body = (Element) bodyList.get(0);// Body
				List<Element> responses = body.elements();
				if (responses != null && responses.size() > 0) {
					Element response = (Element) responses.get(0);
					List<Element> results = response.elements();
					if (results != null && results.size() > 0) {
						resultArray = new JSONArray();
						for (int i = 0; i < results.size(); i++) {
							Element result = (Element) results.get(i);

							resultArray.add(convertToJSONOrText(result));
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println(end - start);
		return resultArray;
	}

	@SuppressWarnings("unchecked")
	private static Object convertToJSONOrText(Element item) {

		List<Element> elements = item.elements();

		if (elements.size() != 0) {
			JSONObject map = new JSONObject();
			for (Element element : elements) {
				int count = 0;
				String key = element.getName();
				// 有些接口，返回的数据是重名的
				if (map.containsKey(key)) {
					count = getSameKeyCount(map, key);
					if (0 != count) {
						if (1 == count) {
							key = element.getName() + "_@@_start_" + count;
						} else {
							key = element.getName() + "_@@_" + count;
						}
					}
				}

				map.put(key, convertToJSONOrText1(element));
			}
			map = reformatMap(map);

			return map;
		} else {
			return item.getText();
		}

	}

	@SuppressWarnings("unchecked")
	private static Object convertToJSONOrText1(Element item) {

		List<Element> elements = item.elements();

		if (elements.size() != 0) {
			JSONObject map = new JSONObject();
			for (Element element : elements) {
				int count = 0;
				String key = element.getName();
				// 有些接口，返回的数据是重名的
				if (map.containsKey(key)) {
					count = getSameKeyCount(map, key);
					if (0 != count) {
						if (1 == count) {
							key = element.getName() + "_@@_start_" + count;
						} else {
							key = element.getName() + "_@@_" + count;
						}
					}
				}

				map.put(key, convertToJSONOrText1(element));
			}
			map = reformatMap(map);

			return map;
		} else {
			return item.getText();
		}

	}

	/**
	 * map中以key为开始的条目数量
	 * 
	 * @param map
	 * @return
	 */
	private static int getSameKeyCount(JSONObject map, String key) {
		int count = 0;
		for (Map.Entry<String, Object> tmp : map.entrySet()) {
			if (tmp.getKey().startsWith(key)) {
				count++;
			}
		}
		return count;
	}

	/**
	 * 将带索引的key对应的数据下移一层
	 * 
	 * @param map
	 */
	private static JSONObject reformatMap(JSONObject map) {
		JSONObject result = new JSONObject();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			// 当前key还有后续带索引的数据
			String key = entry.getKey();

			if (map.containsKey(key + "_@@_start_1")) {
				JSONArray array = new JSONArray();

				array.add(map.get(key));
				array.add(map.get(key + "_@@_start_1"));

				int size = map.entrySet().size();
				for (int i = 2; i < size; i++) {
					Object object = map.get(key + "_@@_" + i);
					if (null != object) {
						array.add(object);
					}
				}

				result.put(key, array);

			} else if (-1 == key.indexOf("_@@_")) {
				result.put(key, entry.getValue());
			}
		}

		return result;
	}

}