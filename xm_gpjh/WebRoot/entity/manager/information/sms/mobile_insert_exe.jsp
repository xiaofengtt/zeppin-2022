<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="com.whaty.platform.Exception.*,com.whaty.platform.sms.*" %>
<jsp:directive.page import="com.whaty.platform.sms.imp.OracleSmsManage"/>

<body>
<%
String mobiles = "";

try {
	String scr = (String) session.getValue("scr"); 
	String T_filename = (String)session.getValue("T_filename");
	String srcFile = scr + T_filename;
	//out.print("scr:" + srcFile);
	SmsManage smsManage = new OracleSmsManage();
	mobiles = smsManage.getMobiles(srcFile);
	//out.print("Mobiles:" + mobiles);

} catch(PlatformException e) {
%>
<script language=javascript>
		alert("<%=e.getMessage() %>");
		history.back();
</script>
<%
	return;
}
%>
<script language="javascript">
	alert("导入完毕,请返回！");
	var mobileObj = window.opener.sms.mobilePhone
	if(mobileObj.value.length ==0) {
		mobileObj.value = "<%=mobiles%>";
	} else {
		mobileObj.value = mobileObj.value + "," + "<%=mobiles%>";
	}
	window.close();
</script>