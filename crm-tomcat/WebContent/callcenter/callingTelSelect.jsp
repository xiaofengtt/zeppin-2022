<%@ page contentType="text/html; charset=GBK" import="enfo.crm.callcenter.*,enfo.crm.web.*,enfo.crm.tools.*,enfo.crm.affair.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//查询条件
String name = Utility.trimNull(request.getParameter("name"));
String tel = Utility.trimNull(request.getParameter("tel"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
String cust_level = Utility.trimNull(request.getParameter("cust_level"));
BigDecimal min_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("min_total_money")));//最低购买金额
BigDecimal max_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("max_total_money")));//最高购买金额
BigDecimal ben_amount_min = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_min")));//最低受益金额
BigDecimal ben_amount_max = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_max")));//最高受益金额
Integer start_rg_times = Utility.parseInt(request.getParameter("start_rg_times"), new Integer(0));//开始购买次数
Integer end_rg_times = Utility.parseInt(request.getParameter("end_rg_times"), new Integer(0));//结束购买次数
Integer is_deal = Utility.parseInt(Utility.trimNull(request.getParameter("is_deal")),new Integer(0));
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("cust_type")),new Integer(0));
//获得对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo_cust = new CustomerVO();
//设置参数
vo_cust.setCust_name(name);
vo_cust.setInput_man(input_operatorCode);
vo_cust.setProduct_id(product_id);
vo_cust.setH_tel(tel);
vo_cust.setCust_level(cust_level);
vo_cust.setMin_times(start_rg_times);
vo_cust.setMax_times(end_rg_times);
vo_cust.setMin_total_money(min_total_money);
vo_cust.setMax_total_money(max_total_money);
vo_cust.setBen_amount_min(ben_amount_min);
vo_cust.setBen_amount_max(ben_amount_max);
vo_cust.setCust_type(cust_type);
vo_cust.setIs_deal(is_deal);
//页面变量
Map map = null;
IPageList pageList =
	customer.listProcAllExt(
		vo_cust,
		Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();
//url设置
String tempUrl = "&name=" + name  + "&product_id=" + product_id + "&tel=" + tel;
sUrl = sUrl + tempUrl;
%>
<html>
<head>
	<TITLE>电话号码选择</TITLE>
	<meta http-equiv="X-UA-Compatible" content="IE=7" >
	<META http-equiv=Content-Type content="text/html; charset=gbk">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<BASE TARGET="_self">
	<!--样式-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css"></link>
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
	<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
	<script type="text/javascript">
	window.onload = function(){
		initQueryCondition();
	};
	function StartQuery(){
		var url = 'callingTelSelect.jsp?page=<%=sPage%>&pagesize=' + document.getElementsByName("pagesize")[0].value 
				+'&name=' + document.getElementsByName("name")[0].value
				+ '&product_id=' + document.getElementsByName("product_id")[0].value
				+ "&is_deal=" + document.getElementsByName("is_deal")[0].value
				+ "&cust_type=" + document.getElementsByName("cust_type")[0].value
				+ "&tel=" + document.getElementsByName("tel")[0].value;
		location = url;	
	};
	/*分页*/
	function refreshPage(){
		StartQuery();
	}
	//查询条件设置
	function setProduct(value){
		prodid=0;
		if (event.keyCode == 13 && value != "")
		{
	        j = value.length;
			for(i=0;i<document.theform.product_id.options.length;i++)
			{
				if(document.theform.product_id.options[i].text.substring(0,j)==value)
				{
					document.theform.product_id.options[i].selected=true;
					prodid = document.theform.product_id.options[i].value;
					break;
				}	
			}
			if (prodid==0)
			{
				sl_alert('输入的产品编号不存在！');
				document.theform.productid.value="";
				document.theform.product_id.options[0].selected=true;	
			}			
		}
		nextKeyPress(this);
	}
	function searchProduct(value){
		prodid=0;
		if (value != "")
		{
	        j = value.length;
			for(i=0;i<document.theform.product_id.options.length;i++)
			{
				if(document.theform.product_id.options[i].text.substring(0,j)==value)
				{
					document.theform.product_id.options[i].selected=true;
					prodid = document.theform.product_id.options[i].value;
					break;
				}	
			}
			if (prodid==0)
			{
				sl_alert('输入的产品编号不存在！');
				document.theform.productid.value="";
				document.theform.product_id.options[0].selected=true;	
			}
			document.theform.product_id.focus();					
		}	
	}
	function callCust(target_custid){
		var url = "/affair/sms/cust_tel.jsp?input_flag=1&target_custid="+target_custid;	
		location = url;	
	}
	function callCust2(){
		var url = "/affair/sms/cust_tel.jsp?action_flag=1&input_flag=1";
		location = url;	
	}
	</script>
</head>
<body>
<form name="theform" method="get" action="#">
<input type="hidden" name="action_flag" id="action_flag" value="0" /><!--拨号选项 0:客户选择;1:手工拨号-->
<div>
	<div align="left">
		<img border="0" src="/images/member.gif" width="32" height="28"><font color="#215dc6"><b>查找客户</b></font>
	</div>
	<div align="right" style="margin-right:20px;">
		<button type="button" class="xpbutton3" id="callButton" name="callButton" onclick="javascript:callCust2();">直接拨打</button>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" class="xpbutton3" id="queryButton" name="queryButton" accessKey=f>查询</button>
	</div>
	<hr noshade color="#808080" size="1" width="95%">
</div>
<div id="queryCondition" class="qcMain" style="display: none; width: 430px" align="center">
	<table id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td>查询条件：</td>
			<td align="right">
			<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose"	onclick="javascript:cancelQuery();"></button>
			</td>
		</tr>
	</table>
	<table width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td valign="bottom" align="right">客户名称：</td>
			<td valign="bottom" align="left">
				<input name="name" value='<%=name%>' onkeydown="javascript:nextKeyPress(this)" size="18" maxlength="100">
			</td>
			<td valign="bottom" align="right">联系电话：</td>
			<td valign="bottom" align="left">
				<input name="tel" value='<%=tel%>' onkeydown="javascript:nextKeyPress(this)" size="18" maxlength="100">
			</td>
		</tr>
		<tr>
			<td valign="bottom" align="right">客户类型：</td>
			<td valign="bottom" align="left">
				<select name="is_deal" id="is_deal" onkeydown="javascript:nextKeyPress(this)" style="width:100px">	
					<%=Argument.getWTCustOptions(is_deal)%>
				</select>
			</td>
			<td valign="bottom" align="right">客户类别：</td>
			<td valign="bottom" align="left">
				<select name="cust_type" id="cust_type" onkeydown="javascript:nextKeyPress(this)" style="width:100px">	
					<%=Argument.getCustTypeOptions(cust_type)%>
				</select>
			</td>
		</tr>
		<tr>
			<td valign="bottom" align="right">产品编号：</td>
			<td valign="bottom" align="left">
				<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10" onkeydown="javascript:nextKeyPress(this)"> &nbsp;
				<button type="button" class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
			</td>
		</tr>
		<tr>
			<td align="right">产品选择：</td>
			<td align="left" colspan=3>
				<SELECT name="product_id" style="width:300px" class="productname" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td align="center" colspan=4>
				<button type="button" class="xpbutton3" name="btnQuery" accessKey=o onclick="javascript:StartQuery();">确定(<u>O</u>)</button>
			</td>
		</tr>
	</table>
</div>
<p>
<div align="center">
	<table border="0" width="95%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trtagsort">
			<td align="center" width="30%">编号</td>
			<td align="center" width="40%">名称</td>
			<td align="center" width="30%">客户类型</td>
		</tr>
		<%
		int iCount = 0;
		int iCurrent = 0;
		Integer cust_id = new Integer(0);
		String cust_no = "";
		String cust_name = "";
		String cust_type_name = "";

		String extension = Utility.trimNull(session.getAttribute("extension"));

		
		while (it.hasNext()) {
			map = (Map) it.next();
			cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
			cust_no = Utility.trimNull(map.get("CUST_NO"));
			cust_name = Utility.trimNull(map.get("CUST_NAME"));
			cust_type_name = Utility.trimNull(map.get("CUST_TYPE_NAME"));
				
		%>
		<tr class="tr<%=(iCurrent % 2)%>"<%if( !"".equals(extension)){ %>title="单击查看电话号码"  style="cursor:hand;" onclick="javascript:callCust(<%=cust_id%>);"<%} %>>
			<td class="tdh" align="center" width="30%"><%=cust_no%></td>  
			<td align="center" width="40%"><%= cust_name%></td> 
			<td align="center" width="30%"><%= cust_type_name%></td> 
		</tr>
		<%}%>
	</table>
	<div align="left" style="margin-top:5px;margin-left:20px;" width="50%"><%=pageList.getPageLink(sUrl)%></div>
</div>
</form>
</body>
</html>