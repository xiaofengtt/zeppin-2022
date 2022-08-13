<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>汇总统计</title>
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
				<li class="cur"><a href="#">汇总统计</a></li>
				<li><a
					href="province_duibi.action?userid=<s:property value="#parameters.userid"/>">对比统计</a></li>
				<li><a
					href="province_huizongprovince.action?userid=<s:property value="#parameters.userid"/>">省内汇总统计</a></li>
				<li><a
					href="province_duibip.action?userid=<s:property value="#parameters.userid"/>">省内对比统计</a></li>
			</ul>
		</div>
		<div class="fliter">
			<form action="province_huizong.action" method="get">
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
						</span>
					</div>
					<div class="group" style="margin-right:20px">
						<span>学科： <select name="subject" style="width:230px;">
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
			<s:if test="avgCountMap.size() > 0">
				<div class="ctt_bd">

					<div class="section clearfix" style="border:0;padding:0;">
						<h2 class="clearfix"
							style="color:#2084ca;margin-left:10px;margin-top:10px;margin-bottom:20px;">
							<span class="fl">此问卷总体满意度： <s:property
									value="avgCountScore" />
							</span>
						</h2>

						<table class="table">
							<thead>
								<tr>
									<th>编号</th>
									<th>项目名称</th>
									<th>省份</th>
									<th>总体满意度</th>

								</tr>
							</thead>
							<tbody>
								<s:set name="flag" value="1" />
								<s:iterator value="avgCountMap" id="question">
									<tr>
										<td><s:property value="#flag" /></td>
										<td><s:property value="#question[0]" /></td>
										<s:if test="#question[3]==0">
											<td>**省</td>
										</s:if>
										<s:else>
											<td><span style="color:red;"> <s:property
														value="#question[1]" /></td>
											</span>
										</s:else>
										<td><s:property value="#question[2]" /></td>
									</tr>
									<s:set name="flag" value="#flag+1" />
								</s:iterator>
							</tbody>
						</table>
					</div>

				</div>
		</div>
		</s:if>
	</div>

</body>
</html>