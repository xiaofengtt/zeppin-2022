<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%sPage = request.getParameter("page");
sPagesize = request.getParameter("pagesize");

Integer role_id = Utility.parseInt(request.getParameter("role_id"), new Integer(0));
String menu_id2 = Utility.trimNull(request.getParameter("menu_id"));
String menu_name2 = Utility.trimNull(request.getParameter("menu_name"));
String menu_id3=Utility.trimNull(request.getParameter("menu_id2"));
Integer flag = Utility.parseInt(request.getParameter("flag"), new Integer(0));
//RolerightLocal role = EJBFactory.getRolering();
RoleLocal role = EJBFactory.getRole();
RoleVO vo = new RoleVO();
vo.setRole_id(role_id);
vo.setInput_man(input_operatorCode);
role.updateRights(vo,menu_id2,flag.intValue(),menu_name2);
role.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	location.replace("role_priority_info.jsp?page=<%=sPage%>&pagesize=<%=sPagesize%>&role_id=<%=role_id%>&menu_id=<%=menu_id3%>&menu_name=<%=menu_name2%>");
</SCRIPT>