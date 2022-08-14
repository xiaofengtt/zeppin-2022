<%@ page contentType="text/csv; charset=GBK" %>
<jsp:directive.page import="enfo.crm.tools.Utility"/>
<%
enfo.crm.web.DocumentFile2 file = new enfo.crm.web.DocumentFile2(pageContext);
file.downloadExcel_presell("presell"+Utility.getCurrentDate());
%>