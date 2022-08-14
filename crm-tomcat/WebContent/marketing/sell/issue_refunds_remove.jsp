<%@ page contentType="text/html; charset=GBK" import="java.math.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
SqstopContractLocal local = EJBFactory.getSqstopContract();//发行期退款申请Bean
SqstopContractVO vo = new SqstopContractVO();

String [] items = request.getParameterValues("serial_no");
Integer serial_no = new Integer(0);
if(items != null && items.length > 0)
{
	for(int i=0; i<items.length; i++)
	{
		serial_no = Utility.parseInt(Utility.trimNull(items[i]), new Integer(0));
		if(serial_no != null && (!serial_no.equals(new Integer(0))))
		{
			vo.setSerial_no(serial_no);
			vo.setInput_man(input_operatorCode);
			local.delete(vo);
		}
	}
}
%>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<script language="javascript">
	sl_remove_ok("issue_refunds_list.jsp");
</script>
<%local.remove();%>