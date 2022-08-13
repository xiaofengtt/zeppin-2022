
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="cn.zeppin.action.sso.UserSession"%>
	<div class="navbar" role="navigation">
	  <div class="container-fluid">
	    <div class="navbar-header">
	    	<a class="logo"  ><img src="../img/logo12.png" height="41"></a>
	    </div>
		<p class="logout" style="padding-right:20px;"><span>欢迎教师 &nbsp;&nbsp;<strong><% out.print(((UserSession)session.getAttribute("usersession")).getName());  %></strong>&nbsp;&nbsp;</span> &nbsp;|&nbsp;  <a href="../teacher/tlg_logout.action">退出</a> </p>	
		
	  </div>
	</div>