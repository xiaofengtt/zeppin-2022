<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
boolean bSuccess = false;
String errorMsg = "";

if (request.getMethod().equals("POST"))
{
	String old_pass = request.getParameter("old_pass");
	String new_pass1 = request.getParameter("new_pass1");
	
	String old_login_user = request.getParameter("old_login_user");
	String new_login_user = request.getParameter("new_login_user");
	int changeuser = Utility.parseInt(request.getParameter("changeuser"),0);  
	
		
	String new_pass2 = request.getParameter("new_pass2");
	try
	{
		
		if(changeuser==1)
		{
			OperatorLocal operator2 = EJBFactory.getOperator();
			operator2.changeLoginUser(old_login_user,new_login_user);
			operator2.remove();
		}
		else
		{
		    OperatorLocal operator = EJBFactory.getOperator();
			operator.changePass(input_operatorCode,old_pass, new_pass1, new_pass2); 
		}
		bSuccess = true;
	}
	catch (Exception e)
	{
		errorMsg = e.getMessage();
	}
}
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<title><%=LocalUtilis.language("menu.modiPassword",clientLocale)%> </title><!--修改密码-->
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>

<script language="javascript">
<%if (bSuccess)
{
	if(Utility.parseInt(request.getParameter("changeuser"),0) == 1) {
	%>
		alert('<%=LocalUtilis.language("message.aliasUpdateOK",clientLocale)%>！');//别名修改成功
	<%
	}else{
	%>
		alert('<%=LocalUtilis.language("message.pwdUpdateOK",clientLocale)%>+！');//密码修改成功
	<%
	}
	%>
<%}
else
{
	if (!errorMsg.equals(""))
	{
	%>
		alert('<%=errorMsg%>');
	//window.close();
<%	}%>

<%}%>
function validateForm(form)
{
	if(document.theform.typeid[0].checked)
	{
		if(!sl_check(document.theform.old_pass, "<%=LocalUtilis.language("class.oldPass",clientLocale)%> ", 40, 1)) return false;//原密码
		if(!sl_check(document.theform.new_pass1, "<%=LocalUtilis.language("class.newPass",clientLocale)%> ", 40, 1)) return false;//新密码
		if(!sl_check(document.theform.new_pass2, "<%=LocalUtilis.language("class.confirmPassword",clientLocale)%> ", 40, 1)) return false;//确认密码
		document.theform.changeuser.value=2;
	}
	else if(document.theform.typeid[1].checked)
	{
		if(!sl_check(document.theform.old_login_user, "<%=LocalUtilis.language("class.oldLoginUser",clientLocale)%> ", 40, 1)) return false;//原登录号
		if(!sl_check(document.theform.new_login_user, "<%=LocalUtilis.language("class.newLoginUser",clientLocale)%> ", 40, 1)) return false;//新登录号
		document.theform.changeuser.value=1;
	}
	if(sl_confirm("<%=LocalUtilis.language("message.saveUpdate",clientLocale)%> "))//保存修改
	return true;
}

function changeInput2()
{	
	if(document.theform.typeid[0].checked)
	{	
		oldpasstr.style.display="";
		new1passtr.style.display="";
		new2passtr.style.display="";
		
		oldlogintr.style.display="none";
		newlogintr.style.display="none";
		
	}
	else 
	{
		oldpasstr.style.display="none";
		new1passtr.style.display="none";
		new2passtr.style.display="none";
		
		oldlogintr.style.display="";
		newlogintr.style.display="";
	}	
}

function clear(){
	var tag = document.getElementsByTagName("input");
	for(var i=0;i != tag.length ; i++){
		if (tag[i].getAttribute("type").toString().toLocaleLowerCase() == "text" || tag[i].getAttribute("type").toString().toLocaleLowerCase() == "password") {
			tag[i].setAttribute("value", "");
		}
	}
}

</script>
</head>
<body leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="password.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="changeuser" value="">

<table border="0" width="100%" cellspacing="0" cellpadding="4">
	<tr>
		<td colspan="2"><img border="0" src="<%=request.getContextPath()%>/images/Feichuang6.jpg" width="33" height="33"><b><%=LocalUtilis.language("menu.modiPassword",clientLocale)%> </b></td>
	</tr><!--修改密码-->
	<tr>
		<td colspan="2">
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
	<tr>
		<td align="center"><INPUT class="flatcheckbox" type="radio" name="typeid" checked onclick="javascript:changeInput2();" value="1"><%=LocalUtilis.language("menu.modiPassword",clientLocale)%> </td>
		<!--修改密码-->
		<td align="center"><INPUT class="flatcheckbox" type="radio" name="typeid"  onclick="javascript:changeInput2();" value="2"><%=LocalUtilis.language("menu.loginUserChange",clientLocale)%> </td>
		<!--修改登录帐号-->
	</tr>	
	<tr id = oldpasstr style="display:">
		<td align="right">*<%=LocalUtilis.language("class.oldPass",clientLocale)%> :</td><!--原密码-->
		<td><input onkeydown="javascript:nextKeyPress(this)" name="old_pass" size="20" type="password" value=""></td>
	</tr>
	<tr id = new1passtr style="display:">
		<td align="right">*<%=LocalUtilis.language("class.newPass",clientLocale)%> :</td><!--新密码-->
		<td><input onkeydown="javascript:nextKeyPress(this)" name="new_pass1" size="20" type="password" value=""></td>
	</tr>
	<tr id = new2passtr style="display:">
		<td align="right">*<%=LocalUtilis.language("class.confirmPassword",clientLocale)%> :</td><!--确认密码-->
		<td><input onkeydown="javascript:nextKeyPress(this)" name="new_pass2" size="20" type="password" value=""></td>
	</tr>
	
	<tr id = oldlogintr style="display:none">
		<td align="right">*<%=LocalUtilis.language("class.oldLoginUser2",clientLocale)%> :</td><!--原登录帐号-->
		<td><input onkeydown="javascript:nextKeyPress(this)" name="old_login_user" size="20" type="text" value=""></td>
	</tr>
	<tr id = newlogintr style="display:none">
		<td align="right">*<%=LocalUtilis.language("class.newLoginUser2",clientLocale)%> :</td><!--新登录帐号-->
		<td><input onkeydown="javascript:nextKeyPress(this)" name="new_login_user" size="20" type="text" value=""></td>
	</tr>
	<tr>
		<td colspan="2">
		<table border="0" width="100%">
			<tr>
				<td align="right">
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--保存-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="clear()"><%=LocalUtilis.language("message.clear",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;<!--清空-->
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
