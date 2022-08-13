<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>

<%
	UserSession userSession = (UserSession)session.getAttribute("user_session");

	if (userSession == null) {
%>
		<script>
			window.top.alert("登录超时，为了您的帐户安全，请重新登录。");
			window.top.location = "/"; 
		</script>
<%
		return;
	}
%>
