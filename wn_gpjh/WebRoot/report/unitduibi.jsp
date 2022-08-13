<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!--<![endif]-->
<head>
<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>对比统计</title>
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
							var q = '<s:property value="#parameters.questionid"/>';

							//加载项目
							function loadProject() {
								$
										.post(
												'unitNormal_getInitProgame.action',
												function(r) {
													var opht = "";
													for ( var i = 0; i < r.length; i++) {
														var ht = '<option value="'
																+ r[i].pid
																+ '"';

														if (p == r[i].pid) {
															ht += " selected ";
														}

														ht += '>' + r[i].pname
																+ '</option>';
														opht += ht;
													}
													projectId = r[0].pid;
													$('#project').html(opht);
													loadSubject(projectId);
													loadquestion(projectId);
												});
							}

							function loadSubject(id) {
								$
										.post(
												'unitNormal_selectSubject.action',
												{
													'projectid' : id
												},
												function(r) {
													var opht = '<option value="0">所有学科</option>';
													for ( var i = 0; i < r.length; i++) {
														var ht = '<option value="'
																+ r[i].sid
																+ '"';
														if (s == r[i]) {
															ht += " selected ";
														}
														ht += '>' + r[i].sname
																+ '</option>';
														opht += ht;
													}
													$('#subject').html(opht);

												});
							}

							function loadquestion(id) {
								$
										.post(
												'unitNormal_selectQuestion.action',
												{
													'projectid' : id
												},
												function(r) {
													var opht = '<option value="0">所有题目</option>';
													for ( var i = 0; i < r.length; i++) {
														var ht = '<option value="'+r[i].sid+'">';
														if (q == r[i]) {
															ht += " selected ";
														}

														ht += '>' + r[i].sname
																+ '</option>';
														opht += ht;
													}

													$('#question').html(opht);

												});
							}

							function getQueryString(name) {
								var reg = new RegExp("(^|&)" + name
										+ "=([^&]*)(&|$)", "i");
								var r = window.location.search.substr(1).match(
										reg);
								if (r != null)
									return unescape(r[2]);
								return null;
							}

							loadProject();

							var pa = getQueryString('userid');
							$('#upaper').html(
									"<a href=\"unitNormal_forwardToPaper.action?userid="
											+ pa + "\">每题统计</a>");
							$('#huizong').html(
									"<a href=\"unitNormal_forward.action?userid="
											+ pa + "\">汇总统计</a>");

						});

						function selectSubject(id) {
							$.post('unitNormal_selectSubject.action', {
								'projectid' : id
							}, function(r) {
								var opht = '<option value="0">所有学科</option>';
								for ( var i = 0; i < r.length; i++) {
									var ht = '<option value="'+r[i].sid+'">'
											+ r[i].sname + '</option>';
									opht += ht;
								}
								$('#subject').html(opht);

							});

							$.post('unitNormal_selectQuestion.action', {
								'projectid' : id
							}, function(r) {
								var opht = '<option value="0">所有题目</option>';
								for ( var i = 0; i < r.length; i++) {
									var ht = '<option value="'+r[i].sid+'">'
											+ r[i].sname + '</option>';
									opht += ht;
								}

								$('#question').html(opht);

							});
						}
					</script>
</head>
<body>
	<div class="wrapper">
		<div class="nav">
			<ul class="clearfix">

				<li class="cur" id="huizong"><a href="#">汇总统计</a></li>
				<li class="cur" id="upaper"><a href="#">每题统计</a></li>
				<li class="cur"><a href="#">对比统计</a></li>

			</ul>
		</div>
		<div class="fliter">
			<div>
				<form action="unitNormal_duibi.action" method="get">
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
							</span> </span> <span>题目： <select id="question" name="questionid"
								style="width:230px;">
									<option value="0">所有题目</option>
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
			<div class="ctt_bd">

				<s:set name="flag" value="0" />
				<s:iterator value="duibiCountMap" id="question">

					<s:if test="#question[3] != #flag">
						<div class="section clearfix" style="border:0;padding:0;">
							<h2 style="margin-left:10px;margin-top:10px;margin-bottom:20px;">
								第
								<s:property value="#question[3]" />
								题：
								<s:property value="#question[2]" />
							</h2>

							<table class="table">
								<thead>
									<tr>
										<th>序号</th>
										<th>项目名称</th>
										<th>承训单位</th>
										<th>总体满意度</th>

									</tr>
								</thead>
								<tbody>
									<s:set name="t" value="1" />
									<s:iterator value="duibiCountMap" id="q">
										<s:if test="#question[3] == #q[3]">
											<tr>
												<td><s:property value="#t" /></td>
												<td><s:property value="#q[0]" /></td>
												<s:if test="#q[5]==1">
													<td><span style="color:red;"><s:property
																value="#q[1]" /></span></td>
												</s:if>
												<s:else>
													<td>**学校</td>
												</s:else>

												<td><s:property value="#q[4]" /></td>
											</tr>
											<s:set name="t" value="#t+1" />
										</s:if>
									</s:iterator>
								</tbody>
							</table>
						</div>
					</s:if>
					<s:set name="flag" value="#question[3]" />
				</s:iterator>

			</div>
		</div>
	</div>

</body>
</html>