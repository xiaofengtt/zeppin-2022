<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取页面变量
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
int flag = Utility.parseInt(request.getParameter("flag"),0);
//设定辅助变量
List list = null;
Map map = new HashMap();
//获得对象
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
//设置查询参数
vo.setProduct_id(product_id);
vo.setSerial_no(null);
vo.setInput_man(input_operatorCode);
//查询
list = product.list_openDate(vo);
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title><%=LocalUtilis.language("menu.topenDateList",clientLocale)%> </title>
<!--开放日信息列表-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>

</script>

</HEAD>

<BODY class="BODY body-nox">
<form name="theform" method="get" action="product_stage_item.jsp">
<input type=hidden name="product_id" value="<%=product_id%>">

<TABLE width="100%" cellSpacing=0 cellPadding=0 border=0>
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0" >
							<tr>
								<td class="page-title"><b><font color="#215dc6"><%=LocalUtilis.language("menu.openDateInfo",clientLocale)%> </font></b></td>
								<!--产品开放日信息-->
							</tr>
						</table>
						<br/>
						<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
							<tr class="trh">
								<td align="center" width="25%"><%=LocalUtilis.language("class.ID",clientLocale)%> </td><!--编号-->
								<td align="center" width="75%"><%=LocalUtilis.language("class.openDate",clientLocale)%> </td><!--产品开放日-->
							</tr>
							<%
							Iterator it = list.iterator();
							int iCount = 0;
							Integer serial_no;
							Integer open_date ;
							while (it.hasNext()){
								iCount++;
								map = (Map)it.next();
								serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
								open_date = Utility.parseInt(Utility.trimNull(map.get("OPEN_DATE")),new Integer(0));
							%>
							<tr class="tr<%=(iCount % 2)%>">
								<td class="tdh" align="center" height="25"><%=serial_no%></td>
								<td align="center" height="25"><%=Format.formatDateCn(open_date)%></td>							
							</tr>
							<%}%>
							<tr class="trbottom">
								<td class="tdh" width="9%" align="center" height="25"><b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=iCount%> <%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>								
								<td width="10%" align="center" height="25">-</td>
							</tr>
						</table>
						<br>
						<table border="0" width="100%">
							<tr>
								<td align="right">
									<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" title="<%=LocalUtilis.language("message.closePage",clientLocale)%> " onclick="javascript:window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>B</u>)</button>
								</td><!--关闭当前页面--><!--关闭-->
							</tr>
						</table>
						</TD>
					</TR>
			</TABLE>
			</TD>
		</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%product.remove();%>