<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%

int _page = Utility.parseInt(sPage,1);
int pagesize = Utility.parseInt(sPagesize, 8);

String cust_name = Utility.trimNull(request.getParameter("cust_name"),"");
Integer product_id = Utility.parseInt(request.getParameter("product_id"),overall_product_id);

if (overall_product_id == null)
	overall_product_id = Argument.getFirstProduct(input_bookCode, null,"");
if (overall_product_id == null)
	overall_product_id = new Integer(0);





PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO vo = new PreContractVO();


vo.setProduct_id(product_id);

vo.setInput_man(input_operatorCode);
vo.setCust_name(cust_name);

IPageList pageList = preContract.queryListAll(vo,_page,pagesize);
List list = pageList.getRsList();


String sQuery = product_id + " $ " + cust_name + " $ " ;
sUrl += "&product_id="+product_id
					+"&cust_name="+cust_name+"&input_man="+input_operatorCode;

preContract.remove();

%>

<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js"></script>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<script language=javascript>
window.onload = function(){
		initQueryCondition()
};


function StartQuery() {
	refreshPage();
}

function refreshPage()
{
  	disableAllBtn(true);
  	location = 'presell_query_list.jsp?page=<%=_page%>&pagesize=' + document.theform.pagesize.value +'&product_id='+document.theform.product_id.value+'&cust_name='+document.theform.cust_name.value;
	
}

</script>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post" action="">

<div id="queryCondition" class="qcMain" style="display:none;width:400px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>
	<tr>
		<td align="right">客户名称:</td>
		<td align="left">
			<input type="text" name="cust_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(cust_name)%>">
		</td>
		
	</tr>
	
	<tr>			

		<td align="right">产品选择:</td >
		<td align="left" colspan=3>
			<SELECT name="product_id" class="productname" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getProductListOptions(input_bookCode, product_id, "",input_operatorCode,48)%>
			</SELECT>
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4><button class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button></td>
	</tr>						
</table>

</div>



<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0>
			<TR>
				<TD>
				<table cellspacing="1" cellpadding="2" border="0" width="100%">
					<tr>
						<td align="center">
						<table border="0" cellspacing="0" width="100%" style="border-collapse: collapse" bordercolor="#111111" cellpadding="0">
							
							<tr>
								<td colspan="6"><IMG src="/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
							</tr>
							
							

<tr><td align=right> <button class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button></td></tr>

							<tr>
								<td colspan="6">
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
							<tr class="trh">
								<td align="center" width="30%">产品名称</td>
								<td align="center">客户名称</td>
								<td align="center">团队</td>
								<td align="center">理财经理</td>
								<td sort="num" align="center" width="90px">有效预约金额</td>
								<td align="center" width="90px">排队预约金额</td>
								<td align="center">预约时间</td>
								<td align="center">预约有效期</td>
							</tr>
<%
int iCurrent = 0;
BigDecimal pre_money_total = new BigDecimal(0.000);   //合计预约金额

for (int i=0; i<list.size(); i++) {
	iCurrent++;
	Map	map = (Map)list.get(i);
	BigDecimal pre_money = (BigDecimal)map.get("PRE_MONEY");
	if(pre_money != null)
		pre_money_total = pre_money_total.add(pre_money);

    Integer link_man = (Integer)map.get("LINK_MAN");

	
%>

							<tr class="tr<%=(iCurrent % 2)%>">
							
								<td align="center">
									<%=Utility.trimNull(map.get("PRODUCT_NAME"))%>
								</td>
								<td align="center" >
									<%=map.get("CUST_NAME")%>
								</td>
								<td align="center"><%=Utility.trimNull(map.get("TEAM_NAME"))%></td>
								<td align="center" ><%=Utility.trimNull(Argument.getIntrustOpName(link_man))%></td>
								<TD align="center" ><%=Utility.trimNull(Format.formatMoney(pre_money))%></TD>
								<td align="center"><%=Utility.trimNull(map.get("PRE_MONEY_WAIT"))%></td>
								<td align="center"><%=map.get("INPUT_TIME")%></td>
								<td align="center" ><%=map.get("PRE_STATUS_NAME")%></td>
							</tr>
<%
}
for (; iCurrent < pageList.getPageSize(); iCurrent++) { %>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td class="tdh" align="center" ></td>
							 
								<td align="center" ></td>
								<td align="center" ></td>
								<td align="center" ></td>
								<td align="center" ></td>
								<TD align="center" ></TD>
								<td align="center" ></td>
								<td align="center" ></td>
							</tr>
<%}%>
							<tr class="trbottom">
								<td class="tdh" width="9%" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
							 
								<td align="center" >-</td>
								<td align="center" >-</td>
								<td align="center" >-</td>
								<td align="center" ><%=Format.formatMoney(pre_money_total)%></td>
								<td align="center" >-</td>
								<TD align="center" >-</TD>
								<TD align="center" >-</TD>
																
							</tr>
						</table>
						<br>
						<table border="0" width="100%">
							<tr valign="top">
								<td>
									<%=pageList.getPageLink(sUrl, clientLocale)%>
								</td>
								<td align="right">
								
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</TD>
			</TR>
		</TABLE>
	</TR>
	
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
