<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.intrust.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/operator.inc" %>

<%
ChannelLocal local = EJBFactory.getChannel();
ChannelVO vo = new ChannelVO();
String[] s = request.getParameterValues("check_channel_id"); 

Integer channel_id;
if (s != null)
{	
	for(int i = 0;i < s.length; i++)
	{	
		channel_id = new Integer(Utility.parseInt(s[i], 0));
		if(channel_id != null)
		{
			vo.setChannelID(channel_id);
			vo.setInputMan(input_operatorCode);	
			local.del(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_remove_ok("channel_list.jsp");
</SCRIPT>
<%local.remove();%>