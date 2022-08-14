<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<!--Ë¢ĞÂ-->
<%
DepartmentLocal department = EJBFactory.getDepartment();
DepartmentVO vo = new DepartmentVO();
String[] s = request.getParameterValues("depart_id");
int depart_id;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		depart_id = Utility.parseInt(s[i], 0);
		if(depart_id != 0)
		{	
			vo.setDepart_id(new Integer(depart_id));
			try {
				department.delet(vo,input_operatorCode);
			} catch (Exception e) {
				department.remove();
				String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //±£´æÊ§°Ü
				out.println("<script type=\"text/javascript\">alert('"+message+","+e.getMessage()+"');</script>");
				return;
			}
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">	
	sl_remove_ok("department.jsp");
</SCRIPT>
<%department.remove();%>
