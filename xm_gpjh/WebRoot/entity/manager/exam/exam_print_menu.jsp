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
				<div id="0110" class="tab_menu2" onClick="parent.openPage('/entity/exam/peExamno.action',this.id)" title="打印学生成绩单">打印成绩单</div>
				<div id="0112" class="tab_menu2" onClick="parent.openPage('/entity/manager/exam/exam_no_auto.jsp',this.id)" title="打印考场签到表">考场签到表</div>
				<div id="0113" class="tab_menu2" onClick="parent.openPage('/entity/exam/peExamRoom.action',this.id)" title="试卷交接单">试卷交接单</div>
				<script>top.openPage('/entity/exam/peExamno.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>