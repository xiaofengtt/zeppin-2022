<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.affair.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;
CustomerVO vo = new CustomerVO();
CustManagerChangesLocal local = EJBFactory.getCustManagerChanges();
//获得参数
String scust_id = request.getParameter("cust_id");
String [] cust_items = Utility.splitString(scust_id, ",");
Integer input_man = null;

//保存产品信息
if(request.getMethod().equals("POST")){
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),new Integer(0));
	
	if (cust_items != null) {
		for (int i = 0;i < cust_items.length; i++) {
			int custId = Utility.parseInt(cust_items[i], 0);
			if (custId != 0) {
				vo.setCust_id(new Integer(custId));
				vo.setInput_man(input_man);
				local.modiCustInputMan(vo);
			}
		}
		bSuccess = true;
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
<title> </title><!-- 修改录入人 -->
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
	if(document.getElementsByName("input_man")[0].getAttribute("value") == ""){
		alert("请选择录入人！");
		return false;
	}

	return sl_check_update();	
}

/*拼后缀参数*/
function getLastOptions(){	
	var url = 'cust_inputMan_modiAction.jsp?input_man=' + document.theform.input_man.value+'&cust_id=' + document.theform.cust_id.value;
	location = url;	
}

function SaveAction(){		
	if(document.theform.onsubmit()){
		disableAllBtn(true);
		document.theform.submit();
	}	
}

function CancelAction(){
	window.returnValue=null;
	window.close();
}
</script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post" action="cust_inputMan_modiAction.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="cust_id" value="<%=scust_id%>" />
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>

<div align="center">
	<table width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.inputMan",clientLocale)%> :</td><!--录入人-->
		<td valign="bottom" align="left">
			<select name="input_man" style="width: 110px;">
					<%=Argument.getManager_Value(new Integer(0))%>
			</select>
		</td>
	</tr>
	</table>
</div>

<br>

<div align="right">
    <!-- 保存 -->
	<button type="button" class="xpbutton3" accessKey=s onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- 关闭 -->
	<button type="button" class="xpbutton3" accessKey=c onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button>&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>