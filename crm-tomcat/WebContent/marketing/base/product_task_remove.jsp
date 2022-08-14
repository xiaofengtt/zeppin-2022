<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
Integer product_id  = new Integer(Utility.parseInt(request.getParameter("product_id"), 0));
String[] s = request.getParameterValues("serial_no");
 
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		 Integer serial_no = Utility.parseInt(s[i], new Integer(0));
		if(serial_no.intValue() != 0)
		{
			vo.setSerial_no(serial_no);
			vo.setInput_man(input_operatorCode);
			product.deleteTask(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
	location =  "product_task_list.jsp?product_id="+<%=product_id%>;
</SCRIPT>
<%product.remove();%>
