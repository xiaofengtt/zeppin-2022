<%@ page contentType="text/html;charset=GBK" import="java.math.*,java.util.*, enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.intrust.*,enfo.crm.vo.*" %>
<%
CommCreditLocal contract = EJBFactory.getCommCredit();
CommCreditVO vo = new CommCreditVO();
String value = request.getParameter("value");

String jsonData = contract.listTreeBySql(new Integer(5500),value); 
request.setCharacterEncoding("GBK");   
response.setCharacterEncoding("GBK");  
response.getWriter().print(jsonData); 
%>