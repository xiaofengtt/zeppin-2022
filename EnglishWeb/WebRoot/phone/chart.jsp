<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page import="cn.zeppin.entity.User" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>初中英语</title>
	<jsp:include page="head.jsp"></jsp:include>
    <script src="../js/m/chartist.min.js"></script>
  </head>
  <body>

    <!-- Make sure all your bars are the first things in your <body> -->
    <header class="bar bar-nav bar-nav-gs">
    	<a class="icon icon-gear pull-right" href="../phone/userSearchGrade?show=1"></a>
    	<a class="pull-left" style="z-index:20;position:relative" href="../phone/myMyIndex"><span class="icon icon-person"></span> 
			<% out.print(((User)session.getAttribute("userphone")).getName());  %>
		</a>
      <h1 class="title"><img class="logo" src="../img/m/logo-text.png" alt="果实网"></h1>
    </header>

    <!-- Wrap all non-bar HTML in the .content div (this is actually what scrolls) -->
   
    <div class="content">
     <div class="step">初中英语</div>
	 <div class="chart-padded">
	 	
		 <div>
		 <div style="margin-left:20px;" class="purple">得分趋势</div>
		 	<div id="score-chart" style="height:200px" class="ct-chart"></div>
		 	
		 	<script type="text/javascript">
		 		
		 		Zepto(function($){
		 			$.post('../phone/phPaperLoadScore',function(ret) {
						
		 				if(ret.Status=='success')
		 				{
			 				var labels=[];
							var data=[];
							
							$.each(ret.Records,function(i,v){
								labels.push(i);
								data.push(v.score);
							});
							var options = {
							  chartPadding:0,
							  axisX: {
							    labelInterpolationFnc: function(value, index) {
							      return index % 2 === 0 ? value : null;
							    },
							    showLabel: true
							  },
							  axisY: {
							  	offset: 30
							    
							  }
							};
							var data = {
							  labels: labels,
							    series: [
							    data
							  ]
							};
							new Chartist.Line('.ct-chart', data,options);
		 				}
		 				else{
		 					alert(ret.Message);
		 					window.location.href="../phone";
		 				}
						
					})
		 			
		 		});
		 		
		 	</script>
		 </div>
		 <div class="chart-num" style="display:none;">
		 	<span>预测分<br>50</span>
			<span>答题量<br>60/100</span>
			<span>正确率<br>78%</span>
		 </div>
	 </div>
	 <ul class="table-view start-grid">
	   <li class="table-view-cell media">
	     <a class="navigate-right" data-transition="slide-in" href="../phone/phCapterLoadCapter" >
		   <span class="pull-left book-icon"><i class="fa fa-book fa-lg"></i></span>
	       <div class="media-body">
	         	教材同步练习
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media">
	     <a class="navigate-right" data-transition="slide-in" href="../phone/phKnowLoadKnowledge" data-transition="slide-in">
	     
	    	<span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
			 
	       <div class="media-body">
	         	知识点专项练习
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media">
	     <a class="navigate-right" href="../phone/phPaperRandomPaper" data-transition="slide-in">
	     	<span class="pull-left book-icon"><i class="fa fa-file-text fa-lg"></i></span>
	      
	       <div class="media-body">
	         	智能组卷模拟测验
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media"> 
	     <a class="navigate-right" href="../phone/phPaperLoadPaper" data-transition="slide-in">
	     <span class="pull-left book-icon"><i class="fa fa-paste fa-lg"></i></span>
	      
	       <div class="media-body">
	         	历届真题模拟测验
	       </div>
	     </a>
	   </li>
	  
	 </ul>
	  
	  <footer>
	  	<div class="footer-copyright">&copy; 2014 中国教育电视台  京ICP证101020号</div>
	  </footer>
      
    </div>
	
	
  </body>
</html>
