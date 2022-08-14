<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,java.math.*,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
String product_code = Utility.trimNull(request.getParameter("product_code"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer start_date = Utility.parseInt(request.getParameter("start_date"),new Integer(Utility.getCurrentDate()));
Integer end_date = Utility.parseInt(request.getParameter("end_date"),new Integer(Utility.getCurrentDate()));

PreContractVO vo = new PreContractVO();
PreContractLocal local = EJBFactory.getPreContract();
vo.setProduct_name(product_name);
vo.setCust_name(cust_name);
vo.setProduct_code(product_code);
vo.setStart_date(start_date);
vo.setEnd_date(end_date);
String[] totalColumn = {"PRE_MONEY"};
IPageList pageList = local.importListProcPagePreContract(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = sUrl+ "&product_code=" + product_code+"&product_name="+product_name 
		+ "&cust_name=" + cust_name+"&start_date="+start_date+"&end_date="+end_date;
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.roleSet",clientLocale)%> </TITLE>
<!--角色设置-->
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

function refreshPage()
{	
	disableAllBtn(true);
	
	location = 'bespeak_query_import.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
			+'&product_code=' + document.theform.product_code.value
			+'&product_name='+document.theform.product_name.value
			+'&cust_name='+document.theform.cust_name.value
			+'&start_date='+document.theform.start_date.value
			+'&end_date='+document.theform.end_date.value;
}



window.onload = function(){
initQueryCondition()};

function StartQuery()
{
	refreshPage();
}
</script>
<BODY class="BODY" >
<form name="theform" method="POST" >
<%@ include file="/includes/waiting.inc"%>
		<div id="queryCondition" class="qcMain" style="display:none;width:500px;">
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
					<td align="right">产品编号: </td>
					<td>
						<input onkeydown="javascript:nextKeyPress(this)" type="text" name="product_code" size="20" value="<%=Utility.trimNull(product_code)%>">
					</td>
					<td align="right">客户名称: </td>
					<td>
						<input onkeydown="javascript:nextKeyPress(this)" type="text" name="cust_name" size="20" value="<%=Utility.trimNull(cust_name)%>">
					</td>
				</tr>
				<tr>
					<td align="right">产品名称:</td>
					<td colspan="3"><input type="text" name="product_name" size="69" value="<%=product_name%>" onkeydown="javascript:nextKeyPress(this)"></td>
				</tr>
				<tr>
					<td align="right">导入时间:</td>
					<td colspan="3">
						从&nbsp;&nbsp;&nbsp;<INPUT TYPE="text" NAME="start_date_picker" class=selecttext value="<%=Format.formatDateLine(start_date)%>" size="22">
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="13">
						<input type="hidden" name="start_date" value="<%=start_date%>" onkeydown="javascript:nextKeyPress(this)">&nbsp;&nbsp;&nbsp;
						到&nbsp;&nbsp;&nbsp;<INPUT TYPE="text" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>" size="22">
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" tabindex="13">
						<input type="hidden" name="end_date" value="<%=end_date%>" onkeydown="javascript:nextKeyPress(this)">
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
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
						<td>			
							<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
					<tr>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton" ><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;<!--查询-->
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" >
					<tr class=trtagsort>
						<td align="center">产品编号</td>
						<td align="center">产品名称</td>
						<td align="center">客户名称</td>
						<td align="center">认购金额</td>
						<td align="center">购买日期</td>
						<td align="center">手机号码</td>
						<td align="center">联系地址</td>
						<td align="center">认购标志</td>
					</tr>
<%
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;
for(int i=0;i<list.size();i++)
{
    map = (Map)list.get(i);
	product_code = Utility.trimNull(map.get("PRODUCT_CODE"));
	product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	String address = Utility.trimNull(map.get("ADDRESS"));
	String mobile = Utility.trimNull(map.get("MOBILE"));
	String turn_flag_name = Utility.trimNull(map.get("TURN_FLAG_NAME"));
	BigDecimal rg_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")),new BigDecimal(0.00));
	Integer pre_date = Utility.parseInt(Utility.trimNull(map.get("PRE_DATE")),new Integer(0));	
%>

					<tr class="tr<%=(iCurrent % 2)%>">

						<td align="left"><%=product_code%></td>
						<td align="left"><%=product_name%></td>
						<td align="left"><%=cust_name %></td>
						<td align="right"><%=Format.formatMoney(rg_money) %></td>
						<td align="center"><%=Format.formatDateLine(pre_date)%></td>
						<td align="center"><%=Utility.trimNull(mobile) %></td>
						<td align="left"><%=address %></td>
						<td> &nbsp;&nbsp;<%=Utility.trimNull(turn_flag_name) %></td>
					</tr>
<%
iCurrent++;
iCount++;
}

for (int i=0;i < pageList.getBlankSize(); i++)
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
						<td class="tdh" align="left" colspan="8"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>				
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>
</HTML>
<%local.remove();%>

