<%@ page contentType="text/csv; charset=GBK"%>
<%
enfo.crm.web.DocumentFile2 file = new enfo.crm.web.DocumentFile2(pageContext);
file.downloadExcel_subscribe("subscribe"+enfo.crm.tools.Utility.getCurrentDate());
%>