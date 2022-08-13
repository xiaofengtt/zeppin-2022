<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>对比统计</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width">

        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

       
        <link rel="stylesheet" href="css/style.css">
        
    <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      //google.setOnLoadCallback(drawChart);
    </script>
		
    </head>
    <body>
		<div class="wrapper">
			<div class="header">
				<s:property value="paper.title"/>
			</div>
			<div class="nav">
				<ul class="clearfix">
					<li><a href="report_summary.action?pid=<s:property value="#parameters.pid"/>">原始数据</a></li>
					<li><a href="report_paper.action?pid=<s:property value="#parameters.pid"/>">每题统计</a></li>
					<li><a href="report_huizong.action?pid=<s:property value="#parameters.pid"/>">汇总统计</a></li>
					<li class="cur"><a href="#">对比统计</a></li>
					<li><a href="report_wanchenglv.action?pid=<s:property value="#parameters.pid"/>">完成率分析</a></li>
				</ul>
			</div>
			<div class="fliter">
				<form action="report_duibi.action" method="get">
					<fieldset>
					<div class="group">
						<span>项目名称：
							<select name="project" style="width:260px">
							<option value="0">所有项目</option>
							<s:set name="a" value="#parameters.project"/>
							<s:iterator value="projects">
								<option value="<s:property value="id"/>" <s:if test="%{id==project}">selected</s:if>><s:property value="name"/></option>
							</s:iterator>
							</select>
						</span>
						<span>承训单位：
							<select name="unit" style="width:260px">
							<option value="0">所有承办单位</option>
							<s:iterator value="units" id="unit">
								<option value="<s:property value="id"/>" <s:if test="%{id == unit}">selected</s:if>><s:property value="name"/></option>
							</s:iterator>
							</select>
						</span>
					</div>
					<div class="group" style="margin-right:20px">
						<span>学科：
							<select name="subject" style="width:230px;">
							<option value="0">所有学科</option>
							<s:iterator value="subjects" id="subject">
								<option value="<s:property value="id"/>" <s:if test="%{id == subject}">selected</s:if>><s:property value="name"/></option>
							</s:iterator>
							</select>
						</span>
						<span>题目：
								<select name="question" style="width:230px">
								<option value="0">所有题目</option>
					<s:iterator value="questions" id="question">					
					<option value="<s:property value="id"/>" <s:if test="%{id == qid}">selected</s:if>><s:property value="#question.name"/></option>
					</s:iterator>
					</select>
						</span>
					</div>
						<input type="hidden" name="pid" value="<s:property value="#parameters.pid"/>"/>
						<div class="search-btn group" style="width:70px;margin-top:30px"><input type="submit" name="submit" value="筛选"></div>
					</fieldset>	
				</form>	
			</div>	
			<div class="content">
				
				<div class="ctt_bd">
					
					<s:set name="flag" value="0"/>
					<s:iterator value="duibiCountMap" id="question">
					
					<s:if test="#question[1] != #flag">
					<div class="section clearfix" style="border:0;padding:0;">
						<h2 style="margin-left:10px;margin-top:10px;margin-bottom:20px;">第<s:property value="#question[1]"/>题：<s:property value="#question[2]"/></h2>
						
						<table class="table">
							<thead>
								<tr> 
								    <th>序号</th>
									<th>项目名称</th>                                                                
									<th>学科</th>
									<th>承训机构</th>
									<th>总体满意度</th>
									
								</tr>
							</thead>
							<tbody>
							<s:set name="t" value="1"/>
							<s:iterator value="duibiCountMap" id="q">
							<s:if test="#question[1] == #q[1]">
								<tr>
								    <td><s:property value="#t"/></td>
									<td><s:property value="infoDic.get(#q[0])[0]"/></td>
									<td><s:property value="infoDic.get(#q[0])[1]"/></td>
									<td><s:property value="infoDic.get(#q[0])[2]"/></td>
									<td><s:property value="#q[4]"/></td>
								</tr>
							<s:set name="t" value="#t+1"/>
							</s:if>
							</s:iterator>
							</tbody>
						</table>
						<div class="results-wrap" id="chart_div_<s:property value="#question[3]"/>">
							<!-- <img src="img/t6.jpg" alt> -->
						</div>
						
		<script language="javascript">
		var data = google.visualization.arrayToDataTable([
          ['序号', '满意度'],
 							<s:set name="t" value="1"/>
							<s:iterator value="duibiCountMap" id="q">
							<s:if test="#question[1] == #q[1]">
          ['<s:property value="#t"/>',  <s:property value="#q[4]"/>],
							<s:set name="t" value="#t+1"/>
							</s:if>
							</s:iterator>
        ]);

        var options = {
          title: '<s:property value="#question[2]"/>',
          //hAxis: {title: 'Year', titleTextStyle: {color: 'red'}},

        };

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div_<s:property value="#question[3]"/>'));
        chart.draw(data, options);
        </script>
        
					</div>
					</s:if>
					<s:set name="flag" value="#question[1]"/>
				</s:iterator>
					
				</div>
				
			</div>
		</div>
        
    </body>
</html>