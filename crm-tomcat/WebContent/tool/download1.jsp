<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ page import="enfo.crm.web.DocumentFileToCrmDB"%>
<%
	//针对合同管理的附件查看
	AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
    AttachmentVO attachmentVO=new AttachmentVO(); 
    String origin_name="";
	String save_name="";
	Integer attachmentId = Utility.parseInt(request.getParameter("attachmentId"), new Integer(0));
	attachmentVO.setAttachment_id(attachmentId);
	List attachmentList=null;
	Map attachmentMap=null;
	attachmentList=attachmentLocal.loadById(attachmentVO);
	if(!attachmentList.isEmpty()){
		attachmentMap=(Map)attachmentList.get(0);
		origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
    	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
	}
	DocumentFileToCrmDB documentFile=new DocumentFileToCrmDB(pageContext);
	documentFile.downloadZipFile(pageContext, save_name, origin_name);
%>