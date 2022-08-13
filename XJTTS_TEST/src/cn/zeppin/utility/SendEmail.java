package cn.zeppin.utility;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SendEmail {
	
	public static String sendEmail(String content,String email){
		
//		String status = "";
//		String path = "E:\\workspace2015\\XJTTS_TEST\\build\\classes\\email.xml";
//		path = path.replaceAll("%20", " ");
//		DocumentBuilderFactory dBuilderFactory = DocumentBuilderFactory.newInstance();
//		Element root = null;
//		String url = "";
//		String user = "";
//		String pwd = "";
//		String title = "";
////		String content = "";
//		int port = 0;
//		try {
//			dBuilderFactory.setIgnoringElementContentWhitespace(true);
//			DocumentBuilder dBuilder = dBuilderFactory.newDocumentBuilder();
//
//			Document document = dBuilder.parse(new File(path));
//			root = document.getDocumentElement();
//
//			user = document.getElementsByTagName("user").item(0).getFirstChild().getNodeValue();
//			System.out.println("哈哈哈哈"+user);
//			pwd = root.getElementsByTagName("pwd").item(0).getFirstChild().getNodeValue();
//			url = document.getElementsByTagName("url").item(0).getFirstChild().getNodeValue();
//			port = Integer.parseInt(document.getElementsByTagName("port").item(0).getFirstChild().getNodeValue());
//			title = document.getElementsByTagName("title").item(0).getFirstChild().getNodeValue();
////			content = document.getElementsByTagName("content").item(0).getFirstChild().getNodeValue();
//		} catch (Exception e) {
//			// TODO: handle exception
//			String eString = e.getMessage();
//			System.out.print(eString);
//
//		}
		String status = "";
		String user = "1755870702@qq.com";
		String pwd = "xingyuntiancai@1";
		String url = "smtp.qq.com";
		String title = "密码重置通知邮件";
		int port = 25;
		try {

			Properties props = new Properties();// 也可用Properties props = System.getProperties(); 
			
			props.put("mail.smtp.host", url);// 存储发送邮件服务器的信息
			props.put("mail.smtp.auth", "true");// 同时通过验证
			
			
			Session s = Session.getInstance(props);// 根据属性新建一个邮件会话
			s.setDebug(true);
			// 由邮件会话新建一个消息对象
			MimeMessage message = new MimeMessage(s);// 由邮件会话新建一个消息对象
			// 设置邮件
			InternetAddress from;
			System.out.println("啊哈哈哈"+user);
			from = new InternetAddress(user);
			message.setFrom(from);// 设置发件人
			InternetAddress to = new InternetAddress();
			to.setAddress(email);
			message.setRecipient(Message.RecipientType.TO, to);// 设置收件人,并设置其接收类型为TO
			message.setSubject(title);// 设置主题
			message.setText(content);// 设置信件内容
			message.setSentDate(new Date());// 设置发信时间
			message.setContent(content,"text/html;charset=utf-8");
			// 发送邮件
			message.saveChanges();// 存储邮件信息
			
			Transport transport = s.getTransport("smtp");
			
			// 以smtp方式登录邮箱,第一个参数是发送邮件用的邮件服务器SMTP地址,第二个参数为用户名,第三个参数为密码
			transport.connect(url, port, user,pwd);
			transport.sendMessage(message, message.getAllRecipients());// 发送邮件,其中第二个参数是所有已设好的收件人地址
			transport.close();
			
			status = "OK";
			return status;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
			status = "ERROR";
			return status;
		}
	}

}
