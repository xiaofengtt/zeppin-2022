<%@ page contentType="text/html;charset=GBK" import="java.math.*,java.util.*, enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.intrust.*,enfo.crm.vo.*" %>
<%
CommCreditLocal contract = EJBFactory.getCommCredit();
CommCreditVO vo = new CommCreditVO();
String value = request.getParameter("value");
String channel_type = request.getParameter("channel_type");

String jsonData = contract.parentTreeBySql(channel_type,value); 
request.setCharacterEncoding("GBK");   
response.setCharacterEncoding("GBK");  
response.getWriter().print(jsonData); 
%>