<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.webreport.*,enfo.crm.tools.*" %>
<%
String name = CellHelper.trimNull(request.getParameter("name"));
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<title>��������</title>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/audit.js"></SCRIPT>
<script language=javascript>
function exportFiles()
{
	var obj = window.dialogArguments
	var path = "C:\\";
	if(document.theform.serial_no[0].checked)
		ExportExcel(obj);
	/*if(document.theform.serial_no[1].checked)
		ExportCell(obj,0);
	if(document.theform.serial_no[2].checked)
		ExportTXT(obj);*/
	if(document.theform.serial_no[1].checked)
	{
		if(!sl_check(document.theform.pdfname, "PDF�ļ���", 50, 1))
			return false;
		
		ExportPDF(obj,path+document.theform.pdfname.value,0);
	}						
	window.close();
}
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="export.jsp" >
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="/images/member.gif" align="absbottom" width="32" height="28"><font color="#220dc6"><b>��ѡ�񵼳�����</b></font></td>
	</tr>
	<tr>
		<td>
			<hr noshade color="#808080" size="1">
		</td>
	</tr>
	</table>
	<table border="0" width="100%" cellspacing="3">
		<tr>
			<td align="center" width="40%">��������</td>
			<td align="center">�ļ���</td>								
		</tr>
		<tr>
			<td align="left" width="40%"><INPUT type="checkbox" name="serial_no" class="flatcheckbox">������Excel:</td>
			<td align="left">(*.xls;*.*)</td>								
		</tr>
		<!--<tr>
			<td align="left" width="40%"><INPUT type="checkbox" name="serial_no" class="flatcheckbox">������Cell:</td>
			<td align="left">(*.xls;*.*)</td>								
		</tr>	
		<tr>
			<td align="left" width="40%"><INPUT type="checkbox" name="serial_no" class="flatcheckbox">�������ı�:</td>
			<td align="left">(*.txt;*.*)</td>								
		</tr>-->
		<tr>
			<td align="left" width="40%"><INPUT type="checkbox" name="serial_no" class="flatcheckbox">������PDF:</td>
			<td align="left"><input type="text" name="pdfname" size="20" value="<%=name%>">(*.pdf)</td>								
		</tr>
						
	</table>
	<br>
	<table border="0" width="100%">
		<tr>
			<td align="right">							
				<button type="button"  class="xpbutton3" accessKey=s onclick="javascript:exportFiles();">ȷ��(<u>S</u>)</button>
				&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=c onclick="javascript:window.returnValue=null;window.close();">�ر�(<u>C</u>)</button>
				&nbsp;&nbsp;
				</td>
		</tr>
	</table>
</form>
</BODY>
</HTML>

