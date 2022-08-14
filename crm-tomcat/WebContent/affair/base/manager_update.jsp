<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.*,enfo.crm.affair.*,enfo.crm.system.*,java.util.* ,enfo.crm.dao.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
TcustmanagersLocal local = EJBFactory.getTcustmanagers();
TcustmanagertreeVO vo = new TcustmanagertreeVO();

boolean bSuccess = false;
Integer depart_id = new Integer(Utility.parseInt(request.getParameter("depart_id"), 1)); // default is 1
Integer margin_id = new Integer(Utility.parseInt(request.getParameter("margin_id"), 1));


if(request.getMethod().equals("POST")){
	vo.setSerial_no(Utility.parseInt(request.getParameter("serial_no"),new Integer(0)));
	vo.setManagerid(Utility.parseInt(request.getParameter("managerid"),new Integer(0)));

	local.tree_modi(vo,input_operatorCode);
	
	bSuccess = true;
}	
%>


<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("index.menu.modifyManager",clientLocale)%> </TITLE><!-- 经理管理修改 -->
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
    //请选择客户经理
	if(theform.managerid.value == ""){alert("<%=LocalUtilis.language("message.selectAccountManager",clientLocale)%> ！");return false;}
	return sl_check_update();
}
</script>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/base/manager_update.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="depart_id" value="<%=depart_id%>">
<input type=hidden name="is_dialog" value="1"> <%if (depart_id.intValue() == 0)
{
%> <input type=hidden name="is_new" value="1"> <%}
%>

		<table border="0" width="100%" cellspacing="0" cellpadding="0">
			<tr>
                <!-- 经理管理 -->
				<td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28"><b><font color="#215dc6"><%=LocalUtilis.language("index.menu.managerManagement",clientLocale)%> </font></b></td>
			</tr>
			<tr>
				<td>
		      		<hr noshade color="#808080" size="1">
				</td>
			</tr>
		</table>

		 <table border="0" width="100%" cellspacing="3">
		 	<tr>
                <!-- 子节点经理 -->
				<td width="31%" align="right"><%=LocalUtilis.language("class.childNodeManager",clientLocale)%> :</td>
				<td width="64%">
					<input type="hidden" name="serial_no" value="<%=depart_id%>"/>
					<select name="managerid" style="width:120px;">
						<%=Argument.getManager_Value(margin_id)%>
					</select>
				</td>
			</tr>	
		</table>
		<table border="0" width="100%">
			<tr>
				<td align="right">&nbsp;&nbsp;
                <!-- 保存 -->
				<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){ document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
				&nbsp;&nbsp;
                 <!-- 取消 -->
				<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;</td>
			</tr>
		</table>
</form>
</BODY>
</HTML>
<%
local.remove();
%>
