<%@ page contentType="text/html; charset=GBK" import="enfo.crm.callcenter.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%//声明对象
String phone = Utility.trimNull(request.getParameter("phone"));

List list = new ArrayList();

CallCenterLocal callcenter = EJBFactory.getCallCenter();
CCVO vo = new CCVO();

vo.setPhoneNumStr(phone);
//vo.setInput_man(input_operatorCode);

String extension = "0";
String service_man_mobile = "0"; 
if(phone.length()>0){
	IPageList pageList = callcenter.listCustByPhone2(vo, 1, -1);
	list = pageList.getRsList();
	if(list.size()>0)
 	{
		Map map = (Map)list.get(0);
 		extension = Utility.trimNull(map.get("Extension"));
		extension = ("".equals(extension))?"0":extension;
		service_man_mobile = Utility.trimNull(map.get("SERVICE_MAN_MOBILE"),"0");
		service_man_mobile = ("".equals(service_man_mobile))?"0":service_man_mobile;
	}
}

response.setContentType("text/xml");  
out.write("<?xml version=\"1.0\" encoding=\"GBK\"?>\n"); 
out.write("<Tips>\n"); 
out.write("<Tip>\n"); 
out.write("<Type>来电客户经理分机号</Type>\n"); 
out.write("<Extension>" + Utility.trimNull(extension)+"-"+Utility.trimNull(service_man_mobile) +"</Extension>\n");
out.write("</Tip>\n</Tips>");

  
%> 
