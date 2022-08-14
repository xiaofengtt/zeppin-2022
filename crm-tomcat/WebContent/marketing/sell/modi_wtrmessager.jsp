<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
try{
TMoneyDataLocal moneyData = EJBFactory.getTMoneyData();
TMoneyDataVO vo = new TMoneyDataVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), null);
Integer cust_id  = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));

boolean bSuccess = false;
if (request.getMethod().equals("POST")){
	moneyData.modiWTR(serial_no,cust_id,cust_name);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/jQuery/FormValidate/css/css.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT language="javascript">

<%if (bSuccess){%>  
	sl_alert("保存成功");
	window.returnValue = 1;
	window.close();	
<%}%>


function validateForm(form)
{
	return sl_check_update();
}

//选择委托人
function openCustInfo(prefix,readonly){
	var cust_id = document.theform.cust_id.value;
	if(cust_id!=0){
		var url = '/marketing/customer_info_simple.jsp?prefix=' + prefix + '&cust_id=' + cust_id+'&readonly='+readonly;
		var ret = showModalDialog(url,'','dialogWidth:700px;dialogHeight:688px;status:0;help:0;');
		document.theform.cust_id.value = ret[7]
		document.theform.cust_name.value = ret[0];
	}
}

</SCRIPT>
<BODY class="BODY">

<form name="theform" method="post" action="modi_wtrmessager.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no"  value="<%=serial_no%>">
<input type="hidden" name="cust_id"   value="<%=cust_id%>">

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
<TR>
	<TD vAlign=top align=left>
	<TABLE cellSpacing=0 cellPadding=2 width="100%" border=0>
		<tr>
			<td><img border="0" src="/images/ico_area.gif" width="32" height="32"><b>委托人修改</b></td>
		</tr>
		<tr>
			<td><hr size="1"></td>
		</tr>
	</TABLE>
	<TABLE cellSpacing=0 cellPadding=3 width="100%" border=0>
		<tr>
			<td><b>客户信息</b></td>
		</tr>
		<tr>
			<td align="right">原委托人:</td> 
			<td >
				<input maxlength="100" class="edline" name="old_cust_name" style="WIDTH: 200px" onkeydown="javascript:nextKeyPress(this);"  value="<%=Utility.trimNull(cust_name)%>"  readonly >
			</td>
		</tr>
		<tr>
			<td align="right">客户列表:</td>
			<td >
				<input class="edline" readonly="readonly" name="cust_name" value="<%=Utility.trimNull(cust_name) %>" style="WIDTH: 240px" onkeydown="javascript:nextKeyPress(this);">
				<button class="xpbutton2" name="cust_show_info" title="查看客户信息" onclick="javascript:openCustInfo('customer','0');">查看</button>
			</td>
		</tr>	
		<tr>
			<td colspan="4">
			<table border="0" width="100%">
				<tr>
					<td align="right">
					<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){document.theform.btnSave.disabled='true';document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!--保存-->
					&nbsp;&nbsp;&nbsp;
					<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button><!--取消-->
					&nbsp;&nbsp;&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</TABLE>
	</TD>
</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%
moneyData.remove();
}catch(Exception e){
	throw e;
}%>