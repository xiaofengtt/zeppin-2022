<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得参数
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
Integer check_flag = new Integer(1);
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String card_type = Utility.trimNull(request.getParameter("card_type"));
Integer cust_type = Utility.parseInt(Utility.trimNull(request.getParameter("cust_type")),new Integer(0));
Integer service_man = Utility.parseInt(Utility.trimNull(request.getParameter("service_man")),new Integer(0));
boolean temp =false;


//获得对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();
//设置参数
vo.setCust_no(cust_no);
vo.setCust_id(cust_id);
vo.setCust_name(cust_name);
vo.setInput_man(input_operatorCode);
vo.setCard_id(card_id);
vo.setCust_type(cust_type);
vo.setCard_type(card_type);
vo.setService_man(service_man);
vo.setChange_check_flag(new Integer(1));

//页面变量
Map map = null;
IPageList pageList =
	customer.listCustUpdateProcPage(vo,check_flag,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();
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
<script language=javascript>
window.onload = function(){
	initQueryCondition();
};

/**客户信息编辑**/
function showInfo(cust_id)
{	
	location = 'customer_update_check_list1.jsp?cust_id=' + cust_id;

	
}
function StartQuery()
{
	refreshPage();
}

function refreshPage()
{
	disableAllBtn(true);
	var url = 'customer_update_check.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
				+'&cust_name=' + document.theform.cust_name.value 
				+ "&card_id=" + document.theform.card_id.value
				+ "&cust_type=" + document.theform.cust_type.value;
	location = url;				
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post">
<div id="queryCondition" class="qcMain"
	style="display: none; width: 475px">
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
		<td valign="bottom" align="right">客户类别 :</td><!--客户类别-->
		<td valign="bottom" align="left">
			<select name="cust_type" id="cust_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
				<OPTION value="0">全部</OPTION>
				<OPTION value="1">个人</OPTION>
				<OPTION value="2">机构</OPTION>
			</select>
		</td>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td valign="bottom" align="left">
			<input name="cust_name" value='<%=cust_name%>' onkeydown="javascript:nextKeyPress(this)" size="18" maxlength="100">
		</td>
	</tr>
	<tr>
		<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td valign="bottom" align="left">
			<input name="card_id" value='<%=card_id%>' onkeydown="javascript:nextKeyPress(this)" size="18">
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
						<td colspan=2><img src="<%=request.getContextPath()%>/images/member.gif"  border=0 width="32" height="28"><font color="#215dc6"><b><%=menu_info%></b></font></td>
					</tr>
					<tr>
						<td align="right">		
							<button type="button"  class="xpbutton3" accessKey=q id="queryButton"
								name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!-- 查询 -->
						</td>
					</tr>
					<tr>
						<td colspan=2>
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center"><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
						<td align="center"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--证件号码-->
						<td align="center"><%=LocalUtilis.language("class.customerType",clientLocale)%> </td><!--客户类别-->
						<td align="center"><%=LocalUtilis.language("class.customerSource",clientLocale)%></td><!--客户来源-->				
						<td align="center">审核</td><!--审核-->
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						cust_id = new Integer(0);
						String is_deal_name = "";
						Integer is_deal2;
						int modi_flag = 0;
						while (it.hasNext()) {
							map = (Map) it.next();
							is_deal2 = Utility.parseInt(Utility.trimNull(map.get("IS_DEAL")),new Integer(0));
							is_deal_name = Argument.getWTName(is_deal2);
							int auth_flag = Utility.parseInt(Utility.trimNull(map.get("AUTH_FLAG")),0);
							if(auth_flag!=2){
								modi_flag = Utility.parseInt(Utility.trimNull(map.get("MODI_FLAG")),0);
							}else{
								modi_flag = -1;
							}	
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center" width="300px"><input type="text" class="ednone"  style="width:100%" value="<%=Utility.trimNull(map.get("CUST_NAME"))%>" readonly></td>
						<td align="center"><input type="text" class="ednone" style="direction:rtl" style="width:120px" value="<%=Utility.trimNull(map.get("CARD_ID"))%>" readonly></td>
						<td align=center><%=Utility.trimNull(map.get("CUST_TYPE_NAME"))%></td>
						<td align="left"><%=Utility.trimNull(map.get("CUST_SOURCE_NAME"))%></td>					
						<td align="center">
							<a href="javascript:#" onclick="javascript:showInfo(<%=map.get("CUST_ID")%>);return false; ">
								<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='<%=LocalUtilis.language("message.edit",clientLocale)%> ' /><!--审核-->
							</a>
						</td>
					</tr>
					<%iCurrent++;
					iCount++;
					}%>

					<%for (; iCurrent < pageList.getPageSize(); iCurrent++) {
					%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<%}
					%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="13"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
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
		</TD>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%customer.remove();%>