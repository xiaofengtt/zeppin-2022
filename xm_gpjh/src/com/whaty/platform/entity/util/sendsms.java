package com.whaty.platform.entity.util;

public class sendsms {

	
	public static void main(String[] args) {
		String strUserHash = UserLogin.getStrUserHash();
		Message message = new Message();
		System.out.println(message.getUserRegInfo(strUserHash));
		System.out.println("status begin:" + message.getMessageBegin(strUserHash,new Integer(1000)));
		System.out.println("status ing:" + message.getMessage(strUserHash,"13241770147","北京网梯科技，网关test2!"));
		System.out.println("status end:" + message.getMessageEnd(strUserHash));
	}

}
