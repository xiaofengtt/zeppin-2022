<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>学习能力移动测评客户端</title>
	<jsp:include page="head.jsp"></jsp:include>
	<link rel="stylesheet" type="text/css" href="../css/m/addtohomescreen.css">
	<script src="../js/m/addtohomescreen.min.js"></script>
	<script>
		//addToHomescreen.removeSession(); 
		addToHomescreen({lifespan : 100});
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
    	<div id="tips" <s:if test="%{message!=null}">style="display:block;"</s:if> >账号或密码错误，请重试！</div> 
      <div class="content-padded">
        <h3 class="logo-wrap"><img class="logo-img" src="../img/m/logo.png" alt="果实网"></h3>
		<h4 class="app-title">学习能力移动测评客户端</h4>
      </div>
	  
	  <div class="login-wrap">
		  <form class="" action="phoneLogin" method="post">
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
