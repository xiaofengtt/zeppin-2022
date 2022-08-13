<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>答题情况</title>
    <jsp:include page="head.jsp"></jsp:include>
    <script src="../js/m/chartist.min.js"></script>
    <script src="../js/mustache.js"></script>
	
  </head>
  <body>

    <!-- Make sure all your bars are the first things in your <body> -->
    <header class="bar bar-nav bar-nav-gs">
    
    	<s:if test="%{clientType==1}">
    		<a class="btn btn-link btn-nav purple pull-left" data-transition="slide-out" href="../phone/chart.jsp">
			    <span class="icon icon-left-nav"></span>
			    	返回首页
			</a>
    	</s:if>
		<h1 class="title purple">测评结果</h1>
		
		<s:if test="%{clientType==1}">
    		<a data-transition="slide-out" class="btn btn-link btn-nav purple pull-right" href="../phone/phPaperLoadAnswerShow?usertest.id=<s:property value='usertestId' />">
			    	错题解析
			</a>
    	</s:if>
		
    </header>

    <!-- Wrap all non-bar HTML in the .content div (this is actually what scrolls) -->
    <div class="content">
    	<div class="padded">
			<div class="circle-chart">
				<div class="circle">
				
					<div id="thistimescore" class="ct-chart finalresults">
						
					</div>
					<div class="getscore">
						<span class="score"><s:property value='currentUserTest.strScore' /></span>
						<span class="split"></span>
						<span class="totalscore"><s:property value='currentUserTest.totalScore' /><span class="f12">分</span></span>
					</div>
					<script type="text/javascript">
					
						Zepto(function($){
							
							new Chartist.Pie('#thistimescore', {
							  series: [<s:property value='currentUserTest.strScore' />,(parseInt(<s:property value='currentUserTest.totalScore' />)-parseInt(<s:property value='currentUserTest.strScore' />))]
							}, {
							  donut: true,
							  donutWidth: 12,
							  startAngle: 0,
							  total: 0,
							  width : '120px',
							  height: '120px',
							  showLabel: false
							});
						})
					</script>
					<div class="text">模考得分</div>
				</div>
				<ul>
					<li>全网平均得分：<strong> <s:property value='currentUserTest.avgScore' /> 分</strong></li>
					<li>击败考生：<strong><s:property value='currentUserTest.strLowerSvore' /></strong></li>
					<li>完成答题：<strong><s:property value='currentUserTest.okItem' />/<s:property value='currentUserTest.totalItem' /></strong></li>
				</ul>
			</div>
    	</div>
		
		<div class="padded">
			<div class="circle-chart">
				<div class="circle">
					<div id="thistimetime" class="ct-chart finalresults">
						
					</div>
					<div class="getscore">
						<span class="score"><s:property value='currentUserTest.time' /></span>
						<span class="split"></span>
						<span class="totalscore"><s:property value='currentUserTest.answerTime' /><span class="f12">分</span></span>
					</div>
					<script type="text/javascript">
						Zepto(function($){
							//console.log(parseInt(<s:property value='currentUserTest.answerTime' />/60));
							//if()
							new Chartist.Pie('#thistimetime', {
							  series: [parseInt(<s:property value='currentUserTest.time' />),(parseInt(<s:property value='currentUserTest.answerTime' />) - parseInt(<s:property value='currentUserTest.time' />))]
							}, {
							  donut: true,
							  donutWidth: 12,
							  startAngle: 0,
							  total: 0,
							  width : '120px',
							  height: '120px',
							  showLabel: false
							});
						})
					</script>
					<div class="text">考试用时</div>
				</div>
				<ul>
					<li>全网平均用时：<strong><s:property value='currentUserTest.strAvgTime' /></strong></li>
					<li>击败考生：<strong><s:property value='currentUserTest.strLowerTime' /></strong></li>
					<li>规定时间得分：<strong><s:property value='currentUserTest.strScore' />分</strong></li>
				</ul>
			</div>
		</div>
		
    	<div class="padded">
			<div class="results-type">答题结果</div>
			<div class="results-list final-results">
				
				<s:iterator value='currentUserTest.mapUti' id='utiex'>
					
					<a href="#">
						<span <s:if test='%{value.flag}'></s:if><s:else>class="error"</s:else> ><s:property value='value.inx' /></span>
					</a>
					
				</s:iterator>
				
			</div>
    	</div>
		
		<div class="padded">
			<div class="results-type">知识点掌握</div>
			 <table class="kn-master">
			 	<thead>
					<tr>
						<th class="tl">考点</th>
						<th>答题量</th>
						<th>正确率</th>
					</tr>
					
				</thead>
				<tbody>
				<s:iterator value='currentUserTest.lstUtk' id='utk' >
					<tr>
						<td class="tl"><s:property value='value.knowledgeName'  /></td>
						<td><s:property value='value.okCount'  />/<s:property value='value.totalCount'  />题 </td>
						<td><s:property value='value.strPrecent'  /></td>
					</tr>
				</s:iterator> 
				</tbody>
				
			 </table>
			
		</div>
		
		<div class="padded">
			
			<div class="results-type">推荐课程</div>
			<table class="kn-master" id="xuexikecheng">
			</table>
			
		</div>
		
		<script id="leftTpl" type="text/template">
			{{#result}}
			<tr>
				<td>{{name}}</td>
			</tr>			
			{{/result}}
		</script>
		
		<script type="text/javascript">
			$.getJSON('../phone/phPaperLoadApi', function(r) {
				if(r.result.length > 0) {
				    var template = $('#leftTpl').html();
				    var html = Mustache.render(template, r);
				    $('#xuexikecheng').html(html);
				}else {
					alert('服务端出错！请稍后重试！');
				}
			});
		</script>
    	
	  <footer>
	  	<div class="footer-copyright">&copy; 2014 中国教育电视台  京ICP证101020号</div>
	  </footer>
      
    </div>
	
	
	
  </body>
</html>