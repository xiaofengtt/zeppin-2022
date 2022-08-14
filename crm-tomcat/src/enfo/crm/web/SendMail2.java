/*
 * �������� 2010-3-8
 *
 * �����������ļ�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
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
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class SendMail2 {
	/** ������ʽ - ��ͨ���� */
	final public static int TO = 0;
	/** ������ʽ - ���� */
	final public static int CC = 1;
	/** ������ʽ - �ܼ����� */
	final public static int BCC = 2;

	/** �ʼ������Ϣ - SMTP ������ */
	private String mailSMTPHost = null;
	/** �ʼ������Ϣ - �ʼ��û��� */
	private String mailUser = null;
	/** �ʼ������Ϣ - ���� */
	private String mailPassword = null;
	/** �ʼ������Ϣ - �������ʼ���ַ */
	private String mailFromAddress = null;
	/** �ʼ������Ϣ - �ʼ����� */
	private String mailSubject = "";
	/** �ʼ������Ϣ - �ʼ����͵�ַ */
	private Address[] mailTOAddress = null;
	/** �ʼ������Ϣ - �ʼ����͵�ַ */
	private Address[] mailCCAddress = null;
	/** �ʼ������Ϣ - �ʼ��ܼ����͵�ַ */
	private Address[] mailBCCAddress = null;
	/** �ʼ������Ϣ - �ʼ�����(���Ͻṹ) */
	private MimeMultipart mailBody = null;
	/** �ʼ������Ϣ - �ʼ��������ļ����ļ��� */
	public static Vector file = new Vector(); //���ڱ��淢�͸������ļ����ļ���
	private String strFolder = "C:\\Temp"; 

	public SendMail2() {
		mailBody = new MimeMultipart();
	}
	
	//�����ռ�������
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
	   * ���� SMTP ������
	   * @param strSMTPHost �ʼ����������ƻ� IP
	   * @param strUser �ʼ��û���
	   * @param strPassword ����
	   */
	public void setSMTPHost(String strSMTPHost, String strUser, String strPassword) {
		this.mailSMTPHost = strSMTPHost;
		this.mailUser = strUser;
		this.mailPassword = strPassword;
	}

	/**
	   * �����ʼ����͵�ַ
	   * @param strFromAddress �ʼ����͵�ַ
	   */
	public void setFromAddress(String strFromAddress) {
		this.mailFromAddress = strFromAddress;
	}

	/**
	   * �����ʼ�Ŀ�ĵ�ַ
	   * @param strAddress �ʼ�Ŀ�ĵ�ַ�б�, ��ͬ�ĵ�ַ����;�ŷָ�
	   * @param iAddressType �ʼ����ͷ�ʽ (TO 0, CC 1, BCC 2) �������ڱ��ඨ��
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
	   * �����ʼ�����
	   * @param strSubject �ʼ�����
	   */
	public void setSubject(String strSubject) {
		this.mailSubject = strSubject;
	}

	/**
	   * �����ʼ��ı�����
	   * @param strTextBody �ʼ��ı�����
	   * @throws MessagingException
	   */
	public void setTextBody(String strTextBody) throws MessagingException {
		MimeBodyPart mimebodypart = new MimeBodyPart();
		mimebodypart.setText(strTextBody, "GBK");
		mailBody.addBodyPart(mimebodypart);
	}

	/**
	   * �����ʼ����ı�����
	   * @param strHtmlBody �ʼ����ı�����
	   * @throws MessagingException
	   */
	public void setHtmlBody(String strHtmlBody) throws MessagingException {
		MimeBodyPart mimebodypart = new MimeBodyPart();
		mimebodypart.setDataHandler(new DataHandler(strHtmlBody, "text/html;charset=GBK"));
		mailBody.addBodyPart(mimebodypart);
	}

	/**
	   * �����ʼ������ⲿ���� URL, �����н�����������ָ�������
	   * @param strURLAttachment �ʼ������ⲿ���� URL
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
	   * �����ʼ�����
	   * @param strFileAttachment �ļ���ȫ·��
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
	   * �ʼ�����(һ�η��Ͷ����ַ, �ŵ��ٶȿ�, �����зǷ��ʼ���ַʱ���жϷ��Ͳ���)
	   * @throws MessagingException
	   */
	public void sendBatch(String authFlag,String userName,String password,Integer input_operatorCode) throws BusiException {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", this.mailSMTPHost);
		properties.put("mail.smtp.auth",authFlag);
		Session session = null;
		Enumeration efile = file.elements(); //����ö��������ı�������
		String filename;
		
		try {
			while (efile.hasMoreElements()) {
				MimeBodyPart mbp = new MimeBodyPart();
				filename = efile.nextElement().toString(); //ѡ���ÿһ��������
				FileDataSource fds = new FileDataSource(strFolder + java.io.File.separator + input_operatorCode + java.io.File.separator + filename); //�õ�����Դ
				mbp.setDataHandler(new DataHandler(fds));
				//�õ�������������BodyPart
				mbp.setFileName(MimeUtility.encodeText(fds.getName()));	 //�õ��ļ���ͬ������BodyPart
				mailBody.addBodyPart(mbp);	      
			}
		} catch (MessagingException e) {
			throw new BusiException("�������ʧ��:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new BusiException("�������ʧ��:" + e.getMessage());
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
			throw new BusiException("����������ʧ��:" +e1.getMessage());
		} catch (MessagingException e1) {
			throw new BusiException("����������ʧ��:" + e1.getMessage());
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
			throw new BusiException("�ʼ���ַ����:" + e2.getMessage());
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
			throw new BusiException("�ʼ�����ʧ�ܣ�������������ã�");
		}
	}

	/**
	   * �����ʼ�������
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
			throw new BusiException("�ʼ�����ʧ�ܣ�������������ã�");
		}
		
	}
	
	/**
	   * �����ʼ�����֧��Ⱥ����
	   * @throws BusiException
	   */
	public void sendMail(Session session,Integer input_operatorCode)throws BusiException{
		MimeMessage mimemessage = new MimeMessage(session);
		Enumeration efile = file.elements(); //����ö��������ı�������
		String filename;
		
		try {
			while (efile.hasMoreElements()) {
				MimeBodyPart mbp = new MimeBodyPart();
				filename = efile.nextElement().toString(); //ѡ���ÿһ��������
				FileDataSource fds = new FileDataSource(strFolder + java.io.File.separator + input_operatorCode + java.io.File.separator + filename); //�õ�����Դ
				mbp.setDataHandler(new DataHandler(fds));
				//�õ�������������BodyPart
				mbp.setFileName(MimeUtility.encodeText(fds.getName()));	 //�õ��ļ���ͬ������BodyPart
				mailBody.addBodyPart(mbp);	      
			}
		} catch (MessagingException e) {
			throw new BusiException("�������ʧ��:" + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new BusiException("�������ʧ��:" + e.getMessage());
		} 
		try {
			mimemessage.setFrom(new InternetAddress(this.mailFromAddress));
		} catch (AddressException e1) {
			throw new BusiException("����������ʧ��:" +e1.getMessage());
		} catch (MessagingException e1) {
			throw new BusiException("����������ʧ��:" + e1.getMessage());
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
			throw new BusiException("�ʼ���ַ����:" + e2.getMessage());
		}
		
		try {
			mimemessage.setSubject(this.mailSubject);
			mimemessage.setContent(this.mailBody);
			mimemessage.setSentDate(new Date());
			
			Transport.send(mimemessage);		
			mailBody = new MimeMultipart();
		} catch (MessagingException e3) {
			System.out.println(e3);
			throw new BusiException("�ʼ�����ʧ�ܣ�������������ã�");
		}
	}
	
	static public void main(String str[]) throws UnsupportedEncodingException, MessagingException, BusiException{
		SendMail2 mail=new SendMail2();
		mail.setAddress("178820737@qq.com",SendMail2.TO);
		mail.setFromAddress("178820737@qq.com");
		mail.setSMTPHost("smtp.qq.com","","");
		mail.setSubject("����һ��");
		mail.setHtmlBody("");
		mail.sendBatch("true","178820737","",new Integer(888));
		System.out.println(111);
	}
}
