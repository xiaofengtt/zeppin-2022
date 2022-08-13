<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<html lang="zh-CN">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=8,IE=edge,chrome=1">
	 
    <base href="<%=basePath%>">
    <title>登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="description" content="中央美术学院">
	
 <!-- CSS -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/app.css" rel="stylesheet">
    <link rel="shortcut icon" href="ico/favicon.png">
    
     <!-- JS -->
    <script src="js/jquery.js"></script>
    <script src="js/app.js"></script>
    <script type="text/javascript" src="js/Validform-min.js"></script>
  </head>
  
  <body>
   <div class="login-header"></div>
	  <div class="container">
		  <div class="clearfix login-cnt">
			  <div class="logo-larger span6"><img src="img/logo-larger.png"></div>
			  <div class="login-form span6">
				  <form action="login_execute.action" method="post" class="login">
				
					  <fieldset>
						  <div class="controls">
						  <div class="input-prepend">
						    <span class="add-on"><img src="img/u.png" alt width="19px"></span>
						    <input class="input-large" id="prependedInput" type="text" name="userName" placeholder="用户名" datatype="s6-18"  nullmsg="请输入用户名！" errormsg="用户名至少6个字符,最多18个字符！" >
						  </div>
					  	</div>
						<div class="controls">
						  <div class="input-prepend">
						    <span class="add-on"><img src="img/p.png" alt width="19px"></span>
						    <input name="pwd" class="input-large" id="prependedInput" type="password" placeholder="密&nbsp;&nbsp;&nbsp;码" datatype="*6-16"  errormsg="密码范围在6~16位之间！" >
						  </div>
					  </div>
						  <button id="submit" type="submit" class="btn btn-primary">登录</button>
					<!-- <span><a href=#"">忘记密码?</a></span>  -->	 
					  </fieldset>
				  </form>
			  </div>
		  </div>
	  </div>
	  <script type="text/javascript">

	$(".login").Validform();
	</script>
  </body>
</html>
