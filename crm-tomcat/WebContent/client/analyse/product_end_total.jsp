<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,java.math.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
ChannelLocal local = EJBFactory.getChannel();
ChannelVO vo = new ChannelVO();


Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
Integer start_date = Utility.parseInt(request.getParameter("start_date"),null);
Integer end_date = Utility.parseInt(request.getParameter("end_date"),null);
String[] totalColumn = new String[0];

vo.setProduct_id(product_id);
vo.setStart_date(start_date);
vo.setEnd_date(end_date);
vo.setSellFlag(new Integer(1));
vo.setInput_man(input_operatorCode);
vo.setProduct_status("110204");



List list  = local.queryByAllAnalysis(vo,1);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>产品销售统计</title>   
    <meta http-equiv="X-UA-Compatible" content="IE=7" >
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
	<script type="text/javascript">

		
	</script>
  </head>
  
  <body class="BODY">
    	<form name="theform" action="direct_selling_customers_stat.jsp" method="get">
    		<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor" id="view_1">
				<tr class="tr2">
					<td class="tdh" width="" align="center">产品编号</td>
					<td class="tdh" width="" align="center">产品名称</td>
					<td class="tdh" width="" align="center">产品状态</td>					
					<td class="tdh" width="" align="center">募集规模</td>
					<td class="tdh" width="" align="center">成立时间</td>
					<td class="tdh" width="" align="center">结束时间</td>
					<td class="tdh" width="" align="center">认购份额</td>
					<td class="tdh" width="" align="center">申购份额</td>
					<td class="tdh" width="" align="center">赎回份额</td>
					<td class="tdh" width="" align="center">新增客户数</td>
					<td class="tdh" width="" align="center">客户总人数</td>
				</tr>
<%
int iCurrent = 0;
for(int i=0;i<list.size();i++){	
	
	Map map = (Map)list.get(i);
	String product_code    = Utility.trimNull(map.get("PRODUCT_CODE"));
	String product_name	   = Utility.trimNull(map.get("PRODUCT_NAME"));
	product_id 			   = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	start_date 	   		   = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0));
	end_date 	   		   = Utility.parseInt(Utility.trimNull(map.get("END_DATE")),new Integer(0));
	BigDecimal rg_amount   = Utility.parseDecimal(Utility.trimNull(map.get("RG_AMOUNT")),new BigDecimal(0));
	BigDecimal sg_amount   = Utility.parseDecimal(Utility.trimNull(map.get("SG_AMOUNT")),new BigDecimal(0));
	BigDecimal sh_amount   = Utility.parseDecimal(Utility.trimNull(map.get("SH_AMOUNT")),new BigDecimal(0));
	Integer new_cust_num   = Utility.parseInt(Utility.trimNull(map.get("NEW_CUST_NUM")),null);
	Integer total_cust_num = Utility.parseInt(Utility.trimNull(map.get("TOTAL_CUST_NUM")),null);
	String product_status_name =Utility.trimNull(map.get("PRODUCT_STATUS_NAME"));
 %>
				<tr class="tr<%=(iCurrent % 2)%>" ondblclick="javascript:queryDetail('<%=product_code+"_"+product_name %>',<%=product_id %>,<%=start_date %>);" style="cursor: hand;">
					<td class=""><a href="#" onclick="javascript:queryProductDetail(<%=product_id%>)"><%=Utility.trimNull(product_code) %></a></td>
					<td class=""><%=Utility.trimNull(product_name) %></td>
					<td class=""><%=Utility.trimNull(product_status_name) %></td>
					<td class="">test</td>
					<td class=""><%=Format.formatDateLine(start_date) %></td>
					<td class=""><%=Format.formatDateLine(end_date) %></td>
					<td align="right"><%=Format.formatMoney(rg_amount) %></td>
					<td align="right"><%=Format.formatMoney(sg_amount) %></td>
					<td align="right"><%=Format.formatMoney(sh_amount) %></td>
					<td align="right"><%=Utility.trimNull(new_cust_num) %></td>
					<td align="right"><%=Utility.trimNull(total_cust_num) %></td>
				</tr>
<%iCurrent++;} %>
    		</table>
    		<br><br><br>
    	</form>
  </body>
</html>
