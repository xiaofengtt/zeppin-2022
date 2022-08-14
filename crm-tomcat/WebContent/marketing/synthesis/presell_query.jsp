<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);
String product_code = request.getParameter("productid");
String cust_name = request.getParameter("cust_name");
String cust_no = request.getParameter("cust_no");
if (overall_product_id == null)
	overall_product_id = Argument.getFirstProduct(input_bookCode, null,"");
if (overall_product_id == null)
	overall_product_id = new Integer(0);
String pre_Code = request.getParameter("pre_Code");

if (pre_Code == null)
	pre_Code =  "";
if (product_code == null)
	product_code = "";
if (cust_name == null)
	cust_name = "";
if (cust_no == null)
	cust_no = "";

String sQuery = overall_product_id + " $ " + pre_Code + " $ " + cust_name + "$"+ cust_no + "$";
sUrl +=  "&productid=" + product_code
		+ "&product_id=" + overall_product_id
		+ "&pre_Code=" + pre_Code
		+ "&cust_name=" + cust_name
		+ "&cust_no=" + cust_no;

PreContractLocal preContract = EJBFactory.getPreContract();
PreContractVO vo = new PreContractVO();

vo.setBook_code(input_bookCode);
vo.setProduct_id(overall_product_id);
vo.setPre_code(pre_Code);
vo.setInput_man(input_operatorCode);
vo.setCust_name(cust_name);
vo.setCust_no(cust_no);
IPageList pageList = preContract.queryAll(vo, _page, pagesize);
List list = pageList.getRsList();

