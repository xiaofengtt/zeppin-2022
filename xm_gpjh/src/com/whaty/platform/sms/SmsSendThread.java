/**
 * 
 */
package com.whaty.platform.sms;

import com.whaty.platform.Exception.PlatformException;

/**
 * @author wq
 * 
 */
public class SmsSendThread extends Thread {

	private String phone;

	private String content;

	private String msgId = "";

	public String code = "";

	public SmsSendThread(String phone, String content) {
		this.phone = this.processPhone(phone);
		this.content = content;
	}

	public SmsSendThread(String msgId, String phone, String content) {
		this.phone = this.processPhone(phone);
		this.content = content;
		this.msgId = msgId;
	}

	public void run() {
		SmsFactory sfactory = SmsFactory.getInstance();
		SmsManage smsManage = sfactory.creatSmsManage();
		try {
			try {

				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String codeStr = null;
			codeStr=smsManage.sendMessage(phone, content);
			setCode(codeStr);
		} catch (Exception e) {//PlatformException
			e.printStackTrace();
		}
	}

	public String startSend() {
		SmsFactory sfactory = SmsFactory.getInstance();
		SmsManage smsManage = sfactory.creatSmsManage();
		String statusCode = "";
		try {
			statusCode = smsManage.sendMessage(msgId, phone, content);
		} catch (PlatformException e) {
			e.printStackTrace();
		}
		return statusCode;
	}

	public String processPhone(String phone) {
		String[] phones = null;
		if (phone != null)
			phones = phone.split(",");
		else
			return "";
		String tmpPhone = "";
		for (int i = 0; i < phones.length; i++) {
			if (phones[i] != null
					&& !phones[i].equals("")
					&& !phones[i].equals("null")
					&& (tmpPhone.length() == 0 || (tmpPhone.length() > 0 && tmpPhone
							.indexOf("," + phones[i]) < 0)))
				tmpPhone += "," + phones[i];
		}
		if (tmpPhone.length() > 0)
			tmpPhone = tmpPhone.substring(1);
		return tmpPhone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
