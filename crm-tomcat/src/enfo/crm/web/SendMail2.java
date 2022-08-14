/*
 * 创建日期 2010-3-8
 *
 * 更改所生成文件模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
package enfo.crm.web;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import enfo.crm.dao.BusiException;
import enfo.crm.tools.Utility;

/**
 * @author taochen
 *
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class SendMail2 {
	/** 发件方式 - 普通发送 */
	final public static int TO = 0;
	/** 发件方式 - 抄送 */
	final public static int CC = 1;
	/** 发件方式 - 密件抄送 */
	final public static int BCC = 2;

	/** 邮件相关信息 - SMTP 服务器 */
	private String mailSMTPHost = null;
	/** 邮件相关信息 - 邮件用户名 */
	private String mailUser = null;
	/** 邮件相关信息 - 密码 */
	private String mailPassword = null;
	/** 邮件相关信息 - 发件人邮件地址 */
	private String mailFromAddress = null;
	/** 邮件相关信息 - 邮件主题 */
	private String mailSubject = "";
	/** 邮件相关信息 - 邮件发送地址 */
	private Address[] mailTOAddress = null;
	/** 邮件相关信息 - 邮件抄送地址 */
	private Address[] mailCCAddress = null;
	/** 邮件相关信息 - 邮件密件抄送地址 */
	private Address[] mailBCCAddress = null;
	/** 邮件相关信息 - 邮件正文(复合结构) */
	private MimeMultipart mailBody = null;
	/** 邮件相关信息 - 邮件附件的文件名的集合 */
	public static Vector file = new Vector(); //用于保存发送附件的文件名的集合
	private String strFolder = "C:\\Temp"; 

	public SendMail2() {
		mailBody = new MimeMultipart();
	}
	
	//用于收集附件名
	 public void attachfile(String fname) {
		 file.addElement(fname);
	 }  
    
	 public void remobvefile(String fname) {
		   file.removeElement(fname);
	 }

	 public void remobveAllfile(){
		   file.removeAllElements();
	 }

	/**
	   * 设置 SMTP 服务器
	   * @param strSMTPHost 邮件服务器名称或 IP
	   * @param strUser 邮件用户名
	   * @param strPassword 密码
	   */
	public void setSMTPHost(String strSMTPHost, String strUser, String strPassword) {
		this.mailSMTPHost = strSMTPHost;
		this.mailUser = strUser;
		this.mailPassword = strPassword;
	}

	/**
	   * 设置邮件发送地址
	   * @param strFromAddress 邮件发送地址
	   */
	public void setFromAddress(String strFromAddress) {
		this.mailFromAddress = strFromAddress;
	}

	/**
	   * 设置邮件目的地址
	   * @param strAddress 邮件目的地址列表, 不同的地址可用;号分隔
	   * @param iAddressType 邮件发送方式 (TO 0, CC 1, BCC 2) 常量已在本类定义
	   * @throws AddressException
	   */
	public void setAddress(String strAddress, int iAddressType) throws
		  AddressException {
		switch (iAddressType) {
		  case SendMail2.TO: {
			String[] alAddress = Utility.splitString(strAddress, ";");
			mailTOAddress = new Address[alAddress.length];
			for (int i = 0; i <alAddress.length; i++) {
			  mailTOAddress[i] = new InternetAddress(alAddress[i]);
			}
			break;
		  }
		  case SendMail2.CC: {
			String[] alAddress = Utility.splitString(strAddress, ";");
			mailCCAddress = new Address[alAddress.length];
			for (int i = 0; i < alAddress.length; i++) {
			  mailCCAddress[i] = new InternetAddress(alAddress[i]);
			}
			break;
		  }
		  case SendMail2.BCC: {
			String[] alAddress = Utility.splitString(strAddress, ";");
			mailBCCAddress = new Address[alAddress.length];
			for (int i = 0; i < alAddress.length; i++) {
			  mailBCCAddress[i] = new InternetAddress(alAddress[i]);
			}
			break;
		  }
		}
	}

	/**
	   * 设置邮件主题
	   * @param strSubject 邮件主题
	   */
	public void setSubject(String strSubject) {
		this.mailSubject = strSubject;
	}

	/**
	   * 设置邮件文本正文
	   * @param strTextBody 邮件文本正文
	   * @throws MessagingException
	   */
	public void setTextBody(String strTextBody) throws MessagingException {
		MimeBodyPart mimebodypart = new MimeBodyPart();
		mimebodypart.setText(strTextBody, "GBK");
		mailBody.addBodyPart(mimebodypart);
	}

	/**
	   * 设置邮件超文本正文
	   * @param strHtmlBody 邮件超文本正文
	   * @throws MessagingException
	   */
	public void setHtmlBody(String strHtmlBody) throws MessagingException {
		MimeBodyPart mimebodypart = new MimeBodyPart();
		mimebodypart.setDataHandler(new DataHandler(strHtmlBody, "text/html;charset=GBK"));
		mailBody.addBodyPart(mimebodypart);
	}

	/**
	   * 设置邮件正文外部链接 URL, 信体中将包含链接所指向的内容
	   * @param strURLAttachment 邮件正文外部链接 URL
	   * @throws MessagingException
	   * @throws MalformedURLException
	   */
	public void setURLAttachment(String strURLAttachment) throws
		  MessagingException, MalformedURLException {
		MimeBodyPart mimebodypart = new MimeBodyPart();
		mimebodypart.setDataHandler(new DataHandler(new URL(strURLAttachment)));
		mailBody.addBodyPart(mimebodypart);
	}

	/**
	   * 设置邮件附件
	   * @param strFileAttachment 文件的全路径
	   * @throws MessagingException
	   * @throws UnsupportedEncodingException
	   */
	public void setFileAttachment(String strFileAttachment) throws
		  MessagingException, UnsupportedEncodingException {
		File path = new File(strFileAttachment);
		if (!path.exists() || path.isDirectory()) {
		  return;
		}
		String strFileName = path.getName();
		MimeBodyPart mimebodypart = new MimeBodyPart();
		mimebodypart.setDataHandler(new DataHandler(new FileDataSource(strFileAttachment)));
		// modified by zord @ 2003/6/16 to support Chinese File Name
		// mimebodypart.setFileName(strFileName);
		mimebodypart.setFileName(MimeUtility.encodeText(strFileName));
		// end of modify
		mailBody.addBodyPart(mimebodypart);
	}

	/**
	   * 邮件发送(一次发送多个地址, 优点速度快, 但是有非法邮件地址时将中断发送操作)
	   * @throws MessagingException
	   */
	public void sendBatch(String authFlag,String userName,String password,Integer input_operatorCode) throws BusiException {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", this.mailSMTPHost);
		properties.put("mail.smtp.auth",authFlag);
		Session session = null;
		Enumeration efile = file.elements(); //利用枚举器方便的遍历集合
		String filename;
		
		try {
			while (efile.hasMoreElements()) {
				MimeBodyPart mbp = new MimeBodyPart();
				filename = efile.nextElement().toString(); //选择出每一个附件名
				FileDataSource fds = new FileDataSource(strFolder + java.io.File.separator + input_operatorCode + java.io.File.separator + filename); //得到数据源
				mbp.setDataHandler(new DataHandler(fds));
				//得到附件本身并置入BodyPart
				mbp.setFileName(MimeUtility.encodeText(fds.getName()));	 //得到文件名同样置入BodyPart
				mailBody.addBodyPart(mbp);	      
			}
		} catch (MessagingException e) {
			throw new BusiException("附件添加失败:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new BusiException("附件添加失败:" + e.getMessage());
		} 
		
		if(authFlag.equals("true")){
			SmtpAuth sa = new SmtpAuth(); 
			sa.setUserinfo(userName, password); 
			session = Session.getInstance(properties, sa);
		}
		else {
			session = Session.getInstance(properties, null);
		}

		MimeMessage mimemessage = new MimeMessage(session);
		try {
			mimemessage.setFrom(new InternetAddress(this.mailFromAddress));
		} catch (AddressException e1) {
			throw new BusiException("服务器配置失败:" +e1.getMessage());
		} catch (MessagingException e1) {
			throw new BusiException("服务器配置失败:" + e1.getMessage());
		}
		
		try {		
		if (mailTOAddress != null) {
		
			mimemessage.addRecipients(javax.mail.Message.RecipientType.TO,
										this.mailTOAddress);
		
		}
		if (mailCCAddress != null) {
		  mimemessage.addRecipients(javax.mail.Message.RecipientType.CC,
									this.mailCCAddress);
		}
		if (mailBCCAddress != null) {
		  mimemessage.addRecipients(javax.mail.Message.RecipientType.BCC,
									this.mailBCCAddress);
		}
		
		} catch (MessagingException e2) {
			throw new BusiException("邮件地址错误:" + e2.getMessage());
		}
		
		try {
			mimemessage.setSubject(this.mailSubject);
			mimemessage.setContent(this.mailBody);
			mimemessage.setSentDate(new Date());
			
			Transport transport = session.getTransport("smtp");
			transport.connect(this.mailSMTPHost, this.mailUser, this.mailPassword);
			Transport.send(mimemessage);		
			
		} catch (MessagingException e3) {
			System.out.println(e3);
			throw new BusiException("邮件发送失败，请检查服务器配置！");
		}
	}

	/**
	   * 连接邮件服务器
	   * @throws BusiException
	   */
	public Session contectServer(String authFlag,String userName,String password)throws BusiException{
		Properties properties = new Properties();
		properties.put("mail.smtp.host", this.mailSMTPHost);
		properties.put("mail.smtp.auth",authFlag);
		Session session = null;
		try{
			if(authFlag.equals("true")){
				SmtpAuth sa = new SmtpAuth(); 
				sa.setUserinfo(userName, password); 
				session = Session.getInstance(properties, sa);
			}
			else {
				session = Session.getInstance(properties, null);
			}
			Transport transport = session.getTransport("smtp");
			transport.connect(this.mailSMTPHost, this.mailUser, this.mailPassword);
			return session;
		}catch(MessagingException e){
			System.out.println(e);
			throw new BusiException("邮件发送失败，请检查服务器配置！");
		}
		
	}
	
	/**
	   * 发送邮件（不支持群发）
	   * @throws BusiException
	   */
	public void sendMail(Session session,Integer input_operatorCode)throws BusiException{
		MimeMessage mimemessage = new MimeMessage(session);
		Enumeration efile = file.elements(); //利用枚举器方便的遍历集合
		String filename;
		
		try {
			while (efile.hasMoreElements()) {
				MimeBodyPart mbp = new MimeBodyPart();
				filename = efile.nextElement().toString(); //选择出每一个附件名
				FileDataSource fds = new FileDataSource(strFolder + java.io.File.separator + input_operatorCode + java.io.File.separator + filename); //得到数据源
				mbp.setDataHandler(new DataHandler(fds));
				//得到附件本身并置入BodyPart
				mbp.setFileName(MimeUtility.encodeText(fds.getName()));	 //得到文件名同样置入BodyPart
				mailBody.addBodyPart(mbp);	      
			}
		} catch (MessagingException e) {
			throw new BusiException("附件添加失败:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new BusiException("附件添加失败:" + e.getMessage());
		} 
		try {
			mimemessage.setFrom(new InternetAddress(this.mailFromAddress));
		} catch (AddressException e1) {
			throw new BusiException("服务器配置失败:" +e1.getMessage());
		} catch (MessagingException e1) {
			throw new BusiException("服务器配置失败:" + e1.getMessage());
		}
		
		try {		
		if (mailTOAddress != null) {
		
			mimemessage.addRecipients(javax.mail.Message.RecipientType.TO,
										this.mailTOAddress);
		
		}
		if (mailCCAddress != null) {
		  mimemessage.addRecipients(javax.mail.Message.RecipientType.CC,
									this.mailCCAddress);
		}
		if (mailBCCAddress != null) {
		  mimemessage.addRecipients(javax.mail.Message.RecipientType.BCC,
									this.mailBCCAddress);
		}
		
		} catch (MessagingException e2) {
			throw new BusiException("邮件地址错误:" + e2.getMessage());
		}
		
		try {
			mimemessage.setSubject(this.mailSubject);
			mimemessage.setContent(this.mailBody);
			mimemessage.setSentDate(new Date());
			
			Transport.send(mimemessage);		
			mailBody = new MimeMultipart();
		} catch (MessagingException e3) {
			System.out.println(e3);
			throw new BusiException("邮件发送失败，请检查服务器配置！");
		}
	}
	
	static public void main(String str[]) throws UnsupportedEncodingException, MessagingException, BusiException{
		SendMail2 mail=new SendMail2();
		mail.setAddress("178820737@qq.com",SendMail2.TO);
		mail.setFromAddress("178820737@qq.com");
		mail.setSMTPHost("smtp.qq.com","","");
		mail.setSubject("测试一下");
		mail.setHtmlBody("");
		mail.sendBatch("true","178820737","",new Integer(888));
		System.out.println(111);
	}
}
