<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer sub_flag = Utility.parseInt(Utility.trimNull(request.getParameter("sub_flag")),new Integer(0));
String productName = Utility.trimNull(request.getParameter("productName"));
BigDecimal fareRate = Utility.parseDecimal(Utility.trimNull(request.getParameter("fareRate")), new BigDecimal(0),4,"0.01");
String summary = Utility.trimNull(request.getParameter("summary"));
boolean bSuccess = false;
List list = null;
Map map = new HashMap();
ProductVO vo = new ProductVO();
ProductLocal local = EJBFactory.getProduct();

vo.setSerial_no(serial_no);
if(request.getMethod().equals("POST")){
	vo.setR_chanel_rate(fareRate);
	vo.setSummary(summary);
	vo.setInput_man(input_operatorCode);
	
	local.modiMarketTrench(vo);;
	bSuccess = true;
}else{
	list = local.queryMarketTrench(vo);
	if(list != null && list.size() != 0)
		map = (Map)list.get(0);
}
%>
<html>
<head>
<title>��Ʒ������������</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Expires" content="0">
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>
<script language=javascript>
<%
if (bSuccess){
%>
	window.returnValue = 1;
	window.close();
<%}%>


function validateForm(form)
{
	if(!sl_checkDecimal(form.fareRate, "�������۷���", 7, 4, 0))	return false;

	if(form.fareRate.value > 100){
		sl_alert("�������۷��ʲ��ܴ���100��");
		form.fareRate.focus();
		return false;
	}

	if(form.fareRate.value <= 0){
		sl_alert("�������۷��ʲ���С�ڵ����㣡");
		form.fareRate.focus();
		return false;
	}

	if(!sl_check(form.summary, "<%=LocalUtilis.language("class.customerSummary",clientLocale)%> ", 2000, 0))		return false;//��ע

	return sl_check_update();
}
</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);">
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>��Ʒ������������</b></font></td>
	</tr>
</table>
<div style="height: 20px;"></div>
<table border="0" width="100%" cellspacing="5" cellpadding="8" class="product-list">
	<tr>
		<td  width="100px" align="right">��Ʒ����:</td>
		<td><input readonly="readonly" class="edline" value="<%=productName %>" size="40"></td>
	</tr>
	<%if(sub_flag.intValue() ==  1){ %>
	<tr>
		<td  align="right">�Ӳ�Ʒ����:</td>
		<td align="left" >
			<input readonly="readonly" class="edline" value="<%=Utility.trimNull(map.get("LIST_NAME")) %>" size="40">
		</td>
	</tr>
	<%} %>
	<tr>
		<td align="right">��������:</td>
		<td><input readonly="readonly" class="edline" value="<%=Utility.trimNull(map.get("CHANNEL_TYPE_NAME")) %>" size="40"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%>:</td><!--��������-->
		<td><input readonly="readonly" class="edline" value="<%=Utility.trimNull(map.get("CHANNEL_NAME")) %>" size="40"></td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("message.channelsRate",clientLocale)%>:</td><!--�������۷���-->
		<td><input type="text" id="fareRate" name="fareRate" value="<%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100")) %>" onkeydown="javascript:nextKeyPress(this)" size="27"/>%</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerSummary",clientLocale)%>:</td><!--��ע-->
		<td><textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="50"><%=Utility.trimNull(map.get("SUMMARY")) %></textarea></td>
	</tr>
</table>
<div style="height: 70px;"></div>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--����-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--ȡ��-->
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%local.remove(); %>
