<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>初中数学</title>
	<jsp:include page="head.jsp"></jsp:include>
    <script src="../js/m/chartist.min.js"></script>
  </head>
  <body>

    <!-- Make sure all your bars are the first things in your <body> -->
    <header class="bar bar-nav bar-nav-gs">
    	<a class="icon icon-gear pull-right" href="../phone/userSearchGrade?subject.id=<s:property value='#parameters.subjectId' />"></a>
      <h1 class="title"><img class="logo" src="../img/m/logo-text.png" alt="果实网"></h1>
    </header>

    <!-- Wrap all non-bar HTML in the .content div (this is actually what scrolls) -->
   
    <div class="content">
     <div class="step"><s:property value='#session.get("subject.name")' /></div>
	 <div class="chart-padded">
	 	
		 <div>
		 <div style="margin-left:20px;" class="purple">得分趋势</div>
		 	<div id="score-chart" style="height:200px" class="ct-chart"></div>
		 	
		 	<script type="text/javascript">
		 		
		 		Zepto(function($){
		 			$.post('../phone/phPaperLoadScore',{'subject.id':<s:property value='#parameters.subjectId' />},function(ret) {
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
						
					})
		 			
		 		});
		 		
		 	</script>
		 </div>
		 <div class="chart-num">
		 	<span>预测分<br>50</span>
			<span>答题量<br>60/100</span>
			<span>正确率<br>78%</span>
		 </div>
	 </div>
	 <ul class="table-view start-grid">
	   <li class="table-view-cell media">
	     <a class="navigate-right" data-transition="slide-in" href="../phone/phCapterLoadCapter?subject.id=<s:property value='#parameters.subjectId' />" >
		   <span class="pull-left book-icon"><i class="fa fa-book fa-lg"></i></span>
	       <div class="media-body">
	         	教材同步练习
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media">
	     <a class="navigate-right" data-transition="slide-in" href="../phone/phKnowLoadKnowledge?subject.id=<s:property value='#parameters.subjectId' />" data-transition="slide-in">
	     
	    	<span class="pull-left book-icon"><i class="fa fa-file-text-o fa-lg"></i></span>
			 
	       <div class="media-body">
	         	知识点专项练习
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media">
	     <a class="navigate-right" href="../phone/phPaperRandomPaper?subject.id=<s:property value='#parameters.subjectId' />" data-transition="slide-in">
	     	<span class="pull-left book-icon"><i class="fa fa-file-text fa-lg"></i></span>
	      
	       <div class="media-body">
	         	智能组卷模拟测验
	       </div>
	     </a>
	   </li>
	   <li class="table-view-cell media"> 
	     <a class="navigate-right" href="../phone/phPaperLoadPaper?subject.id=<s:property value='#parameters.subjectId' />" data-transition="slide-in">
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
