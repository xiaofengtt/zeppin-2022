<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
try{
ProductCellLocal productCell = EJBFactory.getProductCell();
String cell_name = Utility.trimNull(session.getAttribute("QUERY_CELL_NAME"));
String cell_code = Utility.trimNull(session.getAttribute("QUERY_CELL_CODE"));
Integer cell_id=Utility.parseInt(Utility.trimNull(request.getParameter("id")),new Integer(-1));
out.print(productCell.querySubJectJosn2(input_bookCode,cell_id,cell_code,input_operatorCode,cell_name));
productCell.remove();
}catch(Exception e){
	throw e;
}
%>