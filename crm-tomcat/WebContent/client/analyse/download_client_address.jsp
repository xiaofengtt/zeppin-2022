<%@ page language="java" contentType="text/csv; charset=gbk" import="enfo.crm.web.*"%>
<%
DocumentFile file = new DocumentFile(pageContext);
String fileName = "�ͻ�ͨѶ¼.xls";
//file.exportClientAddressList(fileName);
file.exportCustAddressBook(fileName);
%>

