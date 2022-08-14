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

	String pagesize = request.getParameter("pagesize");
	try
	{
		OperatorLocal operator = EJBFactory.getOperator();
		if(changeuser==1)
		{
			operator.changeLoginUser(old_login_user,new_login_user);
			operator.remove();
		}
		else if(changeuser==2)
		{
			operator.changePass(input_operatorCode,old_pass, new_pass1, new_pass2); 
		}else{
			operator.changePagesize(input_operatorCode,pagesize);
			session.setAttribute("defaultPagesize",pagesize);//Ϊ�����޸ĺ�������Ч
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
<title><%=LocalUtilis.language("menu.modiPassword",clientLocale)%> </title><!-- �޸����� -->
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>

<script language="javascript">
<%if (bSuccess){%>
	window.opener = null;
	window.returnValue = 1;
	window.close();
<%
}
else{
	if (!errorMsg.equals("")){%>
	alert("<%=errorMsg%>");
	//window.close();
<%}
}%>

function validateForm(form)
{
	if(document.theform.typeid[0].checked){
		var pass1=document.theform.new_pass1.value;
		if(pass1==''){
			alert("����д������");
			document.theform.new_pass1.focus();
			return false;
		}
		if(pass1.length<6){
			alert("����������6λ");
			document.theform.new_pass1.focus();
			return false;//������
		}
		var pass2=document.theform.new_pass2.value;
		if(pass2==''){
			alert("����дȷ������");
			document.theform.new_pass2.focus();
			return false;//������
		}
		if(pass2.length<6){
			alert("ȷ����������6λ");
			document.theform.new_pass2.focus();
			return false;
		}
		document.theform.changeuser.value=2;
	}
	else if(document.theform.typeid[1].checked){
		//ԭ��¼��
		if(!sl_check(document.theform.old_login_user, "<%=LocalUtilis.language("class.oldLoginUser",clientLocale)%> ", 40, 1)) return false;
		//�µ�¼��
		if(!sl_check(document.theform.new_login_user, "<%=LocalUtilis.language("class.newLoginUser",clientLocale)%> ", 40, 1)) return false;
		document.theform.changeuser.value=1;
	}else if(document.theform.typeid[2].checked){
		if(!sl_checkNum(document.theform.pagesize,"����",3,1)) return false;
		document.theform.changeuser.value=3;
	}
	//�����޸�
	if(sl_confirm("<%=LocalUtilis.language("message.saveUpdate",clientLocale)%> "))
	return true;
}

function changeInput2()
{	
	if(document.theform.typeid[0].checked){	
		oldpasstr.style.display="";
		new1passtr.style.display="";
		new2passtr.style.display="";
		
		oldlogintr.style.display="none";
		newlogintr.style.display="none";

		pagesizetr.style.display="none";
		
	}
	else if(document.theform.typeid[1].checked){
		oldpasstr.style.display="none";
		new1passtr.style.display="none";
		new2passtr.style.display="none";
		
		oldlogintr.style.display="";
		newlogintr.style.display="";

		pagesizetr.style.display="none";
	}
	else{
		oldpasstr.style.display="none";
		new1passtr.style.display="none";
		new2passtr.style.display="none";

		oldlogintr.style.display="none";
		newlogintr.style.display="none";

		pagesizetr.style.display="";
	}	
}
</script>
</head>
<body leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)" class="body body-nox">
<form name="theform" method="post" action="password.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="changeuser" value="">

<table border="0" width="100%" cellspacing="0" cellpadding="4">
	<tr>
		<td colspan="3" class="page-title"><b>�������� </b></td>
	</tr>
	<tr>
		<!--�޸�����-->
		<td align="center"><INPUT class="flatcheckbox" type="radio" name="typeid" checked onclick="javascript:changeInput2();" value="1"><%=LocalUtilis.language("menu.modiPassword",clientLocale)%> </td>
		<!--�޸ĵ�¼�ʺ�-->
		<td align="center"><INPUT class="flatcheckbox" type="radio" name="typeid"  onclick="javascript:changeInput2();" value="2"><%=LocalUtilis.language("menu.modiLoginUser",clientLocale)%> </td>
		<!--�������� -->
		<td align="center"><INPUT class="flatcheckbox" type="radio" name="typeid"  onclick="javascript:changeInput2();" value="3">�������� </td>
	</tr>	
	<tr id = oldpasstr style="display:">
		<td align="right">*<%=LocalUtilis.language("class.oldPass",clientLocale)%> :</td><!--ԭ����-->
		<td><input onkeydown="javascript:nextKeyPress(this)" name="old_pass" size="20" type="password" value=""></td>
	</tr>
	<tr id = new1passtr style="display:">
		<td align="right">*<%=LocalUtilis.language("class.newPass",clientLocale)%> :</td><!--������-->
		<td><input onkeydown="javascript:nextKeyPress(this)" name="new_pass1" size="20" type="password" value=""></td>
	</tr>
	<tr id = new2passtr style="display:">
		<td align="right">*<%=LocalUtilis.language("class.confirmPassword",clientLocale)%> :</td><!--ȷ������-->
		<td><input onkeydown="javascript:nextKeyPress(this)" name="new_pass2" size="20" type="password" value=""></td>
	</tr>
	
	<tr id = oldlogintr style="display:none">
		<td align="right">*<%=LocalUtilis.language("class.oldLoginUser",clientLocale)%> :</td><!--ԭ��¼�ʺ�-->
		<td><input onkeydown="javascript:nextKeyPress(this)" name="old_login_user" size="20" type="text" value=""></td>
	</tr>
	<tr id = newlogintr style="display:none">
		<td align="right">*<%=LocalUtilis.language("class.newLoginUser",clientLocale)%> :</td><!--�µ�¼�ʺ�-->
		<td><input onkeydown="javascript:nextKeyPress(this)" name="new_login_user" size="20" type="text" value=""></td>
	</tr>
	<tr id = pagesizetr style="display:none">
		<td align="right">*ÿҳ��ʼ��ʾ���� :</td>
		<td><input onkeydown="javascript:nextKeyPress(this)" name="pagesize" size="20" type="text" value=""></td>
	</tr>
	<tr>
		<td colspan="3">
		<table border="0" width="100%">
			<tr>
				<td align="right">
				<!--����-->
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;
				<!--ȡ��-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
