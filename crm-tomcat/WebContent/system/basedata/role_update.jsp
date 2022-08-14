<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
RoleVO vo = new RoleVO();
RoleLocal role = EJBFactory.getRole();

Integer role_id = Utility.parseInt(request.getParameter("role_id"),null);
String role_name = new String("");
String summary = new String("");
Integer flag_access_all = new Integer(0);
Integer flag_encryption = new Integer(0);
boolean bSuccess = false;
List listAll = null;
Map map = null;

if(role_id.intValue()!= 0){
	vo.setRole_id(role_id);
	listAll = role.listBySql(vo);
	map = (Map)listAll.get(0); 
}
if(map!= null){
	role_name = Utility.trimNull(map.get("ROLE_NAME"));
	summary = Utility.trimNull(map.get("SUMMARY"));	
	flag_access_all = Utility.parseInt(Utility.trimNull(map.get("FLAG_ACCESS_ALL")),new Integer(0));
	flag_encryption = Utility.parseInt(Utility.trimNull(map.get("FLAG_ENCRYPTION")),new Integer(0));
}

if(request.getMethod().equals("POST")){
	vo.setRole_id(role_id);
	vo.setRole_name(Utility.trimNull(request.getParameter("role_name")));
	vo.setFlag_access_all( Utility.parseInt(request.getParameter("flag_access_all"),new Integer(0)));
	vo.setFlag_encryption(Utility.parseInt(request.getParameter("flag_encryption"),new Integer(0)));
	vo.setSummary(Utility.trimNull(request.getParameter("summary")));	
	role.modi(vo,input_operatorCode);
	bSuccess = true;
}

%>

<html>
<head>
<title><%=LocalUtilis.language("menu.roleUpdate",clientLocale)%> </title>
<!--修改角色-->
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
	//if(!sl_checkNum(form.role_id, "角色编号", 10, 1))	return false;
	if(!sl_check(form.role_name, "<%=LocalUtilis.language("class.roleName",clientLocale)%> ", 16, 1))		return false;//角色名称
	
	if(!checkHTML("'\"<>|!@#$%^&*()_+{}?/\\",$$("role_name").getAttribute("value"),$$("role_name"))){
		alert("<%=LocalUtilis.language("message.roleNameError",clientLocale)%> ："+"'\"<>|!@#$%^&*()_+{}?/\\");//角色名称中有不能使用的字符
		return false;
	}
	
	if(!sl_check(form.summary, "<%=LocalUtilis.language("class.customerSummary",clientLocale)%> ", 200, 0))		return false;//备注

	return sl_check_update();
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="role_update.jsp" onsubmit="javascript:return validateForm(this);"><input type=hidden name="is_dialog" value="1">
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("menu.roleInfoNew",clientLocale)%> </b></font></td>
		</tr><!--新增角色信息-->
	</table>
	<br/>
	<table border="0" width="100%" cellspacing="0" class="product-list">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.roleID",clientLocale)%> : </td><!--角色类别-->
			<td  align="left"><input type="text" readonly="readonly" class="edline" name="role_id" size="15" value="<%=role_id %>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.roleName",clientLocale)%> : </td><!--角色名称-->
			<td align="left" ><input type="text" name="role_name" size="15" value="<%=role_name %>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>								
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%> :</td><!--备注-->			
			<td><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="30"><%=summary %></textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<table>
					<tr align="center">
						<td align="right"><%=LocalUtilis.language("menu.flagEncryption",clientLocale)%> :</td><!--显示加密-->
						<td><input type="checkbox" name="flag_encryption" value="1" <%if(flag_encryption.intValue()==1)out.print("checked");%>/></td>
						<td align="right"><%=LocalUtilis.language("menu.flagAccessAllView",clientLocale)%> :</td><!--查看全部客户权限-->
						<td><input type="checkbox" name="flag_access_all" value="1" <%if(flag_access_all.intValue()==1)out.print("checked");%>/></td>
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

