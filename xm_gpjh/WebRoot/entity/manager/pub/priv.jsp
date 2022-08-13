<%@ page import="com.whaty.platform.entity.user.ManagerPriv,com.whaty.platform.interaction.*" %>
<%@page import = "com.whaty.platform.sso.*,com.whaty.platform.campus.user.*,com.whaty.platform.entity.user.ManagerPriv"%>
<jsp:directive.page import="com.whaty.platform.vote.user.VoteManagerPriv"/>
<jsp:directive.page import="com.whaty.platform.vote.VoteFactory"/>
<jsp:directive.page import="com.whaty.platform.sso.web.servlet.UserSession"/>
<jsp:directive.page import="com.whaty.platform.entity.user.ManagerPriv;"/>
<%
  // UserSession us = (UserSession)session.getAttribute("user_session");
   //if(us != null && "2".equals(us.getUserLoginType())){
   		%>
   			<script type="text/javascript">
   				//window.location.href="<%=request.getContextPath()%>/enitty/submanager/notice/notice_list.jsp";
   			</script>
   		<%
   //}
   
    SsoUserPriv ssoUserPriv=(SsoUserPriv)session.getAttribute("ssoUserPriv");
	InteractionUserPriv interactionUserPriv = (InteractionUserPriv)session.getAttribute("interactionUserPriv");
	CampusInfoManagerPriv campusManagerpriv =(CampusInfoManagerPriv)session.getAttribute("campusManagerpriv");;
	ManagerPriv includePriv=(ManagerPriv)session.getAttribute("eduplatform_priv");
	
%>