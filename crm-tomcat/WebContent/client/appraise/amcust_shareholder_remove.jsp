<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.intrust.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
AmlVO vo =	new  AmlVO();
AmShareHolderLocal local = EJBFactory.getAmShareHolder();
String[] s = request.getParameterValues("serial_nos");
int serial_no;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		serial_no = Utility.parseInt(s[i], 0);
		if(serial_no != 0)
		{
			vo.setSerial_no(new Integer(serial_no));
			vo.setInput_man(input_operatorCode);
			local.delete(vo);
		}
	}
}
%>
<SCRIPT LANGUAGE="javascript">
	alert("²Ù×÷³É¹¦!");
	//location.href="amcust_info.jsp?cust_id="+<%=cust_id%>;
	window.close(); 
</SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>


<%local.remove();%>