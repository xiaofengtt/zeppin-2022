<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
RoleLocal role = EJBFactory.getRole();

Integer role_id = Utility.parseInt(request.getParameter("role_id"),null);

//Integer input_operatorCode = new Integer(0);
RoleVO vo = new RoleVO();
Utility.debug("role_id:"+role_id);
if (role_id.intValue() != 0)
{
	vo.setRole_id(role_id);
	role.delete(vo,input_operatorCode);

}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	window.returnValue = 1;
	location =  "role.jsp";
</SCRIPT>
<%role.remove();%>