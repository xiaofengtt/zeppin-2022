<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!--<![endif]-->
<head>
<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>汇总统计</title>
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

							//加载项目
							function loadProject() {
								$
										.post(
												'unitNormal_getInitProgame.action',
												function(r) {
													var opht = "";
													for ( var i = 0; i < r.length; i++) {
														var ht = '<option value="'+r[i].pid+'">'
																+ r[i].pname
																+ '</option>';
														opht += ht;
													}
													projectId = r[0].pid;
													$('#project').html(opht);
													loadSubject(projectId);
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
														var ht = '<option value="'+r[i].sid+'">'
																+ r[i].sname
																+ '</option>';
														opht += ht;
													}
													projectId = r[0].pid;

													$('#subject').html(opht);

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

							$('#duibi').html(
									"<a href=\"unitNormal_forwardToDuibi.action?userid="
											+ pa + "\">对比统计</a>");

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
								projectId = r[0].pid;

								$('#subject').html(opht);

							});
						}

						function search() {
							var projectid = $("#project option:selected").val();
							var subjectid = $("#subject option:selected").val();

							$
									.post(
											"unitNormal_huizong.action",
											{
												"projectid" : projectid,
												"subjectid" : subjectid
											},
											function(r) {

												var ht = ""

												for ( var i = 0; i < r.length; i++) {
													var index = i + 1;
													if (r[i].flag == 1) {
														var t1 = "<tr>"
																+ "<td>"
																+ index
																+ "</td>"
																+ "<td>"
																+ r[i].projectName
																+ "</td>"
																+ "<td><span style=\"color:red;\">"
																+ r[i].unitName
																+ "</span></td>"
																+ "<td>"
																+ r[i].score
																+ "</td>"
																+ "</tr>";
														ht += t1;
													} else {
														var t = "<tr>"
																+ "<td>"
																+ index
																+ "</td>"
																+ "<td>"
																+ r[i].projectName
																+ "</td>"
																+ "<td>"
																+ "**学校"
																+ "</td>"
																+ "<td>"
																+ r[i].score
																+ "</td>"
																+ "</tr>";
														ht += t;
													}

												}
												$('#datamanyi').html(ht);
											});
						}
					</script>
</head>
<body>
	<div class="wrapper">
		<div class="nav">
			<ul class="clearfix">

				<li class="cur"><a href="#">汇总统计</a></li>
				<li class="cur" id="upaper"><a href="#">每题统计</a></li>
				<li class="cur" id="duibi"><a href="#">对比统计</a></li>
			</ul>
		</div>
		<div class="fliter">
			<div>
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
						<button type="submit" name="submit" onclick="search()">筛选</button>
					</div>
				</fieldset>
			</div>
		</div>
		<div class="content">
			<div class="ctt_bd">

				<div class="section clearfix" style="border:0;padding:0;">


					<table class="table">
						<thead>
							<tr>
								<th>编号</th>
								<th>项目名称</th>
								<th>承训机构</th>
								<th>总体满意度</th>

							</tr>
						</thead>
						<tbody id="datamanyi">

						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>

</body>
</html>