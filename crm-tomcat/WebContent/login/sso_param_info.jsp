<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%//声明对象
Integer begin_date = new Integer(Utility.getCurrentDate());
Integer end_date = new Integer(Utility.getCurrentDate());
OperatorLocal local = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();
Integer op_code = Utility.parseInt(request.getParameter("op_code"),new Integer(0));
vo.setOp_code(op_code);
vo.setBegin_date(begin_date);
vo.setEnd_date(end_date);
IPageList insideServiceTasksList = local.listInsideServiceTasks(vo,1,10);
List insideList = insideServiceTasksList.getRsList();
response.setContentType("text/xml");  
out.write("<?xml version=\"1.0\" encoding=\"GBK\"?>\n"); 
out.write("<Tips>\n"); 
out.write("<Tip>\n"); 
out.write("<Type>待处理客户信息</Type>\n"); 
out.write("<Num>" + insideList.size() +"</Num>\n");
out.write("</Tip>\n</Tips>");

  
%> 
