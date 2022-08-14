<%@ page contentType="text/html; charset=GBK" import="java.util.*,enfo.crm.util.*,enfo.crm.service.*,java.math.*,enfo.crm.intrust.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>
<%
CustomerLocal cust_local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

boolean bSuccess = false;
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));
String mobile = Utility.trimNull(request.getParameter("mobile"));
String new_mobile = Utility.trimNull(request.getParameter("new_mobile"));

if (request.getMethod().equals("POST")){
	vo.setCust_id(cust_id);
	vo.setMobile(new_mobile);
	vo.setInput_man(input_operatorCode);
	cust_local.modiCustomerMobile(vo);
	cust_local.modiCustYc_Mobile(cust_id);
	bSuccess = true;
}

Map customer_map = cust_local.queryCustomersByCustId(cust_id);
mobile = Utility.trimNull(customer_map.get("MOBILE"));
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.personInfo",clientLocale)%></TITLE><!--个人客户信息-->
<BASE TARGET="_self">
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
<script type="text/javascript">
<%if(bSuccess){%>
	sl_update_ok();
	
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form) {
	if (! sl_checkNum(form.new_mobile, "新手机号码 ", 11, 1)) return false;
	if (sl_confirm("变更手机号码"))
		document.theform.submit();
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="customer_mobile_update.jsp" >

<input type="hidden" name="cust_id" value="<%=cust_id%>"/>

<table border="0" width="100%" cellspacing="3" cellpadding="3">
	<tr>
		<td align="right"><font color="red">*</font>手机号码 ：</td>
		<td>
			<input id="mobile" name="mobile" class="edline" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(mobile)%>" readonly />
		</td>
		<td align="right"><font color="red">*</font>新手机号码 ：</td>
		<td>
			<input id="new_mobile" name="new_mobile" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(new_mobile)%>" />
		</td>	
	</tr>
</table>

<div align="right" style="margin-right:5px">
    <BUTTON class="xpbutton3" accesskey="s" id="btnSave" name="btnSave" onclick="javascript:validateForm(document.theform);">保存 (<U>S</U>)</BUTTON>
	&nbsp;&nbsp;
    <BUTTON class="xpbutton3" accesskey="b" id="btnReturn" name="btnReturn" onclick="javascript:disableAllBtn(true);window.close();">关闭 (<U>B</U>)</BUTTON>
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>