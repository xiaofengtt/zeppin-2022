<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
RoleVO vo = new RoleVO();
RoleLocal role = EJBFactory.getRole();
Integer role_id = Utility.parseInt(request.getParameter("role_id"),new Integer(0));
boolean bSuccess = false;

if(request.getMethod().equals("POST")){
	vo.setRole_id(role_id);
	vo.setRole_name(request.getParameter("role_name"));
	vo.setSummary(request.getParameter("summary"));
	vo.setFlag_access_all( Utility.parseInt(request.getParameter("flag_access_all"),new Integer(0)));
	vo.setFlag_encryption(Utility.parseInt(request.getParameter("flag_encryption"),new Integer(0)));
	try{
		role.append(vo ,input_operatorCode);
		bSuccess = true;
	} catch (Exception e) {
		String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //����ʧ��
		out.println("<script type=\"text/javascript\">alert('"+message+","+e.getMessage()+"');</script>");
	}
	
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

function $$(_name){
	return document.getElementsByName(_name)[0];
}


function checkHTML(easy_tag,find_tag){
	var tags = new Array();
	for(var i=0;i != easy_tag.length;i++){
		tags.push(easy_tag.charAt(i));
	}
	while( tags.length !=0 ){
		if(find_tag.indexOf(tags.pop()) != -1 ){
			if(arguments[2] != null) arguments[2].focus();
			return false;
		}
	}
	return true
}

function validateForm(form)
{
	if(!sl_checkNum(form.role_id, "<%=LocalUtilis.language("class.qRoleID",clientLocale)%> ", 3, 1))	return false;//��ɫ���
	if(!(parseFloat($$("role_id").getAttribute("value")).toFixed(0) >0 && parseFloat($$("role_id").getAttribute("value")).toFixed(0) <= 255)){
		alert("<%=LocalUtilis.language("menu.roleIDSize",clientLocale)%> ");//��ɫ��ŵĴ�СӦ����1-255֮�䡣
		return false;
	}
	
	if(!sl_check(form.role_name, "<%=LocalUtilis.language("class.roleName",clientLocale)%> ", 16, 1))		return false;//��ɫ����
	if(!checkHTML("'\"<>|!@#$%^&*()_+{}?/\\",$$("role_name").getAttribute("value"),$$("role_name"))){
		alert("<%=LocalUtilis.language("message.roleNameError",clientLocale)%> ��"+"'\"<>|!@#$%^&*()_+{}?/\\");//��ɫ�������в���ʹ�õ��ַ�
		return false;
	}
	
	if(!sl_check(form.summary, "<%=LocalUtilis.language("class.customerSummary",clientLocale)%> ", 200, 0))		return false;//��ע

	return sl_check_update();
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="role_new.jsp" onsubmit="javascript:return validateForm(this);"><input type=hidden name="is_dialog" value="1">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("menu.roleInfoNew",clientLocale)%> </b></font></td>
					<!--������ɫ��Ϣ-->
				</tr>
</table>
<br/>
<table border="0" width="100%" cellspacing="3" class="product-list">
			<tr>
				<td  align="right"><%=LocalUtilis.language("class.qRoleID",clientLocale)%> : </td><!--��ɫ���-->
				<td  align="left"><input type="text" name="role_id" size="15" value="<%=Utility.trimNull(vo.getRole_id()) %>" onkeydown="javascript:nextKeyPress(this)"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.roleName",clientLocale)%> : </td><!--��ɫ����-->
				<td align="left" ><input type="text" name="role_name" size="15" value="<%=Utility.trimNull(vo.getRole_name()) %>" onkeydown="javascript:nextKeyPress(this)"></td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--��ע-->
				<td><textarea rows="3" name="summary"  onkeydown="javascript:nextKeyPress(this)" cols="30"></textarea></td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<table>
						<tr align="center">
							<td align="right"><%=LocalUtilis.language("menu.flagEncryption",clientLocale)%> :</td><!--��ʾ����-->
							<td><input type="checkbox" class="flatcheckbox" name="flag_encryption" value="1" /></td>
							<td align="right"><%=LocalUtilis.language("menu.flagAccessAllView",clientLocale)%> :</td><!--�鿴ȫ���ͻ�Ȩ��-->
							<td><input type="checkbox" class="flatcheckbox" name="flag_access_all" value="1" /></td>
						</tr>
					</table>
				</td>
			</tr>
</table>
<table border="0" width="100%">
			<tr>
				<td align="center">
				<button type="button"   class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--����-->
				<button type="button"   class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;<!--ȡ��-->
				</td>
			</tr>
</table>
</form>
</body>
</html>
