<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
Integer preProductId = Utility.parseInt(Utility.trimNull(request.getParameter("preProductId")),new Integer(0));
String productCode = Utility.trimNull(request.getParameter("productCode"));
String product_name = Utility.trimNull(request.getParameter("q_productName"));
Integer quotacheck_flag = Utility.parseInt(request.getParameter("quotacheck_flag"),new Integer(1));


//页面辅助参数
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
String[] totalColumn = new String[0];
List list = null;
Map map = null;
//获得对象及结果集
ProductInfoReposLocal preProduct = EJBFactory.getProductInfoRepos();
ProductVO vo = new ProductVO();

vo.setPreproduct_name(product_name);
vo.setProduct_status("110202");
vo.setQuotacheck_flag(quotacheck_flag);
vo.setInput_man(input_operatorCode);

IPageList pageList = preProduct.preProductList(vo,t_sPage,t_sPagesize);
list = pageList.getRsList();
//url设置
sUrl = sUrl+"&preProductId="+preProductId
				+"&product_name="+product_name
				+"&quotacheck_flag="+quotacheck_flag;
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.saleParameterSet",clientLocale)%> </TITLE><!--销售参数设置-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
	window.onload = function(){
		initQueryCondition();
	};
	function StartQuery()
	{
		refreshPage();
	}
		/*刷新*/
	function refreshPage()
	{
		var url ="quota_check_list.jsp?page=1&pagesize=" + document.theform.pagesize.value
						+"&q_productName="+document.theform.q_productName.value
						+"&quotacheck_flag="+document.theform.quotacheck_flag.value;
						
		location = url
	}
	/*查询功能*/
	function StartQuery(){
		refreshPage();
	}

	/*审核*/
	function showInfo(preProductId,check_flag){
		window.location.href="quota_check_info.jsp?preProductId="+preProductId+"&quotacheck_flag="+check_flag;
	}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
			<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       		onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>
	<table width="100%" cellspacing="0" cellpadding="2" border="0">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName4",clientLocale)%> :</td><!--产品名称-->
			<td colspan="3">
				<input type="text" name="q_productName" size="66" value="<%=product_name%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.checkFlag2",clientLocale)%> :</td><!--产品审核状态-->
			<td>				
				<select name="quotacheck_flag" style="width:122">
					<option value="1" <%if(quotacheck_flag.intValue()== 1)out.print("selected"); %>>设置未审核</option>
					<option value="3" <%if(quotacheck_flag.intValue()== 3)out.print("selected"); %>>调整未审核</option>	
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan=6><button type="button"  class="xpbutton3" name="btnQuery" accessKey=o onClick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--确认-->(<u>O</u>)</button></td>
		</tr>
  </table>	
</div>

<div style="margin-top:5px">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=4 class="page-title">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td><!--基本管理>>产品管理-->						
					</tr>
					<tr>
						<td align=right ><div class="btn-wrapper"><button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>F</u>)</button></div></td>				
					</tr>
					<tr><td>&nbsp;&nbsp;&nbsp; </td></tr>
				</table>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
	<tr class=trtagsort>
		<td align="center"><%=LocalUtilis.language("class.productID",clientLocale)%> </td><!--产品名称-->
		<td align="center"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
		<td align="center"><%=LocalUtilis.language("class.productType",clientLocale)%> </td>
		<td align="center"><%=LocalUtilis.language("class.periodUnit",clientLocale)%> </td>	
		<td align="center"><%=LocalUtilis.language("class.preMoney",clientLocale)%> </td><!--预期发行金额-->				
		<td align="center"><%=LocalUtilis.language("message.check",clientLocale)%> </td><!--审核-->
	</tr>
<%
BigDecimal pre_money = new BigDecimal(0);
BigDecimal min_money = new BigDecimal(0);
String period;
Integer pre_num;
String product_code = "";
Integer periodUnit ;

Iterator iterator = list.iterator();
while(iterator.hasNext()){
	map = (Map)iterator.next();
	preProductId = Utility.parseInt(Utility.trimNull(map.get("PREPRODUCT_ID")),new Integer(0));
	pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00));
	period = Utility.trimNull(map.get("PERIOD"));
    product_name = Utility.trimNull(map.get("PREPRODUCT_NAME"));
	String class_type1_name = Utility.trimNull(map.get("CLASS_TYPE1_NAME"));
	Integer check_flag = Utility.parseInt(Utility.trimNull(map.get("QUOTACHECK_FLAG")),new Integer(0));
 %>
	<tr class="tr<%=(iCurrent % 2)%>">
		<td align="center"><%=preProductId %></td>
		<td align="center"><%=product_name %></td>
		<td align="center"><%=class_type1_name %></td>	
		<td align="center"><%=period %></td>
		<td align="center"><%=Format.formatMoney(pre_money) %></td>
		<td align="center" width="50px">
			<%if (input_operator.hasFunc(menu_id, 102)) {%>
               	<a href="#" onclick="javascript:showInfo(<%=preProductId %>,<%=check_flag %>);">
	           		<img border="0" src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16">
	           	</a>
			<%}%>
		</td>
	</tr>
<%iCurrent++;
iCount++;
}
%>
<%for(; iCurrent < pageList.getPageSize(); iCurrent++){%>  
      <tr class="tr<%=(iCurrent % 2)%>">
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
      </tr>           
<%}%>   	
	<tr class="trbottom">
		<!--合计--><!--项-->
        <td class="tr<%=(iCurrent % 2)%>" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
	</tr>		
</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<%@ include file="/includes/foot.inc"%>

</HTML>
