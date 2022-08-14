<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%
	
IntrustContractLocal contract = EJBFactory.getIntrustContract();
IntrustCapitalInfoLocal capitalinfo = EJBFactory.getIntrustCapitalInfo();		
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
Integer cust_id=Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
Integer start_date=Utility.parseInt(request.getParameter("start_date"),new Integer(0));
Integer end_date=Utility.parseInt(request.getParameter("end_date"),new Integer(0));
String contract_bh=Utility.trimNull(request.getParameter("contract_bh"));
String busi_id=Utility.trimNull(request.getParameter("busi_id"),"120388");
String capital_use=Utility.trimNull(request.getParameter("capital_use"));
Integer product_id=Utility.parseInt(request.getParameter("product_id"),new Integer(0));
int readOnly=Utility.parseInt(request.getParameter("readonly"),0);
String capital_name=Utility.trimNull(request.getParameter("capital_name"));

input_bookCode=new Integer(1);

contract.setProduct_id(product_id);
contract.setContract_bh(contract_bh);
capitalinfo.setContract_bh(contract.getContract_bh());
capitalinfo.setProduct_id(contract.getProduct_id());
capitalinfo.setBusi_id(busi_id);
capitalinfo.setBook(input_bookCode);
capitalinfo.setInput_man(input_operatorCode);
capitalinfo.setCapital_name(capital_name);
capitalinfo.list();

String sUrl = "capital.jsp?pagesize=" + sPagesize+"&cust_id="+cust_id +"&capital_name="+capital_name+"&contract_bh="+contract_bh + "&product_id=" + product_id+"&busi_id="+busi_id+"&capital_use="+capital_use+"&readonly="+readOnly;
capitalinfo.gotoPage(sPage, sPagesize);
int iCurrent=0;

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

</HEAD>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
initQueryCondition()};
function showInfo(serial_no,busi_id,capital_use,check_flag)
{
	product_id=document.theform.product_id.value;
	contract_bh=document.theform.contract_bh.value;
	readonly=document.theform.readonly.value;
	if(showModalDialog('capital_info.jsp?from_flag=1&capital_use=191105&start_date='+document.theform.start_date.value+'&end_date='+document.theform.end_date.value+'&cust_id='+document.theform.cust_id.value+'&serial_no=' + serial_no +'&busi_id='+busi_id+'&capital_use='+capital_use+'&contract_bh='+contract_bh+'&product_id='+product_id+'&readonly='+readonly+'&check_flag='+check_flag, '','dialogWidth:700px;dialogHeight:600px;STATUS=0;help=0;')!=null)
	{
		sl_update_ok();
		location.reload();
	}
	//location.href = 'capital/capitaltype_select1.jsp';
}
function StartQuery()
{
		location = 'capital.jsp?page=1&pagesize=' + document.theform.pagesize.value +'&product_id='+document.theform.product_id.value+ '&contract_bh='+ document.theform.contract_bh.value +'&busi_id='+document.theform.busi_id.value+'&readonly='+ document.theform.readonly.value+'&capital_name='+document.theform.capital_name.value+'&cust_id='+document.theform.cust_id.value;
}

function refreshPage()
{
	StartQuery();
}

function confirmRemoveCapital(obj)
{
	if(checkedCount(obj) == 0)
	{
		sl_alert("请选定要删除的记录！");
		return false;
	}

	if(confirm('您确定要删除选定的记录吗？'))
	{disableAllBtn(true);
		document.theform.remove.value=1;
		document.theform.submit();
	}
}
</script>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="capital_remove.jsp">
<input type="hidden" name="contract_bh" value="<%=contract_bh%>">
<input type="hidden" name="product_id" value="<%=product_id%>">
<input type="hidden" name="busi_id" value="<%=busi_id%>">
<input type="hidden" name="capital_use" value="<%=capital_use%>">
<input type=hidden name="remove">
<input type="hidden" name="readonly" value="<%=readOnly%>">
<input type="hidden" name="start_date" value="<%=start_date%>">
<input type="hidden" name="end_date" value="<%=end_date%>">
<input type="hidden" name="cust_id" value="<%=cust_id%>">


<div id="queryCondition" class="qcMain" style="display:none;width:290px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>
<tr>
  <td align="right">资产名称:</td>
					<td align="left" >
							<input type="text"  name="capital_name" value="<%=capital_name%>" onkeydown="javascript:nextKeyPress(this);" size="30">							
					</td>
						
