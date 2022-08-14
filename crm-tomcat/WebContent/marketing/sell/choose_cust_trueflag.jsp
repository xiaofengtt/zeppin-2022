<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,java.io.*" %>
<%@ include file="/includes/operator.inc" %>
<HTML>
<HEAD>
<TITLE>客户真实性设置</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type="text/javascript">
var retval = 0;

function chosen() {
	if (document.theform.true_flag.value=="0") {
		sl_alert("请选择一个选项！");
		document.theform.true_flag.focus();
		return;
	}
	retval = document.theform.true_flag.value;
	window.close();
}

window.onunload = function() {
		window.returnValue = retval;
	};
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0">
<form name="theform">
<TABLE cellSpacing=0 cellPadding=0 width="100%" height="100%" border=0 >
<tr>
	<td width="50%" align="right">客户真实性 :&nbsp;&nbsp;</td>
	<td width="*">
		<select name="true_flag">
			<%=Argument.getCustInfoTrueFlagList(new Integer(0))%>
		</select>		
	</td>
</tr>
<tr>
	<td colspan="2" align="center"><button type="button"  class="xpbutton2" onclick="javascript:chosen();">确定</button>
		&nbsp;&nbsp;<button type="button"  class="xpbutton2" onclick="javascript:window.close();">取消</button></td>
</tr>
</TABLE>
</form>
</BODY>
</HTML>
