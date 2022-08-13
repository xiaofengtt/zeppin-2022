package com.whaty.platform.entity.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.whaty.platform.GlobalProperties;

public class EmailSendUtil {
	
	public EmailSendUtil(){
		
	}
	 //发送邮件
    public static int sendMail(List<InternetAddress> adreesList,String subject,StringBuffer msg){
		return sendMail(adreesList, subject, msg.toString());
    }
    public static int sendMail(List<InternetAddress> adreesList,String subject,String msg){
    	if(adreesList!=null && !adreesList.isEmpty()){
	    	GlobalProperties props = new GlobalProperties("mail.properties");
	    	Map<String, String> map = props.getMailSendPrameter();
	    	HtmlEmail email = new HtmlEmail();
			email.setHostName((String)map.get("mailServerHost"));
			email.setCharset((String)map.get("charSet"));
			try {
	    		email.setTo(adreesList);
	    		String fromAdd =  (String)map.get("fromAddress");
				email.setFrom(fromAdd);
				email.setAuthentication(fromAdd.substring(0, fromAdd.indexOf("@")), (String)map.get("password"));
				email.setSubject(subject);
				email.setMsg(msg);
				email.send();
			}catch(EmailException e){
				e.printStackTrace();
			}
    	}
		return adreesList.size();
    }
    public static int sendMail(List<InternetAddress> adreesList,String subject,String msg,String [] path){
    	if(adreesList!=null && !adreesList.isEmpty()){
	    	GlobalProperties props = new GlobalProperties("mail.properties");
	    	Map<String, String> map = props.getMailSendPrameter();
	    	HtmlEmail email = new HtmlEmail();
			email.setHostName((String)map.get("mailServerHost"));
			email.setCharset((String)map.get("charSet"));
			try {
	    		email.setTo(adreesList);
	    		email.setSentDate(new Date());
	    		String fromAdd =  (String)map.get("fromAddress");
				email.setFrom(fromAdd);
				email.setAuthentication(fromAdd/*.substring(0, fromAdd.indexOf("@"))*/, (String)map.get("password"));
				email.setSubject(subject);
				email.setMsg(msg);
				for(int i=0;i<path.length;i++){
					EmailAttachment attachment = new EmailAttachment();
					attachment.setPath(path[i]);
//					System.out.println(path[i]);
					String os = System.getProperty("os.name");
					String splitString = "\\\\";
			    	if(os.indexOf("Linux") >= 0){
			    		splitString = "/";
			    	}
			    	String fileName[] = path[i].split(splitString);
					try {
						attachment.setName(MimeUtility.encodeText(fileName[fileName.length-1]));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					} 
					email.attach(attachment);
				}
				email.send();
			}catch(EmailException e){
				e.printStackTrace();
			}
    	}
		return adreesList.size();
    }
	public static void main(String[] args) {
		InternetAddress ia = new InternetAddress();
		ia.setAddress("houxuelong@whaty.com");
		List<InternetAddress> list = new ArrayList<InternetAddress>();
		list.add(ia);
		String path[] = new String[0];
		sendMail(list, "测试", "爱上低价房拉萨的肯定疯了",path);
	}
}
