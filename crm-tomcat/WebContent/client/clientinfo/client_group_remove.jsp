<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*"%>
<%@ include file="/includes/operator.inc" %>
<%
CustGroupLocal local = EJBFactory.getCustGroup();
CustGroupVO vo = new CustGroupVO();

String [] items = request.getParameterValues("group_id");
int group_id = 0;
if(items != null)
{
	for(int i=0;i<items.length;i++)
	{
		group_id = Utility.parseInt(items[i], 0);
		if(group_id != 0)
		{	
			vo.setGroupId(new Integer(group_id));
			vo.setInputMan(input_operatorCode);
			local.delCustGroup(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("client_group_list.jsp");
</SCRIPT>
<%local.remove();%>