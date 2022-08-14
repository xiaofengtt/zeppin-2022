<%@ page contentType="text/html; charset=GBK" import="enfo.crm.dao.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.vo.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%
try{
IntrustCapitalTypeLocal local = EJBFactory.getIntrustCapitalTypeLocal();
String json = local.queryCapitaltypeJosn(null,clientLocale);

response.getWriter().print(json);
}catch(Exception e){
	throw e;
}
%>