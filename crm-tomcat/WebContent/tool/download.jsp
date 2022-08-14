<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ page import="enfo.crm.web.DocumentFileToCrmDB"%>
<%
	//针对会议功能的附件查看
	AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
    AttachmentVO attachmentVO=new AttachmentVO();
    String origin_name="";
	String save_name="";
	String s=request.getParameter("serial_no");
	Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
	Integer table_id= Utility.parseInt(request.getParameter("table_id"), new Integer(0));
	attachmentVO.setDf_serial_no(serial_no);
	attachmentVO.setDf_talbe_id(table_id);//会议是1，活动任务是2
	List attachmentList=null;
	Map attachmentMap=null;
	attachmentList=attachmentLocal.load(attachmentVO);
	if(!attachmentList.isEmpty()){
		//attachmentMap=(Map)attachmentList.get(0);
		attachmentMap=(Map)attachmentList.get(attachmentList.size()-1);
		origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
    	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
	}else{
		origin_name="此会议记录没有上传附件";
	}
/**
	try{
		response.reset();
		out.clear();
		out=pageContext.pushBody();
		mydown.initialize(pageContext);
		mydown.setContentDisposition(null);
		mydown.downloadFile("/file/c:/uploadfiles/20110425-032455-16.txt");
	}catch(Exception e){
		String errors="<li>文件下载失败，可能文件已经不存在了</li>";
	}
	*/
	DocumentFileToCrmDB file=new DocumentFileToCrmDB(pageContext);
	//file.downloadProblemFile2(save_name,origin_name);
	file.downloadZipFile(pageContext, save_name, origin_name);
%>