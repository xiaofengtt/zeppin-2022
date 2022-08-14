<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得参数
Integer group_id = Utility.parseInt(request.getParameter("groupId"), new Integer(0));
String group_name = Utility.trimNull(request.getParameter("group_name"));

CustGroupMemberLocal local = EJBFactory.getCustGroupMember();
CustGroupMemberVO vo = new CustGroupMemberVO();

String[] s = request.getParameterValues("selectbox"); 

Integer cust_id = new Integer(0);
if (s != null)
{	
	for(int i = 0;i < s.length; i++)
	{	
		cust_id = Utility.parseInt(s[i], new Integer(0));
		
		if(cust_id.intValue() != 0)
		{
			vo.setGroup_id(Utility.parseInt(group_id, new Integer(0)));
			vo.setCust_id(Utility.parseInt(cust_id, new Integer(0)));
			vo.setInsertMan(input_operatorCode);
			local.appendCustGroupMember(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_update_ok();
	location = "client_group_member_list.jsp?group_id="+<%=group_id%> + "&group_name=" + '<%=group_name%>';

</SCRIPT>
<%local.remove();%>