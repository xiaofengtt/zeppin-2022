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
				<div id="0110" class="tab_menu2" onClick="parent.openPage('/entity/exam/peMainCourseTime.action',this.id)" title="设定主干课程报名时间">报名时间设置</div>
				<div id="0111" class="tab_menu2" onClick="parent.openPage('/entity/manager/exam/auto_allot_examno.jsp',this.id)" title="自动生成考试场次">生成考试场次</div>
				<div id="0112" class="tab_menu2" onClick="parent.openPage('/entity/exam/peExamMaincourseNo.action',this.id)" title="场次时间设置">场次时间设置</div>
				<div id="0113" class="tab_menu2" onClick="parent.openPage('/entity/exam/prExamOpenMaincourse.action',this.id)" title="考试课程调整">考试课程调整</div>
				<div id="0114" class="tab_menu2" onClick="parent.openPage('/entity/exam/peMainCourseSignUp.action',this.id)" title="查询统计报名信息">查询统计信息</div>
				<script>top.openPage('/entity/exam/peMainCourseTime.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>