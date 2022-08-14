<%@ page contentType="text/html;" import="enfo.crm.callcenter.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%String phone=Utility.trimNull(request.getParameter("phone"));
String s2=Utility.trimNull(request.getParameter("callinrecord"));
//String s2=new String(s1.getBytes("UTF-8"),"UTF-8");
enfo.crm.callcenter.CallCenterLocal callcenter=EJBFactory.getCallCenter();
CCVO vo=new CCVO();
	vo.setSerial_no(Utility.parseInt(request.getParameter("new_cc_id"),new Integer(0)));
	vo.setContent(s2);
	//vo.setSerial_no(new Integer(776));
	//vo.setContent("aaa");
	vo.setStatus("1");
	vo.setInput_man(input_operatorCode);
	try{
	callcenter.modiCallRecords(vo);
	}catch (Exception e){
		out.print(e.getMessage());
	}
	
callcenter.remove();
%>