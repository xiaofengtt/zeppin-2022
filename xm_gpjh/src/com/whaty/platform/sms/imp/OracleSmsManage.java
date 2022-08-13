/**
 * 
 */
package com.whaty.platform.sms.imp;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.whaty.platform.GlobalProperties;
import com.whaty.platform.Exception.PlatformException;
import com.whaty.platform.sms.SmsManage;


/**
 * @author wq
 * 
 */
public class OracleSmsManage extends SmsManage {
	//private SmsManagerPriv smsManagerPriv;

	public OracleSmsManage() {

	}

//	public OracleSmsManage(SmsManagerPriv smsManagerPriv) {
//		this.smsManagerPriv = smsManagerPriv;
//	}

	
	public String sendMessage(String msgId ,String phone, String content)throws PlatformException {
		GlobalProperties props = new GlobalProperties();
		HashMap map = props.getSmsGWSendPrameter();
		String smsUrl = (String) map.get("url");
		String smsAccount = (String) map.get("account");
		String smsPassword = (String) map.get("password");
		try {
			content = new String(content.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String yid = String.valueOf(new Date().getTime());

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(smsUrl);
		NameValuePair accPara = new NameValuePair("account", smsAccount);
		NameValuePair pswPara = new NameValuePair("password", smsPassword);
		NameValuePair phonePara = new NameValuePair("phone", phone);
		NameValuePair yidPara = new NameValuePair("yid", yid);
		NameValuePair pcodePara = new NameValuePair("pcode", "");
		NameValuePair msgPara = new NameValuePair("message", content);

		post.setRequestBody(new NameValuePair[] { accPara, pswPara, phonePara,
				yidPara, pcodePara, msgPara });
		
		
		try {
			int code = client.executeMethod(post);
			
			int statusCode = post.getStatusCode();
			//判断短信是否发送成功
//			OracleSmsMessage smsMessage = new OracleSmsMessage();
//			smsMessage.setMsgId(msgId);
//			if(statusCode==200){
//				smsMessage.setSendStatus("0");   //发送成功状态
//			}else{
//				smsMessage.setSendStatus("1");   //发送失败状态
//			}
//			smsMessage.updateMsgStatus();
			
			return new Integer(statusCode).toString();
		} catch (HttpException e) {
			throw new PlatformException("Error in connect with Service Provider:" + e.toString());
		} catch (IOException e) {
			throw new PlatformException("Error in I/O:" + e.toString());
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsManage#sendMessage(java.lang.String,
	 *      java.lang.String)
	 */
	public String sendMessage(String phones, String content)
			throws PlatformException {
		GlobalProperties props = new GlobalProperties();
		HashMap map = props.getSmsGWSendPrameter();
		String smsUrl = (String) map.get("url");
		String smsAccount = (String) map.get("account");
		String smsPassword = (String) map.get("password");
		try {
			content = new String(content.getBytes("UTF-8"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String yid = String.valueOf(new Date().getTime());

		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod(smsUrl);
		NameValuePair accPara = new NameValuePair("account", smsAccount);
		NameValuePair pswPara = new NameValuePair("password", smsPassword+"1");//modified by zhaoyuxiao,for test using
		NameValuePair phonePara = new NameValuePair("phone", phones);
		NameValuePair yidPara = new NameValuePair("yid", yid);
		NameValuePair pcodePara = new NameValuePair("pcode", "");
		NameValuePair msgPara = new NameValuePair("message", content);

		post.setRequestBody(new NameValuePair[] { accPara, pswPara, phonePara,
				yidPara, pcodePara, msgPara });
		
		
		try {
			int code = client.executeMethod(post);
			int statusCode = post.getStatusCode();
			return new Integer(statusCode).toString();
		} catch (HttpException e) {
			System.out.println("发短信时出现错误");
			e.printStackTrace();
			throw new PlatformException(
					"Error in connect with Service Provider:" + e.toString());
		} catch (IOException e) {
			System.out.println("发短信时出现错误");
			e.printStackTrace();
			throw new PlatformException("Error in I/O:" + e.toString());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsManage#sendMessage(com.whaty.platform.sms.basic.SmsMessage)
	 */
//	public String sendMessage(SmsMessage message) throws PlatformException {
//		
//			GlobalProperties props = new GlobalProperties();
//			HashMap map = props.getSmsGWSendPrameter();
//			String smsUrl = (String) map.get("url");
//			String smsAccount = (String) map.get("account");
//			String smsPassword = (String) map.get("password");
//
//			String content = message.getContent();
//			try {
//				content = new String(content.getBytes("UTF-8"), "ISO-8859-1");
//			} catch (UnsupportedEncodingException e1) {
//				e1.printStackTrace();
//			}
//
//			HttpClient client = new HttpClient();
//			PostMethod post = new PostMethod(smsUrl);
//			NameValuePair accPara = new NameValuePair("account", smsAccount);
//			NameValuePair pswPara = new NameValuePair("password", smsPassword);
//			NameValuePair phonePara = new NameValuePair("phone", message
//					.getTargets());
//			NameValuePair yidPara = new NameValuePair("yid", "");
//			NameValuePair pcodePara = new NameValuePair("pcode", "");
//			NameValuePair msgPara = new NameValuePair("message", content);
//
//			post.setRequestBody(new NameValuePair[] { accPara, pswPara,
//					phonePara, yidPara, pcodePara, msgPara });
//			try {
//				int code = client.executeMethod(post);
//				String value = new Integer(code).toString();
//				
//				 return value;
//			} catch (HttpException e) {
//				throw new PlatformException(
//						"Error in connect with Service Provider:"
//								+ e.toString());
//			} catch (IOException e) {
//				throw new PlatformException("Error in I/O:" + e.toString());
//			}
//	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.whaty.platform.sms.SmsManage#addSmsMessage(java.lang.String,
	 *      java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
//	public int addSmsMessage(String targets, String content, String sender,
//			String scope) throws PlatformException {
//		if (smsManagerPriv.addSms == 1) {
//			OracleSmsMessage smsMessage = new OracleSmsMessage();
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			smsMessage.setTime(format.format(new Date(System.currentTimeMillis())));
//			smsMessage.setTargets(targets);
//			smsMessage.setContent(content);
//			smsMessage.setSender(sender);
//			smsMessage.setScope(scope);
//			int sub = smsMessage.add();
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$addSmsMessage</whaty><whaty>STATUS$|$"
//							+ sub
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			 return sub;
//		} else {
//			throw new PlatformException("您没有添加短信的权限!");
//		}
//	}
//
//	public int addSmsMessage(String targets, String content, String sender,
//			String scope, String siteId, String type, String setTime)
//			throws PlatformException {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		if (smsManagerPriv.addSms == 1) {
//			OracleSmsMessage smsMessage = new OracleSmsMessage();
//			smsMessage.setTargets(targets);
//			smsMessage.setContent(content);
//			smsMessage.setSender(sender);
//			smsMessage.setScope(scope);
//			smsMessage.setType(type);
//			smsMessage.setTime(format.format(new Date(System.currentTimeMillis())));
//			smsMessage.setSiteId(siteId);
//			if("0".equals(type))
//				smsMessage.setSetTime(" ");
//			else
//				smsMessage.setSetTime(setTime);
//			int sub = smsMessage.add();
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$addSmsMessage</whaty><whaty>STATUS$|$"
//							+ sub
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			 return sub;
//		} else {
//			throw new PlatformException("您没有添加短信的权限!");
//		}
//	}
//
//	public int addSystemSmsMessage(String targets, String content,
//			String sender, String scope, String siteId, String type,
//			String setTime) throws PlatformException {
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		OracleSmsMessage smsMessage = new OracleSmsMessage();
//		smsMessage.setTargets(targets);
//		smsMessage.setContent(content);
//		smsMessage.setSender(sender);
//		smsMessage.setScope(scope);
//		smsMessage.setType(type);
//		smsMessage.setSiteId(siteId);
//		smsMessage.setTime(format.format(new Date(System.currentTimeMillis())));
//		smsMessage.setSetTime(setTime);
//		smsMessage.setStatus("1");
//		int sub = smsMessage.add();
//		UserLog
//		.setInfo(
//				"<whaty>USERID$|$"
//						+ this.smsManagerPriv.getManagerId()
//						+ "</whaty><whaty>BEHAVIOR$|$addSystemSmsMessage</whaty><whaty>STATUS$|$"
//						+ sub
//						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//						+ LogType.MANAGER
//						+ "</whaty><whaty>PRIORITY$|$"
//						+ LogPriority.INFO + "</whaty>", new Date());
//		 return sub;
//	}
//	
//	public int addSystemSmsMessage(String targets, String content,
//			String sender, String scope, String siteId, String type,
//			String setTime,String sendStatus) throws PlatformException {
//		OracleSmsMessage smsMessage = new OracleSmsMessage();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		smsMessage.setTime(format.format(new Date(System.currentTimeMillis())));
//		smsMessage.setTargets(targets);
//		smsMessage.setContent(content);
//		smsMessage.setSender(sender);
//		smsMessage.setScope(scope);
//		smsMessage.setType(type);
//		smsMessage.setSiteId(siteId);
//		smsMessage.setSetTime(setTime);
//		smsMessage.setStatus("1");
//		smsMessage.setSendStatus(sendStatus);
//		int sub = smsMessage.add();
//		UserLog
//		.setInfo(
//				"<whaty>USERID$|$"
//						+ this.smsManagerPriv.getManagerId()
//						+ "</whaty><whaty>BEHAVIOR$|$addSystemSmsMessage</whaty><whaty>STATUS$|$"
//						+ sub
//						+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//						+ LogType.MANAGER
//						+ "</whaty><whaty>PRIORITY$|$"
//						+ LogPriority.INFO + "</whaty>", new Date());
//		 return sub;
//	}
//
//	
//	public int addSmsMessage(String targets, String content, String sender,
//			String scope, String siteId) throws PlatformException {
//		if (smsManagerPriv.addSms == 1) {
//			OracleSmsMessage smsMessage = new OracleSmsMessage();
//			smsMessage.setTargets(targets);
//			smsMessage.setContent(content);
//			smsMessage.setSender(sender);
//			smsMessage.setScope(scope);
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			smsMessage.setTime(format.format(new Date(System.currentTimeMillis())));
//			smsMessage.setSiteId(siteId);
//			int sub = smsMessage.add();
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$addSmsMessage</whaty><whaty>STATUS$|$"
//							+ sub
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			 return sub;
//		} else {
//			throw new PlatformException("您没有添加短信的权限!");
//		}
//	}
//
//	public int addSmsMessage(String targets, String content, String sender,
//			String scope, String siteId, String teaId) throws PlatformException {
//		if (smsManagerPriv.addSms == 1) {
//			OracleSmsMessage smsMessage = new OracleSmsMessage();
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			smsMessage.setTime(format.format(new Date(System.currentTimeMillis())));
//			smsMessage.setTargets(targets);
//			smsMessage.setContent(content);
//			smsMessage.setSender(sender);
//			smsMessage.setScope(scope);
//			smsMessage.setSiteId(siteId);
//			smsMessage.setTea_id(teaId);
//			int sub = smsMessage.add();
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$addSmsMessage</whaty><whaty>STATUS$|$"
//							+ sub
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			 return sub;
//		} else {
//			throw new PlatformException("您没有添加短信的权限!");
//		}
//	}
//
//	public int checkSmsMessage(String checker, List smsMessages)
//			throws PlatformException {
//		if (smsManagerPriv.checkSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			int suc = smsList.checkSmsMessages(checker, smsMessages);
//
//			for (int i = 0; i < smsMessages.size(); i++) {
//				String msgId = (String) smsMessages.get(i);
//				SmsMessage message = this.getSmsMessage(msgId);
//				if (message.getType().equals("0")) {
//					String mobile = message.getTargets();
//					String content = message.getContent();
//					SmsSendThread sendThread = new SmsSendThread(mobile,
//							content);
//					sendThread.start();
//				}
//			}
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$checkSmsMessage</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("你没有审核短信的权限!");
//		}
//	}
//
//	public int reSendSmsMessage(String checker, List smsMessages)
//			throws PlatformException {
//		if (smsManagerPriv.checkSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			int suc = smsList.checkSmsMessages(checker, smsMessages);
//
//			for (int i = 0; i < smsMessages.size(); i++) {
//				String msgId = (String) smsMessages.get(i);
//				SmsMessage message = this.getSmsMessage(msgId);
//				addSmsMessage(message.getTargets(), message.getContent(),
//						message.getSender(), null, "0", message.getType(),
//						message.getSetTime());
//			}
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$reSendSmsMessage</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("你没有审核短信的权限!");
//		}
//	}
//
//	public int rejectSmsMessage(String checker, String[] msgIds, String[] notes)
//			throws PlatformException {
//		if (smsManagerPriv.rejectSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			int suc = smsList.rejectSmsMessages(checker, msgIds, notes);
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$rejectSmsMessage</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("你没有驳回短信的权限!");
//		}
//	}
//
//	public List getSmsMessageNumBySite(com.whaty.platform.entity.util.Page pageover, String site_id,
//			String start_time, String end_time) throws PlatformException {
//		if (smsManagerPriv.getSmsStatistic == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			List smsNmList = smsList.getSmsMessageNumBySite(pageover, site_id,
//					start_time, end_time);
//			return smsNmList;
//		} else {
//			throw new PlatformException("你没有查看短信统计的权限!");
//		}
//	}
//
//	
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.whaty.platform.sms.SmsManage#getSmsMessage(java.lang.String)
//	 */
//	public SmsMessage getSmsMessage(String id) throws PlatformException {
//		if (smsManagerPriv.getSms == 1) {
//			return new OracleSmsMessage(id);
//		} else {
//			throw new PlatformException("你没有查看短信的权限!");
//		}
//	}
//
//	public List getSmsMessagesList(Page page, String id, String targets,
//			String content, String status, String sender, String startTime,
//			String endTime, String scope, String checker, String siteId)
//			throws PlatformException {
//		if (smsManagerPriv.getSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			List searchList = new ArrayList();
//			if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("id", id, "="));
//			if (targets != null && !targets.equals("")
//					&& !targets.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("targets", targets, "like"));
//			if (content != null && !content.equals("")
//					&& !content.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("content", content, "like"));
//			if (status != null && !status.equals("")
//					&& !status.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("status", status, "="));
//			if (sender != null && !sender.equals("")
//					&& !sender.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("sender", sender, "like"));
//			if (startTime != null && !startTime.equals("")
//					&& !startTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", startTime, ">="));
//			if (endTime != null && !endTime.equals("")
//					&& !endTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", endTime, "<="));
//			if (scope != null && !scope.equals("")
//					&& !scope.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("scope", scope, "like"));
//			if (checker != null && !checker.equals("")
//					&& !checker.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("checker", checker, "="));
//			if (siteId != null && !siteId.equals("")
//					&& !siteId.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("site", siteId, "="));
//
//			List orderList = new ArrayList();
//			orderList.add(new OrderProperty("to_number(id)", "1"));
//			return smsList.searchSmsMessages(page, searchList, orderList);
//		} else {
//			throw new PlatformException("你没有获取短信列表的权限!");
//		}
//	}
//
//	public int getSmsMessagesNum(String id, String targets, String content,
//			String status, String sender, String startTime, String endTime,
//			String scope, String checker, String siteId)
//			throws PlatformException {
//		if (smsManagerPriv.getSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			List searchList = new ArrayList();
//			if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("id", id, "="));
//			if (targets != null && !targets.equals("")
//					&& !targets.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("targets", targets, "like"));
//			if (content != null && !content.equals("")
//					&& !content.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("content", content, "like"));
//			if (status != null && !status.equals("")
//					&& !status.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("status", status, "="));
//			if (sender != null && !sender.equals("")
//					&& !sender.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("sender", sender, "like"));
//			if (startTime != null && !startTime.equals("")
//					&& !startTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", startTime, ">="));
//			if (endTime != null && !endTime.equals("")
//					&& !endTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", endTime, "<="));
//			if (scope != null && !scope.equals("")
//					&& !scope.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("scope", scope, "like"));
//			if (checker != null && !checker.equals("")
//					&& !checker.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("checker", checker, "="));
//			if (siteId != null && !siteId.equals("")
//					&& !siteId.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("site", siteId, "="));
//
//			return smsList.searchSmsMessagesNum(searchList);
//		} else {
//			throw new PlatformException("你没有获取短信列表的权限!");
//		}
//	}
//
//	public List getSmsMessagesList(Page page, String id, String targets,
//			String content, String status, String sender, String startTime,
//			String endTime, String scope, String checker, String siteId,
//			String teaId) throws PlatformException {
//		if (smsManagerPriv.getSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			List searchList = new ArrayList();
//			if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("id", id, "="));
//			if (targets != null && !targets.equals("")
//					&& !targets.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("targets", targets, "like"));
//			if (content != null && !content.equals("")
//					&& !content.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("content", content, "like"));
//			if (status != null && !status.equals("")
//					&& !status.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("status", status, "="));
//			if (sender != null && !sender.equals("")
//					&& !sender.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("sender", sender, "like"));
//			if (startTime != null && !startTime.equals("")
//					&& !startTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", startTime, ">="));
//			if (endTime != null && !endTime.equals("")
//					&& !endTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", endTime, "<="));
//			if (scope != null && !scope.equals("")
//					&& !scope.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("scope", scope, "like"));
//			if (checker != null && !checker.equals("")
//					&& !checker.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("checker", checker, "="));
//			if (siteId != null && !siteId.equals("")
//					&& !siteId.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("site", siteId, "="));
//			if (teaId != null && !teaId.equals("")
//					&& !teaId.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("tea_id", teaId, "="));
//			List orderList = new ArrayList();
//			orderList.add(new OrderProperty("to_number(id)", "1"));
//			return smsList.searchSmsMessages(page, searchList, orderList);
//		} else {
//			throw new PlatformException("你没有获取短信列表的权限!");
//		}
//	}
//
//	public List getSmsMessagesList(Page page, String id, String targets,
//			String content, String status, String sender, String startTime,
//			String endTime, String scope, String checker, String siteId,
//			String teaId, String type) throws PlatformException {
//		if (smsManagerPriv.getSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			List searchList = new ArrayList();
//			if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("id", id, "="));
//			if (targets != null && !targets.equals("")
//					&& !targets.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("targets", targets, "like"));
//			if (content != null && !content.equals("")
//					&& !content.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("content", content, "like"));
//			if (status != null && !status.equals("")
//					&& !status.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("status", status, "="));
//			if (sender != null && !sender.equals("")
//					&& !sender.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("sender", sender, "like"));
//			if (startTime != null && !startTime.equals("")
//					&& !startTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", startTime, ">="));
//			if (endTime != null && !endTime.equals("")
//					&& !endTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", endTime, "<="));
//			if (scope != null && !scope.equals("")
//					&& !scope.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("scope", scope, "like"));
//			if (checker != null && !checker.equals("")
//					&& !checker.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("checker", checker, "="));
//			if (siteId != null && !siteId.equals("")
//					&& !siteId.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("site", siteId, "="));
//			if (teaId != null && !teaId.equals("")
//					&& !teaId.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("tea_id", teaId, "="));
//			if (type != null && !type.equals("")
//					&& !type.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("type", type, "="));
//			List orderList = new ArrayList();
//			orderList.add(new OrderProperty("to_number(id)", "1"));
//			return smsList.searchSmsMessages(page, searchList, orderList);
//		} else {
//			throw new PlatformException("你没有获取短信列表的权限!");
//		}
//	}
//
//	public int getSmsMessagesNum(String id, String targets, String content,
//			String status, String sender, String startTime, String endTime,
//			String scope, String checker, String siteId, String teaId)
//			throws PlatformException {
//		if (smsManagerPriv.getSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			List searchList = new ArrayList();
//			if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("id", id, "="));
//			if (targets != null && !targets.equals("")
//					&& !targets.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("targets", targets, "like"));
//			if (content != null && !content.equals("")
//					&& !content.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("content", content, "like"));
//			if (status != null && !status.equals("")
//					&& !status.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("status", status, "="));
//			if (sender != null && !sender.equals("")
//					&& !sender.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("sender", sender, "like"));
//			if (startTime != null && !startTime.equals("")
//					&& !startTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", startTime, ">="));
//			if (endTime != null && !endTime.equals("")
//					&& !endTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", endTime, "<="));
//			if (scope != null && !scope.equals("")
//					&& !scope.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("scope", scope, "like"));
//			if (checker != null && !checker.equals("")
//					&& !checker.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("checker", checker, "="));
//			if (siteId != null && !siteId.equals("")
//					&& !siteId.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("site", siteId, "="));
//			if (teaId != null && !teaId.equals("")
//					&& !teaId.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("tea_id", teaId, "="));
//			return smsList.searchSmsMessagesNum(searchList);
//		} else {
//			throw new PlatformException("你没有获取短信列表的权限!");
//		}
//	}
//
//	public int getSmsMessagesNum(String id, String targets, String content,
//			String status, String sender, String startTime, String endTime,
//			String scope, String checker, String siteId, String teaId,
//			String type) throws PlatformException {
//		if (smsManagerPriv.getSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			List searchList = new ArrayList();
//			if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("id", id, "="));
//			if (targets != null && !targets.equals("")
//					&& !targets.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("targets", targets, "like"));
//			if (content != null && !content.equals("")
//					&& !content.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("content", content, "like"));
//			if (status != null && !status.equals("")
//					&& !status.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("status", status, "="));
//			if (sender != null && !sender.equals("")
//					&& !sender.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("sender", sender, "like"));
//			if (startTime != null && !startTime.equals("")
//					&& !startTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", startTime, ">="));
//			if (endTime != null && !endTime.equals("")
//					&& !endTime.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("time", endTime, "<="));
//			if (scope != null && !scope.equals("")
//					&& !scope.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("scope", scope, "like"));
//			if (checker != null && !checker.equals("")
//					&& !checker.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("checker", checker, "="));
//			if (siteId != null && !siteId.equals("")
//					&& !siteId.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("site", siteId, "="));
//			if (teaId != null && !teaId.equals("")
//					&& !teaId.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("tea_id", teaId, "="));
//			if (type != null && !type.equals("")
//					&& !type.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("type", type, "="));
//			return smsList.searchSmsMessagesNum(searchList);
//		} else {
//			throw new PlatformException("你没有获取短信列表的权限!");
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.whaty.platform.sms.SmsManage#updateSmsMessage(java.lang.String,
//	 *      java.lang.String, java.lang.String, java.lang.String,
//	 *      java.lang.String, java.lang.String)
//	 */
//	public int updateSmsMessage(String id, String targets, String content,
//			String status, String sender, String time, String scope)
//			throws PlatformException {
//		if (smsManagerPriv.updateSms == 1) {
//			OracleSmsMessage smsMessage = new OracleSmsMessage();
//			smsMessage.setMsgId(id);
//			smsMessage.setTargets(targets);
//			smsMessage.setContent(content);
//			smsMessage.setStatus(status);
//			smsMessage.setSender(sender);
//			smsMessage.setTime(time);
//			smsMessage.setScope(scope);
//			int suc = smsMessage.update();
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$updateSmsMessage</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("您没有编辑短信的权限!");
//		}
//	}
//
//	public int updateSmsMessage(String id, String targets, String content,
//			String status, String sender, String time, String scope,
//			String type, String setTime) throws PlatformException {
//		if (smsManagerPriv.updateSms == 1) {
//			OracleSmsMessage smsMessage = new OracleSmsMessage();
//			smsMessage.setMsgId(id);
//			smsMessage.setTargets(targets);
//			smsMessage.setContent(content);
//			smsMessage.setStatus(status);
//			smsMessage.setSender(sender);
//			smsMessage.setTime(time);
//			smsMessage.setScope(scope);
//			smsMessage.setType(type);
//			smsMessage.setSetTime(setTime);
//			int suc = smsMessage.update();
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$updateSmsMessage</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("您没有编辑短信的权限!");
//		}
//	}
//
//	public int updateSmsMessage(String id, String targets, String content)
//			throws PlatformException {
//		if (smsManagerPriv.updateSms == 1) {
//			OracleSmsMessage smsMessage = new OracleSmsMessage();
//			smsMessage.setMsgId(id);
//			smsMessage.setTargets(targets);
//			smsMessage.setContent(content);
//			int suc = smsMessage.updateTarget();
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$updateSmsMessage</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("您没有编辑短信的权限!");
//		}
//	}
//
//	public int updateSmsMessageSendStatus(String id, String mobile,
//			String sendStatus) throws PlatformException {
//		if (smsManagerPriv.updateSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			int suc = smsList.updateSmsMessageSendStatus(id, mobile, sendStatus);
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$updateSmsMessageSendStatus</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("您没有编辑短信的权限!");
//		}
//	}
//
	public String getMobiles(String fileName) throws PlatformException {
		String mobiles = "";
		try {
			Workbook w = Workbook.getWorkbook(new File(fileName));
			Sheet sheet = w.getSheet(0);
			int rows = sheet.getRows();

			for (int i = 1; i < rows; i++) {
				String name = sheet.getCell(0, i).getContents().trim();
				String mobile = sheet.getCell(1, i).getContents().trim();
				mobiles += mobile + ",";
			}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (mobiles.length() > 0)
			mobiles = mobiles.substring(0, mobiles.length() - 1);
		return mobiles;
	}
//
//	public int deleteSmsMessage(List smsMessages) throws PlatformException {
//		if (smsManagerPriv.deleteSms == 1) {
//			OracleSmsList smsList = new OracleSmsList();
//			int suc = smsList.deleteSmsMessages(smsMessages);
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$deleteSmsMessage</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("你没有删除短信的权限!");
//		}
//	}
//
//	public List getSmsSystemPoints(Page page, String id)
//			throws PlatformException {
//		if (smsManagerPriv.manageSmsSystemPoint == 1) {
//			OracleSmsList list = new OracleSmsList();
//			List searchList = new ArrayList();
//			if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("id", id, "="));
//			return list.getSmsSystemPoints(page, searchList, null);
//		} else {
//			throw new PlatformException("您没有管理系统短信点的权限!");
//		}
//	}
//
//	public int getSmsSystemPointsNum(String id) throws PlatformException {
//		if (smsManagerPriv.manageSmsSystemPoint == 1) {
//			OracleSmsList list = new OracleSmsList();
//			List searchList = new ArrayList();
//			if (id != null && !id.equals("") && !id.equalsIgnoreCase("null"))
//				searchList.add(new SearchProperty("id", id, "="));
//			return list.getSmsSystemPointsNum(searchList);
//		} else {
//			throw new PlatformException("您没有管理系统短信点的权限!");
//		}
//	}

//	public int updateSmsSystemPoint(String id, String content)
//			throws PlatformException {
//		if (smsManagerPriv.manageSmsSystemPoint == 1) {
//			OracleSmsSystemPoint smsSystemPoint = new OracleSmsSystemPoint();
//			smsSystemPoint.setId(id);
//			smsSystemPoint.setContent(content);
//			int suc = smsSystemPoint.update();
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$updateSmsSystemPoint</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("您没有管理系统短信点的权限!");
//		}
//	}
//	
//	public int addSmsSystemPoint(String id,String name, String content,String status)
//	throws PlatformException{
//		if (smsManagerPriv.manageSmsSystemPoint == 1) {
//			OracleSmsSystemPoint smsSystemPoint = new OracleSmsSystemPoint();
//			smsSystemPoint.setId(id);
//			smsSystemPoint.setName(name);
//			smsSystemPoint.setContent(content);
//			smsSystemPoint.setStatus(status);
//			int suc = smsSystemPoint.add();
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$updateSmsSystemPoint</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("您没有管理系统短信点的权限!");
//		}
//	}
//
//	public int updateSmsSystemPointStatus(String id, String status)
//			throws PlatformException {
//		if (smsManagerPriv.manageSmsSystemPoint == 1) {
//			OracleSmsSystemPoint smsSystemPoint = new OracleSmsSystemPoint();
//			smsSystemPoint.setId(id);
//			smsSystemPoint.setStatus(status);
//			int suc = smsSystemPoint.changeStatus();
//			UserLog
//			.setInfo(
//					"<whaty>USERID$|$"
//							+ this.smsManagerPriv.getManagerId()
//							+ "</whaty><whaty>BEHAVIOR$|$updateSmsSystemPointStatus</whaty><whaty>STATUS$|$"
//							+ suc
//							+ "</whaty><whaty>NOTES$|$</whaty><whaty>LOGTYPE$|$"
//							+ LogType.MANAGER
//							+ "</whaty><whaty>PRIORITY$|$"
//							+ LogPriority.INFO + "</whaty>", new Date());
//			return suc;
//		} else {
//			throw new PlatformException("您没有管理系统短信点的权限!");
//		}
//	}
//
//	public SmsSystemPoint getSmsSystemPoint(String id) throws PlatformException {
//		if (smsManagerPriv.manageSmsSystemPoint == 1) {
//			return new OracleSmsSystemPoint(id);
//		} else {
//			throw new PlatformException("您没有管理系统短信点的权限!");
//		}
//	}



}
