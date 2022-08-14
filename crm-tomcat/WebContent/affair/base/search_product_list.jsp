<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*,enfo.crm.affair.*"%>
<%
request.setCharacterEncoding("UTF-8");
Integer flag = Utility.parseInt(request.getParameter("flag"),new Integer(0));
Integer input_operatorCode = Utility.parseInt(request.getParameter("input_operatorCode"),new Integer(0));
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));  
String inputSearch = request.getParameter("inputSearch");
String data = "";
if(flag.intValue()==0){
	data = Argument.getProductListOptionsCRM(new Integer(1),"",new Integer(102),input_operatorCode,inputSearch,"",productId);
}else{
	data = Argument.getProductListOptionsCRM(new Integer(1),"",new Integer(102),input_operatorCode,"",inputSearch,productId);
}
out.println(data);
%>
