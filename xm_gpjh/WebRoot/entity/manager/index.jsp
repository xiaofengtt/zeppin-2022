<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<%
String path = request.getContextPath();
%>
<%//@ include file="pub/priv.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>国培计划项目管理系统</title>
<script language="JavaScript" src="js/frame.js"></script>

<script type="text/javascript">
	function   window.onunload()   { 
		if(window.screenLeft>10000|| window.screenTop>10000){ 
			var url="<%=path%>/sso/login_close.action?loginErrMessage=clear";
			if (window.XMLHttpRequest) {
		        req = new XMLHttpRequest();
		    }
		    else if (window.ActiveXObject) {
		        req = new ActiveXObject("Microsoft.XMLHTTP");
		    }
		    req.open("Get",url,true);
		    req.onreadystatechange = function(){
		    	if(req.readyState == 4);
		    };
	  		req.send(null);
  		}
	}
	function logout(){
		var url="<%=path%>/sso/login_close.action?loginErrMessage=clear";
		if (window.XMLHttpRequest) {
	        req = new XMLHttpRequest();
	    }
	    else if (window.ActiveXObject) {
	        req = new ActiveXObject("Microsoft.XMLHTTP");
	    }
	    req.open("Get",url,true);
	    req.onreadystatechange = function(){
	    	if(req.readyState == 4){
		    	window.top.navigate("/");
	    	}
	    };
  		req.send(null);
	}
</script>
<%
UserSession userSession = (UserSession)session.getAttribute("user_session");
if(userSession.getSsoUser().getId().equals("402880a9203242180120324733d70001")){%>
<script>
function logcms(){
	var s=document.getElementById('loginform');
	s.submit();
}
</script>
<%}%>
</head>
<%

/*
		String loginId = "guocheng";
		SsoFactory factory=SsoFactory.getInstance();
		SsoUserPriv ssoUserPriv=factory.creatSsoUserPriv(loginId);
		SsoUserOperation ssoUserOperation=factory.creatSsoUserOperation(ssoUserPriv);
		SsoManagerPriv ssoManagerPriv =factory.creatSsoManagerPriv();
		SsoManage  ssoManager =factory.creatSsoManage(ssoManagerPriv);
		
		session.setAttribute("ssoUserPriv",ssoUserPriv);
		
		PlatformFactory pfactory=PlatformFactory.getInstance();
		PlatformManage platformManage=pfactory.createPlatformManage();
		
		EntityUser enuser=platformManage.getEntityUser(ssoUserPriv.getId(),"manager");
		
			session.removeAttribute("infomanager_priv");	  
		  	session.removeAttribute("smsmanager_priv");  	  	
		  	session.setAttribute("eduplatform_user",enuser);
			ManagerPriv managerPriv= pfactory.getManagerPriv(enuser.getId());
			session.setAttribute("eduplatform_priv",managerPriv);
*/

        session.setAttribute("type",request.getParameter("type"));

 %>
 <%if(userSession.getSsoUser().getId().equals("402880a9203242180120324733d70001")){%>
<form id="loginform" name="loginform" action="http://www.gpjh.cn/cms/login/CmsSubmit.do" method="post" target="top" style="display:none">
<input type="hidden" name="loginName" value="admin"/>
<input type="hidden" name="password" value="111111"/>
</form>
<%}%>
<frameset rows="62,34,*,38" frameborder="no" border="0" framespacing="0" name="TCB">
	<frame src="/entity/manager/pub/topBar1.jsp" name="banner" scrolling="NO" noresize>
	<frame src="/entity/manager/pub/topBar2.jsp" name="banner" scrolling="NO" noresize>
	<frameset cols="213,*" frameborder="no" border="0" framespacing="0" name="TC">
		<frameset rows="*,0" frameborder="no" border="0" framespacing="0" name="TH">
			<frame src="/entity/manager/pub/tree.jsp" name="tree" scrolling="no" noresize/>
			<frame src="/entity/manager/pub/help.htm" name="help" scrolling="no" noresize/>
		</frameset>
		<frameset rows="0,*" frameborder="no" border="0" framespacing="0" name="MC">
			<frame src="/entity/manager/pub/menu.htm" name="menu" scrolling="no" noresize/>	
			<frame src="/entity/manager/pub/content_pszj.htm" name="content" scrolling="no" noresize/>
		</frameset>
	</frameset>
	<frame src="/entity/manager/pub/bottom.htm" name="bottom" scrolling="NO" noresize>
</frameset>
<noframes></noframes>
<body>
</body>
</html>
