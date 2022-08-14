<%@ page language="java" contentType="text/csv; charset=gbk" import="enfo.crm.web.*"%>
<%
DocumentFile file = new DocumentFile(pageContext);
String fileName = "客户通讯录.xls";
//file.exportClientAddressList(fileName);
file.exportCustAddressBook(fileName);
%>

