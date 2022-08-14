<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.customer.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获得对象及结果集
RatingVO vo = new RatingVO();
SystemValueLocal system_value = EJBFactory.getSystemValue();
String[] s = request.getParameterValues("operand_v_id");
Integer input_man = Utility.parseInt(Utility.trimNull(input_operatorCode),new Integer(0));
int operand_v_id;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		operand_v_id = Utility.parseInt(s[i], 0);
		if(operand_v_id != 0)
		{
			vo.setOperand_v_id(new Integer(operand_v_id));
			vo.setInput_man(input_man);
			system_value.delete_tsystemvalue(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//删除成功
	window.returnValue = 1;
	location =  "system_value.jsp";
</SCRIPT>
<%system_value.remove();%>
