<%@ page language="java" pageEncoding="GBK" import="java.util.*,enfo.crm.web.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
SendMail mail = new SendMail(pageContext);
Vector fileList = SendMail.file;

String strFolder = "C:\\Temp\\" + input_operatorCode; //文件上传服务器目录(TEMP+登陆用户名)

boolean bSuccess = false;
boolean bCancel = false;
String flag = request.getParameter("flag");
String flag2 = request.getParameter("flag2");
String to_email = Utility.trimNull(request.getParameter("to_email"));//收件人邮箱
String title = Utility.trimNull(request.getParameter("title"));
String content = Utility.trimNull(request.getParameter("content"));

String file_name = "";

//获得地址栏参数
String checkFlag = Utility.trimNull(request.getParameter("checkflag"));
String checkFlag2 = Utility.trimNull(request.getParameter("checkflag2"));
String [] cust_id_item = request.getParameterValues("cust_id");//发送邮件时，选中的客户ID

//获取发送邮件地址及参数
Map map = Argument.getMailMessage();
String strSMTPHost = Utility.trimNull(map.get("SMTP_SERVER"));
String mailFrom = Utility.trimNull(map.get("MAIL_FROM"));
String user = Utility.trimNull(map.get("SMTP_USER"));
String password = Utility.trimNull(map.get("SMTP_USERPWD"));

//打开页面时清空所有相关邮件信息
if(checkFlag.equals("yes"))   
{
	java.io.File dir = new java.io.File(strFolder);
	//判断是否有该目录
	if (dir.isDirectory())
	{
		//获得目录中的文件,并删除文件或目录
		java.io.File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) 
		{
			java.io.File f = files[i];
			f.delete();
		}
	}	
	mail.remobveAllfile(); //移走附件集合中的所有元素	
	mail.remobveAllCust();  //收件人信息保存完之后把收件人信息队列清除
	mail.remobveAllEmail();  //清空所有Email地址
	//如果为群发邮件就进行客户查询	
	if(checkFlag2.equals("yes"))
	{		
		String sQuery = request.getParameter("sQuery");
		String cust_no = request.getParameter("cust_no");
		Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
		CustomerLocal cust_local = EJBFactory.getCustomer();
		CustomerVO cust_vo = new CustomerVO();
		cust_vo.setInput_man(input_operatorCode);
		cust_vo.setBook_code(new Integer(0));
		cust_vo.setProduct_id(product_id);
		//获得操作员的所有客户
		List cust_list = cust_local.listProcAll(cust_vo);
		Map cust_map = new HashMap();
		//将客户编号和Email存入SendMail.java的队列里面
		for(int j=0; j<cust_list.size(); j++)
		{
			cust_map = (Map)cust_list.get(j);
			String temp = Utility.trimNull(cust_map.get("E_MAIL"));
			if((temp!=null) && (!temp.equals(""))){
				mail.attachCust(Utility.trimNull(cust_map.get("CUST_ID")));
				if(to_email.equals(""))
					to_email = temp;
				else
					to_email = to_email + "," + temp;
			}
		}
		cust_local.remove();
	}else{
		//选择其客户发送邮件
		if (cust_id_item != null)
		{   
			for(int i = 0;i < cust_id_item.length; i++)
			{  	
				String [] paras = Utility.splitString(cust_id_item[i], ",");
				for(int n = 0;n < paras.length;n++)	
				{				
					String temp = Argument.getEmailName(Utility.parseInt(paras[n], new Integer(0)),input_operatorCode);
					if((temp!=null) && (!temp.equals("")))
					{	
						mail.attachCust(paras[n].trim());
						if(to_email.equals(""))
						{
							to_email = temp;
						}
						else
						{
							to_email =to_email + "," + temp;
						}
					}	
				}						
			}
		}
	}	
}	

