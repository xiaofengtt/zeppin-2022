<%@ page contentType="text/html; charset=UTF-8"  %>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=UTF-8">

<TITLE>customerInfo_save.jsp</TITLE>
</HEAD>
<BODY>
<% 
String customer_birthday_picker = request.getParameter("customer_birthday_picker");//出生日期
String customer_sex_name = request.getParameter("customer_sex_name");//性别
//String nationality = request.getParameter("nationality");
 String customer_service_man = new String (request.getParameter("customer_service_man").getBytes("gbk"),"utf-8");//客户经理
 String customer_cust_type_name = request.getParameter("customer_cust_type_name");//客户类别
// String customer_post_address = request.getParameter("customer_post_address");//地址
try{
customer_sex_name = new String(customer_sex_name.getBytes("gbk"),"UTF-8");
//nationality = new String(nationality.getBytes("gbk"),"UTF-8");
//customer_service_man = new String(customer_service_man.getBytes("gbk"),"UTF-8");
//customer_service_man = customer_service_man.replaceAll("杀","");
customer_cust_type_name = new String(customer_cust_type_name.getBytes("gbk"),"UTF-8");
//customer_post_address = new String(customer_post_address.getBytes("gbk"),"UTF-8");
//customer_post_address = customer_post_address.replaceAll("猫","");
}catch(Exception e){
e.printStackTrace();
}
session.setAttribute("customer_birthday_picker",customer_birthday_picker);
session.setAttribute("customer_sex_name",customer_sex_name);
//session.setAttribute("nationality",nationality);
//session.setAttribute("customer_service_man",customer_service_man);
session.setAttribute("customer_cust_type_name",customer_cust_type_name);
//session.setAttribute("customer_post_address",customer_post_address);
%>

</BODY>
</HTML>
