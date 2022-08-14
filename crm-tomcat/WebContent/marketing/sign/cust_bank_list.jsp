<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));
String q_bank_name = Utility.trimNull(request.getParameter("q_bank_name"));
String q_bank_acct = Utility.trimNull(request.getParameter("q_bank_acct"));
CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
CustomerInfoVO vo = new CustomerInfoVO();
vo.setCust_id(cust_id);
vo.setBank_name(q_bank_name);
vo.setBank_acct(q_bank_acct);
Map map = null;
IPageList pageList =
	customer.listCustBank(
		vo,
		Utility.parseInt(sPage, 1),
		Utility.parseInt(sPagesize, 10));
List list = pageList.getRsList();
Iterator it = list.iterator();

enfo.crm.customer.CustomerLocal local = EJBFactory.getCustomer();
Map cust_map = new HashMap();
CustomerVO vo1 = new CustomerVO();
vo1.setCust_id(cust_id);
vo1.setInput_man(input_operatorCode);
List cust_list = local.listCustomerLoad(vo1);
String cust_name = "";
String card_type_name = "";
String card_id = "";
if(cust_list != null && cust_list.size() > 0){
	cust_map = (Map)cust_list.get(0); 
	cust_name= Utility.trimNull(cust_map.get("CUST_NAME"));
	card_type_name = Utility.trimNull(cust_map.get("CARD_TYPE_NAME"));
	card_id= Utility.trimNull(cust_map.get("CARD_ID"));
}
%>

<HTML>
<HEAD>
<TITLE>客户银行账户信息</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function showInfo(serial_no)
{
	var cust_id = document.theform.cust_id.value;
	var ret= showModalDialog("cust_bank_update.jsp?serial_no="+serial_no+'&cust_id='+cust_id, "", "dialogWidth:420px;dialogHeight:400px;status:0;help:0");
	if (ret != null)
	{
		sl_update_ok();
		refreshPage();
	}
}

function newInfo()
{
	var cust_id = document.theform.cust_id.value;
	if( showModalDialog("cust_bank_new.jsp?cust_id="+cust_id, "", "dialogWidth:420px;dialogHeight:400px;status:0;help:0") != null)
	{
		sl_update_ok();
		refreshPage();
	}
}

function StartQuery()
{
	var cust_id = document.theform.cust_id.value;
	location = 'cust_bank_list.jsp?page=1&cust_id='+cust_id+'&pagesize=' + document.theform.pagesize.value 
							+'&q_bank_name=' + document.theform.q_bank_name.value 
							+'&q_bank_acct=' + document.theform.q_bank_acct.value;
}

function refreshPage()
{
	StartQuery();
}

window.onload = function(){
initQueryCondition()};
</script>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="cust_bank_remove.jsp">
<input type="hidden" name="cust_id" value="<%=cust_id %>"> 

<div id="queryCondition" class="qcMain" style="display:none;width:420px;">	
	<table id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
		<tr>
			<td>查询条件:</td>
			<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
									onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>			
		<table border="0" width="100%" cellspacing="2" cellpadding="2">					
			<tr>
				<td align="right">银行名称: </td>
				<td>
					<input type="text" maxlength="100" name="q_bank_name" onkeydown="javascript:nextKeyPress(this)" value="<%=q_bank_name%>" size="20">
				</td>
				<td align="right">银行账号: </td>
				<td>
					<input type="text" maxlength="100" name="q_bank_acct" onkeydown="javascript:nextKeyPress(this)" value="<%=q_bank_acct%>" size="20">
				</td>
			<tr>
				<td align="center" colspan="4"><button type="button"  class="xpbutton3" accessKey=o name="btnQuery"
						onclick="javascript:StartQuery();">确定(<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
				</td>
			</tr>					
		</table>				
</div>
<input type=hidden name="readerInfo" value="">
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="2" cellpadding="1">
					<tr>
						<td colspan="2" class="page-title"><b><%=menu_info%>>>客户银行账户信息</b></td>
					</tr>
					<tr>
						<td align="right">
						<font color="#3300FF"><b><%=Utility.trimNull(cust_name) %> - <%=card_type_name %>：<%=card_id %></b></font>
						<div class="btn-wrapper">
							<button type="button"   class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;

							<button type="button"   class="xpbutton3" accessKey=n name="btnNew" title="新建" onclick="javascript:newInfo();">新建(<u>N</u>)</button>
							&nbsp;&nbsp;&nbsp;
							<button type="button"  class="xpbutton3" accessKey=d name="btnCancel" title="删除" onclick="javascript:if(confirmRemove(document.theform.serial_no)) document.theform.submit();">删除(<u>D</u>)</button>
						</div>
						<br/>
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" bgcolor="#000000" width="100%">
					<tr class="trh">
						<td align="center" height="25"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox
						(document.theform.serial_no,this);">银行名称</td>
						<td align="center" height="25">银行账号</td>
						<td align="center" height="25">编辑</td>
					</tr>
					<%int iCount = 0;
					int iCurrent = 0;
					Integer family_id;
					
					while (it.hasNext()) {
						map = (Map) it.next();
						Integer serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
						String bank_name = Utility.trimNull(map.get("BANK_NAME"));
						String bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" height="25">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"></td>
								<td width="90%" align="left"><%=Utility.trimNull(bank_name)%></td>
							</tr>
						</table>
						</td>
						<td align="left" height="25"><%=Utility.trimNull(bank_acct)%></td>
						<td align="center" height="25">
							<button type="button"   class="xpbutton2" name="btnEdit" onclick="javascript:showInfo(<%=serial_no%>);">&gt;&gt;</button>					
						</td>
					</tr>
					<%iCurrent++;
						iCount++;
					}%>

					<%for (; iCurrent < pageList.getPageSize(); iCurrent++) {
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" height="25"></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
					</tr>
					<%}
%>
					<tr class="trbottom">
						<td class="tdh" align="center" height="25"><b>合计 <%=iCount%> 项</b></td>
						<td align="center" height="25"></td>
						<td align="center" height="25"></td>
					</tr>
				</table>

				<br>

				<table border="0" width="100%" class="page-link">
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
<%customer.remove();
%>
