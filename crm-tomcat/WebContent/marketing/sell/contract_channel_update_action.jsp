<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
boolean bSuccess = false;

if(request.getMethod().equals("POST")){

	bSuccess = true;
}
%>
<html>
<head>
<title><%=LocalUtilis.language("menu.roleNew",clientLocale)%> </title>
<!--������ɫ-->
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


	return sl_check_update();
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="role_new.jsp" onsubmit="javascript:return validateForm(this);"><input type=hidden name="is_dialog" value="1">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title">
			<font color="#215dc6"><b>��ͬ������Ϣ</b></font>
		</td>
		<!--������ɫ��Ϣ-->
	</tr>
</table>
<br/>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td  align="right">��Ʒ���:</td>
		<td  align="left">
			<input type="text" name="role_id" size="15" value="<%//=Utility.trimNull(vo.getRole_id()) %>">
		</td>
		<td  align="right">��Ʒ����:</td>
		<td  align="left">
			<input type="text" name="role_id" size="15" value="<%//=Utility.trimNull(vo.getRole_id()) %>">
		</td>
	</tr>
	<tr>
		<td align="right">ί��������</td>
		<td align="left" >
			<input type="text" name="role_name" size="15" value="<%//=Utility.trimNull(vo.getRole_name()) %>"/>
		</td>
	</tr>	
	<tr>
		<td  align="right">��ͬ���:</td>
		<td  align="left">
			<input type="text" name="role_id" size="15" value="<%//=Utility.trimNull(vo.getRole_id()) %>">
		</td>
		<td  align="right">�Ϲ����:</td>
		<td  align="left">
			<input type="text" name="role_id" size="15" value="<%//=Utility.trimNull(vo.getRole_id()) %>">
		</td>
	</tr>
	<tr>
		<td  align="right">ǩ������:</td>
		<td  align="left">
			<input type="text" name="role_id" size="15" value="<%//=Utility.trimNull(vo.getRole_id()) %>">
		</td>
		<td  align="right">��������:</td>
		<td  align="left">
			<input type="text" name="role_id" size="15" value="<%//=Utility.trimNull(vo.getRole_id()) %>">
		</td>
	</tr>
	<tr>
		<td  align="right">�ɿʽ:</td>
		<td  align="left">
			<input type="text" name="role_id" size="15" value="<%//=Utility.trimNull(vo.getRole_id()) %>">
		</td>
		<td  align="right">�Ϲ����:</td>
		<td  align="left">
			<input type="text" name="role_id" size="15" value="<%//=Utility.trimNull(vo.getRole_id()) %>">
		</td>
	</tr>
</table>
<table border="0" width="100%">
			<tr>
				<td align="center">
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--����-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;<!--ȡ��-->
				</td>
			</tr>
</table>
</form>
</body>
</html>
