<%@ page contentType="text/html; charset=GBK" import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,java.util.*," %>
<%@ include file="/includes/operator.inc" %>
<%//ÉùÃ÷¶ÔÏó
Integer begin_date = new Integer(Utility.getCurrentDate());
Integer end_date = new Integer(Utility.getCurrentDate());
Integer op_code = new Integer(0);
MenuInfoLocal menu = EJBFactory.getMenuInfo();
String login_name = Utility.trimNull(request.getParameter("login_name"));
menu_id = Utility.trimNull(request.getParameter("menu_id"));
op_code = Utility.parseInt(Utility.trimNull(request.getParameter("OP_CODE")),new Integer(0));
List list =  menu.listMenuRight(new Integer(888),menu_id,"",new Integer(3));
System.out.println("list.zise="+list.size());
JsonUtil.get3LevelMenuJson(list);
String json =JsonUtil.get3LevelMenuJson(list);

%>
<%=json%>