<%@ page language="java" contentType="text/csv; charset=GBK" import="enfo.crm.web.*"%>
<%
DocumentFile file = new DocumentFile(pageContext);
String fileName = "代理销售更新";
file.exportPurchaseImportChange(fileName);
%>