package com.whaty.platform.entity.util;

import java.net.URL;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.soap.SOAPElement;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.SOAPHeaderElement;
import org.apache.axis.types.Schema;

import com.whaty.platform.GlobalProperties;


public class Message {
	
	private String smsUrl;
	private String smsAccount;
	private String smsPassword;
	private String namespace;
	
	public Message(){
		GlobalProperties props = new GlobalProperties();
		Map<String,String> map = props.getSmsGWSendPrameter();
		this.smsUrl = (String) map.get("url");
		this.smsAccount = (String) map.get("account");
		this.smsPassword = (String) map.get("password");
		this.namespace = (String)map.get("namespace");
	}
	public  int  getUserRegInfo(String strUserHash){
		int reStr = 0;
		Object o = null;
		try{
			  Service service = new Service();
		      Call call = null;
		      call = (Call) service.createCall();
		      call.setTargetEndpointAddress(new URL(this.getSmsUrl()));
		      call.setOperationName(new QName(this.getNamespace(),"GetUserRegInfo"));
		      call.addParameter(new QName(this.getNamespace(),"strUserHash"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.setReturnType(XMLType.XSD_SCHEMA);
		     // call.setUseSOAPAction(true);
		      call.setSOAPActionURI("http://tempuri.org/GetUserRegInfo");
		      o = call.invoke(new Object[]{strUserHash});
		      Schema s = (Schema)o;
		      MessageElement[] me = s.get_any();
		      int nLength = me[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().getLength();
		      int UserMaxCount = 1;
		      int UserCurrCount = 1;
		      for(int i = 0;i<nLength;i++){
		    	  if(me[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(i).getNodeName().equals("UserMaxCount"))
		    		  UserMaxCount = Integer.parseInt(me[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(i).getFirstChild().getNodeValue());
		    	  if(me[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(i).getNodeName().equals("UserCurrCount"))
		    		  UserCurrCount = Integer.parseInt(me[1].getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(i).getFirstChild().getNodeValue());
		    	  
		      }
		      reStr =UserMaxCount - UserCurrCount;
		}catch(Exception e){
			e.printStackTrace();
		}
		return reStr;
	}
	
	public  synchronized void  agetUserAccount(String strUserHash,String startDate,String endDate){
		String reStr = null;
		Object o = null;
		try{
			  Service service = new Service();
		      Call call = null;
		      call = (Call) service.createCall();
		      call.setTargetEndpointAddress(new URL(this.getSmsUrl()));
		      call.setOperationName(new QName(this.getNamespace(),"GetUserAccount"));
		      call.addParameter(new QName(this.getNamespace(),"strUserHash"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.addParameter(new QName(this.getNamespace(),"strUserDate"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.addParameter(new QName(this.getNamespace(),"strUserEndDate"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.setReturnType(XMLType.XSD_SCHEMA);
		      call.setUseSOAPAction(true);
		      call.setSOAPActionURI("http://tempuri.org/GetUserAccount");
		      SOAPHeaderElement element = new SOAPHeaderElement(new QName(this.getNamespace(),"ValidHeader"));
		      SOAPElement se = element.addChildElement("Username");
		      se.addTextNode(this.getSmsAccount());
		      se = element.addChildElement("Password");
		      se.addTextNode(this.getSmsPassword());
		      call.addHeader(element);
		      o = call.invoke(new Object[]{strUserHash,startDate,endDate});
		      Schema s = (Schema)o;
		      MessageElement[] me = s.get_any();
		      for(int i = 0;i<me.length;i++){
		    	  System.out.println(me[i] + "nieshengjie");
		      }
		}catch(Exception e){
			e.printStackTrace();
		}
		//return reStr;
	}
	public   synchronized int getMessageBegin(String strUserHash,Integer count){
		int reStatus = 0;
		Service service = null;
		Call call = null;
		try{
			  service = new Service();
		     
		      call = (Call) service.createCall();
		      call.setTargetEndpointAddress(new URL(this.getSmsUrl()));
		      call.setOperationName(new QName(this.getNamespace(),"SetMessageBegin"));
		      call.addParameter(new QName(this.getNamespace(),"strUserHash"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.addParameter(new QName(this.getNamespace(),"strCount"), XMLType.XSD_INT, ParameterMode.IN);
		      call.setReturnType(XMLType.XSD_INT);
		      call.setUseSOAPAction(true);
		      call.setSOAPActionURI("http://tempuri.org/SetMessageBegin");
		      SOAPHeaderElement element = new SOAPHeaderElement(new QName(this.getNamespace(),"ValidHeader"));
		      SOAPElement se = element.addChildElement("Username");
		      se.addTextNode(this.getSmsAccount());
		      se = element.addChildElement("Password");
		      se.addTextNode(this.getSmsPassword());
		      call.addHeader(element);
		      reStatus = ((Integer)call.invoke(new Object[]{strUserHash,count})).intValue();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			service = null;
			call = null;
		}
		return reStatus;
	}
	
	public synchronized boolean getMessage(String strUserHash,String strTarPhone,String strMessage){
		boolean reBool = false;
		Service service = null;
		Call call = null;
		try{
			  service = new Service();
		      call = (Call) service.createCall();
		      call.setTargetEndpointAddress(new URL(this.getSmsUrl()));
		      call.setOperationName(new QName(this.getNamespace(),"SetMessage"));
		      call.addParameter(new QName(this.getNamespace(),"strUserHash"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.addParameter(new QName(this.getNamespace(),"strTarPhone"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.addParameter(new QName(this.getNamespace(),"strMessage"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.setReturnType(XMLType.XSD_BOOLEAN);
		      call.setUseSOAPAction(true);
		      call.setSOAPActionURI("http://tempuri.org/SetMessage");
		      SOAPHeaderElement element = new SOAPHeaderElement(new QName(this.getNamespace(),"ValidHeader"));
		      SOAPElement se = element.addChildElement("Username");
		      se.addTextNode(this.getSmsAccount());
		      se = element.addChildElement("Password");
		      se.addTextNode(this.getSmsPassword());
		      call.addHeader(element);
		      reBool = ((Boolean)call.invoke(new Object[]{strUserHash,strTarPhone,strMessage})).booleanValue();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			service = null;
			call = null;
		}
		return reBool;
	}
	
	public synchronized int  getMessageEnd(String strUserHash){
		int  reStatus = 1;
		 Service service = null;
		 Call call = null;
		try{
			  service = new Service();
		      call = (Call) service.createCall();
		      call.setTargetEndpointAddress(new URL(this.getSmsUrl()));
		      call.setOperationName(new QName(this.getNamespace(),"SetMessageEnd"));
		      call.addParameter(new QName(this.getNamespace(),"strUserHash"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.addParameter(new QName(this.getNamespace(),"strPDate"), XMLType.XSD_STRING, ParameterMode.IN);
		      call.setReturnType(XMLType.XSD_INT);
		      call.setUseSOAPAction(true);
		      call.setSOAPActionURI("http://tempuri.org/SetMessageEnd");
		      SOAPHeaderElement element = new SOAPHeaderElement(new QName(this.getNamespace(),"ValidHeader"));
		      SOAPElement se = element.addChildElement("Username");
		      se.addTextNode(this.getSmsAccount());
		      se = element.addChildElement("Password");
		      se.addTextNode(this.getSmsPassword());
		      call.addHeader(element);
		      reStatus = ((Integer)call.invoke(new Object[]{strUserHash,""})).intValue();
		     
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			service = null;
			call = null;
		}
		return reStatus;
	}
	public String getSmsUrl() {
		return smsUrl;
	}
	public void setSmsUrl(String smsUrl) {
		this.smsUrl = smsUrl;
	}
	public String getSmsAccount() {
		return smsAccount;
	}
	public void setSmsAccount(String smsAccount) {
		this.smsAccount = smsAccount;
	}
	public String getSmsPassword() {
		return smsPassword;
	}
	public void setSmsPassword(String smsPassword) {
		this.smsPassword = smsPassword;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
}
