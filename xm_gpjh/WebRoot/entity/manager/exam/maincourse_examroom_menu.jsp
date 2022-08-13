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
				<div id="0110" class="tab_menu2" onClick="parent.openPage('/entity/manager/exam/maincourse_examroom_auto.jsp',this.id)" title="按考试场次,科目安排考场">自动安排考场</div>
				<div id="0111" class="tab_menu2" onClick="parent.openPage('/entity/exam/peExamMaincourseRoom.action',this.id)" title="考场信息调整">考场信息调整</div>
				<div id="0112" class="tab_menu2" onClick="parent.openPage('/entity/exam/peMainCourseExamRoom.action',this.id)" title="考场分配结果">考场分配结果</div>
				<div id="0113" class="tab_menu2" onClick="parent.openPage('/entity/manager/exam/maincourse_paperbag_auto.jsp',this.id)" title="生成试卷袋">生成试卷袋</div>
				<div id="0114" class="tab_menu2" onClick="parent.openPage('/entity/exam/peMainCoursePaperBag.action',this.id)" title="试卷统计列表">试卷统计列表</div>
				<script>top.openPage('/entity/exam/peMainCourseTime.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>