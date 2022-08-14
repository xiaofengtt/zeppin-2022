<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer productId = overall_product_id;
Integer preproductId = new Integer(0);
String sProductId = Utility.trimNull(request.getParameter("productId"));
String[] temp = sProductId.split("-");
if (temp.length>0) {
	preproductId = Utility.parseInt(temp[0], new Integer(0));
}
if (temp.length>1) {
	productId = Utility.parseInt(temp[1], overall_product_id);
}

PreContractLocal preContract = EJBFactory.getPreContract();

String productName = "";//Argument.getProductName(productId);
BigDecimal factMoney = new BigDecimal(0.0);
Integer preStartDate = new Integer(0);
Integer preEndDate = new Integer(0);
Integer startDate = new Integer(0);

if (productId.intValue()>0) {
	ProductLocal product = EJBFactory.getProduct();
	ProductVO productVo = new ProductVO();
	productVo.setProduct_id(productId);
	List productList = product.load(productVo);
	if (productList.size()>0) {
		Map map = (Map)productList.get(0);
		productName = (String)map.get("PRODUCT_NAME");
		factMoney = (BigDecimal)map.get("FACT_MONEY");
		preStartDate = (Integer)map.get("PRE_START_DATE");
		preEndDate = (Integer)map.get("PRE_END_DATE");
		startDate = (Integer)map.get("START_DATE");
	}
	product.remove();
} else {
	List preproductList = preContract.loadPreproduct(preproductId);
	if (preproductList.size()>0) {
		Map map = (Map)preproductList.get(0);
		productName = (String)map.get("PREPRODUCT_NAME");
		factMoney = (BigDecimal)map.get("PRE_FACT_MONEY");
		preStartDate = (Integer)map.get("PRE_START_DATE");
		preEndDate = (Integer)map.get("PRE_END_DATE");
		startDate = (Integer)map.get("START_DATE");
	}
}

PreContractVO vo = new PreContractVO();
vo.setProduct_id(productId);
vo.setPre_product_id(preproductId);
List list =  preContract.statPreContract2(vo);

preContract.remove();
%>

<HTML>
<HEAD>
<TITLE>预约统计分析表</TITLE>
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
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>

<div style="margin-top:25px">
<table border="2" cellspacing="0" width="100%" style="border-collapse:collapse">
	<tr>
		<th align="center">项目名称</th><th align="center">项目规模</th>
		<th align="center">推介起始日期</th><th align="center">推介终止日期</th><th colspan="2" align="center">实际成立日期</th>
	</tr>
	<tr>
		<td align="center"><%=productName%></td><td align="right"><%=Format.formatMoney2(factMoney)%></td>
		<td align="center"><%=Format.formatDateLine(preStartDate)%></td>
		<td align="center"><%=Format.formatDateLine(preEndDate)%></td>
		<td colspan="2" align="center"><%=Format.formatDateLine(startDate)%></td>
	</tr>
	<tr>
		<td colspan="6">&nbsp;</td>
	</tr>
	<tr>
		<th align="center">预约时间</th><th align="center">客户</th><th align="center">金额</th><th align="center">电话</th>
		<th align="center">是否缴款</th><th align="center">缴款金额</th>
	</tr>
<%
	int nBespeaks = 0;
	int nPays = 0;
	BigDecimal preMoneyTotal = new BigDecimal(0.0);
	BigDecimal payMoneyTotal = new BigDecimal(0.0);

	for (int i=0; i<list.size(); i++) {
		Map map = (Map)list.get(i);
		Integer entryType = (Integer)map.get("ENTRY_TYPE");
		if (entryType.intValue()==2) // 缴款记录
			continue;

		nBespeaks ++;
		Integer date = (Integer)map.get("DATE");
		String custName = Utility.trimNull(map.get("CUST_NAME"));
		String tel = Utility.trimNull(map.get("CUST_TEL"));		
		BigDecimal preMoney = (BigDecimal)map.get("MONEY");		
		preMoneyTotal = preMoneyTotal.add(preMoney);

		String isPaid = (String)map.get("IS_PAID");
		BigDecimal payMoney = (BigDecimal)map.get("PAY_MONEY");
		if ("是".equals(isPaid)) {
			nPays ++;
			payMoneyTotal = payMoneyTotal.add(payMoney);
		}
%>
	<tr>
		<td align="center"><%=Format.formatDateLine(date)%></td><td align="center"><%=custName%></td>
		<td align="right"><%=Format.formatMoney2(preMoney)%></td><td align="center"><%=tel%></td>
		<td align="center"><%=isPaid%></td><td align="right"><%=Format.formatMoney2(payMoney)%></td>
	</tr>
<%
	}
 %>	
	<tr>
		<td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td>
	</tr>
	<tr>
		<td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td><td align="right">客户数合计：<%=nBespeaks%></td><td align="right">金额合计：<%=Format.formatMoney2(preMoneyTotal)%></td><td>&nbsp;</td>
		<td align="right">客户数合计：<%=nPays%></td><td align="right">缴款金额合计：<%=Format.formatMoney2(payMoneyTotal)%></td>
	</tr>
<%
	nPays = list.size() - nBespeaks; //
 %>
	<tr>
		<td colspan="4" style="border-bottom:0px;border-right:0px;">&nbsp;</td><td rowspan="2" colspan="2" style="border-left:0px;border-bottom:0px;">&nbsp;</td>
	</tr>
`	<tr>
		<td colspan="4" style="border-top:0px;border-right:0px;">&nbsp;</td>
	</tr>
	<tr>
		<th align="center">缴款时间</th><th align="center">客户</th><th align="center">金额</th><th align="center">电话</th><td rowspan="<%=4+nPays%>" colspan="2" style="border-top:0px;">&nbsp;</td>
	</tr>
<%	
	payMoneyTotal = new BigDecimal(0.0);

	for (int i=0; i<list.size(); i++) {
		Map map = (Map)list.get(i);
		Integer entryType = (Integer)map.get("ENTRY_TYPE");
		if (entryType.intValue()==1) // 预约记录
			continue;

		Integer date = (Integer)map.get("DATE");
		String custName = Utility.trimNull(map.get("CUST_NAME"));
		String tel = Utility.trimNull(map.get("CUST_TEL"));		
		BigDecimal payMoney = (BigDecimal)map.get("MONEY");
		payMoneyTotal = payMoneyTotal.add(payMoney);
%>
	<tr>
		<td align="center"><%=Format.formatDateLine(date)%></td><td align="center"><%=custName%></td>
		<td align="right"><%=Format.formatMoney2(payMoney)%></td><td align="center"><%=tel%></td>
	</tr>
<%
	}
 %>
	<tr>
		<td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td>
	</tr>
	<tr>
		<td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td><td align="center">&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;</td><td align="right">客户数合计：<%=nPays%></td><td align="right">金额合计：<%=Format.formatMoney2(payMoneyTotal)%></td><td>&nbsp;</td>
	</tr>
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