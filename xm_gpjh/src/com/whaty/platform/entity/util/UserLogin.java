package com.whaty.platform.entity.util;

import java.net.InetAddress;
import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.soap.SOAPElement;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.SOAPHeaderElement;

import com.whaty.platform.GlobalProperties;

public class UserLogin {
	public static MD5 md5 = new MD5();
	
	public static String getStrUserHash(){
		GlobalProperties props = new GlobalProperties();
		Map<String,String> map = props.getSmsGWSendPrameter();
		String smsUrl = (String) map.get("url");
		String smsAccount = (String) map.get("account");
		String smsPassword = (String) map.get("password");
		String namespace = (String)map.get("namespace");
		String strUserHash = "";
		try{
			  Service service = new Service();
		      Call call = null;
		      call = (Call) service.createCall();
		      call.setTargetEndpointAddress(new URL(smsUrl));
		      call.setOperationName(new QName(namespace,"GetUserLogin"));
		      call.addParameter(new QName(namespace,"strUserName"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.addParameter(new QName(namespace,"strUserPass"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.addParameter(new QName(namespace,"strUserIP"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.setReturnType(XMLType.XSD_STRING);
		      call.setUseSOAPAction(true);
		      call.setSOAPActionURI("http://tempuri.org/GetUserLogin");
		      SOAPHeaderElement element = new SOAPHeaderElement(new QName(namespace,"ValidHeader"));
		      SOAPElement se = element.addChildElement("Username");
		      se.addTextNode(smsAccount);
		      se = element.addChildElement("Password");
		      se.addTextNode(smsPassword);
		      call.addHeader(element);
		      InetAddress address = InetAddress.getLocalHost();   
		      strUserHash = (String)call.invoke(new Object[]{smsAccount,md5.getMD5ofStr(smsPassword),address.getHostAddress()});
		      /*call.setOperationName(new QName(nameSpace,"SetMessageBegin"));
		      call.addParameter(new QName(nameSpace,"strUserHash"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.addParameter(new QName(nameSpace,"strCount"), XMLType.XSD_INT, ParameterMode.IN);
		      call.setReturnType(XMLType.XSD_INT);
		      int reStr = (Integer)call.invoke(new Object[]{strUserHash,1});
		      System.out.println(reStr);*/
		}catch(Exception e){
			e.printStackTrace();
		}
		return strUserHash;
	}
}
