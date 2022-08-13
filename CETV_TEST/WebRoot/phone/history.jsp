<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="org.apache.struts2.components.Include"%>
<%@page import="cn.zeppin.utility.*" %>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>选择历届真题试卷</title>
    <jsp:include page="head.jsp"></jsp:include>
	
  </head>
  <body>

    <!-- Make sure all your bars are the first things in your <body> -->
    <header class="bar bar-nav bar-nav-gs">
    	<a class="btn btn-link btn-nav pull-left" href="../phone/chart.jsp?subjectId=<s:property value="subjectId" />"><span class="icon icon-left-nav"></span>返回</a>
      <h1 class="title"><img class="logo" src="../img/m/logo-text.png" alt="果实网"></h1>
    </header>

    <!-- Wrap all non-bar HTML in the .content div (this is actually what scrolls) -->
    <div class="content">
     <div class="step">选择历届真题试卷</div>
	 <ul class="table-view">
	 
	 	<s:iterator value='lstPapers' id='paper'>
	 		<li class="table-view-cell">
			   <a class="navigate-right" href="../phone/phPaperJumpPaper?paper.id=<s:property value='#paper.id' />"><s:property value='#paper.name' />
			   		<s:if test="%{cureentTest.get(#paper.id)!=null}">
				   		<p>已作答：<s:property value='cureentTest.get(#paper.id).score' /> 分/<s:property value='#paper.totalScore' />分</p>
				   </s:if>
			   </a>
			   
			   
		   </li>
	 	</s:iterator>
	  
	 </ul>
	  
	  <footer>
	  	<div class="footer-copyright">&copy; 2014 中国教育电视台  京ICP证101020号</div>
	  </footer>
      
    </div>
	
	
  </body>
</html>
