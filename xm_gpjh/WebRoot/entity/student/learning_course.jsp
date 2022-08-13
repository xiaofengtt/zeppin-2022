<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
<style type="text/css">
<!--
.STYLE1 {color: #FF0000}
-->
</style>
</head>

<body>
<div id="main_content">
	<div class="content_title">您当前的位置是：学生<s:property value="peStudent.getTrueName()"/>的工作室 > 在学课程</div>
  <div class="student_cntent_k">
    	<div class="k_cc">
    		<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr class="table_bg1">
    <td>课程编码</td>
    <td>课程名称</td>
    <td>学分</td>
    <td>开始学习</td>
    <td>实时答疑室</td>
    <td>学科论坛</td>
    <td>课程类别</td>
	<td>教材</td>
	<td>辅导教师</td>
  </tr>
 <s:if test="learningCourses.size > 0">
  <s:iterator value="learningCourses" status="stuts">
  	<s:if test="#stuts.odd == true">
		<tr class="table_bg2">
	</s:if>
	<s:else>
		<tr>
	</s:else>
		<td><s:property value="getPrTchOpencourse().getPeTchCourse().getCode()"/></td>
		<td><s:property value="getPrTchOpencourse().getPeTchCourse().getName()"/></td>
		<td><s:property value="getPrTchProgramCourse().getCredit()"/></td>
		 
		<s:if test="getEnumConstByFlagElectiveAdmission().getCode()==0">
			<td colspan='3'><font color="#ff0000">选课未开课</td>
		</s:if>
		<s:else>
			<td><div class="study_bt" onmouseover="this.className='study_bt_M'" onmouseout="this.className='study_bt'"><a href='/sso/interaction_InteractionStuManage.action?course_id=<s:property value="getPrTchOpencourse().peTchCourse.id"/>' target="_blank">开始学习</a></div></td>
			<script>
				var tmp_url_<s:property value="getId()"/> = "http://218.19.140.213/vlog.asp?roomid=<s:property value="getPrTchOpencourse().getId()"/>&op=0&user=" + escape("<s:property value="getPeStudent().getTrueName()"/>") + "&nick=" + escape("<s:property value="getPeStudent().getTrueName()"/>") + "&roomname=" + escape("<s:property value="getPrTchOpencourse().getPeTchCourse().getName()"/>") + "&roomgroup=" + escape("实时多媒体答疑教室");
			</script>
			<td><div class="study_bt" onmouseover="this.className='study_bt_M'" onmouseout="this.className='study_bt'"><a href='#' onclick='window.open(tmp_url_<s:property value="getId()"/>);'>实时答疑室</a></div></td>
			<td><div class="study_bt" onmouseover="this.className='study_bt_M'" onmouseout="this.className='study_bt'"><a href='/entity/workspaceStudent/student_toCourseforum.action?opencourseid=<s:property value="getPrTchOpencourse().getId()"/>' target="_blank"'>学科论坛</a></div></td>
		</s:else>
		
		<td><s:property value="getPrTchProgramCourse().getPeTchProgramGroup().getPeTchCoursegroup().getName()"/></td>
		<td>&nbsp;<s:property value="books(getPrTchOpencourse().getPeTchCourse().getId())" escape="false"/></td>
		<td><s:property value="courseTeachers(getPrTchOpencourse().getPeTchCourse().getId())" escape="<br>" /></td>
	</tr>
  </s:iterator>
 </s:if>
  
</table>
<br/>
<div align="center"><a href="/entity/workspaceStudent/student_toLearntCourses.action" target=_self  >[已修课程查询]</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="/entity/workspaceStudent/student_toNotLearnCourses.action" target=_self  >[未修课程查询]</a></div>
    </div>
        <p>&nbsp;</p>
        <p class="STYLE1">备注：</p>
        <p class="STYLE1">在学课程：当前学习在学课程；</p>
        <p class="STYLE1">已修课程：已修过的课程，及免修免考课程；</p>
        <p class="STYLE1">未修课程：教学计划中未选课程（不含免修免考）；</p>
  </div>
</div>
</body>
</html>
