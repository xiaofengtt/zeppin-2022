package cn.zeppin.product.utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XMLUtils {
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 * @throws DocumentException 
	 */
	public static Map<String,Object> doXMLParse(String strxml) throws IOException, DocumentException {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map<String,Object> m = new HashMap<String, Object>();
		
		Document doc = DocumentHelper.parseText(strxml);
		Element root = doc.getRootElement();
		m = getChildrenText(root);
		
		return m;
	}
	
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getChildrenText(Element e) {
		Map<String, Object> map = new HashMap<String, Object>();
		Iterator<Element> iterator = e.elementIterator();  
        while(iterator.hasNext()){  
            Element ee = iterator.next();  
            if(ee.hasMixedContent()){
            	map.put(ee.getName(), getChildrenText(ee));
            }else{
            	map.put(ee.getName(), ee.getTextTrim());
            }
              
        }
		
		return map;
	}
}
