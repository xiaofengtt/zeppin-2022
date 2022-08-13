<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>选择教材</title>
	<jsp:include page="head.jsp"></jsp:include>
  </head>
  <body>

    <!-- Make sure all your bars are the first things in your <body> -->
    <header class="bar bar-nav bar-nav-gs">
    	<a class="btn btn-link btn-nav pull-left" href="../phone/userSearchGrade?show=<s:property value='showback' />"><span class="icon icon-left-nav"></span>返回</a>
      <h1 class="title"><img class="logo" src="../img/m/logo-text.png" alt="果实网"></h1>
    </header>

    <!-- Wrap all non-bar HTML in the .content div (this is actually what scrolls) -->
    <div class="content">
     <div class="step">第二步:选择教材</div>
	 <div id="step2">
		 <ul class="table-view">
		 <s:iterator value='textbookMap' id='area' >
		   <li class="table-view-cell media">
		     <a class="navigate-right" href="../phone/userUpdateTextbook?textbook.id=<s:property value='key' />&show=<s:property value='showback' />">
		       <div class="pull-left imgbox"><img class="media-object pull-left" src="http://58.215.40.149/CETV_TEST/<s:property value='value.picture'/>"></div>
		       <div class="media-body">
		         <div class="texttitle"><s:property value='value.name' /></div>
				 <div class="textdes">
			         <p><s:property value='value.version' /></p>
					 <p><s:property value='value.publisher' /></p>
				 </div>
		       </div>
		     </a>
		   </li> 
		 </s:iterator>
		 </ul>
		 <div class="step-dot">
			 
		 	<span></span>
			<span class="cur"></span>
			<span></span>
		 </div>
	 </div>
	  
	  <footer>
	  	<div class="footer-copyright">&copy; 2014 中国教育电视台  京ICP证101020号</div>
	  </footer>
      
    </div>
	
	
  </body>
</html>
