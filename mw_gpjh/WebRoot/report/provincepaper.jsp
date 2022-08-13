<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>每题统计</title>
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
		<div class="nav">
			<ul class="clearfix">
				<li class="cur"><a href="#">每题统计</a></li>
				<li><a
					href="province_huizong.action?userid=<s:property value="#parameters.userid"/>">汇总统计</a></li>
				<li><a
					href="province_duibi.action?userid=<s:property value="#parameters.userid"/>">对比统计</a></li>
				<li><a
					href="province_huizongprovince.action?userid=<s:property value="#parameters.userid"/>">省内汇总统计</a></li>
				<li><a
					href="province_duibip.action?userid=<s:property value="#parameters.userid"/>">省内对比统计</a></li>
			</ul>
		</div>
		<div class="fliter">
			<form action="province_paper.action" method="get">
				<fieldset>
					<div class="group">
						<span>项目名称： <select name="project" style="width:260px">
								<option value="0">所有项目</option>
								<s:set name="a" value="#parameters.project" />
								<s:iterator value="projects" id="p">
									<option value="<s:property value="#p[0]"/>"
										<s:if test="%{#p[0]==project}">selected</s:if>>
										<s:property value="#p[1]" />
									</option>
								</s:iterator>
						</select>
						</span> <span>承训单位： <select name="unit" style="width:260px">
								<option value="0">所有承办单位</option>
								<s:iterator value="units" id="unit">
									<option value="<s:property value="#unit[0]"/>"
										<s:if test="%{#unit[0] == unit}">selected</s:if>>
										<s:property value="#unit[1]" />
									</option>
								</s:iterator>
						</select>
						</span>
					</div>
					<div class="group" style="margin-right:20px">
						<span>子项目： <select name="subproject" style="width:230px;">
								<option value="0">所有子项目</option>
								<s:iterator value="subprojects" id="subproject">
									<option value="<s:property value="#subproject[0]"/>"
										<s:if test="%{#subproject[0] == subproject}">selected</s:if>>
										<s:property value="#subproject[1]" />
									</option>
								</s:iterator>
						</select>
						</span> <span>学科： <select name="subject" style="width:230px;">
								<option value="0">所有学科</option>
								<s:iterator value="subjects" id="subject">
									<option value="<s:property value="#subject[0]"/>"
										<s:if test="%{#subject[0] == subject}">selected</s:if>>
										<s:property value="#subject[1]" />
									</option>
								</s:iterator>
						</select>
						</span>
					</div>
					<input type="hidden" name="userid"
						value="<s:property value="#parameters.userid"/>" />
					<div class="search-btn group" style="width:70px;margin-top:30px">
						<button type="submit" name="submit">筛选</button>
					</div>
				</fieldset>
			</form>
		</div>
		<div class="content">
			<div class="ctt_hd">
				<s:property value="paper.name" />
			</div>
			<div class="ctt_bd">
				<h1>
					问卷名称：
					<s:property value="paper.title" />
				</h1>

				<s:iterator value="paper.questions" id="question">

					<s:if
						test="#question.type != 0 && #question.type!=6 && #question.type!=7 ">

						<div class="section clearfix">
							<h2 style="width:700px">
								第
								<s:property value="#question.inx" />
								题：
								<s:property value="#question.name" />
							</h2>
							<div class="que_type">题型：评分选择题</div>
							<div class="table_txt">
								<table class="table">
									<thead>
										<tr>
											<th>选项</th>
											<th>投票数</th>
											<th>比例</th>
										</tr>
									</thead>
									<tbody>
										<s:set name="flag" value="1" />
										<s:iterator value="#question.answers" id="answer">
											<s:if test="%{#flag%2 == 0}">
												<tr class="even">
											</s:if>
											<s:else>
												<tr>
											</s:else>
											<td class="label"><s:property value="#answer.name" /></td>
											<s:set name="count"
												value="answerCountMap.get(#question.id + '_' + #answer.id)[0]" />
											<s:set name="percent"
												value="answerCountMap.get(#question.id + '_' + #answer.id)[1]" />
											<s:if test="#count==null">
												<s:set name="count" value="0" />
											</s:if>

											<td><s:property value="#count" /></td>
											<s:if test="#percent==null">
												<td>0%</td>
											</s:if>
											<s:else>
												<td><s:property value="#percent" /></td>
											</s:else>
											</tr>
											<s:set name="flag" value="#flag+1" />
										</s:iterator>
									</tbody>
								</table>
							</div>

							<div class="table_img">
								<ul class="tabnav clearfix">

								</ul>

								<div class="tabcontainer">
									<div id="bar_<s:property value="#question.id"/>" class="tab"></div>
									<div id="pie_<s:property value="#question.id"/>"
										class="tab current"></div>
									<div id="line_<s:property value="#question.id"/>" class="tab">
										<img src="img/t3.jpg" alt>
									</div>
								</div>

								<script language="javascript">		  
        var data = google.visualization.arrayToDataTable([
		  ['Task', 'Hours per Day'],
		<s:set name="flag" value="1"/>
		<s:iterator value="#question.answers" id="answer">
		<s:set name="count" value="answerCountMap.get(#question.id + '_' + #answer.id)[0]"/>
		<s:if test="#count==null"><s:set name="count" value="0"/></s:if>
		<s:if test="#question.answers.size == #flag">
		['<s:property value="#answer.name"/>', <s:property value="#count"/>]
		</s:if>
		<s:else>
		['<s:property value="#answer.name"/>', <s:property value="#count"/>],
		</s:else>
		<s:set name="flag" value="#flag+1"/>
		</s:iterator>
        ]);

        var options = {
          title: '',
          width: 500,
          height:375,
          chartArea:{left:100, top:30, width:400, height:300},
          legend:{position:'right', alignment:'top'},
          //backgroundColor:'red',
          //is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('pie_<s:property value="#question.id"/>'));
        chart.draw(data, options);
</script>
							</div>

						</div>
					</s:if>
				</s:iterator>
				<!-- <div class="pagiation">共5页 |<a href="#"><首页</a>  < <a href="#">上一页</a> <a href="#">1</a>   2   3   4   5 下一页>  尾页>|</div> -->
			</div>
		</div>
	</div>
	<script src="js/jquery-1.9.1.min.js"></script>

	<script>
	 $('ul.tabnav a').click(function() {
	 		var curChildIndex = $(this).parent().prevAll().length + 1;
	 		$(this).parent().parent().children('.current').removeClass('current');
	 		$(this).parent().addClass('current');
	 		$(this).parent().parent().next('.tabcontainer').children('.current').fadeOut('fast',function() {
	 			$(this).removeClass('current');
	 			$(this).parent().children('div:nth-child('+curChildIndex+')').fadeIn('normal',function() {
	 				$(this).addClass('current');
	 			});
	 		});
	 		return false;								
	 	});
	 </script>
</body>
</html>
