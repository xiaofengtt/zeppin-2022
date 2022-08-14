<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%


FaqsLocal faqs = EJBFactory.getFaqs();
String[] s = request.getParameterValues("serial_no");
FaqsVO vo = new FaqsVO();
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
			faqs.delete(vo);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
	location =  "knowledge_repos_list.jsp";
</SCRIPT>
<%faqs.remove();%>
