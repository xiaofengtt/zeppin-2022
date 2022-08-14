<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
try{

	out.clear();
	out = pageContext.pushBody();
request.setCharacterEncoding("utf-8");
DocumentFile2 excel = new DocumentFile2(pageContext);
excel.exportProductTemp(input_bookCode, input_operatorCode);
}catch(Exception e){
	e.printStackTrace();
} 
%>