preContract.remove();
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function searchProduct(value) {
	prodid=0;
	if (value != "") {
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

window.onload = function(){
initQueryCondition()};
function refreshPage()
{
  	disableAllBtn(true);
  	location = 'presell_query.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&productid='+document.theform.productid.value+'&product_id='+document.theform.product_id.value+'&cust_name='+document.theform.cust_name.value+'&pre_Code='+document.theform.pre_Code.value+'&cust_no='+document.theform.cust_no.value;
}

function showInfo(serialno)
{
	location = 'presell_query_info.jsp?serial_no='+serialno+'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&product_id=<%=overall_product_id%>&pre_Code=<%=pre_Code%>';
}

function StartQuery() {
	refreshPage();
}

//查看客户信息
function getCustomer(cust_id){
	var url = '<%=request.getContextPath()%>/marketing/customerInfo2.jsp?cust_id='+ cust_id ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');
}
<%--
function showCusInfo(custid) {   
	window.open('customer_query_info.jsp?cust_id='+custid +'&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value,'_blank','Scrollbars=yes,left=100, top=98,width=888,height=438');
}--%>

function setProduct(value)
{
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
</script>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="get" action="presell_query.jsp">

<div id="queryCondition" class="qcMain" style="display:none;width:400px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>
	<tr>
		<td  align="right">预约编号:</td>
		<td align="left">
		<input type="text" name="pre_Code" size="15" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(pre_Code)%>">
        </td>
		<td align="right">客户名称:</td>
		<td align="left">
		<input type="text" name="cust_name" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(cust_name)%>">
		</td>
	</tr>
	<tr>
		<td align="right">客户编号:</td>
		<td align="left" ><input type="text" name="cust_no" size="20" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(cust_no)%>">&nbsp; &nbsp;</td>
		<td align="right">产品编号:</td >
		<td align="left">
			<input type="text" onkeydown="javascript:setProduct(this.value);nextKeyPress(this)" maxlength="16" name="productid" value=""  maxlength=8 size="10">
			&nbsp;<button type="button"  class="searchbutton"  onkeydown="javascript:nextKeyPress(this)" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td >
	</tr>
	<tr>			

		<td align="right">产品选择:</td >
		<td align="left" colspan=3>
		<SELECT name="product_id" class="productname" onkeydown="javascript:nextKeyPress(this)"><%=Argument.getProductListOptions(input_bookCode, overall_product_id, "",input_operatorCode,0)%></SELECT>
		</td></tr><tr>
		<td align="center" colspan=4><button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button></td>
	</tr>						
</table>

</div>



<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" border=0>
			<TR>
				<TD>
				<table cellspacing="1" cellpadding="2" border="0" width="100%">
					<tr>
						<td align="center">
						<table border="0" cellspacing="0" width="100%" style="border-collapse: collapse" bordercolor="#111111" cellpadding="0">
							<%//int  showTabInfo =  showTabInfoTop.intValue();
							//if(showTabInfo != 1){ %>
							<tr>
								<td colspan="6"><IMG src="/images/member.gif" align=absBottom border=0 width="32" height="28"><b><%=menu_info%></b></td>
							</tr>
							<%//} %>
							

<tr><td align=right> <button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button></td></tr>

							<tr>
								<td colspan="6">
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
							<tr class="trh">
								<td >产品名称</td>
								<td>预约号</td>
								<td>客户名称</td>
								<td sort="num">预约金额</td>
								<td>预约日期</td>
								<td sort="num">认购金额</td>
								<TD sort="num">已审金额</TD>
								<td>资金到账日期</td>
								<td>联系人</td>
								<td>查询</td>
							</tr>
<%
int iCurrent = 0;
BigDecimal pre_money_total = new BigDecimal(0.000);   //合计预约金额
BigDecimal rg_money_total = new BigDecimal(0.000);    //合计已审金额
BigDecimal uncheck_rg_money_total = new BigDecimal(0.000);    //合计认购金额
for (int i=0; i<list.size(); i++) {
	iCurrent++;
	Map	map = (Map)list.get(i);
	BigDecimal pre_money = (BigDecimal)map.get("PRE_MONEY");
	if(pre_money != null)
		pre_money_total = pre_money_total.add(pre_money);

	BigDecimal rg_money = (BigDecimal)map.get("RG_MONEY");
	if(rg_money!=null)
		rg_money_total = rg_money_total.add(rg_money);

	BigDecimal uncheck_rg_money = (BigDecimal)map.get("UNCHECK_RG_MONEY");
	if(uncheck_rg_money!=null)
		uncheck_rg_money_total = uncheck_rg_money_total.add(uncheck_rg_money);	

	Integer serial_no = (Integer)map.get("SERIAL_NO");
	Integer product_id = (Integer)map.get("PRODUCT_ID");
	String pre_code = (String)map.get("PRE_CODE");
	String _cust_name = (String)map.get("CUST_NAME");
	Integer pre_date = (Integer)map.get("PRE_DATE");
	Integer rg_date = (Integer)map.get("RG_DATE");
    Integer link_man = (Integer)map.get("LINK_MAN");
	Integer cust_id = (Integer)map.get("CUST_ID");
%>

							<tr class="tr<%=(iCurrent % 2)%>">
							<td align="left"   noWrap="true"><%=Utility.trimNull(Argument.getProductName(product_id))%></td>
								<td class="tdh" align="center" >
								<table border="0" width="100%" cellspacing="0" cellpadding="0">
									<tr>
										<td width="10%"></td>
										<td width="90%" align="left"><%=Utility.trimNull(pre_code)%></td>
									</tr>
								</table>
								</td>
							 
								<td align="left" ><a
									href="#" onclick="javascript:<%--showCusInfo--%>getCustomer(<%=cust_id%>);return false;"><%=Utility.trimNull(_cust_name)%></a></td>
							 
								
								<td align="right" ><%=Utility.trimNull(Format.formatMoney(pre_money))%></td>
								<td align="center" noWrap="true"><%=Format.formatDateCn(pre_date)%></td>
								<td align="right" ><%=Utility.trimNull(Format.formatMoney(uncheck_rg_money))%></td>
								<TD align="right" ><%=Utility.trimNull(Format.formatMoney(rg_money))%></TD>
								<td align="center" ><%=Format.formatDateCn(rg_date)%></td>
								<td align="center" ><%=Utility.trimNull(Argument.getIntrustOpName(link_man))%></td>
								<td align="center" >
								<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:disableAllBtn(true);showInfo(<%=serial_no%>);">&gt;&gt;</button>
								</td>

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
								<td align="center" ></td>
								<td align="center" ></td>
							</tr>
<%}%>
							<tr class="trbottom">
								<td class="tdh" width="9%" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
							 
								<td align="center" >-</td>
								<td align="center" >-</td>
								<td align="right" ><%=Format.formatMoney(pre_money_total)%></td>
								<td align="center" >-</td>
								<td align="right" ><%=Format.formatMoney(uncheck_rg_money_total)%></td>
								<TD align="right" ><%=Format.formatMoney(rg_money_total)%></TD>
								<td align="center" >-</td>
								<td align="center" >-</td>
								<td align="center" >-</td>
								
							</tr>
						</table>
						<br>
						<table border="0" width="100%">
							<tr valign="top">
								<td><%=pageList.getPageLink(sUrl, clientLocale)%></td>
								<td align="right">
								
								<!--<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" title="返回上一页" onclick="javascript:history.back();">返回(<u>B</u>)</button>
								&nbsp;&nbsp;&nbsp;-->
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
	</TBODY>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>