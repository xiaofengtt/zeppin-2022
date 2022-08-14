<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>

<%	
//查询条件
try{
String sQuery = Utility.trimNull(request.getParameter("sQuery"));	//认购页面的查询条件
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
int from_flag_benifitor = Utility.parseInt(request.getParameter("from_flag_benifitor"),Utility.parseInt(request.getParameter("from_flag"),1));

IntrustBenifitorLocal benifitor = EJBFactory.getIntrustBenifitor();
IntrustContractLocal contract = EJBFactory.getIntrustContract();

Integer contract_id = Utility.parseInt(request.getParameter("contract_id"), null);
input_bookCode=new Integer(1);
contract.setSerial_no(contract_id);

contract.load();
String disabled = "";

benifitor.setContract_bh(contract.getContract_bh());
benifitor.setProduct_id(contract.getProduct_id());
benifitor.setBook_code(input_bookCode);
benifitor.setInput_man(input_operatorCode);
benifitor.setFlag(new Integer(1));

if ((Utility.parseInt(contract.getCheck_flag(),new Integer(0))).intValue() != 1 || !("120101").equals(contract.getHt_status()))
	disabled = " disabled ";

benifitor.list();

String sUrl = "benifiter.jsp?pagesize=" + sPagesize + "&from_flag_benifitor=" + from_flag_benifitor 
			+ "&sQuery=" + sQuery + "&contract_id=" + contract_id;
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
<LINK href="/includes/default.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<script language=javascript>
function newInfo()
{
	if(showModalDialog('benifiter_info.jsp?check_flag=1&contract_bh=<%=Utility.trimNull(contract.getContract_bh())%>&product_id=<%=Utility.trimNull(contract.getProduct_id())%>', '', 'dialogWidth:720px;dialogHeight:500px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function showInfo(serialno, checkflag)
{
	if(showModalDialog('benifiter_info.jsp?serial_no='+serialno+'&contract_bh=<%=Utility.trimNull(contract.getContract_bh())%>&product_id=<%=Utility.trimNull(contract.getProduct_id())%>&check_flag='+checkflag, '', 'dialogWidth:720px;dialogHeight:500px;status:0;help:0') != null)
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
	location = 'benifiter.jsp?contract_id=<%=contract_id%>&page=1&pagesize=' + document.theform.pagesize.value
				+ '&from_flag_benifitor=' + '<%=from_flag_benifitor%>' + '&sQuery='+'<%=sQuery%>';
}

function op_return()
{
	tempArray = '<%=sQuery%>'.split('$');
	from_flag_benifitor = document.theform.from_flag_benifitor.value;
	
	if(from_flag_benifitor==1)		
		location = 'purchase.jsp?page=1&pagesize=' + tempArray[6] + '&productid=' + tempArray[0] + '&product_id=' + tempArray[1] 
					+ '&cust_name=' + tempArray[2] + '&query_contract_bh=' + tempArray[3] + '&card_id=' + tempArray[4] + '&pre_flag=' + tempArray[5];
	else if(from_flag_benifitor==4)
        location = 'purchase_mode.jsp?page=1&pagesize=' + tempArray[6] + '&productid=' + tempArray[0] + '&product_id=' + tempArray[1] 
					+ '&cust_name=' + tempArray[2] + '&query_contract_bh=' + tempArray[3] + '&card_id=' + tempArray[4] + '&check_flag=' + tempArray[5];
	else
		location = 'purchase3.jsp?page=1&pagesize=' + tempArray[5] + '&productid=' + tempArray[0] + '&product_id=' + tempArray[1]
					+ '&cust_name=' + tempArray[2] + '&query_contract_bh=' + tempArray[3] + '&card_id=' + tempArray[4] ;
}


//function editCustinfo(cust_id)
//{	
//	window.open('/transaction/customer/customer_info.jsp?cust_id=' + cust_id + '&contract_id='+ '<%=contract_id%>'+'&sQuery='+'<%=sQuery%>' +'&from_flag_benifitor='+'<%=from_flag_benifitor%>' +'&from_flag=5','_blank','left=100, top=100,width=900,height=700');   
//}

function getTransactionCustomer1(cust_id,readonly)
{  
	var v = showModalDialog('customer_info.jsp?formFlag=12&cust_id=' + cust_id + '&readonly=' + readonly,'','dialogWidth:670px;dialogHeight:540px;status:0;help:0;');
}
</script>
<BODY class="BODY body-nox">

<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="get" action="benifiter_remove.jsp">
<input type=hidden name="contract_id" value="<%=contract_id%>">
<input type=hidden name="sQuery" value="<%=sQuery%>">
<input type=hidden name="from_flag_benifitor" value="<%=from_flag_benifitor%>">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td class="page-title"><b><%=menu_info%>&gt;&gt;受益人信息</b><br/></td>
					
					</tr>
					<tr>
						<td align=right>
						<div class="btn-wrapper">
					  	<%if (input_operator.hasFunc(menu_id, 100)){%>
							<button type="button"  class="xpbutton3" accessKey=n name="btnNew" <%=disabled%> title="新建" onclick="javascript:newInfo();">新建(<u>N</u>)</button>
							&nbsp;&nbsp;&nbsp;
						<%}%>
						<%if (input_operator.hasFunc(menu_id, 101)){%>
							<button type="button"  class="xpbutton3" accessKey=d name="btnCancel" <%=disabled%> title="删除" onclick="javascript:if(confirmRemove(document.theform.serial_no)) {disableAllBtn(true);document.theform.submit();}">删除(<u>D</u>)</button>
							&nbsp;&nbsp;&nbsp;
						<%}%>						
							<button type="button"  class="xpbutton3" accessKey=b name="btnCancel" title="返回" onclick="javascript:disableAllBtn(true);op_return();">返回(<u>B</u>)</button>
							&nbsp;&nbsp;&nbsp;
							</div>
						</td>
					</tr>
					<tr>
						<td>
							<br/>
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" height="25"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">名称</td>
						<%--<td align="center" height="25">证件类型</td>
						<td align="center" height="25">证件号</td>--%>
						<td align="center" height="25">受益序号</td>
						<td align="center" height="25" sort="num">受益金额</td>						
						<td align="center" height="25">受益起/止日期</td>
						<td align="center" height="25">缴款银行</td>
					    <td align="center" height="25">银行帐号</td>
						<!-- <td align="center" height="25">编辑</td>	 -->					
					</tr>
<%
BigDecimal ben_amount = new BigDecimal("0.000");
BigDecimal to_amount = new BigDecimal("0.000");
int iCount = 0;
int iCurrent = 0;
Integer serial_no;

while (benifitor.getNext() && iCurrent < benifitor.getPageSize())
{   
	serial_no = benifitor.getSerial_no();
	
	if (benifitor.getBen_amount() != null)
		ben_amount = ben_amount.add(benifitor.getBen_amount());
	if (benifitor.getRemain_amount() != null)
		to_amount = to_amount.add(benifitor.getRemain_amount());
%>

					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" height="25">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"></td>
								<td width="90%" align="left"><a href='#' onclick="javascript:getTransactionCustomer1('<%=benifitor.getCust_id()%>','1');return false;"><%=Utility.trimNull(benifitor.getCust_name())%></a></td>
							</tr>
						</table>
						</td>
						<%--<td align="center" height="25"><%=Utility.trimNull(customer.getCard_type_name())%></td>
						<td align="center" height="25"><%=Utility.trimNull(customer.getCard_id())%></td>--%>
						<td align="center" height="25"><%=Utility.trimNull(benifitor.getContract_bh())%> - <%=Utility.trimNull(benifitor.getList_id())%></td>
						<td align="right"  height="25"><%=Utility.trimNull(Format.formatMoney(benifitor.getBen_amount()))%></td>					
						<td align="center" height="25"><%=Utility.trimNull(Format.formatDateCn(benifitor.getBen_date()))%>/
							<%if(benifitor.getBen_end_date()!=null && benifitor.getBen_end_date().intValue()>=20990101) out.print("无固定期限"); else out.print(Format.formatDateCn(benifitor.getBen_end_date()));%></td>
						<td align="left"   height="25"><%=Utility.trimNull(benifitor.getBank_name())%><%=Utility.trimNull(benifitor.getBank_sub_name())%></td>
						<td align="center" height="25"><%=Utility.trimNull(benifitor.getBank_acct())%></td>				
						<!--<td align="center" height="25"><%if (input_operator.hasFunc(menu_id, 102)){%>
							<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showInfo(<%=serial_no%>,<%=contract.getCheck_flag()%>);">&gt;&gt;</button>
							<%}%>					
						</td>-->
						<%--<td align="center" height="25">
						<%if (input_operator.hasFunc("20201", 102)) {%>
							<button type="button"  class="xpbutton2" name="btnEditCust" onclick="javascript:editCustinfo(<%=benifitor.getCust_id()%>);">&gt;&gt;</button>
						<%}%>	
						</td>--%>
					</tr>
<%
iCurrent++;
iCount++;
}

for (; iCurrent < benifitor.getPageSize(); iCurrent++)
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" height="25"></td>						
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>					
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<!-- td align="center" height="25"></td> -->					
					</tr>
<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="center" height="25"><b>合计 <%=iCount%> 项</b></td>					
						<td align="center" height="25"></td>
						<td align="right"  height="25"><%=Format.formatMoney(to_amount)%></td>					
						<td align="center" height="25">-</td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<!--<td align="center" height="25"></td> -->							
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
<%
benifitor.remove();
contract.remove();
}catch(Exception e){
	throw e;
}
%>

