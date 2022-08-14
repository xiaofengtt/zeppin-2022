<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();
boolean bSuccess = false;
Integer type_id = new Integer(Utility.parseInt(request.getParameter("type_id"), 0));

if (request.getMethod().equals("POST"))
{
	vo.setType_id(type_id);
	vo.setType_value(request.getParameter("type_value"));
	vo.setType_content(request.getParameter("type_content"));
	dictparam.addReportparam(vo,input_operatorCode);
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
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script  LANGUAGE=javascript>
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>
function validateForm(form)
{
	if(!sl_checkNum(form.type_id, "<%=LocalUtilis.language("class.typeID",clientLocale)%> ", 10, 1))	return false;//参数类型
	if(!sl_check(form.type_value, "<%=LocalUtilis.language("message.paraValue",clientLocale)%> ", 10, 1))	return false;//参数值	
	if(!sl_check(form.type_content, "<%=LocalUtilis.language("class.typeContent2",clientLocale)%> ", 60, 1))	return false;//参数含义	
	return sl_check_update();
}
</script>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="report_config_new.jsp" onsubmit="javascript:return validateForm(this);"><input type=hidden name="is_dialog" value="1">

<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28">
								    <font color="#215dc6"><b><%=LocalUtilis.language("menu.parameterSet",clientLocale)%> </b></font><!--参数设置-->
								</td>
							</tr>
							<tr>
								<td>
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3">
							<tr>
								<td width="46%" align="right"><b><%=LocalUtilis.language("menu.backupNew",clientLocale)%> </b></td><!--参数信息-->
								<td width="49%"></td>
							</tr>
							<tr>
								<td width="46%" align="right"><%=LocalUtilis.language("class.typeID",clientLocale)%> :</td><!--参数类型-->
								<td width="49%"><input type="text" name="type_id" onkeydown="javascript:nextKeyPress(this)" size="20"></td>
							</tr>
							<tr>
								<td width="46%" align="right"><%=LocalUtilis.language("message.paraValue",clientLocale)%> :&nbsp;</td><!--参数值-->
								<td width="49%"><input type="text" name="type_value" onkeydown="javascript:nextKeyPress(this)" size="20"></td>
							</tr>
							<tr>
								<td width="46%" align="right"><%=LocalUtilis.language("class.typeContent2",clientLocale)%> :</td><!--参数含义-->
								<td width="49%"><input type="text" name="type_content" onkeydown="javascript:nextKeyPress(this)" size="20"></td>
							</tr>
						</table>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();">
								    <%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)
								</button><!--保存-->
								&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">
								    <%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)
								</button><!--取消-->
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
<%dictparam.remove();
%>
