<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.customer.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
GradeTotalLocal local = EJBFactory.getGradeTotal();
GradeTotalVO vo = new GradeTotalVO();

String[] s = request.getParameterValues("serial_no");
Integer serial_no;
if (s != null) {
	for (int i = 0; i < s.length; i++) {
		serial_no = Utility.parseInt(s[i], new Integer(0));
		if (!serial_no.equals(new Integer(0))) {
			vo.setSerial_no(serial_no);
			vo.setInput_man(input_operatorCode);
			local.delGradeTotal(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> £¡");//É¾³ý³É¹¦
	window.returnValue = 1;
	location =  "client_mark_grade_list.jsp";
</SCRIPT>
<%local.remove();%>