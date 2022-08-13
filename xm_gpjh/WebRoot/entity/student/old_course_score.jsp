<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已修成绩查询</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
</head>
  
<body>
<div id="main_content">
	<div class="content_title">您当前的位置：学生<s:property value="peStudent.trueName"/>的工作室 > 成绩查询 > 已修在修课程成绩</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="12">&nbsp;&nbsp; 在 修 的 课 程 &nbsp;&nbsp; <s:if test="!scoreDate">(期末考试成绩查询还未开始)</s:if> </td>
	</tr>

    <tr class="table_bg1"> 
      <td height=25px width="6%"  align=center>课程编号</td>
      <td height=25px width="15%" align=center>课程名称</td>
      <td height=25px width="10%"  align=center>课程类别</td>
      <td height=25px width="8%" align=center>学分</td>
      <td height=25px width="10%"  align=center>所属学期</td>
      <td height=25px width="7%" align=center>平时成绩</td>
      <td height=25px width="7%" align=center>作业成绩</td>
      <td height=25px width="7%" align=center>考试成绩</td>
      <td height=25px width="7%" align=center>总评成绩</td>
      <td height=25px width="8%" align=center>成绩状态</td>
      <td height=25px width="15%" align=center>辅导教师</td>
    </tr>
 <s:iterator value="nowCourseScoreList" status="num">	
  <tr <s:if test="#num.index%2 == 0 ">class="table_bg2"</s:if> >
      <td height=25px width="6%"  align=center><s:property value="prTchOpencourse.peTchCourse.code"/>&nbsp;</td>
      <td height=25px width="15%" align=center><s:property value="prTchOpencourse.peTchCourse.name"/>&nbsp;</td>
      <td height=25px width="10%"  align=center><s:property value="courseGroup(id)"/>&nbsp;</td>
      <td height=25px width="8%" align=center><s:property value="prTchProgramCourse.credit"/>&nbsp;</td>
      <td height=25px width="10%"  align=center><s:property value="prTchOpencourse.peSemester.name"/>&nbsp;</td>
      <td height=25px width="7%" align=center><s:property value="scoreUsual"/>&nbsp;</td>
      <td height=25px width="7%" align=center><s:property value="scoreHomework"/>&nbsp;</td>
      <td height=25px width="7%" align=center><s:property value="scoreExam"/>&nbsp;</td>
      <td height=25px width="7%" align=center><s:property value="scoreTotal"/>&nbsp;</td>
      <td height=25px width="8%" align=center><s:property value="enumConstByFlagScoreStatus.name"/>&nbsp;</td>
      <td height=25px width="15%" align=center><s:property value="getCourseTeacher(id)" escape="false"/>&nbsp;</td>
  </tr>
  </s:iterator>
         
</table>    	
</div>
</div>
<div class="student_cntent_k">
<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="8">&nbsp;&nbsp; 已 修 的 课 程</td>
	</tr>
    <tr class="table_bg1"> 
      <td height=25px width="7%"  align=center>课程编号</td>
      <td height=25px width="25%" align=center>课程名称</td>
      <td height=25px width="10%"  align=center>课程类别</td>
      <td height=25px width="8%" align=center>学分</td>
      <td height=25px width="10%"  align=center>所属学期</td>
      <td height=25px width="10%" align=center>成绩</td>
      <td height=25px width="10%" align=center>成绩状态</td>
      <td height=25px width="20%" align=center>辅导教师</td>
    </tr>
  <s:iterator value="oldCourseScoreList" status="n">
  <tr <s:if test="#n.index%2 == 0 ">class="table_bg2"</s:if> >
      <td height=25px width="7%"  align=center><s:property value="prTchOpencourse.peTchCourse.code"/>&nbsp;</td>
      <td height=25px width="25%" align=center><s:property value="prTchOpencourse.peTchCourse.name"/>&nbsp;</td>
      <td height=25px width="10%"  align=center><s:property value="courseGroup(id)"/>&nbsp;</td>
      <td height=25px width="8%" align=center><s:property value="prTchProgramCourse.credit"/>&nbsp;</td>
      <td height=25px width="10%"  align=center><s:property value="prTchOpencourse.peSemester.name"/>&nbsp;</td>
      <td height=25px width="10%" align=center><s:property value="scoreTotal"/>&nbsp;</td>
      <td height=25px width="10%" align=center><s:property value="enumConstByFlagScoreStatus.name"/>&nbsp;</td>
      <td height=25px width="20%" align=center><s:property value="getCourseTeacher(id)" escape="false"/>&nbsp;</td>
  </tr>
  </s:iterator>   
	</table>
	<br/>
	
    </div>
    </div>
</div>
</body>
</html>
