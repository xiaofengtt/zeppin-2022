<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
SystemInfoLocal systeminfo = EJBFactory.getSystemInfo();
boolean bSuccess = false;
int backupflag = Utility.parseInt(request.getParameter("backupflag"), 1);
scaption=enfo.crm.tools.LocalUtilis.language("menu.backupDB2",clientLocale);//备份数据库
if (request.getMethod().equals("POST"))
{
	systeminfo.backupDataBase(backupflag,Utility.trimNull(request.getParameter("type_name")),Utility.trimNull(request.getParameter("type_value")),input_operatorCode);
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
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>
function validateForm(form)
{
	
	if(!sl_check(form.type_value, "<%=LocalUtilis.language("class.backupRemark",clientLocale)%> ", 60, 1))	return false;//备份说明	
	if(!sl_check(form.type_name, "<%=LocalUtilis.language("class.backupFileName",clientLocale)%> ", 60, 1))	return false;//备份文件名
	
	if(sl_confirm("<%=LocalUtilis.language("message.confirmBackup",clientLocale)%> "))//确认备份
	{
		waitting.style.visibility='visible'; 
		document.theform.btnSave.disabled =  true;
		document.theform.submit();
	}	
}
</script>
</HEAD>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" >
<%@ include file="/includes/waiting.inc"%>	
<form name="theform" method="POST" action="backup_new.jsp">

<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("menu.parameterSet",clientLocale)%> </b></font></td>
							</tr><!--参数设置-->
							<tr>
								<td>
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3">
							<tr>
								<td width="46%" align="right"><b><%=LocalUtilis.language("menu.backupInfo",clientLocale)%> </b></td><!--备份信息-->
								<td width="49%"></td>
							</tr>
							<tr>
							<td align=right><%=LocalUtilis.language("class.backUpDB",clientLocale)%> :</td><!--备份当前库-->
								<td align=left>
									<INPUT name="backupflag" checked maxlength="17" checked class="flatcheckbox" type="radio" value=1 onkeydown="javascript:nextKeyPress(this)" > 
								</td>
							</tr>	
							<tr>
							<td align=right><%=LocalUtilis.language("class.BackupHisDB",clientLocale)%> :</td><!--备份历史库-->
								<td align=left>
									<INPUT name="backupflag" maxlength="17" checked class="flatcheckbox" type="radio" value=2 onkeydown="javascript:nextKeyPress(this)" > 
								</td>
							</tr>	
							<tr>
								<td width="46%" align="right"><%=LocalUtilis.language("class.backupFileName",clientLocale)%> :</td><!--备份文件名-->
								<td width="49%"><input type="text" name="type_name" onkeydown="javascript:nextKeyPress(this)" size="20"></td>
							</tr>
							<tr>
								<td width="46%" align="right"><%=LocalUtilis.language("class.backupRemark",clientLocale)%> :</td><!--备份说明-->
								<td width="49%"><input type="text" name="type_value" onkeydown="javascript:nextKeyPress(this)" size="20"></td>
							</tr>
							
						</table>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:validateForm(document.theform);">
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
<%@ include file="/includes/foot.inc"%>
</BODY>

</HTML>
<%systeminfo.remove();
%>
