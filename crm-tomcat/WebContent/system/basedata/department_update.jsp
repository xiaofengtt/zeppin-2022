<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.*,enfo.crm.system.*,java.util.* ,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%DepartmentLocal depart = EJBFactory.getDepartment();
boolean bSuccess = false;
Integer depart_id = new Integer(Utility.parseInt(request.getParameter("depart_id"), 0));
String depart_jc = "" ;
String manager_man = "";
String leader_man = ""; 
String depart_name = "";
Integer parent_id = new Integer(0);

DepartmentVO vo = new DepartmentVO();
List list = null;
Map map = null;

if(depart_id.intValue() != 0){
	list = depart.listAll(depart_id);
	for(int i=0;i<list.size();i++){
		map = (Map)list.get(i);
		parent_id = Utility.parseInt(Utility.trimNull(map.get("PARENT_ID")),new Integer(0));
		depart_name = Utility.trimNull(map.get("DEPART_NAME"));
		depart_jc = Utility.trimNull(map.get("DEPART_JC"));
		manager_man = Utility.trimNull(map.get("MANAGER_MAN"));
		leader_man = Utility.trimNull(map.get("LEADER_MAN"));
	
	}
}

String departName = "";
if(parent_id.intValue()>0){
	departName = Argument.getDepartOptions(parent_id);
}

if(request.getMethod().equals("POST")){
	vo.setDepart_id(depart_id);
	vo.setDepart_name(Utility.trimNull(request.getParameter("depart_name")));
	vo.setDepart_jc(Utility.trimNull(request.getParameter("depart_jc")));
	vo.setManager_man(Utility.trimNull(request.getParameter("manager_man")));
	vo.setLeader_man(Utility.trimNull(request.getParameter("leader_man")));
	depart.modi(vo,input_operatorCode);
	bSuccess = true;
}	
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.departmentUpdate",clientLocale)%> </TITLE>
<!--部门-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK HREF="<%=request.getContextPath()%>/includes/default.css" TYPE="text/css" REL="stylesheet">
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/financing.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form)
{
	//if(!sl_checkNum(form.depart_id, "<%=LocalUtilis.language("class.departID",clientLocale)%> ", 9, 1))	return false;//部门编号
	if(!sl_check(form.depart_name, "<%=LocalUtilis.language("class.departName2",clientLocale)%> ", 24, 1))		return false;//部门名称
	if(!sl_check(form.depart_jc, "<%=LocalUtilis.language("class.departJC",clientLocale)%> ", 12, 0))	return false;//部门简称
	if(!sl_check(form.manager_man, "<%=LocalUtilis.language("class.managerMan",clientLocale)%> ", 20, 0))	return false;//部门负责人
	if(!sl_check(form.leader_man, "<%=LocalUtilis.language("class.leaderMan2",clientLocale)%> ", 20, 0))	return false;//部门高管领导

	return sl_check_update();
}
</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="department_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="depart_id" value="<%=depart_id%>">
<input type=hidden name="is_dialog" value="1"> <%if (depart_id.intValue() == 0)
{
%> <input type=hidden name="is_new" value="1"> <%}
%>

		<table border="0" width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td class="page-title"><b><font color="#215dc6"><%=LocalUtilis.language("menu.departManage",clientLocale)%> </font></b></td>
			</tr><!--部门管理-->
		</table>
		<br/>
		 <table border="0" width="100%" cellspacing="3">
							<%if(parent_id.intValue()>0){%>
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("class.parentID",clientLocale)%> :</td><!--上级部门编号-->
								<td width="64%"><input  onkeydown="javascript:nextKeyPress(this)" readonly="readonly" class="edline" size="20" name="parent_id" value="<%=parent_id%>"></td>
							</tr>
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("class.parentName2",clientLocale)%> :</td><!--上级部门名称-->
								<td width="64%"><input  onkeydown="javascript:nextKeyPress(this)" readonly="readonly" class="edline" size="20" name="parent_name" value="<%=departName%>"></td>
							</tr>
							<%}%>
							
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("class.departName2",clientLocale)%> :</td><!--部门名称-->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="depart_name" value="<%=depart_name%>"></td>
							</tr>
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("class.departJC",clientLocale)%> :</td><!--部门简称-->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="depart_jc" value="<%=depart_jc%>"></td>
							</tr>
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("class.managerMan",clientLocale)%> :</td><!--部门负责人-->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="manager_man" value="<%=manager_man%>"></td>
							</tr>
							<tr>
								<td width="31%" align="right"><%=LocalUtilis.language("class.leaderMam",clientLocale)%> :</td><!--高管领导-->
								<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="leader_man" value="<%=leader_man%>"></td>
							</tr>
		</table>
		<br/>
		<table border="0" width="100%">
			<tr>
				<td align="right">&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){ document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--保存-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				</td><!--取消-->
			</tr>
		</table>
</form>
</BODY>
</HTML>
<%
depart.remove();
%>














