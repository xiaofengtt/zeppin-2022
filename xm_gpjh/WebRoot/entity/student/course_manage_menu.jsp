<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% response.setHeader("expires", "0"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link href="/entity/manager/pub/images/layout.css" rel="stylesheet" type="text/css" />
</head>
<body>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
		<div id="main_content">
			<div id="tab_bg">
				<div id="0110" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/peLearntCourseScore.action',this.id)" title="已修课程">已修课程</div>
				<div id="0111" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/peNotLearntCourse.action',this.id)" title="未修课程">未修课程</div>
				<div id="0112" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/peLearningCourseScore.action',this.id)" title="在修课程">在修课程</div>
				<div id="0113" class="tab_menu2" onClick="parent.openPage('/entity/workspaceStudent/peSelectedCourse.action',this.id)" title="已选课程">已选课程</div>
				<script>parent.openPage('/entity/workspaceStudent/peLearntCourseScore.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>