<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];

BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo = new BenifitorVO();
List list = null;
Map map = null;

String userid=((Integer)application.getAttribute("USER_ID")).toString();
String contract_sub_bh = request.getParameter("contract_sub_bh");
String cust_name = request.getParameter("cust_name");
String card_id = request.getParameter("card_id");
Integer product_id = Utility.parseInt(Utility.trimNull(request.getParameter("product_id")),new Integer(0));

vo.setBook_code(new Integer(1));
vo.setContract_sub_bh(contract_sub_bh);
vo.setProduct_id(product_id);
vo.setInput_man(input_operatorCode);
vo.setCust_name(cust_name);
vo.setCust_no(card_id);
IPageList pageList = benifitor.query(vo, totalColumn,t_sPage,t_sPagesize);
list = pageList.getRsList();
sUrl = "benifiter_print.jsp?pagesize=" + sPagesize + "&product_id=" + product_id 
		+"&contract_sub_bh="+ contract_sub_bh+"&card_id="+card_id+"&cust_name="+cust_name;
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

window.onload = function(){
initQueryCondition()};

function refreshPage()
{
	disableAllBtn(true);		
	var url = 'benifiter_print.jsp?contract_sub_bh='+document.theform.contract_sub_bh.value;
	var url = url + '&page=1&pagesize='+document.theform.pagesize.value;
	var url = url + '&product_id='+document.theform.product_id.value;
	var url = url + '&cust_name='+document.theform.cust_name.value
	var url = url + '&card_id='+document.theform.card_id.value;

	location = url;	
}

function StartQuery()
{
	refreshPage()

}
function confirmRemove()
{

	if(checkedCount(document.theform.selectbox) == 0)
	{
		sl_alert("请选定要打印的记录！");
		return false;
	}
	disableAllBtn(true);
    document.theform.submit();	
}
function searchProduct(value)
{
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

function confirmPrint()
{
var userid="<%=userid%>";
	if(checkedCount(document.theform.selectbox)==0)
		sl_alert('请选择打印记录!');
	
	if(checkedCount(document.theform.selectbox)>0){
	
	disableAllBtn(true);	
	if(userid=="5"){
	document.theform.action="newbenifitor_info_print_all.jsp";
	}
	else
	{
	document.theform.action="benifitor_info_print_all.jsp";
	}
	document.theform.submit();
	}
}

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
<form name="theform"　method="POST"  >

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
		<td align="right">客户名称: </td>
		<td align="left">
		<INPUT type="text" onkeydown="javascript:nextKeyPress(this)" name="cust_name" size="15" value="<%=Utility.trimNull(cust_name)%>">&nbsp;&nbsp;
		</td>
		<td align="right">
		合同编号: 
		</td>
		<td align="left">
			<INPUT type="text" onkeydown="javascript:nextKeyPress(this)" name="contract_sub_bh" size="15" value="<%=Utility.trimNull(contract_sub_bh)%>">&nbsp;&nbsp;</td>
		</tr>
		<tr>
		<td align="right">
		证件号码: 
		</td>
		<td align="left">
			<INPUT type="text" onkeydown="javascript:nextKeyPress(this)" name="card_id" size="15" value="<%=Utility.trimNull(card_id)%>">&nbsp;&nbsp;
		</td>
		<td align="right">产品编号:</td >
		<td align="left">
		<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">
		
		&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		
		</td >
	</tr>
	<tr>			
		<td align="right">产品选择:</td >
		<td align="left" colspan=3>
		<SELECT name="product_id" class="productname"><%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%></SELECT>
		</td>
	</tr>
	<tr>
		<td align=center colspan=4><button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button></td>
	</tr>
</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=7><IMG src="/images/member.gif" border=0 width="32" height="28"><b><%=menu_info%></b></td>
					</tr>
			       <tr><td align=right> <button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
						<%if (input_operator.hasFunc(menu_id, 107)){%>
						<button type="button"  class="xpbutton3"  accessKey=p name="btnDel" title="打印" onclick="javascript:confirmPrint();">打印(<u>P</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<%}%></td>
					</tr>
					<tr>
						<td colspan="7">
						<hr noshade color="#808080"  size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" >
						<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.selectbox,this);">&nbsp;&nbsp;编号</td>
						<td align="center" >受益人</td>
						<TD align="center" >产品名称</TD>
						<td align="center"  >合同号</td>
						<td align="center" >受益金额</td>
						<td align="center" >起始日期</td>
						<td align="center" >受益级别</td>
					</tr>
<%

int iCount = 0;
int iCurrent = 0;
Integer serial_no = new Integer(0);
Integer cust_id = new Integer(0);
BigDecimal ben_amount = new BigDecimal("0.000");
BigDecimal fact_money = new BigDecimal("0.000");
String contract_bh = "";
String product_name = "";
Integer ben_date = new Integer(0);
String prov_level_name = "";
Integer period_unit = new Integer(0);

Iterator iterator = list.iterator();

while(iterator.hasNext()){
	map = (Map)iterator.next();
	
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	ben_amount = Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0),2,"1"); 
	cust_id =  Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	contract_bh = Utility.trimNull(map.get("CONTRACT_BH"));
	product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	cust_name = Utility.trimNull(map.get("CUST_NAME"));
	contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	ben_date = Utility.parseInt(Utility.trimNull(map.get("BEN_DATE")),new Integer(0));
	prov_level_name = Utility.trimNull(map.get("PROV_LEVEL_NAME"));
	period_unit = Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0));
	fact_money = Utility.parseDecimal(Utility.trimNull(map.get("FACT_MONEY")),new BigDecimal(0),2,"1"); 
	product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	
    if (ben_amount != null)
		ben_amount = ben_amount.add(ben_amount);
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="left"  >
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%"><input class="flatcheckbox" type="checkbox" name="selectbox" value="<%=serial_no%>$<%=contract_bh%>$<%=cust_id %>$<%=product_id %>"></td>
							<td width="90%" align="left">
							<p><%=serial_no%></p>
							</td>
						</tr>
						</table>
						</td>
						<td align="left" ><%=cust_name%></td>
						<TD align="left" ><%=product_name%></TD>
						<td align="center"  ><%=contract_sub_bh%></td>
						<td align="right" ><%=Format.formatMoney(ben_amount)%></td>
						<td align="center" ><%=Format.formatDateCn(ben_date)%></td>
						<td align="center" ><%=prov_level_name%></td>
					</tr>
					<%iCurrent++;
iCount++;
}

for (int i=0;i < pageList.getBlankSize(); i++)
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" ></td>
						<TD align="center" ></TD>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
					</tr>
<%}
%>
					<tr class="trbottom">
						<td align="left" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
						<TD align="center" >-</TD>
						<td align="center"  >-</td>
						<td align="right" ></td>
						<td align="right" ><%=Utility.trimNull(Format.formatMoney(ben_amount))%></td>
						<td align="center" ></td>
						<td align="center" ></td>
					</tr>
				</table>

				<br>

				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
				
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form>

<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%benifitor.remove();
%>
