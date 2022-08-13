<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主干课预约查询</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
</head>
  
<body>
<div id="main_content">
	<div class="content_title">您当前的位置：学生<s:property value="peStudent.trueName"/>的工作室 > 考试预约 > 主干课预约查询</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="7">&nbsp;&nbsp; 已经预约考试的主干课 <s:if test="!checkDate">(当前不是操作时间)</s:if>   </td>
	</tr>

    <tr class="table_bg1"> 
      <td height=25px width="20%"  align=center>课程编号</td>
      <td height=25px width="20%" align=center>课程名称</td>
      <td height=25px width="20%"  align=center>考试开始时间</td>
      <td height=25px width="20%" align=center>考试结束时间</td>
      <td height=25px width="20%" align=center>操作</td>
    </tr>
 <s:iterator value="courseList" status="num">	
  <tr <s:if test="#num.index%2 == 0 ">class="table_bg2"</s:if> >
      <td height=25px width="20%"  align=center><s:property value="prExamOpenMaincourse.peTchCourse.code"/>&nbsp;</td>
      <td height=25px width="20%" align=center><s:property value="prExamOpenMaincourse.peTchCourse.name"/>&nbsp;</td>
      <td height=25px width="20%"  align=center><s:date name="prExamOpenMaincourse.peExamMaincourseNo.startDatetime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
      <td height=25px width="20%" align=center><s:date name="prExamOpenMaincourse.peExamMaincourseNo.endDatetime" format="yyyy-MM-dd HH:mm:ss" />&nbsp;</td>
       <td height=25px width="20%" align=center><s:if test="checkDate"><a href="/entity/workspaceStudent/mainCourseReserverView_cancelMaincourse.action?id=<s:property value="id"/>">取消预约</a></s:if>&nbsp;</td>    
  </tr>
  </s:iterator>
         
</table>	
</div>
</div>
</div>
</body>
</html>
