package cn.zeppin.product.utility.ali;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * xml工具
 * @author Administrator
 *
 */
public class XmlUtil {
    
   /**
    * 字符串转Document
    * @param xmlStr
    * @return
    * @throws DocumentException
    */
    public Document getDocumentByStr(String xmlStr) throws DocumentException
    {
    	Document document = DocumentHelper.parseText(xmlStr);
    	return document;
    }
    
    /**

     * 将xml转换为bean

     * @param <T> 泛型

     * @param xml 要转换为bean的xml

     * @param cls bean对应的Class

     * @return xml转换为bean

     */

    @SuppressWarnings("unchecked")
	public static <T> T xmlToObject(String xml, Class<T> cls){

        XStream xstream = new XStream(new DomDriver());

        //xstream使用注解转换

        xstream.processAnnotations(cls);

        return (T) xstream.fromXML(xml);

    }
    
//    public static void main(String[] args) throws Exception {
//		SAXReader reader = new SAXReader();
//		//读取文件 转换成Document  
//        File file = new File(XmlUtil.class.getClassLoader().getResource("result.xml").getPath());
//        Document document = reader.read(file);  
//        String documentStr = document.asXML(); 
//        Alipay alipay = XmlUtil.xmlToObject(documentStr, Alipay.class);
//        System.out.println(alipay.getResponse().getAccountPageQueryResult().getAccountLogList().getAccountQueryAccountLogVO().getBalance());
//	}
}
