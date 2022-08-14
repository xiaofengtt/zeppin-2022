<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.intrust.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.affair.*,enfo.crm.tools.*,java.util.*"  %>
<%@ include file="/includes/operator.inc" %>
<%
try{
	Integer product_ids = Utility.parseInt(Utility.trimNull(request.getParameter("product_id")), new Integer(0));
	String product_code = Utility.trimNull(request.getParameter("product_code"));
	String product_name = Utility.trimNull(Argument.getProductFlag(product_ids,"product_name"));

	//1.修改操作员的初始产品
	OperatorLocal operator = EJBFactory.getOperator();
	TOperatorVO vo = new TOperatorVO();
	vo.setOp_code(input_operatorCode);
	vo.setProduct_id(product_ids);
	operator.changeProduct(vo);
	operator.remove();

	//2.产品信息保存到会话中
	session.setAttribute("product_code", product_code);
	session.setAttribute("overall_product_id", product_ids);	

	out.clear();
	if(product_ids.intValue() == 0 )
		response.getWriter().write("全部");
	else
		response.getWriter().write(product_code+"-"+product_name);
}catch(Exception e){
	throw e;
}
%>