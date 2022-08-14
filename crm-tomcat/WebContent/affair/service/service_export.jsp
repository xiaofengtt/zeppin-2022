<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
try{
enfo.crm.web.DocumentFile excel = new enfo.crm.web.DocumentFile(pageContext);
excel.exportService(input_operatorCode);
}catch(Exception e){
	e.printStackTrace();
} 
%>

