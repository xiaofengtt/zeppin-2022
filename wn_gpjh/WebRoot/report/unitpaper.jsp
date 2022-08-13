<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!--<![endif]-->
<head>
<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>每题统计</title>
		<meta name="description" content="">
			<meta name="viewport" content="width=device-width">

				<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

				<link rel="stylesheet" href="css/style.css">
					<script type="text/javascript" src="https://www.google.com/jsapi"></script>
					<script type="text/javascript" src="./js/jquery-1.9.1.min.js"></script>
					<script type="text/javascript">
	//页面加载完
	$(function() {

		var projectId = "";
		var subjectId = "";
		
		var p = '<s:property value="#parameters.projectid"/>';
		var s = '<s:property value="#parameters.subjectid"/>';

		//加载项目
		function loadProject() {
			$.post('unitNormal_getInitProgame.action', function(r) {
				var opht = "";
				for ( var i = 0; i < r.length; i++) {
					var ht = '<option value="'+r[i].pid+'"';
					
					if (p == r[i].pid) {
						ht += " selected ";
					}
					
					
					 ht +='>'+ r[i].pname+ '</option>';
					opht += ht;
				}
				projectId = r[0].pid;
				$('#project').html(opht);
				loadSubject(projectId);
			});
		}

		function loadSubject(id) {
			$.post('unitNormal_selectSubject.action', {
				'projectid' : id
			}, function(r) {
				var opht = '<option value="0">所有学科</option>';
				for ( var i = 0; i < r.length; i++) {
					var ht = '<option value="'+r[i].sid+'"';
					
					if (s == r[i]) {
													ht += " selected ";
														}
					
					 ht+='>'+ r[i].sname	+ '</option>';
					opht += ht;
				}
				projectId = r[0].pid;

				$('#subject').html(opht);

			});
		}

		function getQueryString(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
			var r = window.location.search.substr(1).match(reg);
			if (r != null)
				return unescape(r[2]);
			return null;
		}

		loadProject();

		var pa = getQueryString('userid');
		$('#upaper').html(
				"<a href=\"unitNormal_forward.action?userid=" + pa
						+ "\">汇总统计</a>");
		$('#duibi').html(
				"<a href=\"unitNormal_forwardToDuibi.action?userid=" + pa
						+ "\">对比统计</a>");

	});

	function selectSubject(id) {
		$.post('unitNormal_selectSubject.action', {
			'projectid' : id
		}, function(r) {
			var opht = '<option value="0">所有学科</option>';
			for ( var i = 0; i < r.length; i++) {
				var ht = '<option value="'+r[i].sid+'">' + r[i].sname
						+ '</option>';
				opht += ht;
			}
			projectId = r[0].pid;

			$('#subject').html(opht);

		});
	}
	
</script>

					<script type="text/javascript" src="https://www.google.com/jsapi"></script>
					<script type="text/javascript">
	google.load("visualization", "1", {
		packages : [ "corechart" ]
	});
</script>
</head>
<body>
	<div class="wrapper">
		<div class="nav">
			<ul class="clearfix">

				<li class="cur" id="upaper"><a href="#">汇总统计</a></li>
				<li class="cur"><a href="#">每题统计</a></li>
				<li class="cur" id="duibi"><a href="#">对比统计</a></li>

			</ul>
		</div>
		<div class="fliter">
			<div>
				<form action="unitNormal_paper.action" method="get">
					<fieldset>
						<div class="group">
							<span>项目名称： <select id="project" name="projectid"
								style="width:260px" onchange="selectSubject(this.value)">
									<option value="0">所有项目</option>
							</select>
							</span> <span>学科： <select id="subject" name="subjectid"
								style="width:230px;">
									<option value="0">所有学科</option>
							</select>
							</span>
						</div>
						<div class="search-btn group" style="width:70px;margin-top:30px">
							<input type="hidden" name="userid"
								value="<s:property value="#parameters.userid"/>" />
							<button type="submit" name="submit">筛选</button>
						</div>
					</fieldset>
				</form>
			</div>
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
											<input type="hidden" value='<s:property value="#count" />' />
											<input type="hidden" value='<s:property value="answerCountMap.get(#question.id + '_' + #answer.id)[0]" />' />
											<input type="hidden" value='<s:property value="#question.id" />' />
											<input type="hidden" value='<s:property value="#answer.id" />' />
											<s:set name="percent"
												value="answerCountMap.get(#question.id + '_' + #answer.id)[1]" />
											<input type="hidden" value='<s:property value="#percent" />' />
											<input type="hidden" value='<s:property value="answerCountMap.get(#question.id + '_' + #answer.id)[1]" />' />
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
							
							        };
							
							        var chart = new google.visualization.PieChart(document.getElementById('pie_<s:property value="#question.id"/>'));
							        chart.draw(data, options);
							</script>
							</div>

						</div>
					</s:if>
				</s:iterator>
			</div>
		</div>
	</div>

</body>
</html>