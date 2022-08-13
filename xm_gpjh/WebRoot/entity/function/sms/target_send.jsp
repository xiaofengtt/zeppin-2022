<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.Exception.*,com.whaty.platform.sms.*" %>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../../teacher/pub/priv.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<%!
   String fixnull(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			return "";
   		}
   		return str;
   }
%>
<body>
<%
	String mobilePhone = fixnull(request.getParameter("mobilePhone"));
	String id = request.getParameter("id");
	String subContent = request.getParameter("msgContent");
	
	SmsManagerPriv smsPriv = teacherOperationManage.getSmsManagerPriv();
	SmsManage smsManage = teacherOperationManage.getSmsManage(smsPriv);
	int suc = smsManage.updateSmsMessage(id,mobilePhone,subContent);
	
	
	if(suc >0) {
	%>
<script>
	alert("短信修改成功，请等待审核！");
	window.location = "./sms_list.jsp";
</script>
	<%
	}
	else
	{
%>
<script>
	alert("短信修改失败！");
	history.back();
</script>
<%
	}
 %>
</body>
</html>