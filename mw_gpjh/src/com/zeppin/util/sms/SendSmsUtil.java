package com.zeppin.util.sms;

import java.io.File;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SendSmsUtil {
	String path = null;
	String username = null;
	String password = null;
	String address = null;
	String mobile = null; 
	String content = null;
	String timestamp = null;
	String key = null;
	{

		path = "/home/www/webapps/mw_gpjh/WEB-INF/config/sms.xml";
//		path = "E:\\workspace2015\\mw_gpjh\\WebRoot\\WEB-INF\\config\\sms.xml";
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

			key = document.getElementsByTagName("key").item(0)
					.getFirstChild().getNodeValue();
			username = document.getElementsByTagName("username").item(0)
					.getFirstChild().getNodeValue();
			//密码由DESUtil加密过，需要解密后使用。
			password = root.getElementsByTagName("password").item(0)
					.getFirstChild().getNodeValue();
			DESUtil de=new DESUtil(key);
			password = de.decrypt(password);
			address = document.getElementsByTagName("address").item(0)
					.getFirstChild().getNodeValue();

//			 System.out.println(key + " --- " + username + " --- " + address);

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
	public SendSmsUtil(String mobile, String content, String timestamp) {
		this.mobile = mobile;
		this.content = content;
		this.timestamp = timestamp;
	}
	
	/**
	 * 发送
	 * @return
	 * @throws JSONException 
	 */
	public String sendSms() throws JSONException{
		String code = "";
		try {
//			String Timestemp = Utils.getTimestemp();
			String Key = Utils.getKey(this.username, password, this.timestamp);
//			String Content = "测试内容";
			// 多个手机号码用“,”隔开。（半角逗号）1000个以内
//			String Mobiles = "18612033494";
			// （可选）预约时间（不预约的话填写,时间格式yyyymmddhhmmss 20160617234758）
			String SchTime = "";
			// 1~5的整数 从低到高
			int Priority = 5;
			// （可选）批次小号 字符串32位以内
			String PackID = "";
			// （可选）批次号 字符串32位以内。例：群发短信3万，每一个包1000。为这个3万个分配一个PacksID
			// ，每1000包分配一个PackID
			String PacksID = "";
			// （可选）
			String ExpandNumber = "";
			// 短信唯一标识 长整型数字。短信ID，用来匹配状态报告（必须数字类型）
			Long SMSID = System.currentTimeMillis();
			StringBuffer _StringBuffer = new StringBuffer();
			_StringBuffer.append("UserName=" + username + "&");
			_StringBuffer.append("Key=" + Key + "&");
			_StringBuffer.append("Timestemp=" + timestamp + "&");
			_StringBuffer.append("Content=" + URLEncoder.encode(content, "utf-8") + "&");
			_StringBuffer.append("CharSet=utf-8&");
			_StringBuffer.append("Mobiles=" + mobile + "&");
			_StringBuffer.append("SchTime=" + SchTime + "&");
			_StringBuffer.append("Priority=" + Priority + "&");
			_StringBuffer.append("PackID=" + PackID + "&");
			_StringBuffer.append("PacksID=" + ExpandNumber + "&");
			_StringBuffer.append("ExpandNumber=" + PacksID + "&");
			_StringBuffer.append("SMSID=" + SMSID);
			Hashtable _Header = new Hashtable();
			_Header.put("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
//			System.out.println(_StringBuffer.toString());
			// post请求
			InputStream _InputStream = Utils.SendMessage(_StringBuffer.toString().getBytes("utf-8"), _Header, address);
			String response = Utils.GetResponseString(_InputStream, "utf-8");
			// 响应的包体
//			System.out.println(response);
			JSONObject js = new JSONObject(response);
			code = js.get("result").toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return code;
	}
//	public static void main(String[] args) throws JSONException {
//		String time = Utils.getBJTime();
////		String time = Utils.getTimestemp();
////		for(int i = 0; i<10; i++){
//			SendSmsUtil sm = new SendSmsUtil("18612033494,", "测试短信发送,当前时间："+time,time);
//			String str = sm.sendSms();
//			System.out.println(str);
////			System.out.println("i="+i+"---"+str);
////		}
//			
//		
//	}
}
