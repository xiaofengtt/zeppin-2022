<%@ page contentType="text/html; charset=GBK" import="java.util.*,enfo.crm.service.*,enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.*" %>
<%
//��¼��֤
String username = Utility.trimNull(request.getParameter("username"));
String password = Utility.trimNull(request.getParameter("password"));
Integer redirect = Utility.parseInt(Utility.trimNull(request.getParameter("redirect")),new Integer(0));

int loginSuccess = 0;
boolean success = false;
String loginResult = "";
String CONST_ENFO_LOGINUSER = "_const_enfo_loginuser_";

LoginService loginService = new LoginService();

//��֤�û��� ����
try{
	loginSuccess = loginService.checkUserPass(username,password);
}
catch(Exception e){
	//throw new Exception("ϵͳ���ݿ�δ��ʼ��!");
	loginResult = "2|ϵͳ���ݿ�δ��ʼ��!";
}
if (loginSuccess==100) {
	loginResult = "1|��֤�ɹ�";
	try{
		loginService.doLogin(request,username,password);
		session.setAttribute(CONST_ENFO_LOGINUSER, username);
		loginResult = "1|��¼�ɹ�";
	}
	catch(Exception e){
		loginResult = "2|��¼ʧ��";
	}	
}
else{
	loginResult = "2|��֤ʧ��";
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
window.location.href= "<%=path%>/";//��ת����¼����	
<%}%>
</script>
</head>
<body>
<%=loginResult%>
</body>
</html>