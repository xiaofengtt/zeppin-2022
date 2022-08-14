<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
ProductVO vo = new ProductVO();
ProductLocal local = EJBFactory.getProduct();
String[] s = request.getParameterValues("serial_no");
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer serial_no = new Integer(0);
if (s != null)
{
	vo.setInput_man(input_operatorCode);
	for(int i = 0;i < s.length; i++)
	{
		serial_no = Utility.parseInt(s[i], new Integer(0));
		if(serial_no.intValue() != 0)
		{
			vo.setSerial_no(serial_no);
			local.delMarketTrench(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
	window.location.href="product_market_trench_set.jsp?productId=<%=productId%>";
</SCRIPT>
<%local.remove();%>
