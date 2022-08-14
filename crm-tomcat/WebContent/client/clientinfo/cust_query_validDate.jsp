<%@ page language="java" pageEncoding="GBK" import="enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.vo.*,java.util.*,java.sql.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
//获取页面传递变量
Integer card_valid_date = Utility.parseInt(request.getParameter("card_valid_date"), new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

vo.setCard_valid_date(card_valid_date);
vo.setInput_man(input_operatorCode);

//页面变量
int first_flag = Utility.parseInt(request.getParameter("first_flag"),0);
IPageList pageList = first_flag!=1?customer.queryCardValidDate(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8)): new JdbcPageList();
Iterator it = first_flag!=1? pageList.getRsList().iterator(): new ArrayList().iterator();

sUrl += "&card_valid_date="+card_valid_date+"&input_man="+input_operatorCode;
customer.remove();
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title>证件即将到期客户信息</title>
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet />
<link href="<%=request.getContextPath()%>/includes/jQuery/css/redmond/jquery-ui-1.8.6.custom.css" type=text/css rel=stylesheet />
<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet />
<script type="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type="text/javascript">

window.onload = function() {
		initQueryCondition();
	};

function StartQuery(){
	refreshPage();
}

function refreshPage(){
	syncDatePicker(theform.card_valid_date_picker, theform.card_valid_date);
	location.search = "?page=1&pagesize=" + document.theform.pagesize.value +
		"&cust_name=" + document.theform.cust_name.value +
		"&card_valid_date=" + document.theform.card_valid_date.value;
}

function validateForm() {
	var form = document.theform;
	var card_id= form.card_id;
	if (card_id == null)
		return true;

	if (card_id.length)
		for (var i=0; i<card_id.length; i++) {
			if (form.card_type[i].value=="110801") {
				if (! sl_check(card_id[i],"<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ",18,15)) return false;//证件号码
				if (! (card_id[i].value.length==15 || card_id[i].value.length==18) ) {
					sl_alert("<%=LocalUtilis.language("message.customerCardIDSizeIs15or18",clientLocale)%> ");//身份证号码必须为15或18位
					card_id[i].focus();
					return false;
				}
			}
		}
	else 
		if (form.card_type.value=="110801") {
			if (! sl_check(card_id,"<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ",18,15)) return false;//证件号码
			if (! (card_id.value.length==15 || card_id.value.length==18) ) {
				sl_alert("<%=LocalUtilis.language("message.customerCardIDSizeIs15or18",clientLocale)%> ");//身份证号码必须为15或18位
				card_id.focus();
				return false;
			}
		}
	return true;
}

function getIndex(elem) {
	var elems = document.getElementsByName(elem.cust_name);
	for (var i=0; i<elems.length; i++)
		if (elems[i]==elem) return i;

	return -1;
}
</script>
</head>

<body class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="post" action="cust_query_validDate.jsp?page=<%=sPage%>">

	<div id="queryCondition" class="qcMain" style="display:none;width:280px;">
		<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		  <tr>
		   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
		   <td align="right">
				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
			</td>
		  </tr>
		</table>
	
		<table width="99%" cellspacing="0" cellpadding="2">
			<tr>
				<td align="left">客户名称 :</td>
				<td>
					<input name="cust_name" value="<%=cust_name%>"/>
				</td>
			</tr>
			<tr>
				<td align="left">证件有效到期时间 :</td>
				<td>
					<input name="card_valid_date_picker" value="<%=Format.formatDateLine(card_valid_date)%>"/>
					<input type="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.card_valid_date_picker,theform.card_valid_date_picker.value,this);">
					<input type="hidden" name="card_valid_date" />
				</td>
			</tr>
			<tr>
				<td align="center" colspan=2>
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
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=2><IMG src="<%=request.getContextPath()%>/images/member.gif" align=absBottom border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>						
					</tr>
                   <tr>
					 <td align="right">
						<button type="button"  class="xpbutton3" id="queryButton" name="queryButton" accessKey=q>查询 (<u>Q</u>)</button>
					 </td>
				   </tr>
				   <tr>
					<td colspan=2>
					<hr noshade color="#808080" size="1">
					</td>
				   </tr>
				</table>

				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:15px;table-layout:fixed">	
					<tr class="trh">
						<td align="center" >客户名称</td>
						<td align="center" >证件号码</td>
						<td align="center" >手机</td>
						<td align="center" >证件到期时间</td>
						<td align="center" >联系地址</td>
						<td align="center" >邮编</td>
						<td align="center" >email</td>		
					</tr>
					<%	
						int i = 0;
						Map map=null;
						Integer v_date = new Integer(0);
						while(it.hasNext()){
							map = (Map)it.next();
							v_date = Utility.parseInt(Utility.trimNull(map.get("CARD_VALID_DATE")),new Integer(0));
						
					%>
					<tr class="tr<%=(i % 2)%>" width="100%">		
						<td align="left"><%=Utility.trimNull(map.get("CUST_NAME"))%><input type="hidden" name="cust_id" value="<%=map.get("CUST_ID")%>"/></td>
						<td align="center"><%=Utility.trimNull(map.get("CARD_ID"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("MOBILE"))%></td>
						<td align="center"><%=Format.formatDateLine(v_date)%></td>
						<td align="center"><%=Utility.trimNull(map.get("POST_ADDRESS"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("POST_CODE"))%></td>
						<td align="center"><%=Utility.trimNull(map.get("E_MAIL"))%></td>
					</tr>
		<%
			}
			for(; i < pageList.getPageSize(); i++) {
		%>
					<tr class="tr<%=(i % 2)%>" width="100%">
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
		<%
			}
		%>
					<tr class="trbottom">
						<!--合计--><!--项-->
			            <td class="tdh" align="left" colspan="7"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
				</TD>
			</TR>
		</TABLE>
	</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>