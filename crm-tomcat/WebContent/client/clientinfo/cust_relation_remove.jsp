<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
CustomerInfoLocal customer = EJBFactory.getCustomerInfo();
CustomerInfoVO vo = new CustomerInfoVO();

vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
String[] s = request.getParameterValues("family_id");
Integer family_id;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		family_id = Utility.parseInt(s[i], null);
		if(family_id != null)
		{
			vo.setFamily_id(family_id);
			customer.deleteRela(vo);
			
			
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("cust_relation_list.jsp");
</SCRIPT>
<%customer.remove();%>