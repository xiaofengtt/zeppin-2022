<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
BenifitorLocal benifitor = EJBFactory.getBenifitor();
BenifitorVO vo = new BenifitorVO();

Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String contract_sub_bh = request.getParameter("contract_sub_bh");
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
int prov_level = Utility.parseInt(request.getParameter("prov_level"),2);

vo.setContract_sub_bh(contract_sub_bh);
vo.setProduct_id(product_id);
vo.setBook_code(new Integer(1));
vo.setCust_no(card_id);
vo.setCust_id(new Integer(0));
vo.setCust_name(cust_name);
vo.setCust_no(cust_no);
vo.setInput_man(input_operatorCode);
vo.setBen_lost_flag(new Integer(2));
vo.setFunction_id(new Integer(1));

String[] totalColumn = new String[0];
IPageList pageList = benifitor.listLostDetail(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));


sUrl = "benifiter_level_change.jsp?pagesize=" + sPagesize + "&product_id=" + product_id +"&contract_sub_bh="+ contract_sub_bh+"&cust_name="+cust_name+"&card_id="+card_id+"&prov_level="+prov_level+"&cust_no="+cust_no;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>

window.onload = function(){
initQueryCondition()};

function showInfo(serialno, contract_bh)
{
	if(showModalDialog('benifiter_level_change_info.jsp?serial_no='+serialno+'&contract_bh='+contract_bh+'&product_id=<%=product_id%>&check_flag='+3, '', 'dialogWidth:600px;dialogHeight:640px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function refreshPage()
{
	StartQuery();
}

function StartQuery()
{
	disableAllBtn(true);
	location = 'benifiter_level_change_list.jsp?contract_sub_bh='+document.theform.contract_sub_bh.value+'&page=1&pagesize='+
	 document.theform.pagesize.value+'&product_id='+document.theform.product_id.value+'&cust_name='+document.theform.cust_name.value+
	 '&card_id='+document.theform.card_id.value+'&cust_no='+document.theform.cust_no.value;
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform">


<div id="queryCondition" class="qcMain" style="display:none;width:460px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table class="product-list">
<tr>
						<td colspan=4 align="left"><%=LocalUtilis.language("class.productName",clientLocale)%> :&nbsp;&nbsp;&nbsp;<SELECT size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class=productname>
							<%=Argument.getProductListOptions(new Integer(1), product_id,"", input_operatorCode,0)%>
						</SELECT> 
						</TD><!--产品名称-->
                       </tr><tr>
						<td  align="right">
						<%=LocalUtilis.language("class.contractID",clientLocale)%> : 
						</td><!--合同编号-->
						<td align="left"><INPUT type="text" onkeydown="javascript:nextKeyPress(this)" name="contract_sub_bh" size="15" value="<%=Utility.trimNull(contract_sub_bh)%>">&nbsp;&nbsp;</td>
						<td  align="right">
						<%=LocalUtilis.language("class.custNO",clientLocale)%> : 
						</td><!--受益人编号-->
						<td align="left"><INPUT type="text" onkeydown="javascript:nextKeyPress(this)" name="cust_no" size="15" value="<%=Utility.trimNull(cust_no)%>">&nbsp;&nbsp;</td>
						</tr>
						<tr>
						<td  align="left"><%=LocalUtilis.language("class.custName2",clientLocale)%> : </td><!--受益人名称-->
						<td align="left">
							<input type="text" name="cust_name" value="<%=cust_name%>" onkeydown="javascript:nextKeyPress(this);" size="25">
						</td>
						<td  align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
						<td align="left"> 						
							<input type="text" name="card_id" value="<%=card_id%>" onkeydown="javascript:nextKeyPress(this);"  size="25">
						</td>
                         </tr>
                         <tr>
						<td align=center colspan=4>
						<button type="button"  class="xpbutton3" accesskey="o" name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</BUTTON>
						</td><!--确定-->
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
						<td colspan=8><IMG src="<%=request.getContextPath()%>/images/member.gif" border=0 width="32" height="28"><b><%=menu_info%></b></td>
					</tr>
					<tr><td align=right> 
						<%if (input_operator.hasFunc(menu_id,108) ){%>
						<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button><!--查询-->
						<%}%>
					</td></tr>
					<tr>
						<td colspan="8">
						<hr noshade color="#808080"  size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" width="100%">
					<tr class="trh">
						<TD align="center" ><%=LocalUtilis.language("class.productName",clientLocale)%> </TD><!--产品名称-->
						<td align="center"  noWrap><%=LocalUtilis.language("class.contractID",clientLocale)%> </td><!--合同编号-->
						<td align="center"  ><%=LocalUtilis.language("class.custNO",clientLocale)%> </td><!--受益人编号-->
						<td align="center" ><%=LocalUtilis.language("class.custName2",clientLocale)%> </td><!--受益人名称-->
						<td align="center" ><%=LocalUtilis.language("class.benAmount",clientLocale)%> </td><!--受益金额-->
						<td align="center" ><%=LocalUtilis.language("class.benDate",clientLocale)%> </td><!--受益日期-->
						<td align="center" ><%=LocalUtilis.language("class.prov_level",clientLocale)%> </td><!--受益级别-->
						<td align="center" ><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->	
					</tr>
<%

int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map rowMap = null;
for(int i=0;i<list.size();i++) 
{
	rowMap = (Map)list.get(i);
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<TD align="left"><%=Utility.trimNull(rowMap.get("PRODUCT_NAME"))%></TD>
						<td align="left"><%=Utility.trimNull(rowMap.get("CONTRACT_SUB_BH"))%>-<%=Utility.trimNull(rowMap.get("LIST_ID"))%></td>
						<td class="tdh" align="left"><%=Utility.trimNull(rowMap.get("CUST_NO"))%></td>
						<TD align="left"  noWrap><%=Utility.trimNull(rowMap.get("CUST_NAME"))%></TD>
						<td align="right"><%=Utility.trimNull(Format.formatMoney((BigDecimal)rowMap.get("BEN_AMOUNT")))%></td>
						<td align="center"><%=Format.formatDateCn((Integer)rowMap.get("BEN_DATE"))%></td>
						<td align="center" width="10%"><%=Utility.trimNull(rowMap.get("PROV_LEVEL_NAME"))%></td>
						<td align="center">
						<%if (input_operator.hasFunc(menu_id,102) ){%>
							<a href="#" onclick="javascript:showInfo(<%=rowMap.get("SERIAL_NO")%>,'<%=rowMap.get("CONTRACT_SUB_BH")%>');">
           			<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
           		</a>
						<%}%>
						</td>
					
					</tr>
<%iCurrent++;
iCount++;
}

for (; iCurrent < pageList.getPageSize(); iCurrent++) 
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" ></td>
						<TD align="center" ></TD>
						<TD align="center" ></TD>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						
					</tr>
<%}%>
					<tr class="trbottom">
						<td class="tdh" align="center"><b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--><%=pageList.getTotalSize()%><%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
						<TD align="center">-</TD>
						<TD align="center">-</TD>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
						<td align="center">-</td>
					</tr>
				</table>
				<br>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl)%></td>
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
<%benifitor.remove();
%>
