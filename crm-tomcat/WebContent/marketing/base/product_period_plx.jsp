<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
vo.setProduct_id(product_id);
vo.setTask_type(new Integer(103));
List list = product.queryPeriodDate1(vo);
Map map = new HashMap();
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK HREF="<%=request.getContextPath()%>/includes/default.css" TYPE="text/css" REL="stylesheet">
<BASE TARGET="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<BODY class="body body-nox">
<form name="theform" method="post" action="#">
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=1 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td class="page-title"><font color="#215dc6"><b><%=LocalUtilis.language("message.formatDateLineInfo",clientLocale)%> </b></font>
								</td><!--信息披露时间信息-->
							</tr>
						</table>
						<br/>
						<table border="0" width="100%" cellspacing="1" class="tablelinecolor">
							<tr class="trh">
								<td width="46%" align="left"><%=LocalUtilis.language("class.serialNumber",clientLocale)%> </td><!--序号-->
								<td width="49%"><%=LocalUtilis.language("class.formatDateLine",clientLocale)%> </td><!--信息披露时间-->
							</tr>
							<%int iCount = 1;
							Iterator iterator = list.iterator();
							while (iterator.hasNext()) {
								map = (Map)iterator.next();
							%>
							<tr class="tr0">
								<td><%=iCount%></td>
								<td><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("TASK_DATE")),new Integer(0)))%></td>
							</tr>
							<%iCount++;
}
%>
						</table>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel"
									name="btnCancel"
									onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.confirm",clientLocale)%> </button><!--确定-->
								&nbsp;&nbsp;</td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>
<%product.remove();%>
