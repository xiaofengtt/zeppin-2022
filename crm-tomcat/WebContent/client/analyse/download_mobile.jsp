<%@ page contentType="text/csv; charset=GBK"  %>
<%
enfo.crm.web.DocumentFile file = new enfo.crm.web.DocumentFile(pageContext);
String filename="C:\\客户手机列表.txt";
file.downloadMobile(filename);
%>