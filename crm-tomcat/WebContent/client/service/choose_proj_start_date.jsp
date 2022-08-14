<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,java.io.*" %>
<%@ include file="/includes/operator.inc" %>
<HTML>
<HEAD>
<TITLE>项目成立日设置</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type="text/javascript">
function chosen() {
	window.returnValue = document.theform.start_date_picker.value;
	window.close();
}

function canceled() {
	window.returnValue = null;
	window.close();
}
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0">
<form name="theform">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0 height="50%">
<tr>
	<td width="50%" align="right">项目成立日 :&nbsp;&nbsp;</td>
	<td width="*">
		<INPUT type="text" name="start_date_picker" class="selecttext">
		<INPUT type="button" value="▼" class="selectbtn" onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="13">
	</td>
</tr>
<tr>
	<td colspan="2" align="right"><button type="button"  class="xpbutton2" onclick="javascript:chosen();">确定</button>
		&nbsp;&nbsp;<button type="button"  class="xpbutton2" onclick="javascript:canceled();">取消</button></td>
</tr>
</TABLE>
</form>
</BODY>
</HTML>
