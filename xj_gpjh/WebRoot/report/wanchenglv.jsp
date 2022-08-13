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
        <title>完成率分析</title>
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
					<li><a href="report_duibi.action?pid=<s:property value="#parameters.pid"/>">对比统计</a></li>
					<li class="cur"><a href="#">完成率分析</a></li>					
				</ul>
			</div>
			<div class="fliter">
				<form action="report_wanchenglv.action" method="get">
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
					</div>
						<input type="hidden" name="pid" value="<s:property value="#parameters.pid"/>"/>
						<div class="search-btn group" style="width:70px;margin-top:30px"><input type="submit" name="submit" value="筛选"></div>
					</fieldset>	
				</form>
			</div>	
			<div class="content">
				<s:if test="wclCountMap.size() > 0">
				<div class="ctt_bd">
					
					<div class="section clearfix" style="border:0;padding:0;">
						<h2 style="color:#2084ca;margin-left:10px;margin-top:10px;margin-bottom:20px;">共有<s:property value="countVotesum"/>人参加了此问卷调查，完成比率为<s:property value="wclPercent"/></h2>
						
						<table class="table">
							<thead>
								<tr>
								    <th>编号</th>
									<th>项目名称</th>
									<th>学科</th>
									<th>承训机构</th>
									<th>计划<br>人数</th>
									<th>实到<br>人数</th>
									<th>投票<br>人数</th>
									<th>投票<br>完成比</th>
								</tr>
							</thead>
							<tbody>
						<s:set name="flag" value="1"/>
						<s:iterator value="wclCountMap" id="q">
								<tr>
								    <td><s:property value="#flag"/></td>
									<td><s:property value="infoDic.get(#q[0])[0]"/></td>
									<td><s:property value="infoDic.get(#q[0])[1]"/></td>
									<td><s:property value="infoDic.get(#q[0])[2]"/></td>
									<td><s:property value="#q[1]"/></td>
									<td><s:property value="#q[2]"/></td>
									<td><s:property value="#q[3]"/></td>
									<td><s:property value="#q[4]"/></td>
								</tr>
							<s:set name="flag" value="#flag+1"/>
						</s:iterator>
							</tbody>
						</table>
        
					</div>
					
					<div class="results-wrap" id="chart_div">
						<img src="img/t4.jpg" alt>
					</div>
					
							<script language="javascript">
		var data = google.visualization.arrayToDataTable([
          ['序号', '实到人数', '投票人数'],
 							<s:set name="t" value="1"/>
							<s:iterator value="wclCountMap" id="q">
          ['<s:property value="#t"/>', <s:property value="#q[2]"/>, <s:property value="#q[3]"/>],
          					<s:set name="t" value="#t+1"/>
							</s:iterator>
        ]);

        var options = {
          title: '完成率统计',
          //hAxis: {title: 'Year', titleTextStyle: {color: 'red'}},
        };

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
        chart.draw(data, options);
        </script>
				</div>
				
			</div>
			</s:if>
		</div>
        
    </body>
</html>
