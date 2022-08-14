<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.customer.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取页面传递参数
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
Integer subject_id = Utility.parseInt(request.getParameter("subject_id"), new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));


//获得对象及结果集
SystemValueLocal systemLocal = EJBFactory.getSystemValue();
RatingVO rating_vo = new RatingVO();
rating_vo.setCust_id(cust_id);
rating_vo.setInput_man(input_operatorCode);
List list = systemLocal.queryListBySqlDetail(rating_vo);

%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.customerRating",clientLocale)%> </TITLE><!--客户评级-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK HREF="/includes/default.css" TYPE="text/css" REL="stylesheet">
<BASE TARGET="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

<SCRIPT LANGUAGE="javascript">

</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="cust_rating_new.jsp" onsubmit="javascript:return validateForm(this);">
<TABLE cellSpacing=0 cellPadding=1 width="100%">
	<TBODY>
		<TR>
			<TD>
			<br><br>
			<b><font size="4"><%=Utility.trimNull(cust_name)%> 明细</font></b>
			<table align="center" id="table3"  border=0 width="90%" class="tablelinecolor" cellspacing="1" cellpadding="2">
				
				<tr class="tr1">
					<td align="center"><b>产品编号</b></td>
					<td align="center"><b>产品名称</b></td>
					<td align="center"><b>合同编号</b></td>
					<td align="center"><b>受益份额</b></td>
					<td align="center"><b>受益起始日</b></td>
					<td align="center"><b>认购金额</b></td>
					<td align="center"><b>购买日期</b></td>
				</tr>
				<%for(int i=0;i<list.size();i++){ 
					Map map = (Map)list.get(i);
				%>
				<tr class="tr1">
					<td align="left">&nbsp;<%=Utility.trimNull(map.get("PRODUCT_CODE"))%></td>
					<td align="left">&nbsp;<%=Utility.trimNull(map.get("PRODUCT_NAME"))%></td>
					<td align="center"><%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%></td>
					<td align="right"><%=Format.formatMoneyD(Utility.trimNull(map.get("BEN_AMOUNT"))) %>&nbsp;</td>
					<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("BEN_DATE")),new Integer(0)))%></td>
					<td align="right"><%=Format.formatMoneyD(Utility.trimNull(map.get("RG_MONEY"))) %>&nbsp;</td>
					<td align="center"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)))%></td>
				</tr>
				<%} %>
			</table>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>

</HTML>
<%systemLocal.remove(); %>