//提交发送邮件信息
if (request.getMethod().equals("POST"))
{		
	if(flag2.equals("1"))
	{
		String[] emails = to_email.split(",");
		for(int i=0;i<emails.length;i++){
			mail.attachEmail(emails[i]);
		}		
		mail.sendMail();
		//发送邮件的同时将邮件内容保存到数据库里
		EmailListLocal email_local = EJBFactory.getEmailList();
		EmailListVO email_vo = new EmailListVO();
		email_vo.setSubject(title);
		email_vo.setBody(content);
		email_vo.setInput_man(input_operatorCode);
		Integer mail_serial_no = email_local.append(email_vo);	//保存邮件信息的时候传出mail_serial_no作为邮件联系人的mail_serial_no和邮件附件的mail_serial_no
		//保存收件人信息
		EmailRecipientsLocal email_rec_local = EJBFactory.getEmailRecipients();
		EmailRecipientsVO email_rec_vo = new EmailRecipientsVO();
		Vector custList = SendMail.cust;		
		for (int i = 0; i < custList.size(); i++)
		{   
			if(custList.get(0)!=null)
			{	
	   			Integer cust_id = Utility.parseInt((String)custList.get(i),null);
	   			
	   			String to = Argument.getEmailName(cust_id,input_operatorCode);
	   			if(!to.equals(""))
	   			{												
					email_rec_vo.setMail_serial_no(new Long(mail_serial_no.toString()));
					email_rec_vo.setRecipients(to);
					email_rec_vo.setCust_id(cust_id);
					email_rec_vo.setInput_man(input_operatorCode);
					email_rec_local.appendReci(email_rec_vo);													
				}	
			}	
		}				
		//保存附件
		EmailAttachLocal email_attach_local = EJBFactory.getEmailAttach();
		EmailAttachVO email_attach_vo = new EmailAttachVO();
		for(int i=0;i<fileList.size();i++)			
		{			
			email_attach_vo.setMail_serial_no(new Long(mail_serial_no.toString()));
			email_attach_vo.setAttach_name((String)fileList.get(i));
			email_attach_vo.setInput_man(input_operatorCode);
			Integer serial_no = email_attach_local.appendAttach(email_attach_vo);			
			//在服务器端保存文件				
			email_attach_local.updateToDB(new Long(serial_no.toString()),(String)fileList.get(i),strFolder);			
		}
				
		//移除实例
		email_local.remove();
		email_rec_local.remove();
		email_attach_local.remove();	
		
		bSuccess = true;		
	}
	else if(flag2.equals("2"))
	{
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
			mail.remobveAllfile();
			mail.remobveAllCust();						
			bCancel = true;		
	}
	else
	{
		for (int i = 0; i < fileList.size(); i++)
		{   	
			if((String)fileList.get(0)!=null)
			{	
			   if(file_name.equals(""))	
			   {
			 	  file_name = (String)fileList.get(i);	   
			   }
			   else
			   {
	   				file_name = (String)fileList.get(i)+";"+ file_name;
			   }	  
			}	
		}				
	}	
}
%>


<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<title><%=LocalUtilis.language("menu.sendMail2",clientLocale)%> </title><!--撰写邮件-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess)
{
%>
	window.returnValue = true;	
	window.close();	
<%}%>

<%if (bCancel)
{
%>
	window.returnValue = null;	
	window.close();	
<%}%>

/*数据验证*/
function validateForm(form)
{
	if(!sl_check(form.to, "<%=LocalUtilis.language("message.recipient",clientLocale)%> "))	return false;//收件人
	if(!sl_check(form.title, "<%=LocalUtilis.language("class.title",clientLocale)%> "))	return false;//主题
	if(!sl_check(form.content, "<%=LocalUtilis.language("class.content2",clientLocale)%> "))	return false;//内容
	if(form.smtp_server.value=="")
	{
		//邮件服务器地址为空  //不能发送邮件  //请配置服务器
        sl_alert("<%=LocalUtilis.language("'message.smtpServerError'/>，<fmt:message key='message.sandMailError'/>！<fmt:message key='message.configureServerTip'",clientLocale)%> ...");
		return false;
	}
	//判断是否需要验证	
	if(form.flag.checked)
	{
		if(form.smtp_user.value=="")
		{
			//邮件服务器需要用户名验证  //不能发送邮件  //请配置用户名
            sl_alert("<%=LocalUtilis.language("message.smtpServerError2'/>，<fmt:message key='message.sandMailError'/>！<fmt:message key='message.configureUserNameTip",clientLocale)%> ...");
			return false;
		}
		if(form.smtp_userpwd.value=="")
		{
			//邮件服务器需要密码验证  //不能发送邮件  //请配置用户名密码
            sl_alert("<%=LocalUtilis.language("message.smtpServerError3'/>，<fmt:message key='message.sandMailError'/>！<fmt:message key='message.configureUserPwdTip",clientLocale)%> ...");
			return false;
		}
	}	
	if(form.mail_from.value=="")
	{
        //发件人地址为空  //不能发送邮件  //请配置发件人
		sl_alert("<%=LocalUtilis.language("message.fromEmialError'/>，<fmt:message key='message.sandMailError'/>！<fmt:message key='message.configureFromEmailTip",clientLocale)%> ...");
		return false;
	}
	form.flag2.value ="1";
	return sl_confirm("<%=LocalUtilis.language("message.send",clientLocale)%> ");//发送
}

