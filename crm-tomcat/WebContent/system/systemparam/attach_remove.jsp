<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ page import="enfo.crm.intrust.*,java.io.*" %>
<%@ include file="/includes/operator.inc" %>
<%
AttachmentLocal attachment = EJBFactory.getAttachment();

String serial_no = Utility.trimNull(request.getParameter("serial_no"));
String [] values=Utility.splitString(serial_no,"$");
String capital_no= values[0];
String file_name = values[1];
File f = new File(file_name);

AttachmentVO vo = new AttachmentVO();
vo.setAttachment_id(Utility.parseInt(capital_no,new Integer(0)));
vo.setInput_man(input_operatorCode);
attachment.delete(vo); 

if(f.exists())
	f.delete();

attachment.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	window.returnValue = true;
	window.close();
</SCRIPT>