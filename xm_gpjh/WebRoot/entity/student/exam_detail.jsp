<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>考试信息</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
</head>
  
<body>
<div id="main_content">
	<div class="content_title">您当前的位置：学生<s:property value="peStudent.trueName"/>的工作室 > 考试信息</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="7">&nbsp;&nbsp; 期 末 考 试 信 息</td>
	</tr>

    <tr class="table_bg1"> 
      <td height=25px width="10%"  align=center>课程编号</td>
      <td height=25px width="20%" align=center>课程名称</td>
      <td height=25px width="20%"  align=center>考试场次</td>
      <td height=25px width="10%" align=center>考场编号</td>
      <td height=25px width="10%"  align=center>座位号</td>
      <td height=25px width="15%" align=center>场次开始时间</td>
      <td height=25px width="15%" align=center>场次结束时间</td>
    </tr>
 <s:iterator value="prExamBookingList" status="num">	
  <tr <s:if test="#num.index%2 == 0 ">class="table_bg2"</s:if> >
      <td height=25px width="10%"  align=center><s:property value="prTchStuElective.prTchOpencourse.peTchCourse.code"/>&nbsp;</td>
      <td height=25px width="20%" align=center><s:property value="prTchStuElective.prTchOpencourse.peTchCourse.name"/>&nbsp;</td>
      <td height=25px width="20%"  align=center><s:property value="peExamNo.name"/>&nbsp;</td>
      <td height=25px width="10%" align=center><s:property value="peExamRoom.code"/>&nbsp;</td>
      <td height=25px width="10%"  align=center><s:property value="seatNo"/>&nbsp;</td>
      <td height=25px width="15%" align=center><s:date name="peExamNo.startDatetime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
      <td height=25px width="15%" align=center><s:date name="peExamNo.endDatetime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
  </tr>
  </s:iterator>
         
</table>    	
</div>
</div>
<s:if test="edutype=='edutype'">
<div class="student_cntent_k">
<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="7">&nbsp;&nbsp;主 干 课 考 试 信 息</td>
	</tr>
    <tr class="table_bg1"> 
      <td height=25px width="10%"  align=center>课程编号</td>
      <td height=25px width="20%" align=center>课程名称</td>
      <td height=25px width="20%"  align=center>考试场次</td>
      <td height=25px width="10%" align=center>考场编号</td>
      <td height=25px width="10%"  align=center>座位号</td>
      <td height=25px width="15%" align=center>场次开始时间</td>
      <td height=25px width="15%" align=center>场次结束时间</td>
    </tr>
  <s:iterator value="prExamStuMaincourseList" status="n">
  <tr <s:if test="#n.index%2 == 0 ">class="table_bg2"</s:if> >
      <td height=25px width="10%"  align=center><s:property value="prExamOpenMaincourse.peTchCourse.code"/>&nbsp;</td>
      <td height=25px width="20%" align=center><s:property value="prExamOpenMaincourse.peTchCourse.name"/>&nbsp;</td>
      <td height=25px width="20%"  align=center><s:property value="prExamOpenMaincourse.peExamMaincourseNo.name"/>&nbsp;</td>
      <td height=25px width="10%" align=center><s:property value="peExamMaincourseRoom.code"/>&nbsp;</td>
      <td height=25px width="10%"  align=center><s:property value="seatNo"/>&nbsp;</td>
      <td height=25px width="15%" align=center><s:date name="prExamOpenMaincourse.peExamMaincourseNo.startDatetime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
      <td height=25px width="15%" align=center><s:date name="prExamOpenMaincourse.peExamMaincourseNo.endDatetime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
  </tr>
  </s:iterator>   
	</table>
	<br/>
	
    </div>
    </div>
</s:if>    
</div>
</body>
</html>
