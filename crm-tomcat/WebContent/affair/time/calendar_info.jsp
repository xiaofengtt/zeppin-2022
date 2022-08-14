<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<% 
Integer serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));
//声明相关显示参数
String schedule_name = "";
String start_date = "";
String end_date ="";
String content ="";

//查找对象
ScheDulesLocal local = EJBFactory.getScheDules();
ScheDulesVO vo = new ScheDulesVO();

if(serial_no.intValue()>0){
	vo.setSerial_no(serial_no);
	List rsList = local.list_query(vo);
	Map rsMap = null;
	
	if(rsList.size()>0){
		rsMap = (Map)rsList.get(0);
		
		schedule_name = Utility.trimNull(rsMap.get("SCHEDULE_NAME"));
		start_date =  Utility.trimNull(rsMap.get("START_DATE"));
		end_date =  Utility.trimNull(rsMap.get("END_DATE"));
		content =  Utility.trimNull(rsMap.get("CONTENT"));
	}
}

if(start_date.length()>0){
	start_date = start_date.substring(0,16);
}

if(end_date.length()>0){
	end_date = end_date.substring(0,16);
}
%>

<HTML>
<HEAD>
	<meta http-equiv="X-UA-Compatible" content="IE=7" >
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<title><%=LocalUtilis.language("menu.scheduleInformationShows",clientLocale)%> </title><!--日程信息显示-->
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
	<base target="_self">
	
	<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>	
</HEAD>
<BODY class="BODY body-nox">
	<div align="left" class="page-title">
        <!--时间管理--><!--日程详细信息-->
		<font color="#215dc6"><b><%=LocalUtilis.language("message.timeManagement",clientLocale)%>>><%=LocalUtilis.language("message.scheduleDetails",clientLocale)%> </b></font>
	</div>
	<br/>
	<table  id="table1" cellSpacing="1" cellPadding="2" width="100%"  bgcolor="#CCCCCC">
		<tr style="background:F7F7F7;">
	 		<td colspan="2" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.scheduleDetails",clientLocale)%> </b></font></td>
	 	</tr>
	 	<tr style="background:F7F7F7;">	
	 		<td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.scheduleType",clientLocale)%> ：</font></td><!--日程类型-->
			<td width="75%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=schedule_name%></font></td>
	 	</tr>
	 	<tr style="background:F7F7F7;">	
	 		<td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.startTime",clientLocale)%> ：</font></td><!--开始时间-->
			<td width="75%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=start_date%></font></td>
	 	</tr>
	 	<tr style="background:F7F7F7;">	
	 		<td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.endTime",clientLocale)%> ：</font></td><!--结束时间-->
			<td width="75%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=end_date%></font></td>
	 	</tr>
	 	<tr style="background:F7F7F7;">	
	 		<td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.scheduleDescription",clientLocale)%> ：</font></td><!--日程描述-->
			<td width="75%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=content%></font></td>
	 	</tr>	 	
	</table>
	
	<div align="right" style="margin-top:10px;margin-right:5px">
        <!--确定-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;
	</div>
</BODY>
</HTML>