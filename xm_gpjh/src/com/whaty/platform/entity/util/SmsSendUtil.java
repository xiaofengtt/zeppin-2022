package com.whaty.platform.entity.util;

import java.util.List;

import com.whaty.platform.Exception.PlatformException;

public class SmsSendUtil { 
	public SmsSendUtil(){
	}

	/**
	 * 发送短信
	 * @param phones
	 * @param content
	 * @return
	 * @throws PlatformException
	 */
//	public static String sendMessage(String phones, String content)
//			throws PlatformException {
//		GlobalProperties props = new GlobalProperties();
//		Map<String,String> map = props.getSmsGWSendPrameter();
//		String smsUrl = (String) map.get("url");
//		String smsAccount = (String) map.get("account");
//		String smsPassword = (String) map.get("password");
//		try {
//			content = new String(content.getBytes("UTF-8"), "ISO-8859-1");
//		} catch (UnsupportedEncodingException e1) {
//			e1.printStackTrace();
//		}
//		String yid = String.valueOf(new Date().getTime());
//
//		HttpClient client = new HttpClient();
//		PostMethod post = new PostMethod(smsUrl);
//		NameValuePair accPara = new NameValuePair("account", smsAccount);
//		NameValuePair pswPara = new NameValuePair("password", smsPassword);//modified by zhaoyuxiao,for test using
//		NameValuePair phonePara = new NameValuePair("phone", phones);
//		NameValuePair yidPara = new NameValuePair("yid", yid);
//		NameValuePair pcodePara = new NameValuePair("pcode", "");
//		NameValuePair msgPara = new NameValuePair("message", content);
//		post.setRequestBody(new NameValuePair[] { accPara, pswPara, phonePara,
//				yidPara, pcodePara, msgPara });
//		try {
//			int code = client.executeMethod(post);
//			int statusCode = post.getStatusCode();
//			return new Integer(statusCode).toString();
//		} catch (HttpException e) {
//			System.out.println("发短信时出现错误");
//			e.printStackTrace();
//			throw new PlatformException(
//					"Error in connect with Service Provider:" + e.toString());
//		} catch (IOException e) {
//			System.out.println("发短信时出现错误");
//			e.printStackTrace();
//			throw new PlatformException("Error in I/O:" + e.toString());
//		}
//	}
	public int canSendCount(){
		String strUserHash = UserLogin.getStrUserHash();
		Message message = new Message();
		return message.getUserRegInfo(strUserHash);
//			return false;
//		}
//		return true;
	}
	public boolean sendMsg(List<String> phoneList,String content){
		String strUserHash = UserLogin.getStrUserHash();
		Message message = new Message();
		StringBuffer phones = new StringBuffer();
		for(int i=0;i<phoneList.size();i++){
			if(i!=phoneList.size()-1){
				phones.append(phoneList.get(i)+";");
			}else{
				phones.append(phoneList.get(i));
			}
		}
		message.getMessageBegin(strUserHash,new Integer(phoneList.size()));
		boolean b = message.getMessage(strUserHash,phones.toString(),content);
		message.getMessageEnd(strUserHash);
		
		return b;
	}
	
	
	public static void main(String[] args) {
//		try {
//			sendMessage("13651199434","测试，收到没？");
//		} catch (PlatformException e) {
//			e.printStackTrace();
//		}
	}
}
