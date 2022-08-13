<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>免修免考申请</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
</head>
  
<body>
<div id="main_content">
	<div class="content_title">您当前的位置：学生<s:property value="peStudent.trueName"/>的工作室 > 申请管理 > 免修免考申请</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="7">&nbsp;&nbsp; 可以申请免考的课程 <s:if test="!checkDate">(当前不是申请操作时间)</s:if>   </td>
	</tr>

    <tr class="table_bg1"> 
      <td height=25px width="20%"  align=center>课程编号</td>
      <td height=25px width="30%" align=center>课程名称</td>
      <td height=25px width="20%"  align=center>课程类别</td>
      <td height=25px width="10%" align=center>学分</td>
      <td height=25px width="20%" align=center>操作</td>
    </tr>
 <s:iterator value="courseList" status="num">	
  <tr <s:if test="#num.index%2 == 0 ">class="table_bg2"</s:if> >
      <td height=25px width="20%"  align=center><s:property value="peTchCourse.code"/>&nbsp;</td>
      <td height=25px width="30%" align=center><s:property value="peTchCourse.name"/>&nbsp;</td>
      <td height=25px width="20%"  align=center><s:property value="peTchProgramGroup.peTchCoursegroup.name"/>&nbsp;</td>
      <td height=25px width="10%" align=center><s:property value="credit"/>&nbsp;</td>
      <td height=25px width="20%"  align=center><s:if test="checkDate"><a href="/entity/workspaceStudent/examAvoid_avoid.action?tchProgramId=<s:property value="id"/>">申请免考</a></s:if>&nbsp;</td>  
  </tr>
  </s:iterator>
         
</table>    	
</div>
</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr class="table_bg1"><td height=25px colspan="7">&nbsp;&nbsp; 已经申请免考的课程</td>
	</tr>

    <tr class="table_bg1"> 
      <td height=25px width="20%"  align=center>课程编号</td>
      <td height=25px width="30%" align=center>课程名称</td>
      <td height=25px width="20%"  align=center>课程类别</td>
      <td height=25px width="10%" align=center>学分</td>
      <td height=25px width="20%" align=center>申请审核状态</td>
    </tr>
 <s:iterator value="applyCourse" status="n">	
  <tr <s:if test="#n.index%2 == 0 ">class="table_bg2"</s:if> >
      <td height=25px width="20%"  align=center><s:property value="peTchCourse.code"/>&nbsp;</td>
      <td height=25px width="30%" align=center><s:property value="peTchCourse.name"/>&nbsp;</td>
      <td height=25px width="20%"  align=center><s:property value="peTchProgramGroup.peTchCoursegroup.name"/>&nbsp;</td>
      <td height=25px width="10%" align=center><s:property value="credit"/>&nbsp;</td>
      <td height=25px width="20%"  align=center><s:property value="applyCheckType(id)"/>&nbsp;</td>  
  </tr>
  </s:iterator>
         
</table>    	
</div>
</div>
</div>
</body>
</html>
