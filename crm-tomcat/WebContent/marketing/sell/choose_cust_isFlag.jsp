<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,java.io.*" %>
<%@ include file="/includes/operator.inc" %>
<HTML>
<HEAD>
<TITLE>系统确认：</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type="text/javascript">
var retval = 0;

function chosen(flag) {
	window.returnValue=flag;
	window.close();
}
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0">
<form name="theform">
<TABLE cellSpacing=0 cellPadding=0 width="100%" height="100%" border=0 >
<tr>
	<td width="50%" align="right">系统确认：&nbsp;&nbsp;</td>
	<td width="*">
		您是否已经预约？
	</td>
</tr>
<tr>
	<td colspan="2" align="center"><button type="button"  class="xpbutton2" onclick="javascript:chosen(1);">是</button>
		&nbsp;&nbsp;<button type="button"  class="xpbutton2" onclick="javascript:chosen(0);">否</button></td>
</tr>
</TABLE>
</form>
</BODY>
</HTML>
