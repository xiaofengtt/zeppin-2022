<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,enfo.crm.web.*,java.math.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//针对合同管理的附件查看
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO(); 
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
String faq_class_no = Utility.trimNull(request.getParameter("faq_class_no"));
String q_faq_keywords = Utility.trimNull(request.getParameter("q_faq_keywords"));

String action=request.getParameter("action");
if(action.equals("del")){
	String save_name=request.getParameter("save_name");
	Integer attachmentId = Utility.parseInt(request.getParameter("attachmentId"), new Integer(0));
	attachmentVO.setAttachment_id(attachmentId);
	attachmentVO.setInput_man(input_operatorCode);
	attachmentVO.setSave_name(save_name);
	attachmentLocal.deleteById(attachmentVO);
	attachmentLocal.deleteFile(attachmentVO);
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("删除成功");
	window.returnValue = 1;
	location =  "wiki_info_update.jsp?serial_no=<%=serial_no%>&faq_class_no=<%=faq_class_no%>&q_faq_keywords=<%=q_faq_keywords%>";
</SCRIPT>
<%
attachmentLocal.remove();
 %>
