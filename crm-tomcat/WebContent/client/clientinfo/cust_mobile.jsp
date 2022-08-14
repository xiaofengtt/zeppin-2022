<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<% 
String mobile = Utility.trimNull(request.getParameter("mobile"));

CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

vo.setMobile(mobile);

List list = customer.queryCustByMobile(vo);
								  
Iterator it =  list.iterator();

%>

<HTML>
<HEAD>

<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<TITLE>�ֻ�����һ�Զ�</TITLE>
</HEAD>

<BODY class="BODY">
<FORM name="theform" method="post" action="cust_mobile.jsp">
<DIV>
<table id="table3" border="0" cellspacing="1" cellpadding="2"
					class="tablelinecolor" width="100%" style="margin-top:5px">
	<tr class="trh">
		<td align="center">�ͻ�����</td>
		<td align="center">�ֻ�����</td>
		<td align="center">֤������</td>
		<td align="center">�ͻ���Դ</td>
	</tr>
<%
	while(it.hasNext()){
	Map map=(Map)it.next();
	String cust_name=Utility.trimNull(map.get("CUST_NAME"));
	String mobiles=Utility.trimNull(map.get("MOBILE"));
	String card_id=Utility.trimNull(map.get("CARD_ID"));
	String cust_sourc_name=Utility.trimNull(map.get("CUST_SOURCE_NAME"));
	
 %>
	<tr class="trbottom">
		<td align="center"><%=cust_name%></td>
		<td align="center"><%=mobiles%></td>
		<td align="center"><%=card_id %></td>
		<td align="center"><%=cust_sourc_name %></td>
	</tr>
<%} %>
</table>
</DIV>
<BR>

	
</FORM>
<div align="right" style="margin-right:5px">
	<!--�ر�-->
    <BUTTON class="xpbutton3" accesskey="b" id="btnReturn" name="btnReturn" 
		onclick="javascript:disableAllBtn(true);window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<U>B</U>)</BUTTON>
</div>
</BODY>
</HTML>