</tr>
<tr><td colspan=2 align=center>
<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:disableAllBtn(true);StartQuery();">确定(<u>O</u>)</button>
</td>
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
						<td colspan="3" class="page-title"><b><%=menu_info%>>>信托资产信息</b></td>
				</tr>
			   
				<tr>
					
					<td align="right" >
					<div class="btn-wrapper">
					<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
							<%if (input_operator.hasFunc(menu_id, 100)){%>
						<button type="button"  class="xpbutton3" accessKey=n name="btnNew"  title="新建" onclick="javascript:showInfo('','<%=busi_id%>','<%=capital_use%>','2');">新建(<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<%}%>
						<%if (input_operator.hasFunc(menu_id, 101)){%>
						<button type="button"  class="xpbutton3" accessKey=d name="btnCancel" title="删除" onclick="javascript:confirmRemoveCapital(document.theform.serial_no);">删除(<u>D</u>)</button>
						&nbsp;&nbsp;&nbsp;
						<%}%>
						<button type="button" class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>
						</div>
					</td>	
				</tr>
				<tr>
				<td colspan="3">
			<br/>
				</td>
			</tr>	
				<tr id=bzr_capitalinfo>
				<td colspan=3>
				<table id="table_capital_bzr" width="100%" border="0" cellspacing="1" cellpadding="2" 
class="tablelinecolor"  >
					<tr class="trh">
						<td   align="center" height="25">资产名称</td>
						<td   align="center" height="25">资产编号</td>
						<td   align="center" height="25">资产类别</td>
						<td   align="center" height="25">资产所有者</td>
						<td   align="center" height="25">入帐价值</td>
						<td   align="center" height="25"><%if(readOnly==1) out.print("查看");else out.print("编辑");%></td>
					</tr>
<%
int iCount=0;
iCurrent=0;
java.math.BigDecimal total_amount=new java.math.BigDecimal(0);
while (capitalinfo.getNext() && iCurrent < capitalinfo.getPageSize())
{
	int icheck_flag=1;
	if(capitalinfo.getCheck_flag()!=null)
		icheck_flag=capitalinfo.getCheck_flag().intValue();	
	if(capitalinfo.getMoney()!=null)
		total_amount=total_amount.add(capitalinfo.getMoney());
%>

					<tr style="display:"  class="tr<%=(iCurrent % 2)%>">
						<td class="tdh"  align="left" height="25">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%">&nbsp;
								<%if(icheck_flag!=2){%>
								<input type="checkbox"  name="serial_no" value="<%=capitalinfo.getSerial_no()%>" class="flatcheckbox">
								<%}%>
								</td>
								<td width="90%" align="left">&nbsp;<%=Utility.trimNull(capitalinfo.getCapital_name())%></td>
							</tr>
						</table>
						</td>
						<td   align="center" height="25">&nbsp;<%=Utility.trimNull(capitalinfo.getCapital_no())%></td>
						<td   align="center" height="25">&nbsp;<%=Utility.trimNull(capitalinfo.getCapital_type_name())%></td>
						<td   align="left" height="25">&nbsp;<%=Utility.trimNull(capitalinfo.getCust_name())%></td>
						<td   align="right" height="25">&nbsp;<%=Utility.trimNull(Format.formatMoney(capitalinfo.getMoney()))%></td>
						<td   align="center" height="25">&nbsp;
						<%if (input_operator.hasFunc(menu_id, 102) || icheck_flag==1)	{%>
						<button type="button"  class="xpbutton2" name="btnEdit" onclick="javascript:showInfo('<%=capitalinfo.getSerial_no()%>','<%=busi_id%>','<%=capital_use%>','<%=icheck_flag%>');">&gt;&gt;</button>
						<%}%>					

						</td>
					</tr>
					<%
					iCurrent++;
					iCount++;
	 }
for (; iCurrent < capitalinfo.getPageSize(); iCurrent++)
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
					</tr>
					<%}
%>
					<tr class="trbottom">
						<td align="center" height="25"><b>合计 <%=capitalinfo.getRowCount()%> 项</b></td>
						<td align="center" height="25">-</td>
						<td align="center" height="25">-</td>
						<td align="center" height="25">-</td>
						<td align="right" height="25"><%=Utility.trimNull(Format.formatMoney(total_amount))%></td>
						<td align="center" height="25">-</td>
					</tr>
				</table>
				</td>	
				</tr>
					
				<tr >
				<td colspan=3>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=capitalinfo.getPageLink(sUrl)%></td>
					
					</tr>
				</table>
				</td>
				</tr>
		</TABLE>
		</TD>
		</tr>
</TABLE>
</TD>
		</tr>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%capitalinfo.remove();
%>
