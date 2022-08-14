<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer servMan = Utility.parseInt(Utility.trimNull(request.getParameter("servMan")), new Integer(-1));
String servManName = Argument.getManager_Name(servMan);
Integer productId = Utility.parseInt(Utility.trimNull(request.getParameter("productId")), new Integer(0));
String productName = "";
if (productId.intValue()>0) 
	productName = Argument.getProductName(productId);
ContractLocal contract = EJBFactory.getContract(); 
List list = contract.statManagerSales(productId, new Integer(0), new Integer(0), servMan, input_operatorCode);
%>

<HTML>
<HEAD>
<TITLE><%=servManName%><%=(productId.intValue()==0)?"按产品的销售统计":"销售“"+productName+"”产品的情况"%></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type="text/javascript">
function showStatistics(productId) {
	showModalDialog("<%=request.getContextPath()%>/client/clientinfo/cust_contract_stat.jsp?servMan=<%=servMan%>&productId="+productId, 
					'','dialogWidth:800px;dialogHeight:600px;status:0;help:0');
}
</script>
</HEAD>

<BODY leftMargin=0 topMargin=0 rightmargin="0">
<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>

<div style="margin-top:25px">
<table border="2" cellspacing="0" width="100%" style="border-collapse:collapse">
	<tr>
		<th align="center">序号</th><th align="center">产品</th>
		<th align="center">金额</th><th align="center">佣金</th>
	</tr>
<%
	for (int i=0; i<list.size(); i++) {
		Map map = (Map)list.get(i);

		Integer _productId = (Integer)map.get("PRODUCT_ID");
		String _productName = Utility.trimNull(map.get("PRODUCT_NAME"));
		BigDecimal rgMoney = (BigDecimal)map.get("RG_MONEY");		
		BigDecimal commissionMoney = (BigDecimal)map.get("COMMISSION_MONEY");
%>
	<tr>
		<td align="center"><%="小计".equals(_productName)?"小计":(""+(i+1))%>
		</td><td align="center"><%="小计".equals(_productName)?"":"<a href='javascript:showStatistics("+_productId+")'>"+_productName+"</a>"%></td>
		<td align="right"><%=Format.formatMoney2(rgMoney)%></td>
		<td align="right"><%=Format.formatMoney2(commissionMoney)%></td>
	</tr>
<%
	}
 %>
</table>
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
