<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%

String faq_class_no = Utility.trimNull(request.getParameter("faq_class_no"));
FaqsLocal faqs = EJBFactory.getFaqs();
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
FaqsVO vo = new FaqsVO();

//附件相关
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO(); 
AttachmentVO fileVO=new AttachmentVO();

attachmentVO.setDf_serial_no(serial_no);
attachmentVO.setDf_talbe_id(new Integer(56));
attachmentVO.setInput_man(input_operatorCode);
List attachmentList=null;
Map attachmentMap=null;
String attachmentId="";
String origin_name="";
String save_name="";
attachmentList=attachmentLocal.load(attachmentVO);
//删除文件
for(int i=0;i<attachmentList.size();i++){
		attachmentMap=(Map)attachmentList.get(i);
		save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
		fileVO.setSave_name(save_name);
		attachmentLocal.deleteFile(fileVO);	
}

if(serial_no.intValue() != 0)
{
	vo.setSerial_no(serial_no);
	vo.setInput_man(input_operatorCode);
	faqs.delete(vo);
}

%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//删除成功
	window.returnValue = 1;
	location =  "wiki_list.jsp?menu_id=<%=faq_class_no%>";
</SCRIPT>
<%faqs.remove();%>
