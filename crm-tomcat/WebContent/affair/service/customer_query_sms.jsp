<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得参数
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer needFeedback = Utility.parseInt(request.getParameter("needFeedback"), new Integer(0));
Integer action_flag = Utility.parseInt(request.getParameter("action_flag"), new Integer(0));
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
//查询条件
String no = Utility.trimNull(request.getParameter("no"));
String name = Utility.trimNull(request.getParameter("name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String tel = Utility.trimNull(request.getParameter("tel"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer is_link = Utility.parseInt(request.getParameter("is_link"),new Integer(0));//是否关联方
Integer accountManager = Utility.parseInt(request.getParameter("accountManager"),new Integer(0));//客户经理
String cust_level = Utility.trimNull(request.getParameter("cust_level"));
BigDecimal min_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("min_total_money")));//最低购买金额
BigDecimal max_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("max_total_money")));//最高购买金额
BigDecimal ben_amount_min = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_min")));//最低受益金额
BigDecimal ben_amount_max = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_max")));//最高受益金额
Integer start_rg_times = Utility.parseInt(request.getParameter("start_rg_times"), new Integer(0));//开始购买次数
Integer end_rg_times = Utility.parseInt(request.getParameter("end_rg_times"), new Integer(0));//结束购买次数

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo_cust = new CustomerVO();

//设置参数
vo_cust.setCust_no(no);
vo_cust.setCust_name(name);
vo_cust.setInput_man(input_operatorCode);
vo_cust.setProduct_id(product_id);
vo_cust.setIs_link(is_link);
vo_cust.setCard_id(card_id);
vo_cust.setH_tel(tel);
vo_cust.setCust_level(cust_level);
vo_cust.setMin_times(start_rg_times);
vo_cust.setMax_times(end_rg_times);
vo_cust.setMin_total_money(min_total_money);
vo_cust.setMax_total_money(max_total_money);
vo_cust.setBen_amount_min(ben_amount_min);
vo_cust.setBen_amount_max(ben_amount_max);
vo_cust.setCust_id(cust_id);
vo_cust.setService_man(accountManager);
//页面变量
Map map = null;
IPageList pageList =
	customer.listProcAllExt(
		vo_cust,
		Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();

//url设置
String tempUrl = "&name="+name+"&no="+no+"&is_link="+is_link+"&card_id="+card_id 
				+"&tel="+tel+"&cust_level="+cust_level+"&accountManager="+accountManager
				+"&product_id="+product_id+"&start_rg_times="+start_rg_times
				+"&end_rg_times="+end_rg_times+"&min_total_money="+min_total_money
				+"&max_total_money="+max_total_money+"&ben_amount_min="+ben_amount_min
				+"&ben_amount_max="+ben_amount_max;
sUrl = sUrl + tempUrl;
 %>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.batchAddCustomers",clientLocale)%> </title><!-- 客户批量新增 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT language="javascript">
function refreshPage(){
	location = 'customer_query_sms.jsp?page=<%=sPage%>&pagesize='+document.theform.pagesize.value
				+"&name=<%=name%>&no=<%=no%>&is_link=<%=is_link%>&card_id=<%=card_id%>"
				+"&tel=<%=tel%>&cust_level=<%=cust_level%>&accountManager=<%=accountManager%>"
				+"&product_id=<%=product_id%>&start_rg_times=<%=start_rg_times%>"
				+"&end_rg_times=<%=end_rg_times%>&min_total_money=<%=min_total_money%>"
				+"&max_total_money<%=max_total_money%>&ben_amount_min=<%=ben_amount_min%>"
				+"&ben_amount_max=<%=ben_amount_max%>";		
}

function tt(){
	var c = document.theform.cust_id;
	alert(c.length);
}
</SCRIPT>
</HEAD>
<BODY class="BODY">
<form name="theform" method="POST">
<div id="div_ccb168_1" style="display: none;">
   <input type="checkbox" id="cust_id" name="cust_id"value="-1" class="flatcheckbox">
</div>
	<table border="0" width="575px" align="center" cellspacing="1" cellpadding="2"	class="tablelinecolor" id="cust_table">
		<%
		int iCount = 0;
		int iCurrent = 0;
		Integer cust_id2 = new Integer(0);
		String cust_no = "";
		String cust_name = "";
		String cust_type_name = "";
		String cust_manager = "";
		for(int i=0;i<list.size();i++) {
			map = (Map) list.get(i);
			cust_id2 = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
			cust_no = Utility.trimNull(map.get("CUST_NO"));
			cust_name = Utility.trimNull(map.get("CUST_NAME"));
			cust_type_name = Utility.trimNull(map.get("CUST_TYPE_NAME"));
			cust_manager = Argument.getOperatorName(Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0)));
				
		%>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td align="left" width="30%">
				<input type="checkbox" id="cust_id" name="cust_id"value="<%=cust_id2%>" class="flatcheckbox">
				&nbsp;<input type="hidden" name="cust_no" value="<%=cust_no%>"/><%=cust_no%>
			</td>
			<td align="left" width="40%"><input type="hidden" name="cust_name" value="<%=cust_name%>"/><%= cust_name%></td> 
			<td align="center" width="15%"><input type="hidden" name="cust_type_name" value="<%=cust_type_name%>"/><%= cust_type_name%></td> 
			<td align="center" width="15%"><input type="hidden" name="cust_manager" value="<%=cust_manager%>"/><%= cust_manager%></td> 
		</tr>
		<%iCount++;iCurrent++;}
		for (int i=0;i < pageList.getBlankSize(); i++){%>
			<tr class="tr<%=(i % 2)%>">
				<td width="30%">&nbsp;</td>
				<td width="40%">&nbsp;</td>
				<td width="15%">&nbsp;</td>
				<td width="15%">&nbsp;</td>
			</tr>
		<%}%>
	</table>
	<table border="0" width="575px" align="center">
		<tr class="tr0">
			<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
		</tr>
	</table>
	<hr size="1" width="575px">
</form>
<SCRIPT language="javascript">
document.theform.pagesize.onchange = function(){
	location = 'customer_query_sms.jsp?page=1&pagesize=' + document.theform.pagesize.value
		+"&name=<%=name%>&no=<%=no%>&is_link=<%=is_link%>&card_id=<%=card_id%>"
		+"&tel=<%=tel%>&cust_level=<%=cust_level%>&accountManager=<%=accountManager%>"
		+"&product_id=<%=product_id%>&start_rg_times=<%=start_rg_times%>"
		+"&end_rg_times=<%=end_rg_times%>&min_total_money=<%=min_total_money%>"
		+"&max_total_money<%=max_total_money%>&ben_amount_min=<%=ben_amount_min%>"
		+"&ben_amount_max=<%=ben_amount_max%>";	
}
</SCRIPT>


</BODY>
</HTML>
