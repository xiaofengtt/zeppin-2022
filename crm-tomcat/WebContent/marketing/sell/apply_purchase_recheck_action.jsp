<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
ApplyreachLocal apply = EJBFactory.getApplyreach();
ApplyreachVO vo = new ApplyreachVO();
String[] s = request.getParameterValues("serial_no");
int serial_no;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		serial_no = Utility.parseInt(s[i], 0);
		if(serial_no != 0)
		{	
			vo.setSerial_no(new Integer(serial_no));
			vo.setInput_man(input_operatorCode);
			apply.recheckApplyreachContract(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("<%=LocalUtilis.language("message.recheckOK",clientLocale)%> ");//…Û∫Àª÷∏¥≥…π¶
	location = "apply_purchase_recheck_list.jsp";
</SCRIPT>
<%apply.remove();%>