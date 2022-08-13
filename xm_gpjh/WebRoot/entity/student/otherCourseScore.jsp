<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>其他成绩查询</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
</head>

<body>
<s:if test="edutype !='高起专'">
<s:if test="uniteCourse.size>0">
<div id="main_content">

	<div class="content_title">统考成绩查询 </div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr class="table_bg1">
    <td>课程名称</td>
    <td>成绩</td>
  </tr>
  <s:iterator value="uniteCourse" status="unite" id="u">
  <s:set name="cor" value="#u.indexOf(\"|\")"/>
  <tr class="table_bg2">
	<td height=25px align=center><s:property value="#u.substring(0,#cor)"/>&nbsp;</td>
	<td height=25px  align=center><s:property value="#u.substring(#cor+1,#u.length())"/>&nbsp;</td>
  </tr>
</s:iterator>

</table>
    </div>
  </div>
</div>
</s:if>
</s:if>
<div id="main_content">
	<div class="content_title">主干课成绩查询 </div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr class="table_bg1">
    <td>课程编号</td>
    <td>课程名称</td>
    <td>学期</td>
    <td>成绩</td>
    <td>成绩状态</td>
  </tr>
  <s:iterator value="mainCourseScoreList">
  <tr class="table_bg2">
	<td height=25px  align=center><s:property value="prExamOpenMaincourse.peTchCourse.code"/>&nbsp;</td>
	<td height=25px align=center><s:property value="prExamOpenMaincourse.peTchCourse.name"/>&nbsp;</td>
	<td height=25px  align=center><s:property value="prExamOpenMaincourse.peExamMaincourseNo.peSemester.name"/>&nbsp;</td>
	<td height=25px  align=center><s:property value="score"/>&nbsp;</td>
	<td height=25px  align=center><s:property value="enumConstByFlagScoreStatus.name"/>&nbsp;</td>
  </tr>
  </s:iterator>
  </table>
    </div>
  </div>
</div>
<s:if test="edutype !='高起专'">
<div id="main_content">
	<div class="content_title">学位外语成绩查询 </div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0" height="56">
  <tr class="table_bg1">
    <td>课程名称</td>
    <td>成绩</td>
  </tr>
  
  <tr class="table_bg2">
	<td height=25px align=center>学位外语(<s:property value="peStudent.enumConstByDegreeEnglishType.name"/>)&nbsp;</td>
	<td height=25px  align=center><s:if test="peStudent.scoreDegreeEnglish>=60">合格</s:if><s:else>不合格</s:else>&nbsp;</td>
  </tr>
</table>
    </div>
  </div>
</div>
</s:if>
</body>
</html>
