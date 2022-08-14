<%@ page contentType="text/html; charset=GBK"  import="java.math.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.customer.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer team_id = Utility.parseInt(request.getParameter("team_id"),new Integer(0));
String leader_name = Utility.trimNull(request.getParameter("leader_name"));
int team_flag = Utility.parseInt(request.getParameter("team_flag"),2);
int sale_flag = Utility.parseInt(request.getParameter("sale_flag"),2);
int product_flag = Utility.parseInt(request.getParameter("product_flag"),1);

String tempUrl = "";
String[] totalColumn = new String[0];
TcustmanagersVO vo = new TcustmanagersVO();
CustomerStatLocal stat_Bean = EJBFactory.getCustomerStat();

vo.setProduct_id(product_id);
vo.setTeam_id(team_id);
vo.setLeader_name(leader_name);
vo.setInput_man(input_operatorCode);
vo.setProduct_flag(new Integer(product_flag));
vo.setTeam_flag(new Integer(team_flag));
vo.setSale_flag(new Integer(sale_flag));
IPageList pageList_teamProduct = stat_Bean.queryTeamProduct(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

List list_team = null;
Map map_teamProduct = null;
list_team = pageList_teamProduct.getRsList();
Iterator iterator_teamProduct = list_team.iterator();	

//分页参数
int iCount = 0;
int iCurrent = 0;

sUrl = sUrl+ "&team_id=" + team_id 
		+ "&product_id="+product_id
		+ "&leader_name=" + leader_name;
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.nonProductSet",clientLocale)%> </TITLE>
<!--非信托产品设置-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>

function $$(_name){
	return document.getElementsByName(_name)[0];
}

function refreshPage()
{
	var product_flag = 2;
	var team_flag=2;
	var sale_flag=2;

	if(document.theform.product_flag.checked)
		product_flag = 1;
	if(document.theform.team_flag.checked)
		team_flag = 1;
	if(document.theform.sale_flag.checked)
		sale_flag = 1;	
	disableAllBtn(true);
	location = 'marketing_contract_stat.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
							+ '&team_id=' + document.theform.team_id.value
							+ '&product_id=' + document.theform.product_id.value
							+ '&leader_name=' + document.theform.leader_name.value
							+ '&product_flag='+product_flag
							+ '&team_flag='+team_flag
							+ '&sale_flag='+sale_flag;
}

function queryDetail(product_id,team_id,link_man)
{
	var _pagesize = document.getElementsByName("pagesize")[0];
	var product_flag = 2;
	var team_flag=2;
	var sale_flag=2;

	if(document.theform.product_flag.checked)
		product_flag = 1;
	if(document.theform.team_flag.checked)
		team_flag = 1;
	if(document.theform.sale_flag.checked)
		sale_flag = 1;	
	location = 'marketing_contract_stat.jsp?page=1&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")
							+ '&team_id=' + team_id
							+ '&product_id=' + product_id
							+ '&leader_name=' + link_man
							+ '&product_flag='+product_flag
							+ '&team_flag='+team_flag
							+ '&sale_flag='+sale_flag;
}

window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}

function checkBoxSelect()
{
	if(document.theform.product_flag_2.checked){//产品
		document.theform.product_flag.checked = true;
	}else{
		document.theform.product_flag.checked = false;	
	}	
	
	if(document.theform.team_flag_2.checked){	//团队
		document.theform.team_flag.checked = true; 
	}else{
		document.theform.team_flag.checked = false	
	}
	
	if(document.theform.sale_flag_2.checked){	//销售经理
		document.theform.sale_flag.checked = true;	
	}else{
		document.theform.sale_flag.checked = false;	
	}
	
	refreshPage();
}

