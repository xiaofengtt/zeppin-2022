<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*"%>
<%@ include file = "/includes/operator.inc" %>

<%
CellUpload file = null;
String filename = "";
boolean success = false;
if (request.getMethod().equals("POST"))
{
	file = new CellUpload(pageContext);
	
	file.parseRequest();
	if (file.uploadFile())
	{		
		filename = file.getFilePathName();
		
		String temp = file.getFileName();
		if(Cell.updateMenu(file.getRptId(temp),file.getRptTitle(temp),file.getMenuFolderName(),input_operatorCode))		
			success = true;
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
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<title>打开模板文件</title>
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/report.js"></SCRIPT>
<script language=javascript>
<%
if(success)	{
%>
	alert("上传文件成功！");	
<%}%>

function openfile()
{	
	if(!checkCellName(document.theform.filename.value))
		return false;	

	location.href = "init.jsp?filename=" + changeSeperator(document.theform.filename.value);
}

function selfdefinition()
{
	if(!checkCellName(document.theform.filename.value))
		return false;	

	//location.href = "selfedit.jsp?filename=" + changeSeperator(document.theform.filename.value);
	var status = 'dialogTop:0px;dialogLeft:0px;dialogWidth:'+screen.availWidth+'px;dialogHeight:'+screen.availHeight+'px;status:0;help:0';
	showModalDialog('selfedit.jsp?filename=' + changeSeperator(document.theform.filename.value),'',status);
}

function uploadfile()
{
	if(!checkCellName(document.theform.filename.value))
		return false;		

	document.theform.submit();
}
</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="openfile.jsp" ENCTYPE="multipart/form-data">
<input type="hidden" name="flag" value="true">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>打开上传模板文件</b></font></td>
	</tr>
	<tr>
		<td>
			<hr noshade color="#808080" size="1">
		</td>
	</tr>
	</table>
	<br>
	<table border="0" width="100%">
		<tr>
			<td align="center" width="60%">文件名:<input type="file" style="font-size: 9pt" name="filename" size="40" onkeydown="javascript:return false;"></td>								
			<td align="right">				
				<button type="button"  class="xpbutton3" accessKey=o onclick="javascript:openfile();">打开(<u>O</u>)</button>
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=o onclick="javascript:selfdefinition();">自定义(<u>O</u>)</button>
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=u onclick="javascript:uploadfile();">上传(<u>U</u>)</button>
				&nbsp;&nbsp;
				</td>
		</tr>
	</table>
</form>
</BODY>
</HTML>

