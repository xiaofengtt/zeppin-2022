<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.callcenter.*"%>
<%@ include file="/includes/operator.inc" %>
<%
String webcallId = Utility.trimNull(request.getParameter("webcallId")); 
%>

<HTML>
<HEAD>
<TITLE>客户搜索</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/webcall/webcall.css" type=text/css rel=stylesheet>
<style>

</style>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_zh_CN.js"></SCRIPT>
<script language=javascript>
function addAction(){
	var url = "<%=request.getContextPath()%>/webcall/customer_info_webcall_add.jsp?webcallId=<%=webcallId%>";
	//window.showModalDialog(url,window,'dialogWidth:300px;dialogHeight:350px');
	var a = document.createElement("a");
    a.href = url;
    document.body.appendChild(a);
    a.click();	
}

function queryAction(){
	var url = "<%=request.getContextPath()%>/webcall/customer_info_webcall_query.jsp?webcallId=<%=webcallId%>";
	//window.showModalDialog(url,window,'dialogWidth:300px;dialogHeight:350px');
	var a = document.createElement("a");
    a.href = url;
    document.body.appendChild(a);
    a.click();	
}
</script>
</HEAD>
<BODY class="BODY2" style="overflow-y: hidden;">
<form name="theform">
<input type="hidden" id="webcallId" name="webcallId" value="<%=webcallId%>">
<IMG style="width:100%" src="<%=request.getContextPath()%>/images/webcall/pic.gif"></IMG>
<div align="center"  class="topDiv"><font color="#C00000" size="3">该访客无对应客户信息</font></div>
<div align="center">
<table height="30%">
	<tr>
		<td vAlign=middle>
			<TABLE cellSpacing=2 cellPadding=2 border=0 >
				<TR>
					<TD align=left>
						<button  class="xpbutton31" id="btnNew" onclick="javascript:addAction();">新建客户</button>	
						<br>
						<button  class="xpbutton31" id="btnQuery" onclick="javascript:queryAction();">客户搜索</button>	
					</TD>
				</TR>
			</TABLE>
		</td>
	</tr>
</table>

</div>


<div class="bottomDiv">&nbsp;&nbsp;<font size="2">您好！<%=input_operator.getOp_name()%></font></div>
</form>
</BODY>
</HTML>

