<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.intrust.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.affair.*,enfo.crm.tools.*,java.util.*"  %>
<%@ include file="/includes/operator.inc" %>
<%
try{
	Integer product_ids = Utility.parseInt(Utility.trimNull(request.getParameter("product_id")), new Integer(0));
	String product_code = Utility.trimNull(request.getParameter("product_code"));
	String product_name = Utility.trimNull(Argument.getProductFlag(product_ids,"product_name"));

	//1.�޸Ĳ���Ա�ĳ�ʼ��Ʒ
	OperatorLocal operator = EJBFactory.getOperator();
	TOperatorVO vo = new TOperatorVO();
	vo.setOp_code(input_operatorCode);
	vo.setProduct_id(product_ids);
	operator.changeProduct(vo);
	operator.remove();

	//2.��Ʒ��Ϣ���浽�Ự��
	session.setAttribute("product_code", product_code);
	session.setAttribute("overall_product_id", product_ids);	

	out.clear();
	if(product_ids.intValue() == 0 )
		response.getWriter().write("ȫ��");
	else
		response.getWriter().write(product_code+"-"+product_name);
}catch(Exception e){
	throw e;
}
%>