package com.makati.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.Xpp3Driver;

public class XmlUtil {

    /**
     * 将xml转换为bean
     * @param <T> 泛型
     * @param xml 要转换为bean的xml
     * @param cls bean对应的Class
     * @return xml转换为bean
     */
    public static <T> T xmlToObject(String xml, Class<T> cls) {
        XStream xstream = new XStream(new DomDriver());
        //xstream使用注解转换
        xstream.processAnnotations(cls);
        return (T) xstream.fromXML(xml);
    }

    /**
     * 对象转xml
     * @param obj
     * @return
     */
    public static String ObjectToXml(Object obj){
        XStream xstream = new XStream(new Xpp3Driver(new NoNameCoder()));
        xstream.autodetectAnnotations(true);//自动检测模式，默认
        String xml2 = xstream.toXML(obj);
        return xml2;
    }
}
