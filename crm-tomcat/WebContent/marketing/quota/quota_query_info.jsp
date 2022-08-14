<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//页面传递变量
try{
Integer preProductId = Utility.parseInt(request.getParameter("preProductId"),new Integer(0));
Integer quotacheck_flag = Utility.parseInt(request.getParameter("quotacheck_flag"),new Integer(0));

BigDecimal pre_money = new BigDecimal(0);
String product_name = "";

//获得对象
ProductInfoReposLocal preProduct = EJBFactory.getProductInfoRepos();
ProductVO vo = new ProductVO();
//页面变量
List listProduct = null;

boolean bSuccess = false ;
String error_mess = "";

if(!request.getMethod().equals("POST")){
	if(preProductId.intValue()!= 0){
		//产品信息
		vo.setPreproduct_id(preProductId);
		vo.setInput_man(input_operatorCode);
		listProduct = preProduct.listBySql(vo);
		if(listProduct.size() >0){
			Map map = (Map)listProduct.get(0);
			if(map != null){
				product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
				pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00));
			}

		}
	}	
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.saleParameterSet",clientLocale)%></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
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
<script language="javascript"> 
<%if (bSuccess){%>	
	sl_alert("保存成功");
	location='quota_check_list.jsp';
<%}%>

//查看团队成员
function showTeamInfo(team_name){
	var _event = window.event.srcElement;
	var url = "<%=request.getContextPath()%>/marketing/base/team_member.jsp?team_id="+arguments[0];
	if(showModalDialog(url,oState, 'dialogWidth:360px;dialogHeight:350px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}
}
var oState = {
	
}
//返回
function CancelAction(){
	window.location.href="quota_query_list.jsp?";
}


</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name ="quotacheck_flag" value="<%=quotacheck_flag %>">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div>
	<table  border="0" width="100%" cellspacing="4" cellpadding="2" class="table-popup">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :&nbsp;&nbsp;</td><!--产品编号-->
			<td align="left" >
				<input type="text" name="preProductId" readonly="readonly" class="edline" value="<%=preProductId%>" onkeydown="javascript:setProduct(this.value);" size="30">
			</td>
		</tr>
		<tr>
			<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productName",clientLocale)%> :&nbsp;&nbsp;</td><!--产品名称-->
			<td  align="left">
				<input type="text" name="productName" readonly="readonly" class="edline" value="<%=product_name%>" onkeydown="javascript:setProduct(this.value);" size="60">
			</td>
		</tr>
		<tr>				
			<td align="right" width="120px"><%=LocalUtilis.language("class.preMoney",clientLocale)%> :&nbsp;&nbsp;</td><!--预期发行金额-->
			<td><input type="text" name="preMoney" readonly="readonly" class="edline" value="<%=Format.formatMoney(pre_money) %>" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" size="30"> </td>								
		</tr>
	</table>
</div><br>
<div >
	<table id="table3" border="0" cellspacing="1" cellpadding="3" class="tablelinecolor" width="100%" class="table-popup">
		<tr class=trtagsort>
				<td align="center"><%=LocalUtilis.language("class.teamName",clientLocale)%> </td><!--团队名称-->
				<td align="center"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> </td><!--销售配额-->
				<td align="center"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%></td><!--合格投资人数量配额-->
				<td align="center"><%=LocalUtilis.language("class.TeamMember",clientLocale)%> </td><!--团队成员-->
			</tr>
<%//查看团队销售信息
if(!"POST".equals(request.getMethod())){
	SaleParameterVO salevo = new SaleParameterVO();
	SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
	int iCount = 0;
	int iCurrent = 0;
	List list = null;
	String[] totalColumn = new String[0];
	Iterator iterator = null; 
	BigDecimal totalMoney = new BigDecimal(0);
	//salevo.setTeamID(team_id);
	salevo.setPre_product_id(preProductId);
	list = sale_parameter.queryQuota(salevo,input_operatorCode);	
	if(list.size() >0){
		iterator = list.iterator();
		while(iterator.hasNext()){
			iCount++;
			Map map = (Map)iterator.next();
			BigDecimal quota_money = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),2,"1");
			Integer team_id = Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));
			String team_name = Utility.trimNull(map.get("TEAM_NAME"));
			Integer quota_qualified_num = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
			totalMoney=totalMoney.add(quota_money);	
	 %>
			<tr class="tr0">
				<td align="center"><%=team_name %><input type="hidden" name="team_id" value="<%=team_id%>"></td>
				<td align="right"><%=Format.formatMoney(quota_money) %></td>
				<td align="center"><%=quota_qualified_num %></td>
	            <td align="center">
	            	<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showTeamInfo('<%=Utility.trimNull(map.get("TEAM_ID"))%>');">&gt;&gt;</button>
	            </td>  
			</tr>
	<%		}
		}
	 %>
			<tr class="trbottom">
				<!--合计--><!--项-->
		        <td class="tdh" align="center"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=iCount%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
				<td class="tdh" align="right"><%=Format.formatMoney(totalMoney) %></td>
				<td class="tdh" align="right" colspan="2">&nbsp;</td>
			</tr>
					
		</table>
	</div>
	<%sale_parameter.remove();
} %>
	<br>
</form>
<div align="right">
	<!--返回-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
<p>
<%
preProduct.remove();
}catch(Exception e){throw e ;}
%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
