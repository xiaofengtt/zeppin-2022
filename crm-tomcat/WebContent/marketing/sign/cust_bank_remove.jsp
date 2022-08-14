<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));
CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
CustomerInfoVO vo = new CustomerInfoVO();

vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
boolean bSuccess = false;
String[] s = request.getParameterValues("serial_no");
Integer serial_no;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		serial_no = Utility.parseInt(s[i], null);
		if(serial_no != null)
		{
			vo.setSerial_no(serial_no);
			customer.deleteCustBank(vo);
			bSuccess = true;
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
<%
if (bSuccess){
%>
	sl_remove_ok("cust_bank_list.jsp?cust_id=<%=cust_id%>");
<%}%>
</SCRIPT>
<%customer.remove();%>