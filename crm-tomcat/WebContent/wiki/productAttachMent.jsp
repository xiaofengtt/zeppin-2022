<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,enfo.crm.web.*,java.math.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//针对合同管理的附件查看
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO(); 

//状态参数
Integer product_status1 = Utility.parseInt(request.getParameter("status"),null);
String class1 = Utility.trimNull(request.getParameter("class1"));

//其他参数
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"),new Integer(0));
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));


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
	location =  "product_new.jsp?preproduct_id=<%=preproduct_id%>&product_id=<%=product_id%>&status=<%=product_status1%>&class1=<%=class1%>";
</SCRIPT>
<%
attachmentLocal.remove();
 %>
