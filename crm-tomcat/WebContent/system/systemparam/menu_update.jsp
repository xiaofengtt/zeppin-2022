<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
MenuInfoVO vo = new MenuInfoVO();
MenuInfoLocal menu = EJBFactory.getMenuInfo();

boolean bSuccess = false;
String menu_id2 = Utility.trimNull(request.getParameter("menu_id"));
String menu_name = Utility.trimNull(request.getParameter("menu_name"));
String menu_info2 = Utility.trimNull(request.getParameter("menu_info"));
String url = Utility.trimNull(request.getParameter("url"));
String  parent_id = Utility.trimNull(request.getParameter("parent_id"));

List rsListSig = null;
Map rowMapSig = null;

if (request.getMethod().equals("POST")){		
	vo.setMenu_id(menu_id2);
	vo.setMenu_name(menu_name);
	vo.setMenu_info(menu_info2);
	vo.setUrl(url);
	
	menu.modi(vo,input_operatorCode);
	bSuccess = true;
}
else{
	rsListSig = menu.listBySig(menu_id2,parent_id);
	rowMapSig = (Map)rsListSig.get(0);
	
	if(rowMapSig != null){
		menu_id2 = Utility.trimNull(rowMapSig.get("MENU_ID"));
		menu_name = Utility.trimNull(rowMapSig.get("MENU_NAME"));
		menu_info2 = Utility.trimNull(rowMapSig.get("MENU_INFO"));
		url = Utility.trimNull(rowMapSig.get("URL"));
	}
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
<title><%=LocalUtilis.language("menu.menuUpdate",clientLocale)%> </title>
<!--菜单信息修改-->

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
<%
if (bSuccess){
%>
window.returnValue = 1;
window.close();
<%}%>

function validateForm(form){
	if(!sl_check(form.menu_name, "<%=LocalUtilis.language("class.menuName",clientLocale)%> ", 60, 1))return false;//菜单名称	
	return sl_check_update();
}
</script>
</HEAD>

<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="menu_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no" value="<%=menu_id2%>">
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
								<td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28">
								    <font color="#215dc6"><b><%=LocalUtilis.language("class.menuInfo3",clientLocale)%> </b></font><!--菜单信息-->
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
								<td width="35%" align="right"><%=LocalUtilis.language("class.menuID",clientLocale)%> </td><!--菜单编号-->
								<td width="65%"><input type="text" name="menu_id" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=menu_id2%>" class="edline" readonly></td>
							</tr>
							<tr>
								<td width="35%" align="right"><%=LocalUtilis.language("class.menuName",clientLocale)%> :</td><!--菜单名称-->
								<td width="63%"><input type="text" name="menu_name" onkeydown="javascript:nextKeyPress(this)" size="20" value="<%=menu_name%>"></td>
							</tr>
							<tr>
								<td width="35%" align="right"><%=LocalUtilis.language("class.menuInfo3",clientLocale)%> :</td><!--菜单信息-->
								<td width="63%"><input type="text" name="menu_info" onkeydown="javascript:nextKeyPress(this)" size="50" value="<%=menu_info2%>"></td>
							</tr>
							<tr>
								<td width="35%" align="right">URL:</td>
								<td width="63%"><input type="text" name="url" onkeydown="javascript:nextKeyPress(this)" size="50" value="<%=url%>"></td>
							</tr>									
						</table>
						<br>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();">
								    <%=LocalUtilis.language("message.save",clientLocale)%> <!--保存-->(<u>S</u>)
								</button>
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
<%menu.remove();%>