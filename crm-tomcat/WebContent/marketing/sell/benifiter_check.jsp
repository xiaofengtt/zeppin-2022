<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>

<%String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
IntrustBenifitorLocal benifitor = EJBFactory.getIntrustBenifitor();
IntrustContractLocal contract = EJBFactory.getIntrustContract();
int from_flag_benifitor=Utility.parseInt(request.getParameter("from_flag_benifitor"),0);
Integer contract_id = Utility.parseInt(request.getParameter("contract_id"), null);
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
input_bookCode=new Integer(1);
contract.setSerial_no(contract_id);
contract.load();
benifitor.setContract_bh(contract.getContract_bh());
benifitor.setProduct_id(contract.getProduct_id());
benifitor.setBook_code(input_bookCode);
benifitor.setInput_man(input_operatorCode);
String sQuery = request.getParameter("t1");
if (sQuery == null || sQuery.equals(""))
{
	sQuery = "";
	benifitor.list();
}
else
{
	benifitor.query(sQuery);
}

String sUrl = "benifiter_check.jsp?pagesize=" + sPagesize+"&contract_id="+contract_id;
benifitor.gotoPage(sPage, sPagesize);
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
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>

<script language=javascript>
function showinfo(cust_id)
{
	//现在受益人不需要审核，所以改为查看受益人信息
	//showModalDialog('benifiter_info_check.jsp?serial_no='+serialno+'&contract_bh=<%=contract.getContract_bh()%>&product_id=<%=contract.getProduct_id()%>', '', 'dialogWidth:420px;dialogHeight:330px;status:0;help:0');
	location = 'customer_query_info.jsp?cust_id='+cust_id;
}

function refreshPage()
{
	StartQuery();
}

function returnPage()
{
	if(document.theform.from_flag_benifitor.value=="1")
		location='purchase_check.jsp';
	else
		location="purchase3_check.jsp";
}

function StartQuery()
{
	location = 'benifiter_check.jsp?contract_id=<%=contract_id%>&page=1&pagesize=' + document.theform.pagesize.value+'&from_flag_benifitor='+document.theform.from_flag_benifitor.value;
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get">
<input type="hidden" name="contract_id" value="<%=contract_id%>">
<input type="hidden" name="from_flag_benifitor" value="<%=from_flag_benifitor%>">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td class="page-title"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align=right ><div class="btn-wrapper"><button type="button"  class="xpbutton3" accessKey=b name="btnCancel" title="返回" onclick="javascript:disableAllBtn(true);returnPage();">返回(<u>B</u>)</button>
						&nbsp;&nbsp;&nbsp;</div></td>	
					</tr>
					<tr>
						<td>
					<br/>
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" height="25">受益人名称</td>
						<td align="center" height="25">证件号</td>
						<td align="center" height="25">受益序号</td>
						<td align="center" height="25" sort="num">受益份额</td>
						<td align="center" height="25">受益级别</td>
						<td align="center" height="25">受益期限</td>
						<td align="center" height="25">受益银行</td>
					    <td align="center" height="25">银行帐号</td>
					</tr>
					<%int iCount = 0;
int iCurrent = 0;
Integer serial_no;
IntrustCustomerInfoLocal customer = EJBFactory.getIntrustCustomerInfo();
java.math.BigDecimal total_amount=new java.math.BigDecimal(0);
while (benifitor.getNext() && iCurrent < benifitor.getPageSize()){
	serial_no = benifitor.getSerial_no();
	customer.setInput_man(input_operatorCode);
	customer.setCust_id(benifitor.getCust_id());
	if(benifitor.getBen_amount()!=null)
		total_amount=total_amount.add(benifitor.getBen_amount());
	customer.load();
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="left" height="25">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><%--<input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"></td>--%>
								<td width="90%" align="left"><%=Utility.trimNull(customer.getCust_name())%></td>
							</tr>
						</table>
						</td>
						<td align="left" height="25"><%=Utility.trimNull(customer.getCard_id())%></td>
						<td align="left" height="25"><%=Utility.trimNull(benifitor.getContract_bh())%> - <%=Utility.trimNull(benifitor.getList_id())%></td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(benifitor.getBen_amount()))%></td>
						<td align="center" height="25"><%=Utility.trimNull(benifitor.getProv_level_name())%></td>	
						
											
						<td align="center" height="25"><%=Utility.trimNull(benifitor.getValid_period())%></td>
						
						
						<td align="left" height="25"><%=Utility.trimNull(benifitor.getBank_name())%><%=Utility.trimNull(benifitor.getBank_sub_name())%></td>
						<td align="center" height="25"><%=Utility.trimNull(benifitor.getBank_acct())%></td>
					</tr>
					<%iCurrent++;
iCount++;
}

for (; iCurrent < benifitor.getPageSize(); iCurrent++)
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
					</tr>
					<%}
%>
					<tr class="trbottom">
						<td align="center" height="25"><b>合计 <%=benifitor.getRowCount()%> 项</b></td>
						<td align="center" height="25">-</td>
						<td align="center" height="25">-</td>
						<td align="center" height="25"><%=Utility.trimNull(Format.formatMoney(total_amount))%></td>
						<td align="center" height="25">-</td>
						<td align="center" height="25"></td>
						<td align="center" height="25">-</td>
						<td align="center" height="25"></td>
					</tr>
				</table>

				<br>

				<table border="0" width="100%">
					<tr valign="top">
						<td><%=benifitor.getPageLink(sUrl)%></td>	
									
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
contract.remove();
customer.remove();
%>
