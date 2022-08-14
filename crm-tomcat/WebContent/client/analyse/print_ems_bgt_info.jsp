<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title>ems编辑</title>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script type="text/javascript" src="/webEditor/fckeditor.js"></script>
<script language=javascript>

function saveInfo()
{
	var jjr_name = document.theform.jjr_name.value;
	var nj_name = document.theform.nj_name.value;
	var number = document.theform.number.value;
	window.returnValue = jjr_name+"$"+nj_name+" $"+number;
	window.close();
}
</script>
</HEAD>

<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0">
<form name="theform" method="post" action="">
<TABLE cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=center>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=center >
						<table border="0" width="96%"   cellspacing="0" cellpadding="0" height="74" >
							<tr>
								<td><img border="0" src="/images/member.gif" align="absbottom" ><b><font color="#215dc6">编辑ems信息</font></b></td>
							</tr>
							<tr>
								<td width="100%">
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3" cellpadding="4">
							<tr>
								<td align="right">寄件人姓名:</td> 
								<td colspan="3"><input name="jjr_name" value="" size="40" /></td>
							</tr>
							<tr>
								<td align="right">内件品名:</td>
								<td colspan="3"  ><input name ="nj_name"  value="" size="40" /></td>
							</tr>
							<tr>
								<td align="right">数量:</td> 
								<td colspan="3"><input name = "number" value="" size="20" /></td>
							</tr>
						</table>
						<table border="0" width="100%" align="center">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:saveInfo();">保存</button>
								&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>
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