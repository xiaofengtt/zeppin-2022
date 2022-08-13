<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>完成率分析</title>
<meta name="description" content="">
<meta name="viewport" content="width=device-width">

<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

<link rel="stylesheet" href="css/style.css">
<%--   <script type="text/javascript" src="https://www.google.com/jsapi"></script>
    <script type="text/javascript">
      google.load("visualization", "1", {packages:["corechart"]});
      //google.setOnLoadCallback(drawChart);
    </script> --%>
</head>
<body>
	<div class="wrapper">
		<div class="header">
			<s:property value="paper.title" />
		</div>
		<div class="nav">
			<ul class="clearfix">
				<li><a
					href="report_summary.action?pid=<s:property value="#parameters.pid"/>">原始数据</a></li>
				<li><a
					href="report_paper.action?pid=<s:property value="#parameters.pid"/>">每题统计</a></li>
				<li><a
					href="report_huizong.action?pid=<s:property value="#parameters.pid"/>">汇总统计</a></li>
				<li><a href="report_manyilv.action?pid=<s:property value="#parameters.pid"/>">满意率</a></li>
				<li><a
					href="report_duibi.action?pid=<s:property value="#parameters.pid"/>">对比统计</a></li>
				<li class="cur"><a href="#">完成率分析</a></li>
			</ul>
		</div>
		<div class="fliter">
			<form action="report_wanchenglv.action" method="post">
				<fieldset>
					<div class="group">
						<span>项目名称： <select name="project" style="width:350px">
								<option value="0">所有项目</option> -->
								<s:set name="a" value="#projectes" />
								<s:iterator value="projectes" id="item">
									<option value="<s:property value="#item[0]"/>"
										<s:if test="%{#item[0]==project}">selected</s:if>>
										<s:property value="#item[1]" />
									</option>
								</s:iterator>
						</select>
						</span> &nbsp; <span><label>完成率（小于）:</label><input type="text"
							id="completePer" name='completePer' value="${fCompletePer}" />%</span>
						<!-- 						<span style="display:none;">承训单位： -->
						<!-- 							<select name="unit" style="width:260px"> -->
						<!-- 							<option value="0">所有承办单位</option> -->
						<!-- 							<s:iterator value="units" id="unit"> -->
						<!-- 								<option value="<s:property value="id"/>" <s:if test="%{id == unit}">selected</s:if>><s:property value="name"/></option> -->
						<!-- 							</s:iterator> -->
						<!-- 							</select> -->
						<!-- 						</span> -->
					</div>
					<!-- 					<div class="group" style="margin-right:20px;display=none;"> -->
					<!-- 						<span>学科： -->
					<!-- 							<select name="subject" style="width:230px;"> -->
					<!-- 							<option value="0">所有学科</option> -->
					<!-- 							<s:iterator value="subjects" id="subject"> -->
					<!-- 								<option value="<s:property value="id"/>" <s:if test="%{id == subject}">selected</s:if>><s:property value="name"/></option> -->
					<!-- 							</s:iterator> -->
					<!-- 							</select> -->
					<!-- 						</span> -->
					<!-- 					</div> -->
					<input type="hidden" name="pid"
						value="<s:property value="#parameters.pid"/>" />
					<div class="search-btn group" style="width:70px;margin-top:30px">
						<button type="submit" name="submit" value="">筛选</button>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="content">
			<%-- <s:if test="wclCountMap.size() > 0"> --%>
			<div class="ctt_bd">

				<div class="section clearfix" style="border:0;padding:0;">
					<h2
						style="color:#2084ca;margin-left:10px;margin-top:10px;margin-bottom:20px;">
						共有
						<s:property value="countVotesum" />
						人参加了此问卷调查，完成比率为
						<s:property value="wclPercent" />
						%
						<span>
							<a
								href="report_wanchenglvDoExcel.action?pid=<s:property value="paper.id" />">导出Excel</a>
						</span>
					</h2>

					<table class="table">
						<thead>
							<tr>
								<th>编号</th>
								<th>项目名称</th>
								<th>承训机构</th>
								<th>学科</th>
								<th>投票<br>人数
								</th>
								<th>已发短信<br>人数
								</th>
								<th>已结业<br>人数
								</th>
								<th>总人数</th>
								<th>投票<br>完成比(%)
								</th>
							</tr>
						</thead>
						<tbody>
							<s:set name="flag" value="1" />
							<%
								int index = 0;
							%>
							<s:iterator value="lstInfo" id="q">
								<tr>
									<td><%=++index%></td>
									<td><s:property value="#q[0]" /></td>
									<td><s:property value="#q[1]" /></td>
									<td><s:property value="#q[2]" /></td>
									<td><s:property value="#q[3]" /></td>
									<td><s:property value="#q[4]" /></td>
									<td><s:property value="#q[5]" /></td>
									<td><s:property value="#q[6]" /></td>
									<td><s:property value="#q[7]" />%</td>
								</tr>
								<s:set name="flag" value="#flag+1" />

							</s:iterator>
						</tbody>
					</table>

				</div>

				<!-- 	<div class="results-wrap" id="chart_div">
						<img src="img/t4.jpg" alt>
					</div> -->

				<%-- 	<script language="javascript">
		var data = google.visualization.arrayToDataTable([
          ['序号', '实到人数', '投票人数'],
 							<s:set name="t" value="1"/>
							<s:iterator value="infoDic" id="q">
          ['<s:property value="key"/>', <s:property value="Value[4]"/>, <s:property value="value[3]"/>],
          					<s:set name="t" value="#t+1"/>
							</s:iterator>
        ]);

        var options = {
          title: '完成率统计',
          //hAxis: {title: 'Year', titleTextStyle: {color: 'red'}},
        };

        var chart = new google.visualization.ColumnChart(document.getElementById('chart_div'));
        chart.draw(data, options);
        </script> --%>
			</div>

		</div>
		<%-- 	</s:if> --%>
	</div>

</body>
</html>
