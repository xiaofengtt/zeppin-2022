<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得参数
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String card_type = Utility.trimNull(request.getParameter("card_type"));
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("cust_type")),new Integer(0));
Integer service_man = Utility.parseInt(Utility.trimNull(request.getParameter("service_man")),new Integer(0));

Integer start_date = Utility.parseInt(Utility.trimNull(request.getParameter("start_date")),new Integer(0));
Integer end_date = Utility.parseInt(Utility.trimNull(request.getParameter("end_date")),new Integer(0));

//获得对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
//设置参数
vo.setCust_no(cust_no);
vo.setCust_name(cust_name);
vo.setInput_man(input_operatorCode);
vo.setCard_id(card_id);
vo.setCust_type(cust_type);
vo.setCard_type(card_type);
vo.setService_man(service_man);
vo.setStart_date(start_date);
vo.setEnd_date(end_date);

//页面变量
Map map = null;
IPageList pageList =
	customer.listCustUpdateProcPage(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();

String tempUrl = "&cust_no=" + cust_no + "&cust_name=" + cust_name 
			+"&card_id="+card_id+"&card_type=" +card_type+"&cust_type="+cust_type
			+"&service_man="+ service_man;
//url设置
sUrl = sUrl + tempUrl;
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
	initQueryCondition();
};

function StartQuery()
{
	refreshPage();
}

function refreshPage()
{
	disableAllBtn(true);	
	syncDatePicker(theform.start_date_picker, theform.start_date);
	syncDatePicker(theform.end_date_picker, theform.end_date);
	var url = 'customer_update_check_list.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
				+'&cust_no=' + document.theform.cust_no.value 
				+ '&cust_name=' + document.theform.cust_name.value
				+ '&card_id=' + document.theform.card_id.value
				+ '&card_type=' + document.theform.card_type.value
				+ "&cust_type=" + document.theform.cust_type.value
				+ "&service_man=" + document.theform.service_man.value
				+ "&start_date=" + document.theform.start_date.value
				+ "&end_date=" + document.theform.end_date.value;
	location = url;				
}

</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="customer_remove.jsp">
<div id="queryCondition" class="qcMain" style="display: none; width: 520px">
<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
	<tr>
		<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		<td align="right">
		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"	onclick="javascript:cancelQuery();"></button>
		</td>
	</tr>
</table>
<table width="99%" cellspacing="0" cellpadding="2">

	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
		<td valign="bottom" align="left">
			<input name="cust_no" value='<%=cust_no%>' onkeydown="javascript:nextKeyPress(this)" size="25">
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td valign="bottom" align="left">
			<input name="cust_name" value='<%=cust_name%>' onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="100">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</td><!-- 证件类别 -->
		<td>
			<select size="1"  name="card_type" style="width: 150px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(1108,card_type)%>
			</select>
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td valign="bottom" align="left">
			<input name="card_id" value='<%=card_id%>' onkeydown="javascript:nextKeyPress(this)" size="25">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.custType",clientLocale)%> :</td><!-- 客户类别 -->
		<td>
			<select size="1"  name="cust_type" style="width: 150px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getCustTypeOptions(cust_type)%>
			</select>
		</td>
		<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!-- 客户经理 -->
		<td>
			<select size="1"  name="service_man" style="width: 150px" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getOperatorOptions(service_man)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">开始时间 :</td>
		<td>
			<input type="text" name="start_date_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(start_date)%>">
			<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);">
			<input type="hidden" name="start_date" value="">
		</td>
		<td align="right">结束时间 :</td>
		<td>
			<input type="text" name="end_date_picker" class=selecttext onkeydown="javascript:nextKeyPress(this)" value="<%=Format.formatDateLine(end_date)%>">
			<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);">
			<input type="hidden" name="end_date" value="">
		</td>
	</tr>
	<tr>
		<td align="center" colspan=4>
		<button type="button"  class="xpbutton3" name="btnQuery" accessKey=o
			onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button><!--确定-->
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
				<table border="0" width="100%" cellspacing="1" cellpadding="1">
					<tr>
						<td colspan=2 class="page-title"><b><%=menu_info%></b></font></td>
					</tr>
					<tr>
						<td align="right">
						<div class="btn-wrapper">		
							<button type="button"  class="xpbutton3" accessKey=q id="queryButton"
								name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!-- 查询 -->
						</div>
						</td>
					</tr>
				</table>

				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center"><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--编号-->
						<td align="center"><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
						<td align="center">字段名</td><!--字段名-->
						<td align="center">修改前</td><!--修改前-->
						<td align="center">修改后</td><!--修改后-->	
						<td align="center">修改人</td><!--修改人-->			
						<td align="center">修改时间</td><!--修改时间-->
					</tr>
<%
int iCurrent = 0;
for(int i=0;i<list.size();i++,iCurrent++){ 
	map = (Map)list.get(i);
	Integer input_time = Utility.parseInt(Utility.trimNull(map.get("INPUT_TIME")),new Integer(0));
%>
					<tr class="tr<%=(i % 2)%>">
						<td align="center"><%=Utility.trimNull(map.get("CUST_NO")) %></td>
						<td><%=Utility.trimNull(map.get("CUST_NAME")) %></td>
						<td><%=Utility.trimNull(map.get("FIELD_CN_NAME")) %></td>
						<td><%=Utility.trimNull(map.get("OLD_FIELD_INFO")) %></td>
						<td><%=Utility.trimNull(map.get("NEW_FIELD_INFO")) %></td>
						<td align="center"><%=Utility.trimNull(map.get("OP_NAME")) %></td>
						<td align="center"><%=Format.formatDateLine(input_time) %></td>
					</tr>
<%} %>
					<%for (; iCurrent < pageList.getPageSize(); iCurrent++) {%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>	
						<td align="center"></td>		
					</tr>
					<%}%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="13"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
					</tr>
				</table>
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
<%customer.remove();%>