package com.zeppin.util.sms;

import java.io.File;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import template.HttpClientUtil;
import template.KeyedDigestMD5;

public class SendSms4Zzx {

	String path = null;
	String LoginName = null;
	String spPassword = null;
	String urlzzx = null;
	String mobile = null; 
	String content = null;
	String SPNumber = null;
	String SmsTempletID = null;
	{

		path = "/home/www/webapps/wn_gpjh/WEB-INF/config/sms.xml"; 
//		path = "E:\\workspace2015\\wn_gpjh\\WebRoot\\WEB-INF\\config\\sms.xml";
		// path =
		// "E:\\apache-tomcat-6.0.32\\webapps\\mw_gpjh\\WEB-INF\\config\\sms.xml";
		// System.out.println(path);
		// path = path.substring(0, path.indexOf("/WEB-INF/"))
		// + "/WEB-INF/config/sms.xml";
		path = path.replaceAll("%20", " ");

		DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory
				.newInstance();
		Element root = null;

		try {
			dBuilderFactory.setIgnoringElementContentWhitespace(true);
			DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();

			Document document = dBuilder.parse(new File(path));
			root = document.getDocumentElement();

			LoginName = document.getElementsByTagName("LoginName").item(0)
					.getFirstChild().getNodeValue();
			//密码由DESUtil加密过，需要解密后使用。
			spPassword = root.getElementsByTagName("spPassword").item(0)
					.getFirstChild().getNodeValue();
			urlzzx = document.getElementsByTagName("urlzzx").item(0)
					.getFirstChild().getNodeValue();
			SPNumber = document.getElementsByTagName("SPNumber").item(0)
					.getFirstChild().getNodeValue();
			SmsTempletID = document.getElementsByTagName("SmsTempletID").item(0)
					.getFirstChild().getNodeValue();

			// System.out.println(uid + "  " + pwd + "  " + url);

		} catch (Exception e) {
			// String eString = e.getMessage();
			e.printStackTrace();

		}

	}
	
	/**
	 * 初始化
	 * @param mobile
	 * @param content
	 */
	public SendSms4Zzx(String mobile, String content) {
		this.mobile = "86"+mobile;
		this.content = content;
	}
	
	/**
	 * 发送
	 * @return
	 * @throws JSONException 
	 */
	public String sendSms() throws JSONException{
		String code = "";
		try {
			String Parameter = "{'content':'"+content+"'}";
			String Timestamp = "" + System.currentTimeMillis();

			TreeMap<String, String> inmap = new TreeMap<String, String>();
			inmap.put("LoginName", LoginName);
			inmap.put("Parameter", Parameter);
			inmap.put("SPNumber", SPNumber);
			inmap.put("SmsTempletID", SmsTempletID);
			inmap.put("Timestamp", Timestamp);
			inmap.put("UserNumber", mobile);

			System.out.println(inmap.entrySet());

			String verifyReq = KeyedDigestMD5.getKeyedDigestGBKWithMap(inmap, spPassword);

			String verify = verifyReq;

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("LoginName", LoginName);
			jsonObject.put("Parameter", Parameter);
			jsonObject.put("SmsTempletID", SmsTempletID);
			jsonObject.put("SPNumber", SPNumber);
			jsonObject.put("Timestamp", Timestamp);
			jsonObject.put("UserNumber", mobile);
			jsonObject.put("Verify", verify);
			String paraData = jsonObject.toString();

			System.out.println(paraData.toString());

			HttpClientUtil httpClient = HttpClientUtil.getInstance();

			try {
				String response = httpClient.getResponseBodyAsString(urlzzx, paraData);
				JSONObject js = new JSONObject(response);
				code = js.get("Result").toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return code;
	}
	
//	public static void main(String[] args) throws JSONException {
//		String time = Utils.getBJTime();
//		SendSms4Zzx sm = new SendSms4Zzx("18612033494", "【测试】测试短信发送,当前时间："+time);
//		String str = sm.sendSms();
//		System.out.println(str);
//		
////		String url = "http://fegzzx.zzx9.cn:8089/templet_submit";
////
////		String LoginName = "361";
////		String Parameter = "{'content':'【测试】您好，这个是超级模板测试。'}";
////		String SmsTempletID = "36112007201746346280508";
////		String SPNumber = "361";
////		String Timestamp = "" + System.currentTimeMillis();
////		String UserNumber = "8618612033494";
////		String spPassword = "361";
////
////		TreeMap<String, String> inmap = new TreeMap<String, String>();
////		inmap.put("LoginName", LoginName);
////		inmap.put("Parameter", Parameter);
////		inmap.put("SPNumber", SPNumber);
////		inmap.put("SmsTempletID", SmsTempletID);
////		inmap.put("Timestamp", Timestamp);
////		inmap.put("UserNumber", UserNumber);
////
////		System.out.println(inmap.entrySet());
////
////		String verifyReq = KeyedDigestMD5.getKeyedDigestGBKWithMap(inmap, spPassword);
////
////		String verify = verifyReq;
////
////		JSONObject jsonObject = new JSONObject();
////		jsonObject.put("LoginName", LoginName);
////		jsonObject.put("Parameter", Parameter);
////		jsonObject.put("SmsTempletID", SmsTempletID);
////		jsonObject.put("SPNumber", SPNumber);
////		jsonObject.put("Timestamp", Timestamp);
////		jsonObject.put("UserNumber", UserNumber);
////		jsonObject.put("Verify", verify);
////		String paraData = jsonObject.toString();
////
////		System.out.println(paraData.toString());
////
////		HttpClientUtil httpClient = HttpClientUtil.getInstance();
////
////		try {
////			String response = httpClient.getResponseBodyAsString(url, paraData);
////			System.out.println("response:"+response);
////			JSONObject js = new JSONObject(response);
////			String code = js.get("Result").toString();
////			System.out.println("code:"+code);
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
//
//	}
}
