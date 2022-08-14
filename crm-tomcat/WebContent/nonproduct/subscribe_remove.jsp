<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.marketing.*,java.math.*,enfo.crm.customer.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
SubscribeVO vo = new SubscribeVO();
SubscribeLocal subscribe = EJBFactory.getSubscribeLocal();
String[] s = request.getParameterValues("q_subscribeId");
int subscribeId;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		subscribeId = Utility.parseInt(s[i], 0);
		if(subscribeId != 0)
		{
			vo.setSubscribe_id(new Integer(subscribeId));
			subscribe.delete(vo,input_operatorCode);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
	location =  "subscribe.jsp";
</SCRIPT>
<%subscribe.remove();%>
