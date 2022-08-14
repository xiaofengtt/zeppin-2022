<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
AttachmentTypeLocal attachment_type = EJBFactory.getAttachmentType();
String[] s = request.getParameterValues("attachment_type_id");
AttachmentTypeVO vo = new AttachmentTypeVO();
int attachment_type_id;
if (s != null)
{
	for(int i = 0;i < s.length; i++)
	{
		attachment_type_id = Utility.parseInt(s[i], 0);
		if(attachment_type_id != 0)
		{
			vo.setAttachmentType_id(new Integer(attachment_type_id));
			attachment_type.delete(vo,input_operatorCode);
		}
	}
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ");//É¾³ý³É¹¦
	window.returnValue = 1;
	location =  "attachment_type.jsp";
</SCRIPT>
<%attachment_type.remove();%>
