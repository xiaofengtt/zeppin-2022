<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
ContractLocal contract = EJBFactory.getContract(); 
Integer custId = Utility.parseInt(request.getParameter("custId"), new Integer(0));
String custName = "";
if (custId.intValue()>0)
	custName = Argument.getCustomerName(custId, input_operatorCode);
Integer productId = Utility.parseInt(request.getParameter("productId"), new Integer(0));
String productName = "";
if (productId.intValue()>0) 
	productName = Argument.getProductName(productId);
Integer servMan = Utility.parseInt(request.getParameter("servMan"), new Integer(0));
String servManName = "";
if (servMan.intValue()>0)
	servManName = Argument.getManager_Name(servMan);
List list = contract.statCustContract(custId, new Integer(2), productId, servMan); // flag: 1正常合同 2全部合同 3已结束合同
%>

<HTML>
<HEAD>
<TITLE><%=servMan.intValue()==0?custName+"的购买情况":servManName+"销售“"+productName+"”产品的情况"%></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
</HEAD>

<BODY leftMargin=0 topMargin=0 rightmargin="0">
<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>

<div style="margin-top:25px">
<%
if (custId.intValue()>0) {
 %>
<table border="2" cellspacing="0" width="100%" style="border-collapse:collapse">
	<tr>		
		<th align="center">序号</th><th align="center">产品</th><th align="center">金额</th>
		<th align="center">购买时间</th><th align="center">到期时间</th><th align="center">客户经理</th>
	</tr>
<%
	BigDecimal moneyTotal = new BigDecimal(0.0);
	for (int i=0; i<list.size(); i++) {
		Map map = (Map)list.get(i);

		Integer _productId = (Integer)map.get("PRODUCT_ID");
		String _productName = Utility.trimNull(map.get("PRODUCT_NAME"));
		//Integer startDate = (Integer)map.get("START_DATE");
		Integer qsDate = (Integer)map.get("QS_DATE");
		Integer endDate = (Integer)map.get("END_DATE");
		BigDecimal money = (BigDecimal)map.get("MONEY");	
		moneyTotal = moneyTotal.add(money);
		String _servManName = Utility.trimNull(map.get("OP_NAME")); 	
%>
	<tr>
		<td align="center"><%=i+1%></td>
		<td align="center"><%=_productName%></td>
		<td align="right"><%=Format.formatMoney2(money)%></td>
		<td align="center"><%=Format.formatDateLine(qsDate/*startDate*/)%></td>
		<td align="center"><%=Format.formatDateLine(endDate)%></td>
		<td align="center"><%=_servManName%></td>
	</tr>
<%
	}
 %>
	<tr>
		<td align="center">小计</td>
		<td align="center">&nbsp;</td>
		<td align="right"><%=Format.formatMoney2(moneyTotal)%></td>
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>
	</tr>
</table>
<%
} else { %>
<table border="2" cellspacing="0" width="100%" style="border-collapse:collapse">
	<tr>		
		<th align="center">序号</th><th align="center">客户</th><th align="center">金额</th>
		<th align="center">购买时间</th><th align="center">到期时间</th><%--th align="center">客户经理</th--%>
	</tr>
<%
	BigDecimal moneyTotal = new BigDecimal(0.0);
	for (int i=0; i<list.size(); i++) {
		Map map = (Map)list.get(i);

		Integer _custId = (Integer)map.get("CUST_ID");
		String _custName = Utility.trimNull(map.get("CUST_NAME"));
		//Integer startDate = (Integer)map.get("START_DATE");
		Integer qsDate = (Integer)map.get("QS_DATE");
		Integer endDate = (Integer)map.get("END_DATE");
		BigDecimal money = (BigDecimal)map.get("MONEY");	
		moneyTotal = moneyTotal.add(money);
		//String _servManName = Utility.trimNull(map.get("OP_NAME")); 	
%>
	<tr>
		<td align="center"><%=i+1%></td>
		<td align="center"><%=_custName%></td>
		<td align="right"><%=Format.formatMoney2(money)%></td>
		<td align="center"><%=Format.formatDateLine(qsDate/*startDate*/)%></td>
		<td align="center"><%=Format.formatDateLine(endDate)%></td>
		<%--td align="center"><%=_servManName%></td--%>
	</tr>
<%
	}
 %>
	<tr>
		<td align="center">小计</td>
		<td align="center">&nbsp;</td>
		<td align="right"><%=Format.formatMoney2(moneyTotal)%></td>
		<td align="center">&nbsp;</td>
		<td align="center">&nbsp;</td>
		<%--td align="center">&nbsp;</td--%>
	</tr>
</table>
<%
} %>
<br/>

<table width="100%" border="0px">
	<tr>
		<td>&nbsp;</td>
		<td width="50px"><button type="button"  align="right" class="xpbutton3" accessKey=c onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>C</u>)</button></td>
	</tr>
</table>
</div>
</BODY>
</HTML>
<% 
contract.remove();
%>
