<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis;"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%

%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title></title><!--积分兑换表-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
}
</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<div id="queryCondition" class="qcMain" style="display:none;width:480px;height:90px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
</div>

<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right">
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);">
		   <%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button><!--查询-->
		&nbsp;&nbsp;&nbsp;<%}%>
	</div>
	<hr noshade color="#808080" size="1" width="98%">
</div>

<div align="center">
	<table border="0"  width="98%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="15px">
				<input type="checkbox" name="btnCheckbox" class="selectAllBox"	
					   onclick="javascript:selectAllBox(document.theform.check_serial_no,this);" />
			</td>
			<td align="center" width="100px">客户名称</td>
			<td align="center" width="100px">礼物名称</td>
			<td align="center" width="100px">兑换日期</td>
			<td align="center" width="100px">兑换数量</td>
			<td align="center" width="100px">兑换单价</td>
			<td align="center" width="*">备注</td>
		</tr>
		
		<%for(int i=0;i<(8);i++){%>     	
	         <tr class="tr0">
	            <td align="center" width="15px">&nbsp;</td>
	            <td align="center" width="100px">&nbsp;</td>
	            <td align="center" width="100px">&nbsp;</td>
	            <td align="center" width="100px">&nbsp;</td>
	            <td align="center" width="100px">&nbsp;</td>       
	            <td align="center" width="50px">&nbsp;</td>   
	            <td align="center" width="*">&nbsp;</td>        
	         </tr>           
		<%}%> 
	
	</table>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>