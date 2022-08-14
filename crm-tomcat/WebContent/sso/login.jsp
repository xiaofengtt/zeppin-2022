<%@ page contentType="text/html; charset=GBK" import="java.util.*,enfo.crm.service.*,enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.*" %>
<%

MenuInfoLocal menu = EJBFactory.getMenuInfo();
List rsList = menu.listMenuViews("30204","");
System.out.println("--------:"+rsList.size());


String ssoLoginName = (String)session.getAttribute("edu.yale.its.tp.cas.client.filter.user");
if(null==ssoLoginName||"".equals(ssoLoginName))
	throw new Exception("丢失系统认证资料！请重新登陆！");
String loginName = Utility.trimNull(request.getParameter("loginNmae"),ssoLoginName);
String apply_name = "";
String errorMsg = "";
boolean success = false;

SystemInfoLocal systeminfo = EJBFactory.getSystemInfo();
Map map= null;
try{
	map=(Map)systeminfo.listBySig1(new Integer(1)).get(0);	
}
catch(Exception e){
	throw new Exception("系统数据库未初始化!");
}	

apply_name = Utility.trimNull(map.get("APPLICATION_NAME"));
String company_name = Utility.trimNull(map.get("USER_NAME"));	

if(apply_name!=null || apply_name!="")
	application.setAttribute("APPLICATION_NAME",apply_name);
else
	application.setAttribute("APPLICATION_NAME","盈丰CRM系统");	
application.setAttribute("COMPANY_NAME", map.get("USER_NAME"));
application.setAttribute("USER_ID",map.get("USER_ID"));

DictparamVO dictVO = new DictparamVO();
DictparamLocal dict = EJBFactory.getDictparam();
LoginService logS = new LoginService();
	
List dictList = null;
List opInfoList = null;

Map dictMap = null;
Map opMap = null;
Map bookMap = null;

Integer user_id= (Integer)application.getAttribute("USER_ID");

OperatorLocal op = EJBFactory.getOperator();
OperatorVO vo = new OperatorVO();

vo.setLogin_user("admin");

List opList = op.listOpCodeByUser(vo);

if(opList!=null)
	opMap = (Map)opList.get(0);

String op_code = (String)opMap.get("OP_CODE");
String op_password = (String)opMap.get("PASSWORD");

vo.setOp_code(new Integer(op_code));
vo.setPassword(op_password);
vo.setIp_address(request.getRemoteAddr());
//登陆
try{
	op.loginSystem(vo);
}catch (Exception e){
	throw new Exception(e.getMessage());
}

//系统初始化，报警提示input_operatorCode
try{
	op.updateBookCode(new Integer(op_code));
}catch(Exception e){
	throw new Exception("系统初始化失败！");
}
		
//登陆后查询信息
vo.setOp_code(new Integer(op_code));
opInfoList = op.listOpAll(vo);

opMap = (Map)opInfoList.get(0);
		
//初始化系统开关所需的参数
dictVO.setFlag_type("DB00001");
//dictVO.setIs_modi(new Integer(0));
		
//查询系统开关参数
dictList = dict.listSysControlValue(dictVO);
			
if(dictList.size() == 0)
{
	errorMsg = "系统开关参数不存在，请检查数据库！";
	throw new Exception("系统开关参数不存在，请检查数据库！");
}
			
dictMap = (Map)dictList.get(0);
Integer dictValue = Utility.parseInt(Utility.trimNull(dictMap.get("VALUE")),null);
				
//获取inputMan
InputMan OPERATOR = logS.loginInputMan(vo);
		
//向Session中赋值
session.setAttribute("OPERATOR",OPERATOR);
session.setAttribute("LOG0001",new Integer(Argument.getSyscontrolValue("LOG0001")));				
session.setAttribute("WEBFLAG","XTWEB");
session.setAttribute("DBDRIVER",dictValue);

success = true;

String user_logo="";
if(user_id!=null)
  user_logo=Argument.getUserLogoInfo(user_id.intValue())[0];
  
  
Utility.debugln("user_id:" + user_id +"user_logo:"+user_logo);

if(success){
	 response.sendRedirect("/login/index.jsp");
}
  
%>
