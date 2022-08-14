<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ page import="enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.web.*,enfo.crm.service.*,enfo.crm.tools.*,java.util.*"%>
<%@ page import="com.mysap.sso.SSO2Ticket" %>
<%@ page import="java.security.cert.*" %>
<%@ page import="java.io.*" %>

<%
System.out.println("--------------SAP IN");
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
String loginUrl = request.getContextPath()+"/login/login.jsp";
String enfosid = Utility.trimNull(request.getParameter("enfosid"));//跳转标志 c:CRM p:项目管理
String UserLanguage=Utility.trimNull(request.getParameter("UserLanguage"),"zh-CN");//SAP语言标志：en-US是英文，zh-CN是中文
Integer enfolanguage = new Integer(0);//ENFO语言标志：0是根据浏览器环境，1是中文，2是英文；需与SAP的转换

//获取 SAP 认证信息
Cookie[] all_Cookies = request.getCookies();
String  ticket = "";//sap 登录票
int len = 0;

if(all_Cookies!=null){
	len = all_Cookies.length;	
}

for (int i=0;i<len;i++){
    //Get MYSAPSSO2 cookie from request context...
    if ("MYSAPSSO2".equals (all_Cookies[i].getName ())) {
        ticket = all_Cookies[i].getValue ();
        break;
    }
}

System.out.println("----------------SAP Ticket Validate");
//If no ticket present we output an error page
if ("".equals(ticket)) {
	//如果没有通过SAP单点登录认证 跳转回SAP单点登录页面
	System.out.println("----------------Ticket Wrong");
	loginUrl = request.getContextPath()+"/login/login.jsp";
}
else{	
	//如果通过SAP单点登录认证 进入系统进行登录认证
	System.out.println("----------------Ticket Right");
	Object [] object = null;
	String vfs= "";//SAP 证书路径
	
    try {
        // Validate logon ticket.
        //vfs= request.getSession().getServletContext().getRealPath("/sso/verify.pse");
        vfs = "D:/sso/verify.pse";
        File ff = new File("D:\\sso\\verify.pse");
        vfs = ff.getAbsolutePath();
        
        object = SSO2Ticket.evalLogonTicket (ticket,vfs, null);
        System.out.println("----------------SSO2Ticket evalLogonTicket");
    } catch (Exception e) {
    	throw new Exception("SAP SSO Error 1: "+e.toString ());
	} catch (Throwable te) {
		throw new Exception("SAP SSO Error 2: "+te.toString ());
    }
	
	String userName = (String)object[0];//获得用户登录名
	if (userName==null || "".equals(userName)) {
		userName = (String)object[4];
		userName = userName.toUpperCase();
	}
	//session.setAttribute("ENFO_SSP_LOGIN_NAME",userName); 本来应该是SSO的 但不知怎么就写错了，就将错就错吧。。。
	Cookie enfoSSOLoginName =new Cookie("ENFO_SSP_LOGIN_NAME",userName);//以"username",userName值/对创建一个Cookie
	
	loginUrl = request.getContextPath()+"/sso/login_sso.jsp";//跳转至登录界面	
	if(enfosid.equals("p")){
		loginUrl = basePath+"/enfointrustweb/sso/login_sso.jsp";
		enfoSSOLoginName.setPath("/enfointrustweb");
	}
	else{
		loginUrl = request.getContextPath()+"/sso/login_sso.jsp";//跳转至单点登录界面
	}
	
	response.addCookie(enfoSSOLoginName);
	
	//语言标记转换
	if(UserLanguage.equals("zh-CN")){
		enfolanguage = new Integer(1);
	}
	else if(UserLanguage.equals("en-US")){
		enfolanguage = new Integer(2);
	}
	
	loginUrl = loginUrl + "?languageFlag="+enfolanguage;
}
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<title>SAP SSO Login</title>
<script language=javascript>
	window.location.href= "<%=loginUrl%>";
</script>
</head>
<body></body>
</html>