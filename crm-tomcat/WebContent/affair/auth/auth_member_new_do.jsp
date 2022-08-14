<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.affair.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//获得参数
Integer ca_id = Utility.parseInt(request.getParameter("ca_id"), new Integer(0));
String ca_name = Utility.trimNull(request.getParameter("ca_name"));
Integer auth_flag = Utility.parseInt(request.getParameter("auth_flag"),new Integer(0));
AuthorizationLocal authorizationLocal = EJBFactory.getAuthorizationLocal();
AuthorizationCustsVO authorizationCustsVO = new AuthorizationCustsVO();

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
			authorizationCustsVO.setCa_id(Utility.parseInt(items[0], new Integer(0)));
			authorizationCustsVO.setCust_id(Utility.parseInt(items[1], new Integer(0)));
			authorizationCustsVO.setAuth_flag(auth_flag);
			authorizationCustsVO.setInput_man(input_operatorCode);
			authorizationLocal.append(authorizationCustsVO);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_update_ok();
	location = "auth_member_list.jsp?ca_id="+<%=ca_id%> + "&ca_name=" + '<%=ca_name%>';
	window.returnValue = true;
	window.close();
</SCRIPT>
<%authorizationLocal.remove();%>