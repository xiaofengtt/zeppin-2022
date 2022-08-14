<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
String family_name=Utility.trimNull(request.getParameter("family_name"));
CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
CustomerInfoVO vo = new CustomerInfoVO();
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setFamily_name(family_name);

Map map = null;
IPageList pageList =
	customer.listRelation(
		vo,
		Utility.parseInt(sPage, 1),
		Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();
%>

<HTML>
<HEAD>
<TITLE></TITLE>
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
function showInfo(family_id)
{
	if( showModalDialog("cust_relation_info.jsp?readerInfo="+theform.readerInfo.value+"&family_id="+family_id, "", "dialogWidth:420px;dialogHeight:520px;status:0;help:0") != null)
	{
		sl_update_ok();
		refreshPage();
	}
}

function newInfo()
{
	
	if( showModalDialog("cust_relation_info.jsp?readerInfo="+theform.readerInfo.value, "", "dialogWidth:420px;dialogHeight:520px;status:0;help:0") != null)
	{
		sl_update_ok();
		refreshPage();
	}
}

function StartQuery()
{
	location = 'cust_relation_list.jsp?page=1&pagesize=' + document.theform.pagesize.value +'&family_name=' + document.theform.family_name.value ;
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
<form name="theform" method="get" action="cust_relation_remove.jsp" ENCTYPE="multipart/form-data">
<div id="queryCondition" class="qcMain" style="display:none;width:250px;">	
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td>查询条件:</td>
			<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
									onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>			
		<table border="0" width="100%" cellspacing="2" cellpadding="2">					
			<tr>
			<td >关联名称: 
				<input type="text" maxlength="100" name="family_name" onkeydown="javascript:nextKeyPress(this)" value="<%=family_name %>" size="20">
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
				<table border="0" width="100%" cellspacing="1" cellpadding="1">
					<tr>
						<td colspan=2 class="page-title"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td valign="bottom" align="right">
						<div class="btn-wrapper">
						<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
						<%if (input_operator.hasFunc(menu_id, 100))
{%>
						<button type="button"  class="xpbutton3"  accessKey=n name="btnNew" title="新建" onclick="javascript:newInfo();">新建(<u>N</u>)</button>
						&nbsp;&nbsp;&nbsp; <%}%> <%if (input_operator.hasFunc(menu_id, 101))
{%>
						<button type="button"  class="xpbutton3"  accessKey=d name="btnCancel" title="删除" onclick="javascript:if(confirmRemove(document.theform.family_id)) document.theform.submit();">删除(<u>D</u>)</button>
						 <%}%>
						 </div>
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" bgcolor="#000000" width="100%">
					<tr class="trh">
						<td align="center" height="25"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox
						(document.theform.family_id,this);">关联编号</td>
						<td align="center" height="25">关联名称</td>
						<td align="center" height="25">编辑</td>
					</tr>
					<%int iCount = 0;
					int iCurrent = 0;
					Integer family_id;
					
					while (it.hasNext()) {
						map = (Map) it.next();
						family_id = Utility.parseInt(Utility.trimNull(map.get("FAMILY_ID")),new Integer(0));
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" align="center" height="25">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td width="10%"><input type="checkbox" name="family_id" value="<%=family_id%>" class="flatcheckbox"></td>
								<td width="90%" align="left"><%=Utility.trimNull(family_id)%></td>
							</tr>
						</table>
						</td>
						<td align="left" height="25"><%=Utility.trimNull(map.get("FAMILY_NAME"))%></td>
						<td align="center" height="25">
						<%if (input_operator.hasFunc(menu_id, 102)) {%>
							<button type="button"  class="xpbutton2"  name="btnEdit" onclick="javascript:showInfo(<%=family_id%>);">&gt;&gt;</button>
						<%}%>						
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
