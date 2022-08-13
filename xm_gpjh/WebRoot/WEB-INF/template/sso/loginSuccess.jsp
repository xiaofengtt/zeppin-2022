<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
  <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

  </head>
  
  <body>
    <s:if test="#attr.user_session.userLoginType==0">
	    will go to vote page!
	    <script language="JavaScript">
	    window.top.location ='<%=path%>/entity/first/firstPeVotePaper_toVote.action';
	    </script>
    </s:if>
    <s:elseif test="#attr.user_session.userLoginType==1">
	    <script language="JavaScript">
	    window.top.location ='<%=path%>/entity/manager/index.jsp';
	    </script>
    </s:elseif>
    <s:elseif test="#attr.user_session.userLoginType==2">
	    <script language="JavaScript">
	    window.top.location ='<%=path%>/entity/manager/index.jsp';
	    </script>
    </s:elseif>
    <s:elseif test="#attr.user_session.userLoginType==3">
	    <script language="JavaScript">
	    window.top.location ='<%=path%>/entity/manager/index.jsp';
	    </script>
    </s:elseif>
    <s:else></s:else>
  </body>
</html>