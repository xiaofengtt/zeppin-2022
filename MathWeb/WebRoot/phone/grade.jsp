<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>选择学段</title>
	<jsp:include page="head.jsp"></jsp:include>
  </head>
  <body>

    <!-- Make sure all your bars are the first things in your <body> -->
    <header class="bar bar-nav bar-nav-gs">
    	<s:if test="%{showback==1}">
    		<a class="btn btn-link btn-nav pull-left" href="../phone/chart.jsp"><span class="icon icon-left-nav"></span>返回</a>
    	</s:if>
      <h1 class="title"><img class="logo" src="../img/m/logo-text.png" alt="果实网"></h1>
    </header>

    <!-- Wrap all non-bar HTML in the .content div (this is actually what scrolls) -->
    <div class="content">
     <div class="step">第一步:选择年级</div>
	 <div id="step1" class="step1">
		 <ul class="table-view">
		 <s:iterator value='gradeMap' id='grade' >
		 	<li class="table-view-cell"><a href="../phone/userUpdateGrade?grade.id=<s:property value='key' />&show=<s:property value='showback' />"><s:property value='value' />学期</a></li>
		 </s:iterator>
		 </ul>
		 <div class="step-dot">
			 <span class="cur"></span>
		 	<span></span>
			<span></span>
		 </div>
	 </div>
	  
	  <footer>
	  	<div class="footer-copyright">&copy; 2014 中国教育电视台  京ICP证101020号</div>
	  </footer>
      
    </div>
	
	
  </body>
</html>
