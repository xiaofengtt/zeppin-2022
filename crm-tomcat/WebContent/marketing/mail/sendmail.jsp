<%@ page language="java" pageEncoding="GBK" import="java.util.*,enfo.crm.web.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
SendMail2 mail=new SendMail2();

//������������
boolean bSuccess = false;
Vector fileList = SendMail2.file;
String file_name = "";
String strFolder = "C:\\Temp\\" + input_operatorCode; //�ļ��ϴ�������Ŀ¼(TEMP+��½�û���)
String checkflag = Utility.trimNull(request.getParameter("checkflag"),"yes");

for (int i = 0; i < fileList.size(); i++){   	
	if((String)fileList.get(0)!=null){	
	   if(file_name.equals("")) {
	 	  file_name = (String)fileList.get(i);	   
	   }
	   else{
   			file_name = (String)fileList.get(i)+";"+ file_name;
		}	  
	}
}	

//��ҳ��ʱ�����������ʼ���Ϣ
if(checkflag.equals("yes")) {	
	java.io.File dir = new java.io.File(strFolder);
	//�ж��Ƿ��и�Ŀ¼
	if (dir.isDirectory()){
		java.io.File[] files = dir.listFiles();
		//���Ŀ¼�е��ļ�,��ɾ���ļ���Ŀ¼
		for (int i = 0; i < files.length; i++) {
			java.io.File f = files[i];
			f.delete();
		}
	}	
	mail.remobveAllfile();	//���߸��������е�����Ԫ��
	checkflag = "false";
}

//EMAIL������Ϣ
String to_email = Utility.trimNull(request.getParameter("to_email"));
String title = Utility.trimNull(request.getParameter("title"));
String content  = Utility.trimNull(request.getParameter("content"));
String attendManCode = Utility.trimNull(request.getParameter("attendManCode"));
String to_name = Utility.trimNull(request.getParameter("to_name"));
String from_name = Utility.trimNull(request.getParameter("from_name"));
String from_email = Utility.trimNull(request.getParameter("from_email"));
Integer auth_flag2 = Utility.parseInt(Utility.trimNull(request.getParameter("auth_flag2")),new Integer(0));
Integer action_flag = Utility.parseInt(Utility.trimNull(request.getParameter("action_flag")),new Integer(0));
String smtp_user = Utility.trimNull(request.getParameter("smtp_user"));
String smtp_userpwd = Utility.trimNull(request.getParameter("smtp_userpwd"));
String smtp_server  = Utility.trimNull(request.getParameter("smtp_server"));

if(to_email.length()==0){
	to_email = Argument.getOpEmailGroup(attendManCode);
}

if(from_name.length()==0){
	from_name = Argument.getOpNameByOpCode(input_operatorCode);
}

if(from_email.length()==0){
	from_email = Argument.getOpEmailName(input_operatorCode);
}