</script>
</HEAD>
<BODY class="BODY body-nox" >
<form name="theform" method="POST" action="noproduct_list.jsp">
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
			<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  				<tr>
					<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   					<td align="right">
   						<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   					</td>
				</tr>
			</table> 
			<!-- 要加入的查询内容 -->
			<table border="0" width="100%" cellspacing="2" cellpadding="2">
				<tr>
			<td  align="right"><%=LocalUtilis.language("class.promotion",clientLocale)%><%=LocalUtilis.language("class.product",clientLocale)%> : </td><!-- 推介期产品 -->
			<td  align="left" colspan="3">
				<SELECT name="product_id"	class="productname" onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 320px">
				<%=Argument.getProductName1(product_id)%>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.teamName",clientLocale)%> : </td><!-- 团队名称 -->
			<td  align="left">
				<select size="1" name="team_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
				<%=Argument.getTeamName(team_id)%>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.linkMan",clientLocale)%>: </td><!-- 销售人员-->
			<td align="left" >
				<select name="leader_name" style="width: 125px;">
					<%=Argument.getOperatorOptionsByRoleId(new Integer(2),Utility.parseInt(leader_name,new Integer(0)))%>
				</select>
			</td> 					
		</tr>
		<tr>
			<td align="right">统计口径:</td>
			<td colspan="3">
				<input type="checkbox" class="flatcheckbox" name="product_flag" value="1" <%if(product_flag==1) out.print("checked");%>>产品
				<input type="checkbox" class="flatcheckbox" name="team_flag" value="1" <%if(team_flag==1) out.print("checked");%>>团队
				<input type="checkbox" class="flatcheckbox" name="sale_flag" value="1" <%if(sale_flag==1) out.print("checked");%>>销售经理
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td><!--确认-->
		</tr>			
	</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td class="page-title">			
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
						<div class="btn-wrapper">
							<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton" onclick="javascript:window.history.back();">返回 (<u>B</u>)</button>
							<!--返回-->
						</div>
						</td>
					</tr>
				</table>
				<table cellSpacing="1" cellPadding="2" style="margin-top:5px" class="table-select" >
					<tr style="background:F7F7F7;" style="margin-top:5px">
						<td>
							统计口径:
							<input type="checkbox" class="flatcheckbox" name="product_flag_2" value="1" <%if(product_flag==1) out.print("checked");%> onclick="javascript:checkBoxSelect();">产品
							<input type="checkbox" class="flatcheckbox" name="team_flag_2" value="1" <%if(team_flag==1) out.print("checked");%> onclick="javascript:checkBoxSelect();">团队
							<input type="checkbox" class="flatcheckbox" name="sale_flag_2" value="1" <%if(sale_flag==1) out.print("checked");%> onclick="javascript:checkBoxSelect();">销售经理
						</td>
					</tr>
				</table>
				<table id="table3" style="margin-top:5px" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					 <tr class="trh">
					 	<%if(product_flag==1){ %>
						<td align="center" height="25"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称 -->
						<%} if(team_flag==1){%>
						<td align="center" height="25"><%=LocalUtilis.language("class.teamName",clientLocale)%></td><!--团队-->
						<td align="center" height="25"><%=LocalUtilis.language("class.quotaMoney",clientLocale)%></td><!--团队配额-->
						<%} if(sale_flag==1){ %>
						<td align="center" height="25"><%=LocalUtilis.language("class.linkMan",clientLocale)%></td><!--销售人员-->
						<%} %>
						<td align="center" height="25"><%=LocalUtilis.language("class.rgMoney",clientLocale)%></td><!--已认购金额-->
						<td align="center" height="25"><%=LocalUtilis.language("class.toMoney3",clientLocale)%> </td><!--已缴款金额-->
					</tr> 
<%
Integer serial_no = new Integer(0);
String product_code = "";
String product_name = "";
BigDecimal pre_money = new BigDecimal(0);
BigDecimal to_money = new BigDecimal(0);
String team_no = "";
String team_name = "";
BigDecimal quota_money = new BigDecimal(0);
Integer leader = new Integer(0);
BigDecimal quota_money_sum  = new BigDecimal(0);
BigDecimal pre_money_sum  = new BigDecimal(0);
BigDecimal to_money_sum  = new BigDecimal(0);

