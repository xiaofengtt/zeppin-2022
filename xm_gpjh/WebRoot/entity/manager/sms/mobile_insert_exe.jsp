<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="jxl.*,java.text.*,java.io.*,java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.Exception.PlatformException"%>
<%@ page import="com.whaty.platform.Exception.*,com.whaty.platform.sms.*,jxl.read.biff.BiffException" %>
<%@ include file="../pub/priv.jsp"%><html>
<body>
<%
String mobiles = "";

try {
	
	//OracleSmsManage
	String scr = (String) session.getValue("scr"); 
	String T_filename = (String)session.getValue("T_filename");
	String srcFile = scr + T_filename;
	//out.print("scr:" + srcFile);
	mobiles = "";
	try {
		Workbook w = Workbook.getWorkbook(new File(srcFile));
		Sheet sheet = w.getSheet(0);
		int rows = sheet.getRows();

		for (int i = 1; i < rows; i++) {
			String name = sheet.getCell(0, i).getContents().trim();
			String mobile = sheet.getCell(1, i).getContents().trim();
			mobiles += mobile +"|"+name+ ",";
		}
	} catch (BiffException e) {
		
	} catch (IOException e) {
		
	}
	if (mobiles.length() > 0)
		mobiles = mobiles.substring(0, mobiles.length() - 1);
	
	//out.print("Mobiles:" + mobiles);

} catch(Exception e) {
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