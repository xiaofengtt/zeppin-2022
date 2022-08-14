<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,enfo.crm.web.*,java.math.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//针对合同管理的附件查看
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO(); 
Integer maintain_id=new Integer(0);

String action=request.getParameter("action");
if(action.equals("del")){
	maintain_id=Utility.parseInt(request.getParameter("maintain_id"),new Integer(0));
	String save_name=request.getParameter("save_name");
	Integer attachmentId = Utility.parseInt(request.getParameter("attachmentId"), new Integer(0));
	attachmentVO.setAttachment_id(attachmentId);
	attachmentVO.setInput_man(input_operatorCode);
	attachmentVO.setSave_name(save_name);
	attachmentLocal.deleteById(attachmentVO);
	attachmentLocal.deleteFile(attachmentVO);
}
if(action.equals("add")){	
	DocumentFileToCrmDB file=null;
	file = new DocumentFileToCrmDB(pageContext);
	file.parseRequest();
	maintain_id=Utility.parseInt(file.getParameter("maintain_id"),new Integer(0));
	file.uploadAttchment(new Integer(101054),"TCOMAINTAINDETAIL",maintain_id,"",input_operatorCode);
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("操作成功");
	window.returnValue = 1;
	location =  "tcoContractMaintain_update.jsp?maintain_id="+<%=maintain_id%>;
</SCRIPT>
<%
attachmentLocal.remove();
 %>
