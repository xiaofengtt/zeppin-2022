<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//页面传递变量
try{
Integer preProductId = Utility.parseInt(request.getParameter("preProductId"),new Integer(0));

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
//设置团队配额
function showInfo(preProductId,team_id){
	if(showModalDialog('quota_adjust.jsp?preProductId=' + preProductId + '&team_id=' + team_id, '', 'dialogWidth:360px;dialogHeight:200px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}
function refreshPage()
{
	var url ="sale_parameter.jsp?page=1&team_id="+document.theform.team_id.value;			
	location = url
}

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
	window.location.href="quota_adjust_list.jsp?";
}

</script>
</HEAD>
<BODY class="BODY" >
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
	<font color="#215dc6"><b><%=menu_info%></b></font>
	<hr noshade color="#808080" size="1">
</div>
<div>
	<table  border="0" width="98%" cellspacing="4" cellpadding="2" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
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
	<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="98%">
		<tr class=trtagsort>
			<td align="center"><%=LocalUtilis.language("class.teamName",clientLocale)%> </td><!--团队名称-->
			<td align="center"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> </td><!--销售配额-->
			<td align="center"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%></td><!--合格投资人数量配额-->
			<td align="center">已经预约金额</td>
			<td align="center">已经预约小额份数</td>
			<td align="center"><%=LocalUtilis.language("class.TeamMember",clientLocale)%> </td><!--团队成员-->
			<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
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
	BigDecimal pre_salemone = new BigDecimal(0);
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
			pre_salemone = pre_salemone.add(Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0),2,"1"));
	 %>
			<tr class="tr0">
				<td align="center"><%=team_name %><input type="hidden" name="team_id" value="<%=team_id%>"></td>
				<td align="right"><%=Format.formatMoney(quota_money) %></td>
				<td align="center"><%=quota_qualified_num %></td>
				<td align="right"><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0),2,"1")) %></td>
				<td align="center"><%=Utility.parseInt(Utility.trimNull(map.get("PRE_QUALIFIEDNUM")),new Integer(0)) %></td>
	            <td align="center">
	            	<button class="xpbutton2" name="btnEdit" onclick="javascript:showTeamInfo('<%=Utility.trimNull(map.get("TEAM_ID"))%>');">&gt;&gt;</button>
	            </td>  
				<td align="center" width="50px">
				<%if (input_operator.hasFunc(menu_id, 102)) {%>
	               	<a href="#" onclick="javascript:showInfo(<%=preProductId %>,<%=team_id %>);">
		           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
		           	</a>
					<%}%>
				</td>
			</tr>
	<%		}
		}
	 %>
			<tr class="trbottom">
				<!--合计--><!--项-->
		        <td class="tdh" align="center"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=iCount %>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
				<td class="tdh" align="right"><%=Format.formatMoney(totalMoney) %></td>
				<td class="tdh" align="right">&nbsp;</td>
				<td class="tdh" align="right"><%=Format.formatMoney(pre_salemone) %></td>
				<td class="tdh" align="right">&nbsp;</td>
				<td class="tdh" align="right">&nbsp;</td>
				<td class="tdh" align="right">&nbsp;</td>
			</tr>
					
		</table>
	</div>	
	<br>
	<%	List his_list = sale_parameter.queryTeamHis(salevo,input_operatorCode);
		Utility.debug("his_list.size(--"+his_list.size());
		if(his_list.size() >0){
			Iterator his_iterator = his_list.iterator();
			while(his_iterator.hasNext()){
				Map his_map = (Map)his_iterator.next();
				Integer list_id =  Utility.parseInt(Utility.trimNull(his_map.get("LIST_ID")),new Integer(0));
				salevo.setList_id(list_id);
	%>	
	<%if(list_id.intValue() ==0 )out.print("初始配额信息"); else out.print("第"+list_id+ "次调整配配额信息:");%><br>
		<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="98%">
			<tr class=trtagsort>
				<td align="center"><%=LocalUtilis.language("class.teamName",clientLocale)%> </td><!--团队名称-->
				<td align="center"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> </td><!--销售配额-->
				<td align="center"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%></td><!--合格投资人数量配额-->
			</tr>
	<%			
				List hisDetail_list = sale_parameter.queryTeamHisDetail(salevo,input_operatorCode);

				if(hisDetail_list.size() >0){
					Iterator hisDetail_iterator = hisDetail_list.iterator();
					while(hisDetail_iterator.hasNext()){
						Map hisDetail_map = (Map)hisDetail_iterator.next();
						BigDecimal quota_money = Utility.parseDecimal(Utility.trimNull(hisDetail_map.get("QUOTAMONEY")), new BigDecimal(0),2,"1");
						Integer team_id = Utility.parseInt(Utility.trimNull(hisDetail_map.get("TEAM_ID")),new Integer(0));
						String team_name = Utility.trimNull(hisDetail_map.get("TEAM_NAME"));
						Integer quota_qualified_num = Utility.parseInt(Utility.trimNull(hisDetail_map.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
	%>
			<tr class="tr0">
				<td align="center"><%=team_name %><input type="hidden" name="team_id" value="<%=team_id%>"></td>
				<td align="right"><%=Format.formatMoney(quota_money) %></td>
				<td align="center"><%=quota_qualified_num %></td>
			</tr>
	<%
					}
				}
	%>
		</table>
		<%	}	
		} %>
</div>
	<%sale_parameter.remove();
} %>
	<br>
</form>
<div align="right">
	<!--返回-->
    <button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
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
