<%@ page contentType="text/html; charset=GBK"  import="java.math.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.customer.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
Integer link_man = Utility.parseInt(request.getParameter("link_man"),new Integer(0));

String tempUrl = "";
TcustmanagersVO vo = new TcustmanagersVO();  
OperatorLocal local = EJBFactory.getOperator();

vo.setProduct_id(product_id);
vo.setLink_man(link_man);
vo.setInput_man(input_operatorCode);
IPageList marketing_pageList = local.queryProductMarketing(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

List list = null;
Map map = null;
list = marketing_pageList.getRsList();
Iterator iterator = list.iterator();	

//分页参数
int iCount = 0;
int iCurrent = 0;

sUrl = sUrl+ "&product_id=" + product_id 
		+ "&link_man="+link_man;
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("class.productMarketing",clientLocale)%> </TITLE>
<!--在售产品统计-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function $$(_name){
	return document.getElementsByName(_name)[0];
}
function refreshPage()
{
	disableAllBtn(true);

	location = 'product_marketing_total.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
							+ '&product_id=' + document.theform.product_id.value
							+ '&link_man=' + document.theform.link_man.value;
}

window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}
/*返回*/
function CancelAction(){
	var url = "<%=request.getContextPath()%>/login/main.jsp?display_flag=1";
	
	window.location.href = url;	
}
</script>
<BODY class="BODY" >
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
					<td align="right"><%=LocalUtilis.language("class.linkMan",clientLocale)%>: </td><!-- 销售人员-->
					<td align="left" >
						<select size="1" name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
							<%=Argument.getOperatorOptionsByRoleId(new Integer(2),link_man)%>
						</select>
					</td> 					
				</tr>
				<tr>
					<td  align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> : </td><!-- 产品名称 -->
					<td  align="left" colspan="3">
						<SELECT name="product_id"	class="productname" onkeydown="javascript:nextKeyPress(this)"  style="WIDTH: 300px">
							<%=Argument.getProductListOptions(new Integer(1),product_id,"",input_operatorCode,0)%>
						</SELECT>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="4">
						<button type="button"  class="xpbutton3" accessKey=O name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
					</td><!--确认-->
				</tr>			
			</table>
		</div>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td>			
							<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b><%=LocalUtilis.language("class.productMarketing",clientLocale)%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"<%if(!input_operator.hasFunc(menu_id, 108)){%> style="display:none"<%}%>><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
					<tr class="trh" bgcolor=#fcfdfd>
						<td align="center" width="*"><%=LocalUtilis.language("class.productID",clientLocale)%> </td><!--产品编号 -->
						<td align="center" width="*"><%=LocalUtilis.language("class.productName",clientLocale)%></td><!--产品名称-->
						<td align="center" width="*"><%=LocalUtilis.language("class.preMoney",clientLocale)%></td><!--预计发行金额-->
						<td align="center" width="*"><%=LocalUtilis.language("class.rgMoney",clientLocale)%></td><!--已售金额-->
						<td align="center" width="*"><%=LocalUtilis.language("class.jkTotalMoney",clientLocale)%> </td><!--已缴款 -->
						<td align="center" width="*"><%=LocalUtilis.language("class.linkMan2",clientLocale)%></td><!--销售人员-->
						<td align="center" width="*"><%=LocalUtilis.language("class.linkMan2",clientLocale)%><%=LocalUtilis.language("class.rgMoney",clientLocale)%></td><!--我的已售金额-->
						<td align="center" width="*"><%=LocalUtilis.language("class.linkMan2",clientLocale)%><%=LocalUtilis.language("class.toMoney3",clientLocale)%></td><!--我的已缴款金额-->
					
					</tr> 
<%
Integer serial_no = new Integer(0);
String product_code = "";
String product_name = "";
String product_jc = "";
BigDecimal pre_money = new BigDecimal(0);
BigDecimal rg_money = new BigDecimal(0);
BigDecimal to_money = new BigDecimal(0);
String link_man_name = "";
BigDecimal my_rg_money = new BigDecimal(0);
BigDecimal my_to_money  = new BigDecimal(0);

if(list.size()>0){
	for(int i=0; i<list.size(); i++){
		map = (Map)iterator.next();
		product_code = Utility.trimNull(map.get("PRODUCT_CODE"));
		product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
		product_jc = Utility.trimNull(map.get("PRODUCT_JC"));
		rg_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),new BigDecimal(0),2,"1");
		pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")),new BigDecimal(0),2,"1");
		to_money = Utility.parseDecimal(Utility.trimNull(map.get("TO_MONEY")),new BigDecimal(0),2,"1");
		link_man = Utility.parseInt(Utility.trimNull(map.get("LINK_MAN")),new Integer(0));
		link_man_name = Utility.trimNull(map.get("LINK_MAN_NAME"));
		my_rg_money = Utility.parseDecimal(Utility.trimNull(map.get("MY_RG_MONEY")),new BigDecimal(0),2,"1");
		my_to_money = Utility.parseDecimal(Utility.trimNull(map.get("MY_TO_MONEY")),new BigDecimal(0),2,"1");
 %>
		<tr bgColor=#ffffff>
			<td align="center" width="*">&nbsp;&nbsp;<%=product_code%></td>
			<td align="left" width="*">&nbsp;&nbsp;<%=product_name%></td>
			<td align="right" width="*"><%=Format.formatMoney(pre_money)%>&nbsp;&nbsp;</td>
			<td align="right" width="*"><%=Format.formatMoney(rg_money)%>&nbsp;&nbsp;</td>
			<td align="right" width="*"><%=Format.formatMoney(to_money)%>&nbsp;&nbsp;</td>
			<td align="left" width="*">&nbsp;&nbsp;<%=link_man_name%></td>
		 	<td align="right" width="*"><%=Format.formatMoney(my_rg_money)%>&nbsp;&nbsp;</td>
			<td align="right" width="*"><%=Format.formatMoney(my_to_money)%>&nbsp;&nbsp;</td>
		</tr>	
		<%iCurrent++;
		iCount++;}
for (int i=0;i < marketing_pageList.getBlankSize(); i++)
{
%>
					<tr class="tr<%=(i % 2)%>">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="8"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=marketing_pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					</tr>	
<%
}
%>				
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=marketing_pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</td>
		</tr>		
		<tr>
        	<td align=right>             		
        	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
			&nbsp;&nbsp;&nbsp;<!--返回-->
        	</td>
        </tr>
	</table>				
</form>
<%@ include file="/includes/foot.inc" %>	
</BODY>
</HTML>
<%
local.remove();
%>

