<%@ page contentType="text/html; charset=GBK" import="java.math.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
BenChangeLocal local = EJBFactory.getBenChanage();//受益权转让Bean
BenChangeVO vo = new BenChangeVO();

String [] items = request.getParameterValues("selectbox");
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
	sl_remove_ok("beneficial_transfer_list.jsp");
</script>
<%local.remove();%>