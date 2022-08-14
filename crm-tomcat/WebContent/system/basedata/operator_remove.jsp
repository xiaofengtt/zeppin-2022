<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
OperatorLocal local = EJBFactory.getOperator();
String[] s = request.getParameterValues("op_code");
TOperatorVO vo = new TOperatorVO();
int op_code;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		op_code = Utility.parseInt(s[i], 0);
		if(op_code != 0)
		{	
			vo.setOp_code(new Integer(op_code));
			local.delete(vo,input_operatorCode);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.cancellationOk",clientLocale)%> £¡");//×¢Ïú³É¹¦
	window.returnValue = 1;
	location =  "operator.jsp";
</SCRIPT>
<%local.remove();%>
