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
				<div id="0110" class="tab_menu2" onClick="parent.openPage('/entity/exam/peExamno.action',this.id)" title="期末考试场次管理">考试场次管理</div>
				<div id="0112" class="tab_menu2" onClick="parent.openPage('/entity/manager/exam/exam_no_auto.jsp',this.id)" title="自动安排考场">自动安排考场</div>
				<div id="0113" class="tab_menu2" onClick="parent.openPage('/entity/exam/peExamRoom.action',this.id)" title="考场管理">考场管理</div>
				<div id="0114" class="tab_menu2" onClick="parent.openPage('/entity/exam/prExamBooking.action?search=true',this.id)" title="考场安排调整">考场安排调整</div>
				<div id="0115" class="tab_menu2" onClick="parent.openPage('/entity/exam/examInfoSearch.action?search=true',this.id)" title="考场安排查询">考场安排查询</div>
				<div id="0116" class="tab_menu2" onClick="parent.openPage('/entity/exam/prExamPaperCount.action',this.id)" title="试卷派发表">试卷派发表</div>
				<script>top.openPage('/entity/exam/peExamno.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>