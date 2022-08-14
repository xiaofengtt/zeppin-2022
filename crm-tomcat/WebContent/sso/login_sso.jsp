<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ page import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.web.*,enfo.crm.service.*,enfo.crm.tools.*,java.util.*"%>
<%@ page import="com.mysap.sso.SSO2Ticket" %>
<%@ page import="java.security.cert.*" %>
<%@ page import="java.io.*" %>

<%
//CRM �����¼ ��¼����
/*˵������ҳ��ٶ��Ѵӵ����¼���������õ�¼�� �õ�¼��ֱ�ӵ�¼*/
System.out.println("--------------SSO IN");
String loginUrl = request.getContextPath()+"/login/login.jsp";
//String userName = Utility.trimNull(session.getAttribute("ENFO_SSP_LOGIN_NAME"));//��ȡ��¼��
String userName = "";
int len = 0;
Cookie[] all_Cookies = request.getCookies();

if(all_Cookies!=null){
	len = all_Cookies.length;	
}
for (int i=0;i<len;i++){
    //Get MYSAPSSO2 cookie from request context...
    if ("ENFO_SSP_LOGIN_NAME".equals (all_Cookies[i].getName())) {
    	userName = Utility.trimNull(all_Cookies[i].getValue());
        break;
    }
}
System.out.println("--------------User Name:"+userName);

//��ö���
LoginService login = new LoginService();

OperatorVO opVO = new OperatorVO();	
DictparamVO dictVO = new DictparamVO();

OperatorLocal op = EJBFactory.getOperator();//����Ա��
DictparamLocal dict = EJBFactory.getDictparam();//�ֵ��
SystemInfoLocal systeminfo = EJBFactory.getSystemInfo();//ϵͳ��

//��ȡ����
String apply_name = "";
String company_name = "";
String input_opCode = login.getLoginUser(userName);
String errorMsg = "";
Locale clientLocale = null;//�õ����ػ���Ϣ
Integer languageFlag = Utility.parseInt(request.getParameter("languageFlag"),Integer.valueOf("0"));//�õ����Ա�־
String languageType = "zh_CN";

//��������
List dictList = null;
List opInfoList = null;

Map dictMap = null;
Map opMap = null;
Map bookMap = null;
Map sysMap= null;

//������Կ���
if(languageFlag.intValue()==0){
	languageFlag = new Integer(Argument.getSyscontrolValue("Global"));		
}
//�������Ի���
if(languageFlag.intValue()==0){
	clientLocale = request.getLocale();//�õ����ػ���Ϣ		
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
//�����¼����
if(input_opCode == null || input_opCode == ""){
	errorMsg = "["+userName +"]"+LocalUtilis.language("login.message.opAccountError",clientLocale);//�Բ�����������Ĳ���Ա�ʺŲ����ڣ�
	throw new Exception(errorMsg);
}
else{
	//��ʼ����½����Ĳ���op_code,password,ip
	opVO.setOp_code(new Integer(input_opCode));
	opVO.setIp_address(request.getRemoteAddr());
	
	try{
		op.loginSystem2(opVO);//�û���¼���� �����¼ר��
	}
	catch(Exception e){
		throw e;
	}
	///////////////////////////////////////////////////////////////////////////////			
	//ϵͳ��ʼ��
	try{
		sysMap=(Map)systeminfo.listBySig1(new Integer(1)).get(0);	
	}
	catch(Exception e){
		throw new Exception(LocalUtilis.language("login.message.dataBaseError",clientLocale));//ϵͳ���ݿ�δ��ʼ��!
	}
	//ϵͳ����
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
	//ϵͳ��ʼ����������ʾinput_operatorCode
	try{
		op.updateBookCode(new Integer(input_opCode));
	}catch(Exception e){
		throw new Exception(LocalUtilis.language("login.message.sysInitError",clientLocale));//ϵͳ��ʼ��ʧ�ܣ�
	}
	
	//��½���ѯ��Ϣ
	opVO.setOp_code(new Integer(input_opCode));
	opInfoList = op.listOpAll(opVO);
	
	opMap = (Map)opInfoList.get(0);
			
	//��ʼ��ϵͳ��������Ĳ���
	dictVO.setFlag_type("DB00001");
			
	//��ѯϵͳ���ز���
	dictList = dict.listSysControlValue(dictVO);
				
	if(dictList.size() == 0){
		errorMsg = LocalUtilis.language("login.message.SysControlError",clientLocale);//ϵͳ���ز��������ڣ��������ݿ⣡
		throw new Exception(errorMsg);
	}
				
	dictMap = (Map)dictList.get(0);
	Integer dictValue = Utility.parseInt(Utility.trimNull(dictMap.get("VALUE")),null);
					
	//��ȡinputMan
	InputMan OPERATOR = login.loginInputMan(opVO);
			
	//��Session�и�ֵ
	session.setAttribute("OPERATOR",OPERATOR);
	session.setAttribute("LOG0001",new Integer(Argument.getSyscontrolValue("LOG0001")));				
	session.setAttribute("WEBFLAG","XTWEB");
	session.setAttribute("DBDRIVER",dictValue);
	session.setAttribute("languageFlag",languageFlag);	
	
	loginUrl = request.getContextPath()+"/login/index.jsp?parent_id=11";//������תҳ��
}
%>


<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<title>ENFO SSO Login</title>
<script language=javascript>
	window.location.href= "<%=loginUrl%>";
</script>
</head>
<body></body>
</html>
