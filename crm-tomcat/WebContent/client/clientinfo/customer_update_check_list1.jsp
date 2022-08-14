<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得参数
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
Integer check_flag = Utility.parseInt(Utility.trimNull(request.getParameter("check_flag")),new Integer(0));
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

//页面变量
Map map = null;
IPageList pageList =
	customer.listCustUpdateProcPage(vo,new Integer(1),Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();

String tempUrl = "&cust_id=" + cust_id + "&cust_no=" + cust_no + "&cust_name=" + cust_name 
			+"&card_id="+card_id+"&card_type=" +card_type+"&cust_type="+cust_type
			+"&service_man="+ service_man;
//url设置
sUrl = "customer_update_check_list1.jsp?pagesize="+sPagesize + tempUrl;

if(check_flag.intValue()!=0){
	customer.checkCustMsg(cust_id,check_flag,input_operatorCode);
	temp=true;
}
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
	if(<%=temp%>){
		location = "customer_update_check.jsp";
	}
};
function check(check_flag,cust_id){
	location = "customer_update_check_list1.jsp?cust_id="+cust_id+"&check_flag="+check_flag;
}
function refreshPage()
{
	disableAllBtn(true);
	var url = 'customer_update_check_list1.jsp?cust_id=<%=cust_id%>&page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 

	location = url;				
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="customer_remove.jsp">
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
						<td colspan=2>
						<hr noshade color="#808080" size="1">
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
						<td><%if (map.get("OLD_FIELD_INFO")==null) {
								out.print("");
							  } else if ("行业".equals(map.get("FIELD_CN_NAME")) || "机构类型".equals(map.get("FIELD_CN_NAME"))) {
								out.print(Argument.getDictParamValue((String)map.get("OLD_FIELD_INFO")));
							  } else if ("委托人类型2".equals(map.get("FIELD_CN_NAME")) ) {
								out.print(Argument.getJgWtrlx2((String)map.get("OLD_FIELD_INFO")));
							  } else {
								out.print(map.get("OLD_FIELD_INFO"));
							  }
							%></td>
						<td><%if (map.get("NEW_FIELD_INFO")==null) {
								out.print("");
							  } else if ("行业".equals(map.get("FIELD_CN_NAME")) || "机构类型".equals(map.get("FIELD_CN_NAME")) ) {
								out.print(Argument.getDictParamValue((String)map.get("NEW_FIELD_INFO")));
							  } else if ("委托人类型2".equals(map.get("FIELD_CN_NAME")) ) {
								out.print(Argument.getJgWtrlx2((String)map.get("NEW_FIELD_INFO")));
							  } else {
								out.print(map.get("NEW_FIELD_INFO"));
							  }
							%></td>
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
<div align="right" style="margin-right:5px">
    <button type="button"  class="xpbutton3" accesskey="s" id="btnSave" name="btnSave" onclick="javascript:if(sl_confirm('审核通过')){check(2,<%=cust_id%>);}">审核通过</BUTTON>
	&nbsp;&nbsp;
    <button type="button"  class="xpbutton3" accesskey="b" id="btnReturn" name="btnReturn" onclick="javascript:if(sl_confirm('审核不通过')){check(3,<%=cust_id%>);}">审核不通过</BUTTON>
	&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" accessKey=b id="btnBack" name="btnBack" onclick="javascript:location='customer_update_check.jsp'" >返回(<u>B</u>)</button>&nbsp;&nbsp;&nbsp;
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%customer.remove();%>