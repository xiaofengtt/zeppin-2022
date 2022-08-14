<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%


FaqsLocal faqs = EJBFactory.getFaqs();
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String faq_class_no = Utility.trimNull(request.getParameter("faq_class_no"));
Integer support_flag = Utility.parseInt(request.getParameter("support_flag"),new Integer(0));
FaqsVO vo = new FaqsVO();

if(serial_no.intValue() != 0)
{
	vo.setSerial_no(serial_no);
	vo.setInput_man(input_operatorCode);
	faqs.countFaq(vo,support_flag);
}

%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("提交成功 ");
	window.returnValue = 1;
	location =  "wiki_info.jsp?serial_no="+<%=serial_no%>+'&faq_class_no=<%=faq_class_no%>';
</SCRIPT>
<%faqs.remove();%>
