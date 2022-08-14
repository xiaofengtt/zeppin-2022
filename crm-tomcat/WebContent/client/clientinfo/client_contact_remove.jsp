<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
Integer contactType = Utility.parseInt(request.getParameter("contactType"), new Integer(0));
CustomerContactLocal local = EJBFactory.getCustomerContact(); 
CustomerContactVO vo = new CustomerContactVO();
String[] s = request.getParameterValues("selectbox"); 

Integer contactid;
if (s != null)
{	
	for(int i = 0;i < s.length; i++)
	{	
		contactid = new Integer(Utility.parseInt(s[i], 0));
		if(contactid != null)
		{
			vo.setContactId(contactid);
			vo.setInsertMan(input_operatorCode);	
			local.delCustomerContact(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("client_contacts_list.jsp?contactType=<%=contactType%>&cust_id=<%=cust_id%>&iQuery=$$0$0$$$8$0$0$0$7290$0$0$0$0$$");
</SCRIPT>
<%local.remove();%>