if(request.getMethod().equals("POST")){
	if(action_flag.intValue()==1){
		mail.setAddress(to_email,SendMail2.TO);
		mail.setFromAddress(from_email);
		mail.setSMTPHost(smtp_server,"","");
		mail.setSubject(title);
		mail.setHtmlBody(content);
		
		if(auth_flag2.intValue()==0){
			mail.sendBatch("false","","",input_operatorCode);
		}
		else if(auth_flag2.intValue()==1){
			mail.sendBatch("true",smtp_user,smtp_userpwd,input_operatorCode);
		}
		
		bSuccess = true;
	}
	else if(action_flag.intValue()==2){
			java.io.File dir = new java.io.File(strFolder);
			if (dir.isDirectory()){
				java.io.File[] files = dir.listFiles();
		
				for (int i = 0; i < files.length; i++) {
					java.io.File f = files[i];
					f.delete();
				}
			}	
			mail.remobveAllfile();				
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
<title><%=LocalUtilis.language("menu.sendMail",clientLocale)%> </title>
<!--�������׫д�ʼ�-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/webEditor/fckeditor.js"></script>	
<script language="javascript">
/*������֤*/
function validateForm(form)
{
	if(!sl_checkEmail(form.to_email, "<%=LocalUtilis.language("class.toEmail",clientLocale)%> "))	return false;//�ռ��˵�ַ
	if(!sl_check(form.title, "<%=LocalUtilis.language("class.title",clientLocale)%> "))	return false;//����
	if(!sl_check(form.content, "<%=LocalUtilis.language("class.title",clientLocale)%> "))	return false;//����
	if(form.smtp_server.value==""){
		sl_alert("<%=LocalUtilis.language("message.smtpServerError'/>��<fmt:message key='message.sandMailError'/>��<fmt:message key='message.configureServerTip",clientLocale)%> ...");
		//�ʼ���������ַΪ�գ����ܷ����ʼ��������÷�����...
		return false;
	}
	
	//�ж��Ƿ���Ҫ��֤	
	if(form.auth_flag.checked){
		if(form.smtp_user.value==""){
			sl_alert("<%=LocalUtilis.language("message.smtpServerError2'/>��<fmt:message key='message.sandMailError'/>��<fmt:message key='message.configureUserNameTip",clientLocale)%> ...");
			//�ʼ���������Ҫ�û�����֤�����ܷ����ʼ����������û���...
			return false;
		}
		
		if(form.smtp_userpwd.value==""){
			sl_alert("<%=LocalUtilis.language("message.smtpServerError3'/>��<fmt:message key='message.sandMailError'/>��<fmt:message key='message.configureUserPwdTip",clientLocale)%> ...");
			//�ʼ���������Ҫ������֤�����ܷ����ʼ����������û�������...
			return false;
		}
	}	
	else{
		document.getElementById("auth_flag2").value=0;
	}
	
	if(form.from_email.value==""){
		sl_alert("<%=LocalUtilis.language("message.fromEmialError'/>��<fmt:message key='message.sandMailError'/>��<fmt:message key='message.configureFromEmailTip",clientLocale)%> ...");
		//�����˵�ַΪ�գ����ܷ����ʼ��������÷�����...
		return false;
	}
	
	document.getElementById("action_flag").value=1;
	return sl_confirm("<%=LocalUtilis.language("message.send",clientLocale)%> ");<!--����-->
}

window.onload = function(){
	var v_bSuccess = document.getElementById("bSuccess").value;
	var v_action_flag = document.getElementById("action_flag").value;
	
	if(v_action_flag==2){
		location.href = "<%=request.getContextPath()%>/marketing/base/meeting_list.jsp";
	}
		
	if(v_bSuccess=="true"){		
			sl_alert("<%=LocalUtilis.language("message.sendOK",clientLocale)%> ��");<!--���ͳɹ�-->
	}
	showAuth();
}

function showAuth(){
	var auth_flag = document.getElementById("auth_flag");

	if(auth_flag.checked){
		document.getElementById("auth_flag2").value=1;
		document.getElementById("authTr").style.display="";
	}
	else{
		document.getElementById("auth_flag2").value=0;
		document.getElementById("authTr").style.display="none";
	}
}

//ѡ���ռ���
function choose_operator(operatorCodes){
	var attendManCode = document.getElementById("attendManCode").value;
	var url = "operator_email_choose.jsp?operatorCodes="+attendManCode;
	var ret = showModalDialog(url,'', 'dialogWidth:550px;dialogHeight:450px;status:0;help:0');
	
	var retStr =ret.split("&");
	
	document.getElementById("attendManCode").value=retStr[0];
	document.getElementsByName("to_name")[0].value=retStr[1];
	document.getElementsByName("to_email")[0].value=retStr[2];
}

/*��Ӹ���*/
function addFiles(){   
	if(showModalDialog('files_add.jsp?title='+ document.theform.title.value,'','dialogWidth:390px;dialogHeight:250px;status:0;help:0')!=null);
	refreshPage()
}

function refreshPage(){
	var url = "sendmail.jsp?attendManCode="+document.getElementsByName("attendManCode")[0].value;
	var url = url + "&action_flag=" + document.getElementsByName("action_flag")[0].value;
	var url = url + "&to_email=" + document.getElementsByName("to_email")[0].value;
	var url = url + "&to_name=" + document.getElementsByName("to_name")[0].value;
	var url = url + "&from_email=" + document.getElementsByName("from_email")[0].value;
	var url = url + "&from_name=" + document.getElementsByName("from_name")[0].value;
	var url = url + "&smtp_server=" + document.getElementsByName("smtp_server")[0].value;
	var url = url + "&title=" + document.getElementsByName("title")[0].value;
	var url = url + "&content=" + document.getElementsByName("content")[0].value;
	var url = url + "&auth_flag2=" + document.getElementsByName("auth_flag2")[0].value;
	var url = url + "&smtp_user=" + document.getElementsByName("smtp_user")[0].value;
	var url = url + "&smtp_userpwd=" + document.getElementsByName("smtp_userpwd")[0].value;
	
	window.location.href=url;
}

/*ȡ�������ʼ�*/
function removeFiles(){   
	if(document.theform.file.value != ""){   	
		document.theform.action_flag.value = 2;
		document.theform.submit();			
	}
	else{
		location.href = "<%=request.getContextPath()%>/marketing/base/meeting_list.jsp";
	}		
}
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post"  action="sendmail.jsp" onsubmit="javascript:return validateForm(this);" >
<!--�޸ĳɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" name="attendManCode" id="attendManCode" value="<%= attendManCode%>"/>
<input type="hidden" name="action_flag" id="action_flag" value="<%= action_flag%>"/>
<input type="hidden" name="checkflag" id="checkflag" value="<%= checkflag%>"/>

<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("menu.sendMail2",clientLocale)%> </b></font>
</div><!--׫д�ʼ�-->
<hr noshade color="#808080" size="1" width="98%">

<div style="margin-left:10px">
<table border="0" width="98%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC">
		<tr style="background:F7F7F7;">
			<td width="160px">
				<p align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<a href="javascript:choose_operator('<%=attendManCode%>');"><%=LocalUtilis.language("class.toEmail",clientLocale)%> </a></font>��</p>
			</td><!--�ռ��˵�ַ-->
			<td width="*">
				<input name="to_email" size="150" onkeydown="javascript:nextKeyPress(this)" value="<%=to_email%>" >&nbsp;(<%=LocalUtilis.language("message.toName",clientLocale)%> )
			</td><!--���ռ����Է�ŷֿ�-->
		</tr>
		
		<tr style="background:F7F7F7;">
			<td width="160px">
				<p align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.toName",clientLocale)%> ��</font></p><!--�ռ�������-->
			</td>
			<td width="*">
				<input  size="150" name="to_name" onkeydown="javascript:nextKeyPress(this)" value="<%=to_name%>" />&nbsp;
			</td>
		</tr>
		
		<tr style="background:F7F7F7;">
			<td width="160px">
				<p align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.fromEmail",clientLocale)%> ��</font></p><!--�����˵�ַ-->
			</td>
			<td width="*"><input name="from_email" size="150" onkeydown="javascript:nextKeyPress(this)" value="<%=from_email%>" >&nbsp;</td>
		</tr>
		
		<tr style="background:F7F7F7;">
			<td width="160px">
				<p align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.fromName",clientLocale)%> ��</font></p><!--����������-->
			</td>
			<td width="*"><input name="from_name" size="150" onkeydown="javascript:nextKeyPress(this)" value="<%=from_name%>" >&nbsp;</td>
		</tr>
		
		<tr style="background:F7F7F7;">
			<td  align="left">
				<p align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.smtpService",clientLocale)%> ��</font></p><!--SMTP������-->
			</td>
			<td width="*"><input name="smtp_server" size="30" onkeydown="javascript:nextKeyPress(this)" value="<%=smtp_server%>" >&nbsp;&nbsp;<%=LocalUtilis.language("message.example",clientLocale)%> ��smtp.163.com</td>
		</tr><!--����-->
		<tr style="background:F7F7F7;">
			<td align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.title",clientLocale)%> </font>��</td><!--����-->
			<td><input name="title" size="150" onkeydown="javascript:nextKeyPress(this)" maxlength="150" value="<%=title%>"></td>
		</tr>
		<tr style="background:F7F7F7;">
			<td align="left" valign="top"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.content2",clientLocale)%> ��</font></td><!--����-->
			<td>
				<textarea rows="10" name="content" cols=30"><%=content%></textarea>
				<script type="text/javascript">
						var oFCKeditor = new FCKeditor('content') ;
						oFCKeditor.BasePath = '/webEditor/' ;
						oFCKeditor.Width = '100%' ;
						oFCKeditor.Height = '150';
						oFCKeditor.ReplaceTextarea();							
				</script>
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.fileName2",clientLocale)%> ��</font></td><!--����-->							
			<td>
				<input name="file" size="100" onkeydown="javascript:nextKeyPress(this)" value="<%= file_name%>" class="edline" readonly>&nbsp;&nbsp;	
				<button type="button"  class="xpbutton4" accessKey=v onclick="javascript:addFiles()"><%=LocalUtilis.language("menu.filesAdd",clientLocale)%> (<u>V</u>)</button>	<!--��Ӹ���-->
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.authFlag",clientLocale)%> ��</font></td><!--�Ƿ���֤-->
			<td>
			<input type="checkbox" name="auth_flag"  id="auth_flag" value="1" onkeydown="javascript:nextKeyPress(this)" class="flatcheckbox" onclick="javascript:showAuth();" checked/>
			<input type="hidden" name="auth_flag2" id="auth_flag2" value="<%= auth_flag2%>"  />
			</td>
		</tr>
		<tr style="display:;background:F7F7F7;" id="authTr">
			<td align="left"><font size="2" face="΢���ź�">&nbsp;&nbsp;</font></td>
			<td>
				<font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("login.userName",clientLocale)%> ��</font><input name="smtp_user" size="30" onkeydown="javascript:nextKeyPress(this)" value="<%=smtp_user%>" >&nbsp;&nbsp;
				<!--�û���-->
				<font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("login.password",clientLocale)%> ��</font><input type="password" name="smtp_userpwd" size="30" onkeydown="javascript:nextKeyPress(this)" value="<%=smtp_userpwd%>" >&nbsp;&nbsp;
				<!--����-->
			</td>
		</tr>
		<tr style="background:F7F7F7;">
			<td align="right" colspan="2">
					<button type="button"  class="xpbutton3" accessKey=s onclick="javascript:if(document.theform.onsubmit()) { document.theform.submit();}"><%=LocalUtilis.language("message.send",clientLocale)%> (<u>S</u>)</button>
					&nbsp;&nbsp;<!--����-->
					<button type="button"  class="xpbutton3" accessKey=c onclick="javascript:window.returnValue=null;removeFiles();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
					&nbsp;&nbsp;<!--ȡ��-->
			</td>
		</tr>
	</table>
</div>

</form>
</BODY>
</HTML>



