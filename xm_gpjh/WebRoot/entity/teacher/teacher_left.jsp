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
<form name="emailForm" action="http://219.238.253.7/cgi-bin/login" method="POST" target="_blank">
	<input type="hidden" name="UserID" value="000000" />
	<input type="hidden" name="Domain_Name" value="cupde.cn">
	<input type="hidden" name="PassWord" value="000000">
</form>
<!--left_menu-->
<div id="left_menu">
<table width="213" height="100%" cellpadding="0" cellspacing="0">
	<tr><td width="207" valign="top" height="100%">
	<div class="menu_bg">
    	<div class="menu_title">教师工作室</div> 
        <div class="student_menu_content">
        	<a href="/entity/teacher/story/info_list.html" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">通知通告</div></a>
        	<a href="/entity/teacher/story/teacher_info.html" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">个人信息</div></a>
        	<a href="/entity/teacher/story/teacher_password_edit.html" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">修改密码</div></a>
        	
        	<a href="/entity/teaching/peTeacher_teacherCourseList.action" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">授课管理</div></a>
        	<a href="/entity/teacher/graduation_paper_index.jsp" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">毕业论文</div></a>
			<a href="teacher_resource_manage.jsp" target="right"><!--div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">公共资源</div--></a>
        	
        	<a href="problems.jsp" target="right"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">使用帮助</div></a>
			<a href='javascript:return false;' onclick="javascript:document.emailForm.submit();" ><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">交流园地</div></a>
        	<a href="javascript:if(confirm('确定退出教师工作室？')) window.location.href='/admin/logout.jsp'" target="_top"><div class="student_menu_bt" onmouseover="this.className='student_menu_bt_M'" onmouseout="this.className='student_menu_bt'">注销退出</div></a>
        </div>
    </div>
	</td>
	<td width="6">
    <div class="menu_Rb">
    </div>
	</td></tr>
</table>
    <div class="clear"></div>
</div>
</body>
</html>