/*添加附件*/
function addFiles()
{   
	if(showModalDialog('files_add.jsp?title='+ document.theform.title.value,'','dialogWidth:390px;dialogHeight:250px;status:0;help:0')!=null);
	document.theform.submit();
}

/*取消发送邮件*/
function removeFiles()
{   
	if(document.theform.file.value != "")
	{   	
		document.theform.flag2.value = 2;
		document.theform.submit();			
	}
	else
	{
		window.close();
	}		
}
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="sendmail.jsp" onsubmit="javascript:return validateForm(this);" >
<input type="hidden" name="flag2" value="">
<input type="hidden" name="checkflag" value="<%=checkFlag %>">
<input type="hidden" name="checkflag2" value="<%=checkFlag2 %>">
<input type="hidden" name="smtp_server" value="<%=strSMTPHost%>">
<input type="hidden" name="smtp_user" value="<%=user%>">
<input type="hidden" name="smtp_userpwd" value="<%=password%>">
<input type="hidden" name="mail_from" value="<%=mailFrom%>">
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<!--撰写邮件-->
                                <td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("menu.sendMail2",clientLocale)%> </b></font></td>
							</tr>
							<tr>
								<td>
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3">
							<tr>
								<td width="10%">
								<p align="right"><%=LocalUtilis.language("message.recipient",clientLocale)%> </p><!--收件人-->
								</td>
								<!--多收件人以逗号分开-->
                                <td width="90%"><input name="to_email" size="100" onkeydown="javascript:nextKeyPress(this)" value="<%=to_email%>" class="edline">&nbsp;(<%=LocalUtilis.language("message.multRecipients",clientLocale)%> )</td>
							</tr>
							<tr>
								<td align="right" width="10%"><%=LocalUtilis.language("class.fileName2",clientLocale)%> :</td><!--附件-->
								<td width="90%"><input name="file" size="100" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(file_name)%>" class="edline" readonly></td>
							</tr>
							<tr>
								<td align="right" width="10%"><%=LocalUtilis.language("class.title",clientLocale)%> :</td><!--主题-->
								<td width="90%"><input name="title" size="100" onkeydown="javascript:nextKeyPress(this)" maxlength="100" value="<%=title%>"></td>
							</tr>
							<tr>
								<td align="right" width="10%"><%=LocalUtilis.language("class.content2",clientLocale)%> :</td><!--内容-->
								<td width="90%"><textarea rows="20" name="content" cols="120"><%=content%></textarea></td>
							</tr>
							<tr>
								<td align="right" width="10%"><%=LocalUtilis.language("class.authFlag",clientLocale)%> :</td><!--是否验证-->
								<td width="90%"><input type="checkbox" name="flag" value="1" onkeydown="javascript:nextKeyPress(this)" class="flatcheckbox" checked></td>
							</tr>
						</table>
						<br>
						<table border="0" width="100%">
							<tr>
								<td align="right">	
								<button type="button"  class="xpbutton4" accessKey=v onclick="javascript:addFiles()"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%> (<u>V</u>)</button><!--添加附件-->
								&nbsp;&nbsp;							
								<!--发送-->
                                <button type="button"  class="xpbutton3" accessKey=s onclick="javascript:if(document.theform.onsubmit()) { document.theform.submit();}"><%=LocalUtilis.language("message.send",clientLocale)%> (<u>S</u>)</button>
								&nbsp;&nbsp;
								<!--取消-->
                                <button type="button"  class="xpbutton3" accessKey=c onclick="javascript:window.returnValue=null;removeFiles();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>