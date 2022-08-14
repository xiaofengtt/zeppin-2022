<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
boolean bSuccess = false;
int first = Utility.parseInt(request.getParameter("first"), 0);
int flag = Utility.parseInt(request.getParameter("subflag"), 0);
Integer serial_no = new Integer(Utility.parseInt(request.getParameter("serial_no"), 0));
Integer type_id = Utility.parseInt(request.getParameter("type_id"),new Integer(5600));
String type_name = "外部网站链接";
String type_value = Utility.trimNull(request.getParameter("type_value"));
String type_content = Utility.trimNull(request.getParameter("type_content"));
String additive_value = Utility.trimNull(request.getParameter("additive_value"));

int editflag = 0; 
String strUrl = "http://";

DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();

if (serial_no.intValue() > 0){
	editflag = 1;
	
	if(first == 1){	
		vo.setSerial_no(serial_no);		
		strUrl = "";
		Map rowMap=(Map)dictparam.listDictparamAll(vo).get(0);
		type_id = Utility.parseInt(Utility.trimNull(rowMap.get("TYPE_ID")),new Integer(0));
		type_name = Utility.trimNull(rowMap.get("TYPE_NAME"));
		type_value = Utility.trimNull(rowMap.get("TYPE_VALUE"));
		type_content = Utility.trimNull(rowMap.get("TYPE_CONTENT"));
		additive_value = Utility.trimNull(rowMap.get("ADDITIVE_VALUE"));
	}
}else{
	vo.setType_id(type_id);
	vo.setType_name(type_name);
	String[] totalColumn = new String[0];
	IPageList pageList = dictparam.pagelistDictparamAll(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
	List rsList = pageList.getRsList();
	Map rowMap = null;
	int value = rsList.size();
	value = value+1;
	
	if(value < 10){
		type_value = "56000" + value;
	}else{
		type_value = "5600" + value;
	}

}

if(flag==1)
{
	editflag = Utility.parseInt(request.getParameter("editflag"), 0);	
	vo.setType_id(type_id);
	vo.setType_name(type_name);	
	vo.setType_value(type_value);
	vo.setType_content(type_content);
	vo.setAdditive_value(additive_value);
	
	if (editflag == 1){
	    vo.setSerial_no(serial_no);
		dictparam.modiDictparam1(vo,input_operatorCode);
	}		
	else{
		dictparam.addWebLinks(vo,input_operatorCode);
	}
		
	bSuccess = true;
}

%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title><%=LocalUtilis.language("menu.backupNew",clientLocale)%> </title>
<!--参数信息-->

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
<%
if (bSuccess){
%>
	window.returnValue = true;
	window.close();
<%}%>

function validateForm(form)
{
	var linkurl = form.additive_value.value;
	var t = "http://";
	var value = linkurl.indexOf(t); 
	if(value != 0){
		alert("<%=LocalUtilis.language("message.URLFormat",clientLocale)%>");
		return false;
	}
	if(!sl_check(form.type_content, "<%=LocalUtilis.language("class.linkName",clientLocale)%> ", 100, 1))	return false;//链接名称	
	if(!sl_check(form.additive_value, "<%=LocalUtilis.language("class.linkURL",clientLocale)%> ", 200, 1))	return false;//链接URL
	return sl_check_update();
}
</script>
</HEAD>

<BODY class="BODY body-nox" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">

<form name="theform" method="post" action="weblinks_info.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="serial_no" value="<%=serial_no%>">

<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
		<TD vAlign=top align=center>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
					<TD>
					<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td class="page-title">
							<font color="#215dc6"><b><%=type_name%> </b></font></td><!--外部链接-->
						</tr>
					</table>
					<br/>
					<table border="0" width="100%" cellspacing="3" class="product-list">
						<tr>
							<td align="right"><%=LocalUtilis.language("class.ID",clientLocale)%> :&nbsp;</td><!--链接编号-->
							<td align="left"><input type="text" name="type_value" onkeydown="javascript:nextKeyPress(this)" size="20" readonly class="edline"value="<%=type_value%>"></td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.linkName",clientLocale)%> :</td><!--链接名称-->
							<td align="left"><input type="text" name="type_content" onkeydown="javascript:nextKeyPress(this)" size="34" value="<%=type_content%>">
								<input type="hidden" name="editflag" value="<%=editflag%>"> 
								<input type="hidden" name="subflag" value=0> 
							</td>
						</tr>
						<tr>
							<td align="right"><%=LocalUtilis.language("class.linkURL",clientLocale)%> :</td><!--链接URL-->
							<td align="left">
							<TEXTAREA rows="4" name="additive_value" onkeydown="javascript:nextKeyPress(this)" cols="35"><%=strUrl%><%=additive_value%></TEXTAREA>
							</td>
						</tr>
					</table>
					<table border="0" width="100%">
						<tr>
							<td align="center">
							<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" 
									onclick="javascript: if(document.theform.onsubmit()){ disableAllBtn(true);document.theform.subflag.value=1;document.theform.submit(); } ">
								<%=LocalUtilis.language("message.save",clientLocale)%> <!--保存-->(<u>S</u>)</button>
							&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">
							    <%=LocalUtilis.language("message.cancel",clientLocale)%> <!--取消-->(<u>C</u>)
							</button>
							&nbsp;&nbsp;
							</td>
						</tr>
					</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>

</HTML>
<%dictparam.remove();%>
