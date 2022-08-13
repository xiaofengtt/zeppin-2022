<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>省内对比统计</title>
		<meta name="description" content="">
			<meta name="viewport" content="width=device-width">

				<!-- Place favicon.ico and apple-touch-icon.png in the root directory -->


				<link rel="stylesheet" href="css/style.css">

					<script type="text/javascript" src="https://www.google.com/jsapi"></script>
					<script type="text/javascript">
						google.load("visualization", "1", {
							packages : [ "corechart" ]
						});
						//google.setOnLoadCallback(drawChart);
					</script>
</head>
<body>
	<div class="wrapper">

		<div class="nav">
			<ul class="clearfix">
				<li><a
					href="province_paper.action?userid=<s:property value="#parameters.userid"/>">每题统计</a></li>
				<li><a
					href="province_huizong.action?userid=<s:property value="#parameters.userid"/>">汇总统计</a></li>
				<li><a
					href="province_duibi.action?userid=<s:property value="#parameters.userid"/>">对比统计</a></li>
				<li><a
					href="province_huizongprovince.action?userid=<s:property value="#parameters.userid"/>">省内汇总统计</a></li>
				<li class="cur"><a href="#">省内对比统计</a></li>
			</ul>
		</div>
		<div class="fliter">
			<form action="province_duibip.action" method="get">
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
						</span> <span>题目： <select name="question" style="width:230px">
								<option value="0">所有题目</option>
								<s:iterator value="questions" id="question">
									<option value="<s:property value="id"/>"
										<s:if test="%{id == qid}">selected</s:if>>
										<s:property value="#question.name" />
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

			<div class="ctt_bd">

				<span><a href="province_duibiDoExcel.action">导出excel</a></span>

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

												<td><s:property value="#q[1]" /></td>


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