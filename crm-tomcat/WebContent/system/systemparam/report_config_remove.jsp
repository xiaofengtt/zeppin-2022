<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();
String flag = request.getParameter("flag");
String[] value = null;

Utility.debug( flag);
if("del".equals(flag)){
	Integer type_id = Utility.parseInt(request.getParameter("type_id"),new Integer(0));
	String type_value = Utility.trimNull(request.getParameter("type_value"));
	Utility.debug(type_value);
	if (type_id.intValue()>0 && type_value != null)
	{
		vo.setType_id(type_id);
		vo.setType_value(type_value);
		dictparam.delReportparam(vo,input_operatorCode);
	
	}
}
else{
	String[] s = request.getParameterValues("serial_no");

	if (s != null)
	{
		for(int i = 0;i < s.length; i++)
		{
			if(!s[i].equals("")){
				value=Utility.splitString(s[i],"&");
				vo.setType_id(Utility.parseInt(value[0],new Integer(0)));
				vo.setType_value(value[1]);
				dictparam.delReportparam(vo,input_operatorCode);
			}
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("report_config.jsp");
</SCRIPT>
<%dictparam.remove();%>