if(list_team.size()>0){
	for(int i=0; i<list_team.size(); i++){
		map_teamProduct = (Map)iterator_teamProduct.next();
		team_name = Utility.trimNull(map_teamProduct.get("TEAM_NAME"));
		quota_money = Utility.parseDecimal(Utility.trimNull(map_teamProduct.get("QUOTAMONEY")),new BigDecimal(0),2,"1");
		pre_money = Utility.parseDecimal(Utility.trimNull(map_teamProduct.get("RG_MONEY")),new BigDecimal(0),2,"1");
		to_money = Utility.parseDecimal(Utility.trimNull(map_teamProduct.get("TO_MONEY")),new BigDecimal(0),2,"1");
		pre_money_sum = pre_money_sum.add(pre_money);
		to_money_sum = to_money_sum.add(to_money);
		quota_money_sum = quota_money_sum.add(quota_money);
		
		product_id = Utility.parseInt(Utility.trimNull(map_teamProduct.get("PRODUCT_ID")),new Integer(0));
		team_id = Utility.parseInt(Utility.trimNull(map_teamProduct.get("TEAM_ID")),new Integer(0));
		String link_man = Utility.trimNull(map_teamProduct.get("LINK_MAN"));
		
 %>
		<tr class="tr<%=(iCurrent%2)%>" ondblclick="javascript:queryDetail('<%=product_id %>','<%=team_id %>','<%=link_man%>');" style="cursor:hand;">
			<%if(product_flag==1){ %>
			<td align="left" height="25">&nbsp;&nbsp;<%=Utility.trimNull(map_teamProduct.get("PRODUCT_NAME"))%></td>
			<%} if(team_flag==1){%>
			<td align="center" height="25">&nbsp;&nbsp;<%=team_name%></td>
			<td align="right" height="25"><%=Format.formatMoney(quota_money)%>&nbsp;&nbsp;</td>
			<%} if(sale_flag==1){%>
			<td align="center" height="25">&nbsp;&nbsp;<%=Utility.trimNull(map_teamProduct.get("LINK_MAN_NAME"))%></td>
			<%} %>
			<td align="right" height="25"><%=Format.formatMoney(pre_money)%>&nbsp;&nbsp;</td>
			<td align="right" height="25"><%=Format.formatMoney(to_money)%>&nbsp;&nbsp;</td>
		</tr>
<%
	iCurrent++;
	iCount++;}
for (int i=0;i < pageList_teamProduct.getBlankSize(); i++)
{
%>
					<tr class="tr<%=(i % 2)%>" >
						<%if(product_flag==1){ %>
						<td></td>
						<%} if(team_flag==1){%>
						<td></td>
						<td></td>
						<%} if(sale_flag==1){%>
						<td></td>
						<%}%>
						<td></td>
						<td></td>
					</tr>
<%}
%>
					<tr class="trbottom">
						<%if(product_flag==1){ %>
						<td class="tdh" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList_teamProduct.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
						<%} if(team_flag==1){%>
						<td align="center" height="25"></td>
						<td align="right" height="25"><%=Format.formatMoney(quota_money_sum)%>&nbsp;&nbsp;</td>
						<%} if(sale_flag==1){%>
						<td align="right" height="25"></td>
						<%}%>
						<td align="right" height="25"><%=Format.formatMoney(pre_money_sum)%>&nbsp;&nbsp;</td>
						<td align="right" height="25"><%=Format.formatMoney(to_money_sum)%>&nbsp;&nbsp;</td>
					</tr>	
<%
}
%>				
				</table>
				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%=pageList_teamProduct.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>				
</form>
<%@ include file="/includes/foot.inc" %>	
</BODY>
</HTML>
<%
stat_Bean.remove();
%>

