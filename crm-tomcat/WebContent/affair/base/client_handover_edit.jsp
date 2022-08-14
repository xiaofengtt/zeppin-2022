<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

CustManagerChangesLocal local = EJBFactory.getCustManagerChanges();

Integer serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),null);
Integer managerid_before = Utility.parseInt(Utility.trimNull(request.getParameter("managerid_before")),null);
Integer managerid_now = Utility.parseInt(Utility.trimNull(request.getParameter("managerid_now")),null);

if(request.getMethod().equals("POST")){
	TcustmanagerchangesVO vo = new TcustmanagerchangesVO();
	vo.setSerial_no(serial_no);
	vo.setManagerid_before(managerid_before);
	vo.setManagerid_now(managerid_now);
	vo.setInput_man(input_operatorCode);
	
	try{
		local.modi(vo);
		bSuccess = true;

	}catch(Exception e){
        String message = LocalUtilis.language("message.saveFail", clientLocale); //保存失败
		out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\"); window.returnValue = null;window.close();</script>");
	}
}

local.remove();
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("index.menu.updateTrunOver",clientLocale)%> </title><!-- 修改移交 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
<% if(bSuccess){ %>
	window.returnValue = true;
	window.close();
<%}%>

function validateForm(form){		
	return sl_check_update();	
}

function SaveAction(){
	if(document.getElementsByName("managerid_before")[0].getAttribute("value") == ""){
		alert("<%=LocalUtilis.language("message.beforeTurnOverManagerError",clientLocale)%> ！"); 
		return false;//请选择移交前的客户经理 
	}
	
	if(document.getElementsByName("managerid_now")[0].getAttribute("value") == ""){
		alert("<%=LocalUtilis.language("message.afterTurnOverManagerError",clientLocale)%> ！"); 
		return false;//请选择移交后的客户经理
	}

	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].submit();
	}	
}

function CancelAction(){
	window.returnValue=null;
	window.close();
}
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" style="width:500px" method="post"  action="<%=request.getContextPath()%>/affair/base/client_handover_edit.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>
<input type="hidden" name="serial_no" value="<%=Utility.trimNull(serial_no)%>" />
<div align="center" >
	<table border="0" width="100%" cellspacing="5" cellpadding="0">	
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.accountManager",clientLocale)%> ：</td><!-- 客户经理 -->
			<td>
				<select name="managerid_before" style="width: 110px;">
					<%=Argument.getManager_Value(managerid_before)%>
				</select>
			</td>
			<td><span><font color="red"><%=LocalUtilis.language("message.transferredTo",clientLocale)%> </font></span></td><!-- 移交至 -->
			<td  align="right">*<%=LocalUtilis.language("class.accountManager",clientLocale)%> ：</td><!-- 客户经理 -->
			<td>
				<select name="managerid_now" style="width: 110px;">
					<%=Argument.getManager_Value(managerid_now)%>
				</select>
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!-- 保存 -->
	&nbsp;&nbsp;&nbsp;&nbsp;
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button><!-- 返回 -->
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>