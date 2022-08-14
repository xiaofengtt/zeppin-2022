<%@ page contentType="text/html; charset=GBK"  import="java.util.*,java.math.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*"%>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;
//获得参数
Integer contact_id = Utility.parseInt(request.getParameter("contact_id"), new Integer(0));
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
Integer contactType = Utility.parseInt(request.getParameter("contactType"), new Integer(0));

String contactor = Utility.trimNull(request.getParameter("contactor"));
String phone_home = Utility.trimNull(request.getParameter("phone_home"));
String phone_office = Utility.trimNull(request.getParameter("phone_office"));
String mobile = Utility.trimNull(request.getParameter("mobile"));
String fax = Utility.trimNull(request.getParameter("fax"));
String email = Utility.trimNull(request.getParameter("email"));
String address = Utility.trimNull(request.getParameter("address"));
String zip_code = Utility.trimNull(request.getParameter("zip_code"));

Integer sex = Utility.parseInt(request.getParameter("sex"), new Integer(-1));
Integer birthday = Utility.parseInt(request.getParameter("birthday"),new Integer(0));
Integer anniversary = Utility.parseInt(request.getParameter("anniversary"),new Integer(0));
Integer ismarried = Utility.parseInt(request.getParameter("ismarried"),new Integer(-1));
String spouse = Utility.trimNull(request.getParameter("spouse"));
Integer boss = Utility.parseInt(request.getParameter("boss"), new Integer(0));
String department = Utility.trimNull(request.getParameter("department"));
String duty = Utility.trimNull(request.getParameter("duty"));
String country = Utility.trimNull(request.getParameter("country"));
String province = Utility.trimNull(request.getParameter("province"));
String city = Utility.trimNull(request.getParameter("city"));

Integer role = Utility.parseInt(request.getParameter("role"), new Integer(-1));
String assistant = Utility.trimNull(request.getParameter("assistant"));
String assistantTelphone = Utility.trimNull(request.getParameter("assistantTelphone"));;
String manager = Utility.trimNull(request.getParameter("manager"));
String managerTelphone = Utility.trimNull(request.getParameter("managerTelphone"));

String contactWay = Utility.trimNull(request.getParameter("contactWay"));
Integer preferredDate = Utility.parseInt(request.getParameter("preferredDate"), new Integer(-1));
Integer preferredTime = Utility.parseInt(request.getParameter("preferredTime"), new Integer(0));

//接受的服务方式=选择的值相加的和
String[] receiveContactWays = request.getParameterValues("receiveContactWay"); 
Integer receiveContactWay = new Integer(0);
int receiveContactWay_it = 0;
if (receiveContactWays != null)
{	
	for(int i = 0;i < receiveContactWays.length; i++)
	{	
		receiveContactWay_it += Utility.parseInt(receiveContactWays[i], 0);
	}
}
receiveContactWay = new Integer(receiveContactWay_it);

//服务=选择的值相加的和
String[] receiveServicess = request.getParameterValues("receiveServices"); 
Integer receiveServices = new Integer(0);
int receiveServices_it = 0;
if (receiveServicess != null)
{	
	for(int i = 0;i < receiveServicess.length; i++)
	{	
		receiveServices_it += Utility.parseInt(receiveServicess[i], 0);
	}
}
receiveServices = new Integer(receiveServices_it);

CustomerContactLocal local = EJBFactory.getCustomerContact(); 
CustomerContactVO vo = new CustomerContactVO();
vo.setCust_id(cust_id);
vo.setContactor(contactor);
vo.setPhoneHome(phone_home);
vo.setPhoneOffice(phone_office);
vo.setMoblie(mobile);
vo.setFax(fax);
vo.setEmail(email);
vo.setAddress(address);
vo.setZipCode(zip_code);
vo.setSex(sex);
vo.setBirthday(birthday);
vo.setAnniversary(anniversary);
vo.setIsMarried(ismarried);
vo.setSpouse(spouse);
vo.setBoos(boss);
vo.setDepartment(department);
vo.setDuty(duty);
vo.setCountry(country);
vo.setProvince(province);
vo.setCity(city);
vo.setRole(role);
vo.setAssistant(assistant);
vo.setAssistantTelphone(assistantTelphone);
vo.setManager(manager);
vo.setManagerTelphone(managerTelphone);
vo.setContactWay(contactWay);
vo.setPreferredDate(preferredDate);
vo.setPreferredTime(preferredTime);
vo.setReceiveContactWay(receiveContactWay);
vo.setReceiveService(receiveServices);
vo.setInsertMan(input_operatorCode);
vo.setContactType(contactType);

if (request.getMethod().equals("POST")){
	if(contact_id.intValue() == 0){
		local.appendCustomerContact(vo);//添加联系人信息
		bSuccess = true;
	}else
	{
		vo.setContactId(contact_id);
		local.modiCustomerContact(vo);//修改联系人信息
		bSuccess = true;
	}
}
%>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
	<script language="javascript">
	<%if(bSuccess){%>
		window.returnValue=1;
		window.close();
	<%}%>
	</script>
</head>
<body>
<input type="hidden" name="cust_id" value="<%=cust_id%>">
</body>
<%local.remove();%>