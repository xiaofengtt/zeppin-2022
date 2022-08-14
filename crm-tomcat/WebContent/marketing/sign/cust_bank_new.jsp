<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));
enfo.crm.intrust.CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
CustomerInfoVO vo = new CustomerInfoVO();
String bank_id = Utility.trimNull(request.getParameter("bank_id"));
String bank_name = Utility.trimNull(request.getParameter("bank_name"));
String bank_acct = Utility.trimNull(request.getParameter("bank_acct"));
boolean bSuccess = false;

if(request.getMethod().equals("POST")){
	vo.setCust_id(cust_id);
	vo.setBank_id(bank_id);
	vo.setBank_name(bank_name);
	vo.setBank_acct(bank_acct);
	customer.addCustBank(vo);
	bSuccess = true;
}
%>
<html>
<head>
<title>新增客户银行账户信息</title>
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

function $$(_name){
	return document.getElementsByName(_name)[0];
}
function validateForm(form)
{
	if(!/^[0-9]*$/.test(form.bank_acct.value)){
		alert("银行卡号请填写纯数字");
		return false;
	}

	if(!sl_checkChoice(form.bank_id, '<%=LocalUtilis.language("class.bankName3",clientLocale)%> ')) return false;//银行名称
	if(!sl_check(form.bank_acct, '<%=LocalUtilis.language("class.bankAcct3",clientLocale)%> ', 80, 1)) return false;//银行帐号
	var selectIndex = document.getElementById("bank_id").selectedIndex;
	document.getElementById("bank_name").value = document.getElementById("bank_id").options[selectIndex].text;
	return sl_check_update();
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="cust_bank_new.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="cust_id" value="<%=cust_id %>"> 
<input type="hidden" name="bank_name" value=""> 
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title">
			<font color="#215dc6"><b>新增客户银行账户信息</b></font>
			</td>
	</tr>
</table>
<br/>
<table border="0" width="100%" cellspacing="3" cellpadding="2" class="product-list">
	<tr>
		<td  align="right">银行名称 : </td>
		<td  align="left">
			<select size="1" name="bank_id" on" style="WIDTH: 120px">
				<%=Argument.getBankOptions(bank_id)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">银行帐号 : </td>
		<td align="left" ><input type="text" name="bank_acct" size="30" value="" onkeydown="javascript:nextKeyPress(this)"></td>
	</tr>
</table>
<table border="0" width="100%">
	<tr>
		<td align="center">
		<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
</html>
