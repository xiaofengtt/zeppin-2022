<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.intrust.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.affair.*,enfo.crm.tools.*,java.util.*"  %>
<%@ include file="/includes/operator.inc" %>

<%
try{
	ProductLocal local = EJBFactory.getProduct();
	ProductVO vo = new ProductVO();
	//获取页面传递变量
	String query_product_code = Utility.trimNull(request.getParameter("query_product_code"));
	String query_product_name = Utility.trimNull(request.getParameter("query_product_name"));	
	Integer query_depart_id = Utility.parseInt(Utility.trimNull(request.getParameter("query_depart_id")),new Integer(0));
	Integer query_admin_manager = Utility.parseInt(Utility.trimNull(request.getParameter("query_admin_manager")),new Integer(0));
	Integer query_admin_sub_man = Utility.parseInt(Utility.trimNull(request.getParameter("query_admin_sub_man")),new Integer(0));

	query_product_name = java.net.URLDecoder.decode(query_product_name, "utf-8");

	vo = new ProductVO();
	vo.setBook_code(input_bookCode);
	vo.setProduct_code(query_product_code);
	vo.setProduct_name(query_product_name);
	vo.setDepart_id(query_depart_id);
	vo.setAdmin_manager(query_admin_manager);
	vo.setInput_man(input_operatorCode);
	vo.setSub_man(query_admin_sub_man);
	
	String json = local.queryProductTableJosn(vo);

	out.clear();

	response.getWriter().write(json);
}catch(Exception e){
	throw e;
}
	
%>