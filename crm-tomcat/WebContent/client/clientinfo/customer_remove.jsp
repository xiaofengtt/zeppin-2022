<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>

<%
CustomerLocal cust_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();
String[] s = request.getParameterValues("cust_id"); 

Integer serial_no;
if (s != null)
{	
	for(int i = 0;i < s.length; i++)
	{	
		serial_no = new Integer(Utility.parseInt(s[i], 0));
		if(serial_no != null)
		{
			cust_vo.setCust_id(serial_no);
			cust_vo.setInput_man(input_operatorCode);	
			cust_local.delete(cust_vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_cancel_ok("client_info_list.jsp");
</SCRIPT>
<%cust_local.remove();%>