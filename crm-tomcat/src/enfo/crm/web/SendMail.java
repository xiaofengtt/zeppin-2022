package enfo.crm.web;
import java.util.*;
import java.io.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import enfo.crm.tools.*;
import com.jspsmart.upload.*;
import enfo.crm.dao.BusiException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

/**
 * �ʼ�����
 * @author dingyj
 * @date 2010-1-11
 * @version 1.0
 * ��������������ע�͵�ģ��Ϊ
 * ���� > ��ѡ�� > Java > �������� > �����ע��
 */
public class SendMail 
{ 	
	private PageContext pageContext;
	private boolean hasParse = false;
	private String strFolder = "C:\\Temp";    //�ļ��ϴ�������Ŀ¼
 
  private String filename; //�������ļ�����
  public static Vector file = new Vector(); //���ڱ��淢�͸������ļ����ļ���
  public static Vector cust = new Vector(); //���ڱ���ͻ�ID�ļ���
  public static Vector mail = new Vector(); //���ڱ���ͻ�ID�ļ���
  
  public SendMail(PageContext in_pageContext) 
  {		 
	try 
	{
		pageContext = in_pageContext;				  		 
	}
	catch (Exception e) 
	{
		pageContext = null;
	}
  }  
  //�����ռ�������
  public void attachfile(String fname) 
  {
	  file.addElement(fname);
  }  
    
  public void remobvefile(String fname) 
  {
		file.removeElement(fname);
  }

  public void remobveAllfile() 
  {
		file.removeAllElements();
  }
  
  //���ڱ���ͻ�ID
  public void attachCust(String cust_id) 
  {
		cust.addElement(cust_id);
  }  

  public void remobveAllCust() 
  {
		cust.removeAllElements();
  }
  
  //���ڱ���ͻ�Email
  public void attachEmail(String value) 
  {
		mail.addElement(value);
  }  

  public void remobveAllEmail() 
  {
		mail.removeAllElements();
  }
  
  public void sendMail() throws Exception
  {	
	try
	{    	
		//��html���л�ȡ�ʼ���Ϣ
		String ttitle = Utility.trimNull(pageContext.getRequest().getParameter("title"));
		String tfile = pageContext.getRequest().getParameter("file");   //������
		String tcontent = Utility.trimNull(pageContext.getRequest().getParameter("content"));
		String tflag = Utility.trimNull(pageContext.getRequest().getParameter("flag")); //�Ƿ���Ҫ��֤
		String sign = Utility.trimNull(pageContext.getRequest().getParameter("sign"));  //����������
		String operator_name = Utility.trimNull(pageContext.getRequest().getParameter("operator_name"));  //����������
		strFolder = strFolder + java.io.File.separator + operator_name;
  
		String smtp_server = Utility.trimNull(pageContext.getRequest().getParameter("smtp_server"));
		int smtp_port = Utility.parseInt(pageContext.getRequest().getParameter("smtp_port"),25);
		String smtp_user = Utility.trimNull(pageContext.getRequest().getParameter("smtp_user"));
		String smtp_userpwd = Utility.trimNull(pageContext.getRequest().getParameter("smtp_userpwd"));
		String mail_sign = Utility.trimNull(pageContext.getRequest().getParameter("mail_sign"));
		String mail_from = Utility.trimNull(pageContext.getRequest().getParameter("mail_from"));
		//�ж��Ƿ���Ҫ��֤	
		if(tflag.equals("1"))
		{
			tflag = "true";
		}
		else
		{
			tflag = "false";
		}	
		Properties props=new Properties();//Ҳ����Properties props = System.getProperties(); 
		props.put("mail.smtp.host",smtp_server);//�洢�����ʼ�����������Ϣ
		props.put("mail.smtp.auth",tflag);//ͬʱͨ����֤
		Session s=Session.getInstance(props);//���������½�һ���ʼ��Ự
		s.setDebug(true);
	
		MimeMessage message=new MimeMessage(s);//���ʼ��Ự�½�һ����Ϣ����
	
		//�����ʼ�
		InternetAddress from=new InternetAddress(mail_from,mail_sign);
		message.setFrom(from);//���÷�����
		
		//�����ı��������,�޷����������ʼ���ַ,���Բ���ҳ�洫�ݵķ���
		String tto = "";
		/*���ص�Ⱥ����ַ�����������ķ���*/
		/*for (int i = 0; i < mail.size(); i++)
		{
			  if(mail.get(i)!=null&&mail.get(i)!="") 
			  {	
				if(tto.equals(""))
					tto = (String)mail.get(i);
				else			  	
					tto = tto + "," + (String)mail.get(i)  ;
			  }
			  Utility.debugln("�ռ��� is " + tto.toString());		  	
		}
		
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(tto, false));//�����ռ���
		message.setSubject(ttitle);//��������	
		message.setSentDate(new Date());//���÷���ʱ�� */
	
		Multipart mp = new MimeMultipart(); //�����BodyPart�����뵽�˴�������Multipart��
		Enumeration efile = file.elements(); //����ö��������ı�������
		if(!(tfile.equals("")))
		{	
			while (efile.hasMoreElements()) 
			{
				MimeBodyPart mbp = new MimeBodyPart();
				filename = efile.nextElement().toString(); //ѡ���ÿһ��������
				FileDataSource fds = new FileDataSource(strFolder + java.io.File.separator + filename); //�õ�����Դ
				mbp.setDataHandler(new DataHandler(fds)); //�õ�������������BodyPart
				mbp.setFileName(fds.getName());	 //�õ��ļ���ͬ������BodyPart
				mp.addBodyPart(mbp);	      
			}
		}			
		MimeBodyPart mbp_body = new MimeBodyPart();
		mbp_body.setText(tcontent); //��Multipart�������	
		mbp_body.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312><div>"+tcontent+"</div>","text/html;charset=GB2312");	
		mp.addBodyPart(mbp_body);	
		message.setContent(mp); //Multipart���뵽�ʼ�	  
	
		Transport transport=s.getTransport("smtp");
		//�����ʼ���Ⱥ���ʼ�ʱ�����ص�Ⱥ����ַ�����������ķ���*/
		transport.connect(smtp_server,smtp_user,smtp_userpwd);//��smtp��ʽ��¼����
		for (int i = 0; i < mail.size(); i++)
		{
			if(mail.get(i)!=null&&mail.get(i)!="") 
			{	
				tto = (String)mail.get(i);
				if(!tto.equals(""))
				{			
					message.setRecipient(Message.RecipientType.TO,new InternetAddress(tto));//�����ռ���  	
					//message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(tto, false));
					message.setSubject(ttitle);//��������	
					message.setSentDate(new Date());//���÷���ʱ��
				
					message.saveChanges();//�洢�ʼ���Ϣ				
					//transport.send(message);
					transport.sendMessage(message,message.getAllRecipients());//�����ʼ�,���еڶ�������������
				}
			}
		}		
		transport.close();
	}
	catch(MessagingException e)
	{
		/*
		/һ���ʼ�����ʧ�ܣ����Ƴ������������ϴ��ĸ�������ɾ���ͻ�������������
		java.io.File dir = new java.io.File(strFolder);
		if (dir.isDirectory())
		{
			java.io.File[] files = dir.listFiles();
		
			for (int i = 0; i < files.length; i++) 
			{
				java.io.File f = files[i];
				f.delete();
			}
		}	
		 file.removeAllElements();
		 cust.removeAllElements(); 		
		 */ 
		 throw new BusiException("�����ʼ�ʧ��: " + e.getMessage());
	}
   }

}

