<%@ page language="java" contentType="text/csv; charset=GBK" import="enfo.crm.web.*"%>
<%
DocumentFile file = new DocumentFile(pageContext);
String fileName = "��������Ϣ";
file.exportExcelBeneficInfo(fileName);
%>
