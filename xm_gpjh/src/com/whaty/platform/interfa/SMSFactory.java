package com.whaty.platform.interfa;

import com.whaty.platform.sms.SmsSendThread;

public class SMSFactory {
	
	private static SMSFactory smsfactory;
	
	private SMSFactory(){}
	
	public static SMSFactory getInstance(){
		if(smsfactory == null){
			return new SMSFactory();
		}else{
			return smsfactory;
		}
	}
	
	public static void  send(String mobile,String content){
		SmsSendThread sendThread = new SmsSendThread(mobile,
				content);
		sendThread.start();
	}
	
	//测试方法
	public static void main(String []args){
		SMSFactory f = SMSFactory.getInstance();
		f.send("15810365930", "要开学了");
	}
}
