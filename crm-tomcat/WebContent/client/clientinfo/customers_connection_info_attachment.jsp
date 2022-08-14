<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,enfo.crm.contractManage.*,enfo.crm.web.*,java.math.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//针对合同管理的附件查看
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO(); 

//其他参数
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));

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

<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<base target="_self">
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

</head>
<body>
	<a id="go-link"></a>
</body>
</html>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	sl_alert("删除成功");
	window.returnValue = 1;
	var url =  'customers_connection_info_update.jsp?serial_no=<%=serial_no%>&cust_name=<%=cust_name%>';
	document.getElementById("go-link").href = url;
	document.getElementById("go-link").click();	
</SCRIPT>
<%
attachmentLocal.remove();
 %>
