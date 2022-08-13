<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title><s:property value="clientName" /></title>
	<jsp:include page="head.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="../css/m/addtohomescreen.css">
	<script src="../js/m/addtohomescreen.min.js"></script>
	<script>
		//addToHomescreen.removeSession(); 
		addToHomescreen();
		//addToHomescreen({skipFirstVisit: false})
		//addToHomescreen({debug:true,lifespan : 100})
		//addToHomescreen.removeSession() 
		
		
	</script>
  </head>
  <body>

    <!-- Make sure all your bars are the first things in your <body> -->
    <header class="bar bar-nav bar-nav-gs">
      <h1 class="title"><img class="logo" src="../img/m/logo-text.png" alt="果实网"></h1>
    </header>

    <!-- Wrap all non-bar HTML in the .content div (this is actually what scrolls) -->
    <div class="content">
      <div class="content-padded">
        <h3 class="logo-wrap"><img class="logo-img" src="../img/m/logo.png" alt="果实网"></h3>
		<h4 class="app-title"><s:property value="clientName" /></h4>
      </div>
	  
	  <div class="login-wrap">
		  <form class="" action="phoneLogin" method="post">
		  		<s:set name="client" value="1" />
		  		<s:if test="%{clientId>0}">
		  			<s:set name="client" value="clientId" />
		  		</s:if>
		  		<input id="client" type="hidden" name="client" value='<s:property value="clientId" />'>
			  <ul class="input-group login-list">
				  <li><input id="username" class="input-week" type="text" placeholder="请输入您的帐号" name="email" value="" required=""></li>
				  <li><input id="password" class="input-week" type="password" placeholder="请输入您的密码" name="password" required=""></li>  
		  	</ul>
			<div class="btn-wrapper">
				<button type="submit" class="btn btn-guo btn-block">登录</button>
			</div>
		  </form>
		  
		  <ul class="subline">
		      <li><a href="#">注册果实网账号</a></li>
			  <li class="pull-right"><a href="#">忘记密码</a>
		  </li></ul>
			  
	  </div>
	  
	  <footer>
	  	<div class="footer-copyright">&copy; 2014 中国教育电视台  京ICP证101020号</div>
	  </footer>
      
    </div>
	
	
  </body>
</html>
