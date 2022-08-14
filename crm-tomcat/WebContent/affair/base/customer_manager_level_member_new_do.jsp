<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得参数
Integer tree_id = Utility.parseInt(request.getParameter("tree_id"), new Integer(0));
String level_name = Utility.trimNull(request.getParameter("level_name"));

enfo.crm.affair.CustManagerTreeLocal custManagerTreeLocal = EJBFactory.getCustManagerTreeLocal();
CustManagerTreeMembersVO vo = new CustManagerTreeMembersVO();


String[] s = request.getParameterValues("selectbox"); 

String str = "";
String [] items = new String[3];
if (s != null)
{	
	for(int i = 0;i < s.length; i++)
	{	
		str = Utility.trimNull(s[i]);
		if(str != null)
		{
			items = Utility.splitString(str,"$");
			vo.setTree_id(Utility.parseInt(items[0], new Integer(0)));
			vo.setManagerid(Utility.parseInt(items[1], new Integer(0)));
			vo.setManagername(Utility.trimNull(items[2]));
			vo.setInput_man(input_operatorCode);
			custManagerTreeLocal.appendCustManagerTreeMember(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_update_ok();
	location = "customer_manager_level_member_list.jsp?tree_id="+<%=tree_id%> + "&level_name=" + '<%=level_name%>';
	window.returnValue = true;
	window.close();
</SCRIPT>
<%custManagerTreeLocal.remove();%>