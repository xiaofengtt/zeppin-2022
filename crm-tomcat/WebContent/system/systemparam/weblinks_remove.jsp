<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>


<%
Integer del_flag = Utility.parseInt(request.getParameter("type_value"),new Integer(0));
DictparamLocal dictparam = EJBFactory.getDictparam();
DictparamVO vo = new DictparamVO();

String[] s = request.getParameterValues("serial_no");
String serial_no;

if (s != null){
	for(int i = 0;i < s.length; i++){
		serial_no = Utility.trimNull(s[i]);
		
		if(serial_no != null){
			vo.setSerial_no(new Integer(serial_no));
			dictparam.delDictparam(vo,input_operatorCode);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("weblinks.jsp");
</SCRIPT>
<%dictparam.remove();%>