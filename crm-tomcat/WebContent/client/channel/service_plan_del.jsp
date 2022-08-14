<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.intrust.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>

<%
ChannelLocal local = EJBFactory.getChannel();
ChannelVO vo = new ChannelVO();
String[] s = request.getParameterValues("q_serial_no"); 

Integer serial_no;
if (s != null)
{	
	for(int i = 0;i < s.length; i++)
	{	
		serial_no = new Integer(Utility.parseInt(s[i], 0));
		if(serial_no != null)
		{
			vo.setSerial_no(serial_no);
			vo.setInputMan(input_operatorCode);	
			local.delServicePlans(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("service_plans.jsp");
</SCRIPT>
<%local.remove();%>