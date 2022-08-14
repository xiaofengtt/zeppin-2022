<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
TcustmanagersLocal local = EJBFactory.getTcustmanagers();
TcustmanagertreeVO vo = new TcustmanagertreeVO();

String[] s = request.getParameterValues("depart_id");
int depart_id;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		depart_id = Utility.parseInt(s[i], 0);
		if(depart_id != 0)
		{	
			vo.setSerial_no(new Integer(depart_id));		
			local.tree_delet(vo,input_operatorCode);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">	
	sl_remove_ok("<%=request.getContextPath()%>/affair/base/manager.jsp");
</SCRIPT>
<%local.remove();%>