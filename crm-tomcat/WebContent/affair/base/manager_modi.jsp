<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@page import="enfo.crm.customer.CustomerLocal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
String cust_id = request.getParameter("cust_id");
Integer service_man = Utility.parseInt(request.getParameter("service_man"),new Integer(0));
boolean bSuccess = false;

if(request.getMethod().equals("POST")){
	String[] all_cust_id = cust_id.split(",");
	for(int i=0;i<all_cust_id.length;i++){
		vo.setCust_id(Utility.parseInt(Utility.trimNull(all_cust_id[i]),new Integer(0)));
		vo.setService_man(service_man);
		vo.setInput_man(input_operatorCode);
		local.modiManager(vo);  
	}
	bSuccess = true;
}
%>

<HTML>
<head>
<title> </title>
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
	if(!sl_checkChoice(form.service_man,'客户经理')) return false;	
	return sl_check_update();
}
</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="manager_modi.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="cust_id" value="<%=cust_id%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td class="page-title"><font color="#215dc6"><b>客户经理设置</b></font></td>
				</tr>
</table>
<br/>
<table border="0" width="100%" cellspacing="3" class="tablelinecolor">
			<tr>
				<td colspan="2" align="center">
					<table>
						<tr align="center">
							<td align="right">客户经理选择 :</td>
							<td>
								<select name="service_man">
									<%=Argument.getManager_Value(input_operatorCode)%>
								</select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
</table>
<br/>
<table border="0" width="100%">
			<tr>
				<td align="center">
				<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--保存-->
				<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;<!--取消-->
				</td>
			</tr>
</table>
</form>
</body>
</html>
