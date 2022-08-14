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
 * 邮件发送
 * @author dingyj
 * @date 2010-1-11
 * @version 1.0
 * 更改所生成类型注释的模板为
 * 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 */
public class SendMail 
{ 	
	private PageContext pageContext;
	private boolean hasParse = false;
	private String strFolder = "C:\\Temp";    //文件上传服务器目录
 
  private String filename; //附件的文件名；
  public static Vector file = new Vector(); //用于保存发送附件的文件名的集合
  public static Vector cust = new Vector(); //用于保存客户ID的集合
  public static Vector mail = new Vector(); //用于保存客户ID的集合
  
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
  //用于收集附件名
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
  
  //用于保存客户ID
  public void attachCust(String cust_id) 
  {
		cust.addElement(cust_id);
  }  

  public void remobveAllCust() 
  {
		cust.removeAllElements();
  }
  
  //用于保存客户Email
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
		//从html表单中获取邮件信息
		String ttitle = Utility.trimNull(pageContext.getRequest().getParameter("title"));
		String tfile = pageContext.getRequest().getParameter("file");   //附件名
		String tcontent = Utility.trimNull(pageContext.getRequest().getParameter("content"));
		String tflag = Utility.trimNull(pageContext.getRequest().getParameter("flag")); //是否需要验证
		String sign = Utility.trimNull(pageContext.getRequest().getParameter("sign"));  //发件人姓名
		String operator_name = Utility.trimNull(pageContext.getRequest().getParameter("operator_name"));  //发件人姓名
		strFolder = strFolder + java.io.File.separator + operator_name;
  
		String smtp_server = Utility.trimNull(pageContext.getRequest().getParameter("smtp_server"));
		int smtp_port = Utility.parseInt(pageContext.getRequest().getParameter("smtp_port"),25);
		String smtp_user = Utility.trimNull(pageContext.getRequest().getParameter("smtp_user"));
		String smtp_userpwd = Utility.trimNull(pageContext.getRequest().getParameter("smtp_userpwd"));
		String mail_sign = Utility.trimNull(pageContext.getRequest().getParameter("mail_sign"));
		String mail_from = Utility.trimNull(pageContext.getRequest().getParameter("mail_from"));
		//判断是否需要验证	
		if(tflag.equals("1"))
		{
			tflag = "true";
		}
		else
		{
			tflag = "false";
		}	
		Properties props=new Properties();//也可用Properties props = System.getProperties(); 
		props.put("mail.smtp.host",smtp_server);//存储发送邮件服务器的信息
		props.put("mail.smtp.auth",tflag);//同时通过验证
		Session s=Session.getInstance(props);//根据属性新建一个邮件会话
		s.setDebug(true);
	
		MimeMessage message=new MimeMessage(s);//由邮件会话新建一个消息对象
	
		//设置邮件
		InternetAddress from=new InternetAddress(mail_from,mail_sign);
		message.setFrom(from);//设置发件人
		
		//由于文本框的限制,无法接受所有邮件地址,所以不用页面传递的方法
		String tto = "";
		/*隐藏掉群发地址，单个单个的发送*/
		/*for (int i = 0; i < mail.size(); i++)
		{
			  if(mail.get(i)!=null&&mail.get(i)!="") 
			  {	
				if(tto.equals(""))
					tto = (String)mail.get(i);
				else			  	
					tto = tto + "," + (String)mail.get(i)  ;
			  }
			  Utility.debugln("收件人 is " + tto.toString());		  	
		}
		
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(tto, false));//设置收件人
		message.setSubject(ttitle);//设置主题	
		message.setSentDate(new Date());//设置发信时间 */
	
		Multipart mp = new MimeMultipart(); //后面的BodyPart将加入到此处创建的Multipart中
		Enumeration efile = file.elements(); //利用枚举器方便的遍历集合
		if(!(tfile.equals("")))
		{	
			while (efile.hasMoreElements()) 
			{
				MimeBodyPart mbp = new MimeBodyPart();
				filename = efile.nextElement().toString(); //选择出每一个附件名
				FileDataSource fds = new FileDataSource(strFolder + java.io.File.separator + filename); //得到数据源
				mbp.setDataHandler(new DataHandler(fds)); //得到附件本身并置入BodyPart
				mbp.setFileName(fds.getName());	 //得到文件名同样置入BodyPart
				mp.addBodyPart(mbp);	      
			}
		}			
		MimeBodyPart mbp_body = new MimeBodyPart();
		mbp_body.setText(tcontent); //向Multipart添加正文	
		mbp_body.setContent("<meta http-equiv=Content-Type content=text/html; charset=gb2312><div>"+tcontent+"</div>","text/html;charset=GB2312");	
		mp.addBodyPart(mbp_body);	
		message.setContent(mp); //Multipart加入到邮件	  
	
		Transport transport=s.getTransport("smtp");
		//发送邮件，群发邮件时，隐藏掉群发地址，单个单个的发送*/
		transport.connect(smtp_server,smtp_user,smtp_userpwd);//以smtp方式登录邮箱
		for (int i = 0; i < mail.size(); i++)
		{
			if(mail.get(i)!=null&&mail.get(i)!="") 
			{	
				tto = (String)mail.get(i);
				if(!tto.equals(""))
				{			
					message.setRecipient(Message.RecipientType.TO,new InternetAddress(tto));//设置收件人  	
					//message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(tto, false));
					message.setSubject(ttitle);//设置主题	
					message.setSentDate(new Date());//设置发信时间
				
					message.saveChanges();//存储邮件信息				
					//transport.send(message);
					transport.sendMessage(message,message.getAllRecipients());//发送邮件,其中第二个参数是所有
				}
			}
		}		
		transport.close();
	}
	catch(MessagingException e)
	{
		/*
		/一旦邮件发送失败，就移除服务器上已上传的附件，并删除客户、附件名队列
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
		 throw new BusiException("发送邮件失败: " + e.getMessage());
	}
   }

}

