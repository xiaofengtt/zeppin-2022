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
				<div id="0110" class="tab_menu2" onClick="parent.openPage('/entity/workspaceTeacher/prTchPaperTitle.action',this.id)" title="设置论文题目">设置论文题目</div>
				<div id="0112" class="tab_menu2" onClick="parent.openPage('/entity/workspaceTeacher/teacher_toPaperStep.action',this.id)" title="论文操作">论文操作</div>
				<div id="0113" class="tab_menu2" onClick="parent.openPage('/entity/workspaceTeacher/stuPaperScore.action',this.id)" title="论文成绩查看">论文成绩查看</div>
				<div id="0114" class="tab_menu2" onClick="parent.openPage('/entity/teacher/enter_paper_forum.jsp',this.id)" title="论文留言板">论文留言板</div>
				<script>parent.openPage('/entity/workspaceTeacher/prTchPaperTitle.action','0110');</script>
			</div>
		</div>
	</td>
  </tr>
</table>
</body>
</html>