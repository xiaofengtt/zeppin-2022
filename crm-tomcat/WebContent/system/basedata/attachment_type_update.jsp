<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
AttachmentTypeVO vo = new AttachmentTypeVO();
AttachmentTypeLocal attachment_type = EJBFactory.getAttachmentType();

Integer attachment_type_id = Utility.parseInt(request.getParameter("attachment_type_id"),null);
String attachment_type_name = new String("");
String attachment_type_summary = new String("");
Integer is_necessary = new Integer(0);
Integer df_table_id = new Integer(0);
String df_table_name = new String("");
boolean bSuccess = false;
List listAll = null;
Map map = null;

if(attachment_type_id.intValue()!= 0){
	vo.setAttachmentType_id(attachment_type_id);
	listAll = attachment_type.listBySql(vo);
	map = (Map)listAll.get(0); 
}
if(map!= null){
	attachment_type_name = Utility.trimNull(map.get("ATTACHMENT_TYPE_NAME"));
	attachment_type_summary = Utility.trimNull(map.get("ATTACHMENT_TYPE_SUMMARY"));	
	is_necessary = Utility.parseInt(Utility.trimNull(map.get("IS_NECESSARY")),new Integer(0));
	df_table_id = Utility.parseInt(Utility.trimNull(map.get("DF_TABLE_ID")),new Integer(0));
	df_table_name=Utility.trimNull(map.get("DF_TABLE_NAME"));
}

if(request.getMethod().equals("POST")){
	vo.setAttachmentType_id(attachment_type_id);
	vo.setAttachmentType_name(request.getParameter("attachment_type_name"));
	vo.setAttachmentTypeSummary(request.getParameter("attachment_type_summary"));
	vo.setIS_Necessary( Utility.parseInt(request.getParameter("is_necessary"),new Integer(0)));
	vo.setDF_Table_id(Utility.parseInt(request.getParameter("df_table_id"),new Integer(0)));
	vo.setDF_Table_name(Utility.trimNull(request.getParameter("df_table_id")));
	attachment_type.modi(vo,input_operatorCode);
	bSuccess = true;
}

%>

<html>
<head>
<title><%=LocalUtilis.language("menu.attachment_typeUpdate",clientLocale)%> </title>
<!--修改附件类别信息-->
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
	//if(!sl_checkNum(form.attachment_type_id, "附件类型编号", 10, 1))	return false;
	if(!sl_check(form.attachment_type_name, "<%=LocalUtilis.language("class.attachmentTypeName",clientLocale)%> ", 16, 1))		return false;//附件类型名称
	
	if(!checkHTML("'\"<>|!@#$%^&*()_+{}?/\\",$$("attachment_type_name").getAttribute("value"),$$("attachment_type_name"))){
		alert("<%=LocalUtilis.language("message.attachment_typeNameError",clientLocale)%> ："+"'\"<>|!@#$%^&*()_+{}?/\\");//附件类型名称中有不能使用的字符
		return false;
	}
	
	if(!sl_check(form.summary, "<%=LocalUtilis.language("class.attachmentTypeSummary",clientLocale)%> ", 200, 0))		return false;//备注

	return sl_check_update();
}
</script>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="attachment_type_update.jsp" onsubmit="javascript:return validateForm(this);"><input type=hidden name="is_dialog" value="1">
	<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("menu.attachmetTypeInfoNew",clientLocale)%> </b></font></td>
		</tr><!--新增附件类型信息-->
	</table>
	<br/>
	<table border="0" width="100%" cellspacing="3" class="product-list">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.qAttachmentTypeID",clientLocale)%> : </td><!--附件类别编号-->
			<td  align="left"><input type="text" readonly="readonly" class="edline" name="attachment_type_id" size="15" value="<%=attachment_type_id %>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.attachmentTypeName",clientLocale)%> : </td><!--附件类别名称-->
			<td align="left" ><input type="text" name="attachment_type_name" size="15" value="<%=attachment_type_name %>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>								
		<tr>
			<td align="right"><%=LocalUtilis.language("class.attachmentTypeSummary",clientLocale)%> :</td><!--备注-->			
			<td><textarea rows="3" name="attachment_type_summary" onkeydown="javascript:nextKeyPress(this)" cols="30"><%=attachment_type_summary %></textarea></td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("menu.isNecessary",clientLocale)%> :</td><!--是否为必要附件-->
			<td><input type="checkbox" name="is_necessary" value="1"  <%if(is_necessary.intValue() == 1)out.print("checked");%>/></td>
		</tr>
<%
	String value;
	if(Utility.trimNull(map.get("DF_TABLE_ID")).length() == 1) {
		value="0"+Utility.trimNull(map.get("DF_TABLE_ID"));
	}	
	else {
		value=Utility.trimNull(map.get("DF_TABLE_ID"));
	}
 %>
		<tr>
			<td align="right" noWrap><%=LocalUtilis.language("class.dfTable",clientLocale)%> :</td><!--对应表--> 
			<td><select size="1" name="df_table_id" onkeydown="javascript:nextKeyPress(this)" style="width: 150px">
					<%=Argument.getAttachmentTypeValue(value)%>
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

