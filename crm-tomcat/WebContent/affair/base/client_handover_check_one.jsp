<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

CustManagerChangesLocal local = EJBFactory.getCustManagerChanges();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String manager_before = Utility.trimNull(request.getParameter("manager_before"));
String manager_now = Utility.trimNull(request.getParameter("manager_now"));
Integer handover_flag=new Integer(0);
Integer input_man=new Integer(0);

//保存信息
if(request.getMethod().equals("POST")){
	serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
	handover_flag=Utility.parseInt(request.getParameter("handover_flag"),new Integer(0));
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);

	TcustmanagerchangesVO vo = new TcustmanagerchangesVO();
	vo.setSerial_no(serial_no);
	vo.setCheck_flag(handover_flag);
	vo.setInput_man(input_man);
	try{
		local.check(vo);
		bSuccess = true;

	}catch(Exception e){
		String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
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
<title><%=LocalUtilis.language("message.CustomerTransferCheck",clientLocale)%> </title><!-- 客户移交审核-->
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
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/base/client_handover_check_one.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("message.CustomerTransferCheck",clientLocale)%></b></font><!-- 客户移交审核-->
	<hr noshade color="#808080" size="1">
</div>
<input type="hidden" name="serial_no" value="<%=serial_no%>" />
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		<tr>
			<td style="width:120px;" align="right"><%=LocalUtilis.language("class.beforeTurnOverManager",clientLocale)%>：</td><!--移交前客户经理-->
			<td ><input type="text" style="width: 120px;" name="manager_before" value="<%=manager_before%>" class="edline" readonly="readonly"></td>
		</tr>	
		<tr>
			<td style="width:120px;" align="right"><%=LocalUtilis.language("class.afterTurnOverManager",clientLocale)%>：</td><!--移交后客户经理-->
			<td ><input type="text" style="width: 120px;" name="manager_now" value="<%=manager_now%>" class="edline" readonly="readonly"></td>
		</tr>	
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.taskCheckOptions",clientLocale)%> ：</td><!-- 审核意见  -->
			<td>
				<select name="handover_flag" style="width: 110px;">
					<option value="2"><%=LocalUtilis.language("message.pass",clientLocale)%></option>
					<option value="3"><%=LocalUtilis.language("message.notPass2",clientLocale)%></option>
				</select>
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
    <!-- 保存 -->
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- 返回 -->
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>