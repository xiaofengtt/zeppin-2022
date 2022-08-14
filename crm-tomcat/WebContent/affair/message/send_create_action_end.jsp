<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.web.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.callcenter.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获取主键ID 
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
String submit_flag = Utility.trimNull(request.getParameter("submit_flag"));
int way_type = Utility.parseInt(request.getParameter("way_type"), 0);
String from_email = Utility.trimNull(request.getParameter("from_email"));
String smtp_userpwd = Utility.trimNull(request.getParameter("smtp_userpwd")); 
//获得对象
boolean bSuccess = false;
SmsRecordLocal sendTotal = EJBFactory.getSmsRecord();
SendSMSVO vo = new SendSMSVO();
SendMail2 mail=new SendMail2();
if(serial_no.intValue()!=0){
	vo.setSerial_no(serial_no);
	vo.setInputOperator(input_operatorCode);
	if("send".equals(submit_flag)){
		if(way_type == 2){//邮件
			vo.setInputOperator(input_operatorCode);
		
			vo.setCom_user_id(user_id);
			vo.setFrom_email(from_email);
			vo.setSmtp_password(smtp_userpwd);
			sendTotal.sendEmail(vo);
		}else{
			sendTotal.sendSms(serial_no,input_operatorCode);
		}
		bSuccess = true;
		vo.setCheck_flag(new Integer(3));
		sendTotal.checkMessage(vo);//即时发送,check_flag=3时无需审核
	}else{
		vo.setCheck_flag(new Integer(1));//提交审核
		sendTotal.checkMessage(vo);	
	}
}
sendTotal.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	<%if("send".equals(submit_flag)){%>
		alert("<%=LocalUtilis.language("message.sendOK",clientLocale)%> ！");//发送成功;
	<%}else{%>
		alert("<%=LocalUtilis.language("message.tSuccessfullysub",clientLocale)%> ！");//提交审核成功
	<%}%>

	window.returnValue = 1;
	window.parent.location.reload();
	window.close();	
</SCRIPT>