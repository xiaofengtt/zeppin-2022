<%@ page contentType="text/csv; charset=GBK"  %>
<%
enfo.crm.web.DocumentFile file = new enfo.crm.web.DocumentFile(pageContext);
String filename="C:\\�ͻ��ֻ��б�.txt";
file.downloadMobile(filename);
%>