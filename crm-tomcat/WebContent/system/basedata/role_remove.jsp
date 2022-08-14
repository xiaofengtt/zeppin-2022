<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
RoleLocal role = EJBFactory.getRole();
String[] s = request.getParameterValues("role_id");
RoleVO vo = new RoleVO();
int role_id;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		role_id = Utility.parseInt(s[i], 0);
		if(role_id != 0)
		{
			vo.setRole_id(new Integer(role_id));
			role.delete(vo,input_operatorCode);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
	location =  "role.jsp";
</SCRIPT>
<%role.remove();%>
