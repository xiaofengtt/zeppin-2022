<%@ page contentType="text/html; charset=GBK" import="java.util.*,enfo.crm.service.*,enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.*" %>
<%
//登录验证
String username = Utility.trimNull(request.getParameter("username"));
String password = Utility.trimNull(request.getParameter("password"));
Integer redirect = Utility.parseInt(Utility.trimNull(request.getParameter("redirect")),new Integer(0));

int loginSuccess = 0;
boolean success = false;
String loginResult = "";
String CONST_ENFO_LOGINUSER = "_const_enfo_loginuser_";

LoginService loginService = new LoginService();

//验证用户名 密码
try{
	loginSuccess = loginService.checkUserPass(username,password);
}
catch(Exception e){
	//throw new Exception("系统数据库未初始化!");
	loginResult = "2|系统数据库未初始化!";
}
if (loginSuccess==100) {
	loginResult = "1|验证成功";
	try{
		loginService.doLogin(request,username,password);
		session.setAttribute(CONST_ENFO_LOGINUSER, username);
		loginResult = "1|登录成功";
	}
	catch(Exception e){
		loginResult = "2|登录失败";
	}	
}
else{
	loginResult = "2|验证失败";
}
String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<title>webCall Login</title>
<script language=javascript>
<%if(redirect.intValue()==1){%>
//window.location.href= "http://128.8.28.239:9080/";
window.location.href= "<%=path%>/";//跳转至登录界面	
<%}%>
</script>
</head>
<body>
<%=loginResult%>
</body>
</html>