<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>

<%
Integer group_id = Utility.parseInt(request.getParameter("group_id"), new Integer(0));
String group_name = Utility.trimNull(request.getParameter("group_name"));

CustGroupMemberLocal local = EJBFactory.getCustGroupMember();
CustGroupMemberVO vo = new CustGroupMemberVO();

String[] s = request.getParameterValues("selectbox"); 
String str = "";
String [] items = new String[2];
if (s != null)
{	
	for(int i = 0;i < s.length; i++)
	{	
		str = Utility.trimNull(s[i]);
		if(str != null)
		{
			items = Utility.splitString(str,"$");
			vo.setGroup_id(Utility.parseInt(items[0], new Integer(0)));
			vo.setCust_id(Utility.parseInt(items[1], new Integer(0)));
			vo.setInsertMan(input_operatorCode);
			local.delCustGroupMember(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("client_group_member_list.jsp?group_id="+<%=group_id%> + "&group_name="+'<%=group_name%>');
</SCRIPT>
<%local.remove();%>