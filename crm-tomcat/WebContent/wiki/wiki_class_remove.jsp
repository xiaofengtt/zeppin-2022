<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
String faq_class_no = Utility.trimNull(request.getParameter("faq_class_no"));

FaqsLocal faqs = EJBFactory.getFaqs();
FaqsVO vo = new FaqsVO();

if(!"".equals(faq_class_no))
{
	vo.setFaq_class_no(faq_class_no);
	vo.setInput_man(input_operatorCode);
	faqs.deleteClass(vo);
}
%>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
</SCRIPT>
<%faqs.remove();%>
