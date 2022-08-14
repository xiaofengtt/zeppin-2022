<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@page import="enfo.crm.intrust.ValidateprintLocal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
ValidateprintLocal local = EJBFactory.getValidateprint();
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer print_flag = Utility.parseInt(request.getParameter("print_flag"),new Integer(1));
String printInfo = Utility.trimNull(request.getParameter("printInfo"));
String[] printInfoArray = Utility.splitString(printInfo,",");
String printDetail = "";
String[] printInfoDetail = null;
boolean bSuccess = false;

if(request.getMethod().equals("POST")){
	if(printInfoArray.length==0){
		local.updatePrintFlag(print_flag,serial_no);  
	}
	else{
		for(int i = 0; i<printInfoArray.length;i++){
			printDetail = printInfoArray[i];
			printInfoDetail = Utility.splitString(printDetail,"|");	
			serial_no = Utility.parseInt(printInfoDetail[0],new Integer(0));
	
			local.updatePrintFlag(print_flag,serial_no);  
			
		}
	}

	bSuccess = true;
}
%>
<html>
<head>
<title><%=LocalUtilis.language("menu.roleNew",clientLocale)%> </title>
<!--新增角色-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>

<%
if (bSuccess){
%>
	window.returnValue = 1;
	window.close();
<%}%>


function validateForm(form)
{
	if(form.print_flag[0].checked)
		print_flag = 1;
	else
		print_flag = 2;		
	return sl_check_update();
}
</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="print_flag.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="serial_no" value="<%=serial_no %>">
<input type=hidden name="printInfo" value="<%=printInfo %>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>打印标志修改</b></font></td>
				</tr>
				<tr>
					<td>
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
</table>
<table border="0" width="100%" cellspacing="3">
			<tr>
				<td colspan="2" align="center">
					<table>
						<tr align="center">
							<td align="right">已打印 :<input type="radio" class="flatcheckbox" name="print_flag" value="1" <%if(print_flag.intValue()==1) out.print("checked");%>/></td>
							<td>&nbsp;&nbsp;&nbsp;</td>
							<td>&nbsp;&nbsp;&nbsp;</td>
							<td>未打印:<input type="radio" class="flatcheckbox" name="print_flag" value="2" <%if(print_flag.intValue()!=1) out.print("checked");%>/></td>
						</tr>
					</table>
				</td>
			</tr>
</table>
<table border="0" width="100%">
			<tr>
				<td align="center">
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--保存-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;<!--取消-->
				</td>
			</tr>
</table>
</form>
</body>
</html>
