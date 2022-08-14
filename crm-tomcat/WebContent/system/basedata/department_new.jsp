<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.*,enfo.crm.system.*,java.util.* ,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%DepartmentLocal depart = EJBFactory.getDepartment();
boolean bSuccess = false;
Integer depart_id = new Integer(Utility.parseInt(request.getParameter("depart_id"), 0));
Integer parent_id = new Integer(Utility.parseInt(request.getParameter("parent_id"), 0));;
List list = null;
Map map = null;
DepartmentVO vo = new DepartmentVO();

String depart_name = "";
if(depart_id.intValue()>0){
	depart_name = Argument.getDepartOptions(depart_id);
}


if (request.getMethod().equals("POST")){
	vo.setDepart_id(Utility.parseInt(request.getParameter("depart_id"),new Integer(0)));
	vo.setDepart_name(Utility.trimNull(request.getParameter("depart_name")));
	vo.setDepart_jc(Utility.trimNull(request.getParameter("depart_jc")));
	vo.setParent_id(Utility.parseInt(request.getParameter("parent_id"),new Integer(0)));
	vo.setManager_man(Utility.trimNull(request.getParameter("manager_man")));
	vo.setLeader_man(Utility.trimNull(request.getParameter("leader_man")));
	try{
		depart.append(vo,input_operatorCode);
		bSuccess = true;
	} catch (Exception e){
		String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
		out.println("<script type=\"text/javascript\">alert('"+message+","+e.getMessage()+"');</script>");
	}
	
	
}
	
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.departmentNew",clientLocale)%> </TITLE>
<!--部门信息-->
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
    if(form.depart_id.value!=null&&form.depart_id.value!='0'){
        if(!sl_checkNum(form.depart_id, "<%=LocalUtilis.language("class.departID",clientLocale)%> ", 9, 1))//部门编号
        	return false;
    }else{
        alert("<%=LocalUtilis.language("message.departIDError",clientLocale)%> ");//部门编号不能为0，请重新输入
        return false;    
    }
	if(!sl_check(form.depart_name, "<%=LocalUtilis.language("class.departName2",clientLocale)%> ", 24, 1))		return false;//部门名称
	if(!sl_check(form.depart_jc, "<%=LocalUtilis.language("class.departJC",clientLocale)%> ", 12, 0))	return false;//部门简称
	if(!sl_check(form.manager_man, "<%=LocalUtilis.language("class.managerMan",clientLocale)%> ", 20, 0))	return false;//部门负责人
	if(!sl_check(form.leader_man, "<%=LocalUtilis.language("class.leaderMan2",clientLocale)%> ", 20, 0))	return false;//部门高管领导
	if(sl_confirm("<%=LocalUtilis.language("message.saveUpdate",clientLocale)%> "))//保存修改
	{
		document.theform.submit();
	}	
}
</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="department_new.jsp" onsubmit="javascript:return validateForm(this);"><input type=hidden name="is_dialog" value="1"> 

		<table border="0" width="100%" cellspacing="0" cellpadding="0">
			<tr>
				<td class="page-title"><b><font color="#215dc6"><%=LocalUtilis.language("menu.departManage",clientLocale)%> </font></b></td>
				<!--部门管理-->
			</tr>
		</table>
		<br/>
		 <table border="0" width="100%" cellspacing="3">				
			<tr>
				<td width="31%" align="right"><%=LocalUtilis.language("class.departID",clientLocale)%> :</td><!--部门编号-->
				<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="depart_id" value=""></td>
			</tr>
			<tr>
				<td width="31%" align="right"><%=LocalUtilis.language("class.departName2",clientLocale)%> :</td><!--部门名称-->
				<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="depart_name" value=""></td>
			</tr>
			<tr>
				<td width="31%" align="right"><%=LocalUtilis.language("class.parentID",clientLocale)%> :</td><!--上级部门编号-->
				<td width="64%"><input readonly class="edline" onkeydown="javascript:nextKeyPress(this)" size="20" name="parent_id" value="<%=parent_id%>"></td>
			</tr>
			<tr>
				<td width="31%" align="right"><%=LocalUtilis.language("class.departJC",clientLocale)%> :</td><!--部门简称-->
				<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="depart_jc" value=""></td>
			</tr>
			<tr>
				<td width="31%" align="right"><%=LocalUtilis.language("class.managerMan",clientLocale)%> :</td><!--部门负责人-->
				<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="manager_man" value=""></td>
			</tr>
			<tr>
				<td width="31%" align="right"><%=LocalUtilis.language("class.leaderMam",clientLocale)%> :</td><!--高管领导-->
				<td width="64%"><input onkeydown="javascript:nextKeyPress(this)" size="20" name="leader_man" value=""></td>
			</tr>
		</table>
		<br/>
		<table border="0" width="100%">
			<tr>
				<td align="right">&nbsp;&nbsp;
				<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){ document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;<!--保存-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;</td><!--取消-->
			</tr>
		</table>
</form>
</BODY>
</HTML>
<%
depart.remove();
%>
