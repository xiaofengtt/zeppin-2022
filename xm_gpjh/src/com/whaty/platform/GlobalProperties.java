/*
 * SystemProperties.java
 *
 * Created on 2004��11��26��, ����3:35
 */

package com.whaty.platform;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * ��ȡϵͳ����
 * @author  Administrator
 */
public class GlobalProperties {
	
	private Properties props;
    
    /** Creates a new instance of SystemProperties */
    public GlobalProperties() {
    	String propsFile = getClass().getResource("").toString();
    	String os = System.getProperty("os.name");
    	if(os.indexOf("Windows") >= 0)
    		propsFile = propsFile.substring(6);	//ȥ��file:/
    	if(os.indexOf("Linux") >= 0)
    		propsFile = propsFile.substring(5); //ȥ��file:
    	propsFile = propsFile.substring(0,propsFile.indexOf("classes")) + "hnsd.properties";
    	//System.out.println(propsFile);
    	props = new Properties();
    	try {
			props.load(new FileInputStream(propsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println("URL:" + props.get("smsgw_url"));
    }
    public GlobalProperties(String properties) {
    	String propsFile = getClass().getResource("").toString();
    	String os = System.getProperty("os.name");
    	if(os.indexOf("Windows") >= 0)
    		propsFile = propsFile.substring(6);	//ȥ��file:/
    	if(os.indexOf("Linux") >= 0)
    		propsFile = propsFile.substring(5); //ȥ��file:
    	propsFile = propsFile.substring(0,propsFile.indexOf("classes")) + properties;
    	props = new Properties();
    	try {
			props.load(new FileInputStream(propsFile));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    public static String getBasicEntityClass(String classname) {
        return null;
    }
    
    public static String getUserFactoryClass(java.lang.String classname) {
        return null;
    }
    
    public static String getInfoFactoryClass(java.lang.String classname) {
        return null;
    }
    
    public static String getResourceFactoryClass(java.lang.String classname) {
        return null;
    }
    
    public static String getAuthorizationFactoryClass(java.lang.String classname) {
        return null;
    }
    public static String getPrivilegeFactoryClass(String classname) {
        return null;
    }
    
   
    
    public static String getFactoryClass(String classname) {
        return null;
    }
    
    public Map<String,String> getSmsGWSendPrameter() {
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("url", props.getProperty("smsgw_url"));
    	map.put("account", props.getProperty("smsgw_account"));
    	map.put("password", props.getProperty("smsgw_password"));
    	map.put("namespace", props.getProperty("name_space"));
    	return map;
    }
    public Map<String,String> getMailSendPrameter() {
    	Map<String,String> map = new HashMap<String,String>();
    	map.put("mailServerHost", props.getProperty("mailServerHost"));
    	map.put("mailServerPort", props.getProperty("mailServerPort"));
    	map.put("password", props.getProperty("password"));
    	map.put("fromAddress", props.getProperty("fromAddress"));
    	map.put("charSet", props.getProperty("charSet"));
    	return map;
    }
    public static void main(String[] args) {
//    	new GlobalProperties();
    	String s = "houxuelong@whaty.com";
    	System.out.println(s.substring(0,s.indexOf("@")));
    }
}
