<jsp:directive.page />
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="images/layout.css" rel="stylesheet" type="text/css" />
</head>

<body>

<form name="emailForm" action="http://219.238.253.7/cgi-bin/login" method="post" target="_blank">
	<input type="hidden" name="UserID" value="000000" />
	<input type="hidden" name="Domain_Name" value="cupde.cn" />
	<input type="hidden" name="PassWord" value="000000" />
</form>

<div id="left_menu">
<table width="213" height="100%" cellpadding="0" cellspacing="0">
	<tr height="100%"><td width="207" valign="top" >
	<div class="menu_bg">
    	<div class="menu_title">学生工作室</div>
        <div class="menu_content">
        	<a href="/entity/student/story/notice.html" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">通知通告</div></a>
			<a href="/entity/student/student_info_manage.jsp" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">个人信息</div></a>
			
			<a href="/entity/student/course_manage.jsp" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'"> 课程管理</div></a>
			
<!-- 		<a href="/entity/student/story/course_elective.html" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'"> 选课管理</div></a>
 -->
 			<a href="/entity/student/story/course_elective_manage.jsp" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'"> 选课管理</div></a>
			<a href="/entity/student/story/on_study_course.html" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'"> 在学课程</div></a>
<!-- 			<a href="/entity/student/story/exam_reserver.html" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">考试预约</div></a>  -->
			<a href="/entity/workspaceStudent/prExamReserver.action" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">考试预约</div></a>
<!-- 		<a href="/entity/student/story/exam_info.html" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">考试信息</div></a> -->	
			<a href="/entity/workspaceStudent/prExamDetail.action" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">考试信息</div></a>
<!-- 		<a href="/entity/student/story/exam_score.html" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">成绩查询</div></a>  -->
			<a href="/entity/student/story/exam_score_manage.jsp" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">成绩查询</div></a>
<!-- 		<a href="/entity/student/story/teach_plan.html" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">教学计划?</div></a>   -->	
			
			<a href="/entity/student/graduation_paper_student_index.jsp" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">毕业论文</div></a>
			<a href="/entity/student/apply_manage.jsp" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">申请管理</div></a>
			<a href="/entity/student/story/intoBBS.jsp" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">交流园地</div></a>
			<a href="/entity/teacher/problems.jsp" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">使用帮助</div></a>
<!--        <a href="javascript:return false;" onclick="javascript:document.emailForm.submit();"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">我的邮箱?</div></a>  --> 
			<a href="javascript:if(confirm('确定退出学生工作室？')) window.location.href='/admin/logout.jsp'" target="_top"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">注销退出</div></a>
        </div>
    </div>
	</td>
	<td width="6" bgcolor="#278abe">
	</td></tr>
</table>
    <div class="clear"></div>
</div>
</body>
</html>
