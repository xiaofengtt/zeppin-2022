<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//上级群组ID和群组名称
Integer parent_group_id = Utility.parseInt(request.getParameter("group_id"), new Integer(0));
String parent_name = Utility.trimNull(request.getParameter("parent_name"));
String flag = Utility.trimNull(request.getParameter("flag"));//edit编辑；new新增
	
String group_name = Utility.trimNull(request.getParameter("group_name"));
boolean bSuccess = false;
String edit_group_name = "";

if(flag.equals("edit"))//如果是编辑，则显示该群组名称
	edit_group_name = parent_name;

CustGroupLocal local = EJBFactory.getCustGroup();
CustGroupVO vo = new CustGroupVO();

if (request.getMethod().equals("POST")){
	vo.setGroupId(Utility.parseInt(request.getParameter("parent_group_id"), new Integer(1)));
	vo.setGroupName(group_name);
	vo.setInputMan(input_operatorCode);

	if(flag.equals("new"))
		local.appendCustGroup(vo);
	else
		local.modiCustGroup(vo);
	bSuccess = true;
}
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK HREF="<%=request.getContextPath()%>/includes/default.css" TYPE="text/css" REL="stylesheet">
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
<script language="javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/includes/financing.js"></script>
<script language="javascript">
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>
//验证表单
function validateForm(form)
{	
    //群组名称
	if(!sl_check(document.getElementsByName("group_name")[0], "<%=LocalUtilis.language("class.groupName2",clientLocale)%> ", 60, 1))		return false; //nvarchar（60）
	//保存修改
    if(sl_confirm("<%=LocalUtilis.language("message.saveUpdate",clientLocale)%> "))
	{
		document.theform.submit();
	}	
}
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0"
	onkeydown="javascript:chachEsc(window.event.keyCode)" class="BODY">
<form name="theform" method="post" action="client_group_new.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="parent_group_id" value="<%=parent_group_id%>" />
<input type="hidden" name="flag" value="<%=flag%>" />
<input type="hidden" name="bSuccess" value="<%=bSuccess%>" />

<div style="height: 10px;"></div>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.groupManagerment",clientLocale)%> </b></font><!--群组管理-->
</div>
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		<tr <%if(flag.equals("edit")) out.print("style='display: none;'");%>>
			<td align="right" ><%=LocalUtilis.language("class.parentName",clientLocale)%> ：</td><!--上级群组名称-->
			<td >
				<input maxlength="100" readonly class='edline'  name="parent_name" size="30" onkeydown="javascript:nextKeyPress(this);" value="<%=parent_name%>">	
			</td>
		</tr>
		<tr>
			<td align="right">*<%=LocalUtilis.language("class.groupName2",clientLocale)%> ：</td><!--群组名称-->
			<td>
				<input type="text" name="group_name" size="30" value="<%=edit_group_name%>">
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
	<!--保存-->
    <button type="button"  class="xpbutton3" accessKey="s" id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){ document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--取消-->
    <button type="button"  class="xpbutton3" accessKey="c" id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
</div>
</form>
</BODY>
</HTML>
