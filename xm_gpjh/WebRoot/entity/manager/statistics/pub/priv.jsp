<%@page import = "com.whaty.platform.sso.web.action.SsoConstant,com.whaty.platform.sso.web.servlet.UserSession,com.whaty.platform.training.user.*"%>

<%
	
	UserSession us = (UserSession)session.getAttribute("user_session");
	
	if(us!=null)
	{
	} else {
%>	
	<script language="javascript">
		window.top.alert("登录超时，为了您的帐户安全，请重新登录。");
		window.top.location = "/";
	</script>
<%
	return ;
	}
%>
