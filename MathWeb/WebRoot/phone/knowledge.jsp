<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="org.apache.struts2.components.Include"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
	<title>选择知识点进行练习</title>
	<jsp:include page="head.jsp"></jsp:include>
	 <!--link rel="stylesheet" type="text/css" href="../css/m/addtohomescreen.css">
	<script src="../js/m/addtohomescreen.js"></script>
	<script>
		addToHomescreen();
	</script-->
</head>
<body>

	<!-- Make sure all your bars are the first things in your <body> -->
	<header class="bar bar-nav bar-nav-gs">
		<a class="btn btn-link btn-nav pull-left" href="../phone/chart.jsp"><span class="icon icon-left-nav"></span>返回</a>
		<h1 class="title">
			<img class="logo" src="../img/m/logo-text.png" alt="果实网">
		</h1>
	</header>

	<!-- Wrap all non-bar HTML in the .content div (this is actually what scrolls) -->
	<div class="content">
		<div class="step">选择知识点进行练习</div>
		<div class="chart-padded">
		
			<s:iterator value='lstKnowledges' id='kl'>
			
				<ul class="list">
					
					<li <s:if test="%{#kl.childs.size()==0}">class="nochild"</s:if>>
						<s:if test="%{#kl.childs.size()>0}">
							<i class="doticon fa fa-plus-circle"></i>
						</s:if>
						<div class="list-title"><s:property value='#kl.name'  /></div>
						<div class="list-progress">
							<span class="progress-label">掌握度</span> 
							
							<s:set name="degree" value='currentDegree.get(#kl.id)' />
							<span class="progress"><span style="width:<s:property value='#degree*100' />%" class="progress-bar"></span></span>
							<span class="progress-percent"><s:property value='formatDouble(#degree)' /></span>
						</div> 
						
						<a href="../phone/phPaperGroupPaper?knowledge.id=<s:property value="#kl.id" />" class="icon icon-compose"></a>
						
						
						<s:if test="%{#kl.childs.size()>0}">
							<ul>
								
								<s:iterator value='#kl.childs' id='klex'>
									
									<li <s:if test="%{#klex.childs.size()==0}">class="nochild"</s:if>>
										<s:if test="%{#klex.childs.size()>0}">
											<i class="doticon fa fa-plus-circle"></i>
										</s:if>
										<div class="list-title"><s:property value='#klex.name'  /></div>
										<div class="list-progress">
											<span class="progress-label">掌握度</span> 
											
											<s:set name="degree" value='currentDegree.get(#klex.id)' />
											<span class="progress"><span style="width:<s:property value='#degree*100' />%" class="progress-bar"></span></span>
											<span class="progress-percent"><s:property value='formatDouble(#degree)' /></span>
										</div> 
										<a href="../phone/phPaperGroupPaper?knowledge.id=<s:property value="#klex.id" />" class="icon icon-compose"></a>
									
									<s:if test="%{#klex.childs.size()>0}">
										<ul>
											
											<s:iterator value='#klex.childs' id='klext'>
												<li class="nochild">
													<div class="list-title"><s:property value='#klext.name'  /></div>
													<div class="list-progress">
														<span class="progress-label">掌握度</span> 
														
														<s:set name="degree" value='currentDegree.get(#klext.id)' />
														<span class="progress"><span style="width:<s:property value='#degree*100' />%" class="progress-bar"></span></span>
														<span class="progress-percent"><s:property value='formatDouble(#degree)' /></span>
													</div> 
													<a href="../phone/phPaperGroupPaper?knowledge.id=<s:property value="#klext.id" />" class="icon icon-compose"></a>								
													
												</li>
											</s:iterator>
											
										</ul>
									</s:if>
									</li>
									
								</s:iterator>
								
							</ul>
						</s:if>
					
					</li>
				</ul>
			
			</s:iterator>
		 
		</div>


		<footer>
			<div class="footer-copyright">&copy; 2014 中国教育电视台 京ICP证101020号</div>
		</footer>

	</div>

</body>
</html>
