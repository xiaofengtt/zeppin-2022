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
String enfosid = Utility.trimNull(request.getParameter("enfosid"));//��ת��־ c:CRM p:��Ŀ����
String UserLanguage=Utility.trimNull(request.getParameter("UserLanguage"),"zh-CN");//SAP���Ա�־��en-US��Ӣ�ģ�zh-CN������
Integer enfolanguage = new Integer(0);//ENFO���Ա�־��0�Ǹ��������������1�����ģ�2��Ӣ�ģ�����SAP��ת��

//��ȡ SAP ��֤��Ϣ
Cookie[] all_Cookies = request.getCookies();
String  ticket = "";//sap ��¼Ʊ
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
	//���û��ͨ��SAP�����¼��֤ ��ת��SAP�����¼ҳ��
	System.out.println("----------------Ticket Wrong");
	loginUrl = request.getContextPath()+"/login/login.jsp";
}
else{	
	//���ͨ��SAP�����¼��֤ ����ϵͳ���е�¼��֤
	System.out.println("----------------Ticket Right");
	Object [] object = null;
	String vfs= "";//SAP ֤��·��
	
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
	
	String userName = (String)object[0];//����û���¼��
	if (userName==null || "".equals(userName)) {
		userName = (String)object[4];
		userName = userName.toUpperCase();
	}
	//session.setAttribute("ENFO_SSP_LOGIN_NAME",userName); ����Ӧ����SSO�� ����֪��ô��д���ˣ��ͽ���ʹ�ɡ�����
	Cookie enfoSSOLoginName =new Cookie("ENFO_SSP_LOGIN_NAME",userName);//��"username",userNameֵ/�Դ���һ��Cookie
	
	loginUrl = request.getContextPath()+"/sso/login_sso.jsp";//��ת����¼����	
	if(enfosid.equals("p")){
		loginUrl = basePath+"/enfointrustweb/sso/login_sso.jsp";
		enfoSSOLoginName.setPath("/enfointrustweb");
	}
	else{
		loginUrl = request.getContextPath()+"/sso/login_sso.jsp";//��ת�������¼����
	}
	
	response.addCookie(enfoSSOLoginName);
	
	//���Ա��ת��
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