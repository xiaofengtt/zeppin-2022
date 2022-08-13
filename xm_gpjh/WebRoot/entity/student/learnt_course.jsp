<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>已修课程列表</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div id="main_content">
	<div class="content_title"> 已修课程列表 </div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="7">&nbsp;&nbsp;我 已 修 的 课 程</td>
	</tr>
    <tr class="table_bg1"> 
      <td height=25px width="10%" align=center>课程ID</td>
      <td height=25px width="40%" align=center>课程名称</td>
      <td height=25px width="20%" align=center>课程类别</td>
      <td height=25px width="10%" align=center>学分</td>
      <td height=25px width="10%" align=center>所属学期</td>
      <td height=25px width="10%" align=center>成绩</td>
      
    </tr>
   <s:if test="learntCourses.size() > 0">
	<s:iterator value="learntCourses" status="stuts">
  	<s:if test="#stuts.odd == true">
		<tr class="table_bg2">
	</s:if>
	<s:else>
		<tr>
	</s:else>
	   <td height=25px align=center><s:property value="getPrTchProgramCourse().getPeTchCourse().getCode()"/>&nbsp;</td>
       <td height=25px align=center><s:property value="getPrTchProgramCourse().getPeTchCourse().getName()"/>&nbsp;</td>
       <td height=25px align=center><s:property value="getPrTchProgramCourse().getPeTchProgramGroup().getPeTchCoursegroup().getName()"/>&nbsp;</td>
       <td height=25px align=center><s:property value="getPrTchProgramCourse().getCredit()"/>&nbsp;</td>
       <td height=25px align=center><s:property value="getPrTchOpencourse().getPeSemester().getName()"/>&nbsp;</td>
       <td height=25px align=center><s:property value="getScoreTotal()"/>&nbsp;</td>
     </tr>
   </s:iterator>
 </s:if>
  
	</table>
	<br/>
	<div align="center"><input type="button" value="返回" onclick="window.history.back()"/> </div>
	
    </div>
    </div>
</div>
</body>
</html>

