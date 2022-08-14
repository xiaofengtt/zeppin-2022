package com.enfo.webservice.client;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import com.sap.webservice.stub.ZWEBSERVICE1ServiceStub;
import com.sap.webservice.stub.ZWEBSERVICE2ServiceStub;



public class SAPClient {
	/**
	 * 客户信息通讯
	 * @param kunnr
	 * @param name
	 * @return
	 * @throws RemoteException
	 */
	public static String sendCustomerInfo(String kunnr,String name) throws RemoteException{
		/*ZWEBSERVICE1ServiceStub zw = new ZWEBSERVICE1ServiceStub();
		ZWEBSERVICE1ServiceStub.ZbapiCustomerCreate zwCreate = new ZWEBSERVICE1ServiceStub.ZbapiCustomerCreate();
		ZWEBSERVICE1ServiceStub.Char16 kunnr2 = new ZWEBSERVICE1ServiceStub.Char16();
		ZWEBSERVICE1ServiceStub.Char40 name2 = new ZWEBSERVICE1ServiceStub.Char40();
		
		kunnr2.setChar16(kunnr);
		name2.setChar40(name);
		zwCreate.setKunnr(kunnr2);
		zwCreate.setName(name2);*/
		/*
		ZWS02ServiceStub zw02 = new ZWS02ServiceStub();
		ZWS02ServiceStub.ZBAPI_CUSTOMER_CREATE zw02Create = new ZWS02ServiceStub.ZBAPI_CUSTOMER_CREATE();
		ZWS02ServiceStub.Char16 kunnr2 = new ZWS02ServiceStub.Char16();
		ZWS02ServiceStub.Char40 name2 = new ZWS02ServiceStub.Char40();
		
		kunnr2.setChar16(kunnr);
		name2.setChar40(name);
		zw02Create.setKUNNR(kunnr2);
		zw02Create.setNAME(name2);
		
		String result = zw02.ZBAPI_CUSTOMER_CREATE(zw02Create).getREKUNNR().getChar16();*/
		
		ZWEBSERVICE1ServiceStub stub = new ZWEBSERVICE1ServiceStub();
		ZWEBSERVICE1ServiceStub.ZbapiCustomerCreate customerCreate = new ZWEBSERVICE1ServiceStub.ZbapiCustomerCreate();
		ZWEBSERVICE1ServiceStub.Char16 kunnr_sap = new ZWEBSERVICE1ServiceStub.Char16();
		ZWEBSERVICE1ServiceStub.Char40 name_sap = new ZWEBSERVICE1ServiceStub.Char40();
		//ZWEBSERVICE1Stub stub = new ZWEBSERVICE1Stub();
		//ZWEBSERVICE1Stub.ZbapiCustomerCreate customerCreate = new ZWEBSERVICE1Stub.ZbapiCustomerCreate();
		//ZWEBSERVICE1Stub.Char16 kunnr_sap = new ZWEBSERVICE1Stub.Char16();
		//ZWEBSERVICE1Stub.Char40 name_sap = new ZWEBSERVICE1Stub.Char40();
		
		kunnr_sap.setChar16(kunnr);
		name_sap.setChar40(name);
		customerCreate.setKunnr(kunnr_sap);
		customerCreate.setName(name_sap);
		
		String result = stub.ZbapiCustomerCreate(customerCreate).getRekunnr().getChar16();		
		return result;
	}
	/**
	 * 项目信息通讯
	 * @param prctrName
	 * @param profitCtr
	 * @return
	 * @throws RemoteException
	 */
	public static String sentProjectInfo(String prctrName,String profitCtr) throws RemoteException{
		String result = "";
		/*ZWEBSERVICE2ServiceStub zw2 = new ZWEBSERVICE2ServiceStub();
		ZWEBSERVICE2ServiceStub.ZbapiProfitcenterCreate zw2Create = new ZWEBSERVICE2ServiceStub.ZbapiProfitcenterCreate();
		ZWEBSERVICE2ServiceStub.Char10 profitCtr2 = new ZWEBSERVICE2ServiceStub.Char10();
		ZWEBSERVICE2ServiceStub.Char20 prctrName2 = new ZWEBSERVICE2ServiceStub.Char20();
		
		prctrName2.setChar20(prctrName);
		profitCtr2.setChar10(profitCtr);
		zw2Create.setPrctrName(prctrName2);
		zw2Create.setProfitCtr(profitCtr2);
		
		result = zw2.ZbapiProfitcenterCreate(zw2Create).getProfitcenter().getChar10();*/
		/*ZWS01ServiceStub zw01 = new ZWS01ServiceStub();
		ZWS01ServiceStub.ZBAPI_PROFITCENTER_CREATE zw01Create = new ZWS01ServiceStub.ZBAPI_PROFITCENTER_CREATE();
		ZWS01ServiceStub.Char10 profitCtr2 = new ZWS01ServiceStub.Char10();
		ZWS01ServiceStub.Char20 prctrName2 = new ZWS01ServiceStub.Char20();
		
		profitCtr2.setChar10(profitCtr);
		prctrName2.setChar20(prctrName);
		zw01Create.setPROFIT_CTR(profitCtr2);
		zw01Create.setPRCTR_NAME(prctrName2);*/
		ZWEBSERVICE2ServiceStub stub = new ZWEBSERVICE2ServiceStub();
		ZWEBSERVICE2ServiceStub.ZbapiProfitcenterCreate proCreate = new ZWEBSERVICE2ServiceStub.ZbapiProfitcenterCreate();
		ZWEBSERVICE2ServiceStub.Char20 prctrName_sap = new ZWEBSERVICE2ServiceStub.Char20();
		ZWEBSERVICE2ServiceStub.Char10 profitCtr_sap = new ZWEBSERVICE2ServiceStub.Char10();
		
		prctrName_sap.setChar20(prctrName);
		profitCtr_sap.setChar10(profitCtr);
		proCreate.setPrctrName(prctrName_sap);
		proCreate.setProfitCtr(profitCtr_sap);		
		
		result = stub.ZbapiProfitcenterCreate(proCreate).getProfitcenter().getChar10();
		
		return result;
	}
	/**
	 * 设置地址 CRM
	 * @param url
	 */
	public static void setCustUrl(String url){
		Constants.sap_customer_url = url;
	}
	/**
	 * 设置地址 Project
	 * @param url
	 */
	public static void setProjUrl(String url){
		Constants.sap_project_url = url;
	}
	/**
	 * 调试
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//SAPClient.setCustUrl("http://sapdev.huaao-trust.com:8000/sap/bc/srt/rfc/sap/zwebservice1/600/zwebservice1/zwebservice1");
		//SAPClient.setCustUrl("http://sapprd.huaao-trust.com:8000/sap/bc/srt/rfc/sap/zwebservice1/900/zwebservice1/zwebservice1");
		//String result = SAPClient.sendCustomerInfo("00000399","temp20101216");
		//SAPClient.setProjUrl("http://sapqas.huaao-trust.com:8000/sap/bc/srt/rfc/sap/zwebservice2/500/zwebservice2/zwebservice2?sap-language=zh&sap-user=java&sap-password=java123");
		SAPClient.setCustUrl("http://sapprd.huaao-trust.com:8000/sap/bc/srt/rfc/sap/zwebservice1/900/zwebservice1/zwebservice1?sap-language=zh&sap-user=java&sap-password=java123");
		SAPClient.setProjUrl("http://sapprd.huaao-trust.com:8000/sap/bc/srt/rfc/sap/zwebservice2/900/zwebservice2/zwebservice2?sap-language=zh&sap-user=java&sap-password=java123");
		String result2 = SAPClient.sentProjectInfo("华澳长兴8号","HA_SATC008");
		//String result = SAPClient.sendCustomerInfo("00000403","张三");
		//System.out.println("-----------Test:"+result);
		System.out.println("-----------Test2:"+result2);
	}
}
