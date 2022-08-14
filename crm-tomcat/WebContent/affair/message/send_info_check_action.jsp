<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.callcenter.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获取主键ID 
String[] temp_checks = request.getParameterValues("serial_no");
int actionFlag = Utility.parseInt(request.getParameter("actionFlag"),0);
Integer t_serial_no;
int way_type;
boolean bSuccess = false;

//获得对象
SmsRecordLocal sendTotal = EJBFactory.getSmsRecord();
SendSMSVO vo = new SendSMSVO();

if(temp_checks!=null){
	for(int i=0;i<temp_checks.length;i++){
		String[] param = Utility.splitString(temp_checks[i],"$");
		t_serial_no = Utility.parseInt(param[0], new Integer(0));
		way_type = Utility.parseInt(param[1],0);
		if(t_serial_no.intValue()!=0){				
			vo.setSerial_no(t_serial_no);
			vo.setInputOperator(input_operatorCode);
		
			vo.setCom_user_id(user_id);
			//vo.setFrom_email(from_email);
			//vo.setSmtp_password(smtp_userpwd);
			if(actionFlag == 1){
				if(way_type == 2)
					sendTotal.sendEmail(vo);
				else
					sendTotal.sendSms(t_serial_no,input_operatorCode);
				vo.setCheck_flag(new Integer(2));//审核通过并且发送信息
				sendTotal.checkMessage(vo);
				bSuccess = true;

			}else{
				vo.setCheck_flag(new Integer(-1));//审核不通过
				sendTotal.checkMessage(vo);
				bSuccess = true;
			}
		}
	}
}
sendTotal.remove();
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title> </title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	<%
	String alert_info = null;
	if(actionFlag ==1)
		alert_info = LocalUtilis.language("message.checkOK",clientLocale);
	else
		alert_info = LocalUtilis.language("message.notPass2",clientLocale) + LocalUtilis.language("message.success",clientLocale);
	if(bSuccess){%>
		alert("<%=alert_info%> ！");//提示成功信息
	<%}%>
	window.returnValue = 1;
	location =  "<%=request.getContextPath()%>/affair/message/send_info_check.jsp";
</SCRIPT>