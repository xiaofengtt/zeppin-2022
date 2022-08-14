<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0)); 
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String sCust_no = Utility.trimNull(request.getParameter("cust_no"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
Integer is_link = Utility.parseInt(request.getParameter("is_link"),new Integer(0));
String signed_spot_flag = Utility.trimNull(request.getParameter("signed_spot_flag"));//现场签约连过来的标识

//暂时设置
input_bookCode = new Integer(1);
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
sUrl = sUrl + "&cust_name=" + cust_name + "&cust_no=" + sCust_no+ "&product_id=" + product_id + "&card_id=" + card_id + "&is_link="+is_link;
CustomerInfoVO vo = new CustomerInfoVO();
CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
vo.setCust_id(cust_id);
vo.setProduct_id(product_id);
vo.setCust_no(sCust_no);
vo.setCust_name(cust_name);
vo.setCard_id(card_id);
vo.setIs_link(is_link);
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
int first_flag = Utility.parseInt(Utility.trimNull(request.getParameter("first_flag")),0);
IPageList pageList  = null;
Iterator it = null;
if(first_flag != 1)
	pageList = customer.listProcAllExt(vo, t_sPage, t_sPagesize);
if(pageList != null)
	it = pageList.getRsList().iterator();
Map map = new HashMap();
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
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
initQueryCondition()};

function showAmlInfo(value)
{
	var iQuery = "<%=cust_id%>"+ "$<%=cust_name%>"+ "$<%=card_id %>"+ "$<%=product_id %>"+ "$<%=is_link%>"
					+ "$" + "<%=sPage%>" + "$" + document.theform.pagesize.value;
	location = 'amcust_info.jsp?flag=1&cust_id='+value + '&iQuery=' + iQuery;
}
function StartQuery()
{
	{
		//if(document.theform.product_id.value=="0"){
		//	alert("请选择产品!");return false;}
		disableAllBtn(true);
		if (document.theform.pagesize!=null) {
			var url = 'aml_customer_list.jsp?page=1&pagesize=' + document.theform.pagesize.value
						+'&cust_name=' + document.theform.cust_name.value
						+ '&cust_no=' + document.theform.cust_no.value
						+ '&product_id=' + document.theform.product_id.value
						+'&card_id='+document.theform.card_id.value;
		}else{
			url = 'aml_customer_list.jsp?page=1&cust_name=' + document.theform.cust_name.value
						+ '&cust_no=' + document.theform.cust_no.value
						+ '&product_id=' + document.theform.product_id.value
						+'&card_id='+document.theform.card_id.value;
		}
		if(document.theform.is_link.checked)
			url = url+'&is_link='+document.theform.is_link.value;
		url = url + '&signed_spot_flag=<%=signed_spot_flag%>'
		location = url;
	}
}

function refreshPage()
{
	disableAllBtn(true);
	var url = 'aml_customer_list.jsp?page=1&pagesize=' + document.theform.pagesize.value
				+'&cust_name=' + document.theform.cust_name.value
				+ '&cust_no=' + document.theform.cust_no.value
				+ '&product_id=' + document.theform.product_id.value
				+'&card_id='+document.theform.card_id.value;
	if(document.theform.is_link.checked)
			url = url+'&is_link='+document.theform.is_link.value;
	location = url;
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
</script>
</HEAD>
<BODY class="BODY"><%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">
<input type="hidden" name="signed_spot_flag" value="<%=signed_spot_flag%>">
<input type="hidden" name="cust_id" value="<%=cust_id%>">
<div id="queryCondition" class="qcMain" style="display:none;width:420px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	  <tr>
	   <td>查询条件：</td>
	   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       onclick="javascript:cancelQuery();"></button></td>
	  </tr>
	</table>

	<table>
	<tr>
						<td valign="bottom"  align="right">客户编号:</td>
						<td valign="bottom"  align="left">
						<input type="text" name="cust_no" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(sCust_no.trim())%>" size="15">&nbsp;&nbsp;
						</td>
						<td valign="bottom"  align="right">
						客户名称:</td><td valign="bottom"  align="left">
						 <input type="text" maxlength="100" name="cust_name" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(cust_name.trim())%>" size="15">&nbsp;&nbsp;
						 </td>

</tr>
					<tr>
						<td valign="bottom"  align="right">证件号码:</td>
						<td valign="bottom"  align="left" colspan="3">
						 <input type="text" name="card_id" onkeydown="javascript:nextKeyPress(this)" value="<%=Utility.trimNull(card_id)%>" size="30">&nbsp;&nbsp;
						</td>
					</tr>
<tr>
						 <td valign="bottom"  align="right">是否关联方:</td>
						 <td valign="bottom"  align="left"><input type="checkbox" name="is_link" value="1" <%if(is_link.intValue()==1) out.print("checked");%> class="flatcheckbox">
						</td>
						<td align="right">产品编号:</td>
						<td align="left">
						<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">
						&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
						</td >
					</tr>
					<tr>

<td align="right">产品选择:</td >
<td align="left" colspan=3>
<SELECT name="product_id" class="productname"><%=Argument.getProductListOptions(input_bookCode,product_id,"",input_operatorCode,0)%></SELECT>
</td>
</tr><tr>
<td align="center" colspan=4><button type="button"  class="xpbutton3" name="btnQuery" accessKey=o onclick="javascript:StartQuery();">确定(<u>O</u>)</button></td>
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
						<td colspan=6 class="page-title"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align=right> 
						<div class="btn-wrapper">
							<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button>
						</div>
						</td>
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" ><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">编号</td>
						<td align="center" >名称</td>
						<td align="center" >联系电话</td>
						<td align="center" sort="num">认购金额</td>
						<td align="center" sort="num">已购总金额</td>
						<td align="center" sort="num">存量金额</td>
						<td align="center" >最近购买</td>
						<td align="center" >证件号码</td>
						<td align="center" >编辑</td>
					</tr>
					<%int iCount = 0;
int iCurrent = 0;
String cust_tel;
String cust_no = "";
BigDecimal cur_to_amount = new BigDecimal(0.000);   //认购金额合计
BigDecimal total_amount = new BigDecimal(0.000);   //总认购金额合计
BigDecimal current_amount = new BigDecimal(0.000);   //金额合计
Integer last_rg_date;		//最近购买

while (it!= null && it.hasNext())
{
    map = (Map)it.next();
    cust_no = Utility.trimNull(map.get("CUST_NO"));
    cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
    cust_name = Utility.trimNull(map.get("CUST_NAME"));
    cust_tel = Utility.trimNull(map.get("CUST_TEL"));
    card_id = Utility.trimNull(map.get("CARD_ID"));
    is_link = Utility.parseInt(Utility.trimNull(map.get("IS_LINK")),new Integer(0));
	cur_to_amount = Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_MONEY")), new BigDecimal(0.00),2,"100");
	total_amount = Utility.parseDecimal(Utility.trimNull(map.get("TOTAL_MONEY")), new BigDecimal(0.00),2,"100");
	current_amount = Utility.parseDecimal(Utility.trimNull(map.get("CURRENT_MONEY")), new BigDecimal(0.00),2,"100");
	last_rg_date = Utility.parseInt(Utility.trimNull(map.get("LAST_RG_DATE")),new Integer(0));;
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" >
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input type="checkbox" name="cust_id" value="<%=cust_id%>" class="flatcheckbox"></td>
								<td width="90%" align="left"><%=cust_no %></td>
							</tr>
						</table>
						</td>
						<td align="left" ><%=cust_name %></td>
						<td align="left" ><%=cust_tel %></td>
						<td align="right" ><%=Utility.trimNull(Format.formatMoney(cur_to_amount))%></td>

						<td align="right" ><%=Utility.trimNull(Format.formatMoney(total_amount))%></td>
						<td align="right" ><%=Utility.trimNull(Format.formatMoney(current_amount))%></td>
						<td align="center" ><%=Format.formatDateCn(last_rg_date)%></td>
						<td align="left" ><%=card_id %></td>
						<td align="center" >
<%if (input_operator.hasFunc(menu_id, 102) || "1".equals(signed_spot_flag)) {%>
						<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showAmlInfo(<%=cust_id %>);">&gt;&gt;</button>
<%}%>
						</td>

					</tr>
					<%iCurrent++;
iCount++;
}

for (int i=0;i<(t_sPagesize-iCount);i++)
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
					</tr>
					<%}
%>
					<tr class="trbottom">
						<td class="tr<%=(iCurrent % 2)%>" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ><%=Utility.trimNull(Format.formatMoney(cur_to_amount))%></td>
						<td align="center" ><%=Utility.trimNull(Format.formatMoney(total_amount))%></td>
						<td align="center" ><%=Utility.trimNull(Format.formatMoney(current_amount))%></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
					</tr>
				</table>

				<br>

				<table border="0" width="100%" class="page-link">
					<tr valign="top">
						<td><%if(pageList != null){%><%=pageList.getPageLink(sUrl,clientLocale)%><%} %></td>

					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form><%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%customer.remove();
%>
