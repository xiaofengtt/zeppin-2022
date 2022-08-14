<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,java.io.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
AttachmentToCrmLocal attachment = EJBFactory.getAttachmentToCrm();
AttachmentVO vo = new AttachmentVO();
String serial_no = Utility.trimNull(request.getParameter("serial_no"));
String [] values=Utility.splitString(serial_no,"$");
String capital_no= values[0];
String file_name = values[1];
File f = new File(file_name);

vo.setAttachment_id(Utility.parseInt(capital_no,new Integer(0)));
vo.setInput_man(input_operatorCode);
attachment.deleteById(vo); 

if(f.exists()) f.delete();

attachment.remove();
%>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	window.close();
</SCRIPT>