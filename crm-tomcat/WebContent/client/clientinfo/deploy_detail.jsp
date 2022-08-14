<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.intrust.*" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh"));

DeployLocal deploy = EJBFactory.getDeploy();
DeployVO vo = new DeployVO();

vo.setCust_id(cust_id);
vo.setProduct_id(product_id);
vo.setSub_product_id(sub_product_id);
vo.setContract_sub_bh(contract_sub_bh);
vo.setInput_man(input_operatorCode);
List list = deploy.queryForCust(vo);

deploy.remove();
%>
<HTML>
<HEAD>
<TITLE>收益明细</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>

</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0">
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
		<table border="0" width="100%" cellspacing="0" cellpadding="10">
<TBODY>
			<tr>
				<td>
				<table border="0" width="100%" cellspacing="1" cellpadding="4">
					<tr>
						<td><img border="0" src="<%=request.getContextPath()%>/images/Feichuang5.jpg" width="38" height="50">
						    <span style="font-size:13px; font-weight:bold"><%=Argument.getProductName(product_id)%>&nbsp;&nbsp;<%=contract_sub_bh%></span>
						</td>
					</tr>					
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" bgColor=#666666  borderColorLight=#ffffff width="100%">
					<tr class="trh">
						<td align="center" height="25">收益日期</td>
						<td align="center" height="25"><%=LocalUtilis.language("class.syTypeName",clientLocale)%> </td><!--收益类别-->
						<td align="center" height="25">分配日期</td>
						<td align="center" height="25">分配方式</td>						
						<td align="center" height="25">本金</td>
						<td align="center" height="25" sort="num">收益率</td>
						<td align="center" height="25" sort="num"><%=LocalUtilis.language("class.syMoney",clientLocale)%> </td><!--本次分配金额-->
					</tr>
<%
BigDecimal total = new BigDecimal(0.00);

for (int i=0; i<list.size(); i++) {
	Map map = (Map)list.get(i);
	total = total.add((BigDecimal)map.get("SY_MONEY"));
%>
					<tr class="tr<%=i%2%>">
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("SY_DATE")),new Integer(0)))%></td>
						<td class="tdh" align="center" height="25"><%=Utility.trimNull(map.get("SY_TYPE_NAME"))%></td>
						<td align="center" height="25"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("FP_DATE")),new Integer(0)))%></td>
						<td class="tdh" align="center" height="25"><%=Utility.trimNull(map.get("JK_TYPE_NAME"))%></td>						
						<td class="tdh" align="right" height="25"><%=Format.formatMoney((BigDecimal)map.get("SY_AMOUNT"))%></td>
						<td align="center" height="25"><%=Format.formatPercent(Utility.parseDecimal(Utility.trimNull(map.get("SY_RATE")),new BigDecimal(0)),1,false)%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("SY_MONEY")),new BigDecimal(0))))%></td>
					</tr>
<%}%>
					<tr class="trbottom">
						<td class="tdh" align="left" height="25" colspan="6">
						<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=list.size()%>
						&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
						<td align="right"><%=Format.formatMoney(total)%></td>
					</tr>				
				</table>
				</td>
		</tr>
</TBODY>
</table>
</TABLE>

</BODY>
</HTML>