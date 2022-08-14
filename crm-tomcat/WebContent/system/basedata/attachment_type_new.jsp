<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
AttachmentTypeVO vo = new AttachmentTypeVO();
AttachmentTypeLocal attachmentType = EJBFactory.getAttachmentType();
Integer attachment_type_id = Utility.parseInt(request.getParameter("attachment_type_id"),new Integer(0));
boolean bSuccess = false;

if(request.getMethod().equals("POST")){
	vo.setAttachmentType_id(attachment_type_id);
	vo.setAttachmentType_name(request.getParameter("attachment_type_name"));
	vo.setAttachmentTypeSummary(request.getParameter("attachment_type_summary"));
	vo.setIS_Necessary( Utility.parseInt(request.getParameter("is_necessary"),new Integer(0)));
	vo.setDF_Table_id(Utility.parseInt(request.getParameter("df_table_id"),new Integer(0)));
	vo.setDF_Table_name(Utility.trimNull(request.getParameter("df_table_id")));
	attachmentType.append(vo,input_operatorCode);
	bSuccess = true;
}
%>
<html>
<head>
<title><%=LocalUtilis.language("menu.attachment_typeNew",clientLocale)%> </title>
<!--新增附件类别-->
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
	if(!sl_check(form.attachment_type_name, "<%=LocalUtilis.language("class.attachmentTypeName",clientLocale)%> ", 16, 1))		return false;//附件类别名称
	if(!checkHTML("'\"<>|!@#$%^&*()_+{}?/\\",$$("attachment_type_name").getAttribute("value"),$$("attachment_type_name"))){
		alert("<%=LocalUtilis.language("message.attachment_typeNameError",clientLocale)%> ："+"'\"<>|!@#$%^&*()_+{}?/\\");//附件类别名称中有不能使用的字符
		return false;
	}

	if(!sl_check(form.summary, "<%=LocalUtilis.language("class.attachmentTypeSummary",clientLocale)%> ", 200, 0))		return false;//备注

	return sl_check_update();
	
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="attachment_type_new.jsp" onsubmit="javascript:return validateForm(this);"><input type=hidden name="is_dialog" value="1">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("menu.attachmetTypeInfoNew",clientLocale)%> </b></font></td>
				</tr>
</table>
<br/>
<table border="0" width="100%" cellspacing="3" class="product-list">
			<tr>
				<td align="right"><%=LocalUtilis.language("class.attachmentTypeName",clientLocale)%> : </td><!--附件类别名称-->
				<td align="left" ><input type="text" name="attachment_type_name" size="15" value="<%=Utility.trimNull(vo.getAttachmentType_name()) %>" onkeydown="javascript:nextKeyPress(this)"></td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.attachmentTypeSummary",clientLocale)%> :</td><!--备注-->
				<td><textarea rows="3" name="attachment_type_summary"  onkeydown="javascript:nextKeyPress(this)" cols="30"></textarea></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("menu.isNecessary",clientLocale)%>:</td><!--是否为必要附件-->
				<td><input type="checkbox" name="is_necessary" value="1" /></td>
			</tr>
			<tr>
				<td align="right" noWrap><%=LocalUtilis.language("class.dfTable",clientLocale)%> :</td><!--对应表--> 
				<td><select size="1" name="df_table_id" onkeydown="javascript:nextKeyPress(this)" style="width: 150px">
					<%=Argument.getAttachmentTypeValue("")%>
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
