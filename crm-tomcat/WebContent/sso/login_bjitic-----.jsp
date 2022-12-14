<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ page import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.web.*,enfo.crm.service.*,enfo.crm.tools.*,java.util.*"%>
<%@ page import="java.io.*" %>

<%
String loginUrl = request.getContextPath()+"/login/login.jsp";
String userName = Utility.trimNull(request.getParameter("userNumber"));//获取登录名
String token = Utility.trimNull(request.getParameter("token"));
/*if(!LtpaTokenManager.verifyToken(Argument.getDictParamName(8002,"800206")+"LtpaToken.properties",token,userName)){
    throw new Exception("非法的登陆方式");
}*/

//获得对象
LoginService login = new LoginService();

OperatorVO opVO = new OperatorVO();	
DictparamVO dictVO = new DictparamVO();

OperatorLocal op = EJBFactory.getOperator();//操作员表
DictparamLocal dict = EJBFactory.getDictparam();//字典表
SystemInfoLocal systeminfo = EJBFactory.getSystemInfo();//系统表

//获取参数
String apply_name = "";
String company_name = "";
String input_opCode = userName;//login.getLoginUser(userName);
String errorMsg = "";
Locale clientLocale = null;//得到本地化信息
Integer languageFlag = Utility.parseInt(request.getParameter("languageFlag"),Integer.valueOf("0"));//得到语言标志
String languageType = "zh_CN";

//辅助参数
List dictList = null;
List opInfoList = null;

Map dictMap = null;
Map opMap = null;
Map bookMap = null;
Map sysMap= null;

//检查语言开关
if(languageFlag.intValue()==0){
	languageFlag = new Integer(Argument.getSyscontrolValue("Global"));		
}
//设置语言环境
if(languageFlag.intValue()==0){
	clientLocale = request.getLocale();//得到本地化信息		
	languageType = clientLocale.getLanguage()+"_"+clientLocale.getCountry();
}
else if(languageFlag.intValue()==1){
	clientLocale = new Locale("zh","CN");
	languageType = "zh_CN";
}
else if(languageFlag.intValue()==2){
	clientLocale = new Locale("en","US");
	languageType = "en_US";
}
//进入登录程序
if(input_opCode == null || input_opCode == ""){
	errorMsg = "["+userName +"]"+LocalUtilis.language("login.message.opAccountError",clientLocale);//对不起，您所输入的操作员帐号不存在！
	throw new Exception(errorMsg);
}
else{
	//通过登录名，转换成操作员编号
	opVO.setLogin_user(input_opCode);
	List list=op.listOpCodeByUser(opVO);
	if (list==null || list.isEmpty())
		throw new Exception("登录的操作员"+input_opCode+"没有对应的CRM系统登录帐号");
	input_opCode=""+(Integer)((Map)list.get(0)).get("OP_CODE");
	//初始化登陆所需的参数op_code,password,ip
	opVO.setOp_code(new Integer(input_opCode));
	opVO.setIp_address(request.getRemoteAddr());
	
	try{
		op.loginSystem2(opVO);//用户登录方法 单点登录专用
	}
	catch(Exception e){
		throw e;
	}
	///////////////////////////////////////////////////////////////////////////////			
	//系统初始化
	try{
		sysMap=(Map)systeminfo.listBySig1(new Integer(1)).get(0);	
	}
	catch(Exception e){
		throw new Exception(LocalUtilis.language("login.message.dataBaseError",clientLocale));//系统数据库未初始化!
	}
	//系统数据
	apply_name = Utility.trimNull(sysMap.get("APPLICATION_NAME"));
	if(languageType.equals("en_US")){
		company_name = Utility.trimNull(sysMap.get("USER_NAME_EN"));
	}
	else {
		company_name = Utility.trimNull(sysMap.get("USER_NAME"));
	}
	
	if(apply_name!=null || apply_name!="")
		application.setAttribute("APPLICATION_NAME",apply_name);
	else
		application.setAttribute("APPLICATION_NAME","No Name");
	
	application.setAttribute("COMPANY_NAME", sysMap.get("USER_NAME"));
	application.setAttribute("USER_ID",sysMap.get("USER_ID"));			
	///////////////////////////////////////////////////////////////////////////////			
	//系统初始化，报警提示input_operatorCode
	try{
		op.updateBookCode(new Integer(input_opCode));
	}catch(Exception e){
		throw new Exception(LocalUtilis.language("login.message.sysInitError",clientLocale));//系统初始化失败！
	}
	
	//登陆后查询信息
	opVO.setOp_code(new Integer(input_opCode));
	opInfoList = op.listOpAll(opVO);
	System.out.println(opInfoList);
	
	opMap = (Map)opInfoList.get(0);
	session.setAttribute("_const_enfo_loginuser_", Utility.trimNull(opMap.get("OP_NAME")));
	//初始化系统开关所需的参数
	dictVO.setFlag_type("DB00001");
			
	//查询系统开关参数
	dictList = dict.listSysControlValue(dictVO);
				
	if(dictList.size() == 0){
		errorMsg = LocalUtilis.language("login.message.SysControlError",clientLocale);//系统开关参数不存在，请检查数据库！
		throw new Exception(errorMsg);
	}
				
	dictMap = (Map)dictList.get(0);
	Integer dictValue = Utility.parseInt(Utility.trimNull(dictMap.get("VALUE")),null);
					
	//获取inputMan
	InputMan OPERATOR = login.loginInputMan(opVO);
			
	//向Session中赋值
	session.setAttribute("OPERATOR",OPERATOR);
	session.setAttribute("LOG0001",new Integer(Argument.getSyscontrolValue("LOG0001")));				
	session.setAttribute("WEBFLAG","XTWEB");
	session.setAttribute("DBDRIVER",dictValue);
	session.setAttribute("languageFlag",languageFlag);	
	
	loginUrl = request.getContextPath()+"/login/index.jsp?parent_id=11";//正常跳转页面
}
%>


<html>
<head>
<title>ENFO SSO Login</title>
<script language=javascript>
	window.location.href= "<%=loginUrl%>";
</script>
</head>
<body></body>
</html>
