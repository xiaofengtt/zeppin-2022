<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
response.setHeader("expires", "0");
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
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
	<div class="content_title">您当前的位置是：教学计划</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
    毕业最低学分：<s:property value="program.getGraduateMinCredit()"/> <br />
    获得学位最低平均分：<s:property value="program.getDegreeAvgScore()"/> <br />
    获得学位最低论文分数：<s:property value="program.getDegreePaperScore()"/> <br />
    毕业论文最小学期：<s:property value="program.getPaperMinSemeser()"/> <br />
    毕业论文资格最小学分：<s:property value="program.getPaperMinCreditHour()"/> <br />
    每学期最大选课数：<s:property value="program.getMaxElective()"/> <br />
    毕业最小年限：<s:property value="program.getMinSemester()"/> <br />
    毕业最大年限：<s:property value="program.getMaxSemester()"/> <br />
    备注：<s:property value="program.getNote()"/> <br /><br />
    
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
  		<thead>学位考试课程</thead>
  		<tr class="table_bg1">
    		
    		<td width="5%">课程编号</td>
    		<td width="20%">课程名</td>
    		<td width="5%">学分</td>
    		<td width="10%">建议选修学期</td>
    		
  		</tr>
   	<s:iterator value="mainCourses()">
  		<tr class="table_bg1">
	    	
	    	<td><s:property value="peTchCourse.code" />&nbsp;</td>
	    	<td><s:property value="peTchCourse.name" />&nbsp;</td>
	    	<td><s:property value="credit" />&nbsp;</td>
    		<td><s:property value="unit"/> </td>
  		</tr>
  	</s:iterator>
		
	</table>
    <br>
    
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  		<thead>公共必修课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低学分:<s:property value="getGGBXcredit()"/>学分</thead>
  		<tr class="table_bg1">
    		
    		<td width="5%">课程编号</td>
    		<td width="20%">课程名</td>
    		<td width="5%">学分</td>
    		<td width="10%">建议选修学期</td>
    		
  		</tr>
   	<s:iterator value="getGGBX()">
  		<tr class="table_bg1">
	    	
	    	<td><s:property value="peTchCourse.code" />&nbsp;</td>
	    	<td><s:property value="peTchCourse.name" />&nbsp;</td>
	    	<td><s:property value="credit" />&nbsp;</td>
    		<td><s:property value="unit"/> </td>
  		</tr>
  	</s:iterator>
		
	</table>
	
	<br>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  		<thead>专业必修课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低学分：<s:property value="getZYBXcredit()"/>学分</thead>
  		<tr class="table_bg1">
    		
    		<td width="5%">课程编号</td>
    		<td width="20%">课程名</td>
    		<td width="5%">学分</td>
    		<td width="10%">建议选修学期</td>
    		
  		</tr>
   	<s:iterator value="getZYBX()">
  		<tr class="table_bg1">
	    	
	    	<td><s:property value="peTchCourse.code" />&nbsp;</td>
	    	<td><s:property value="peTchCourse.name" />&nbsp;</td>
	    	<td><s:property value="credit" />&nbsp;</td>
    		<td><s:property value="unit"/> </td>
  		</tr>
  	</s:iterator>
		
	</table>
	
	<br>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  		<thead>专业选修课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低学分：<s:property value="getZYXXcredit()"/>学分</thead>
  		<tr class="table_bg1">
    		
    		<td width="5%">课程编号</td>
    		<td width="20%">课程名</td>
    		<td width="5%">学分</td>
    		<td width="10%">建议选修学期</td>
    		
  		</tr>
   	<s:iterator value="getZYXX()">
  		<tr class="table_bg1">
	    	
	    	<td><s:property value="peTchCourse.code" />&nbsp;</td>
	    	<td><s:property value="peTchCourse.name" />&nbsp;</td>
	    	<td><s:property value="credit" />&nbsp;</td>
    		<td><s:property value="unit"/> </td>
  		</tr>
  	</s:iterator>
		
	</table>
	
	<br>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  		<thead>公共选修课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低学分：<s:property value="getGGXXcredit()"/>学分</thead>
  		<tr class="table_bg1">
    		
    		<td width="5%">课程编号</td>
    		<td width="20%">课程名</td>
    		<td width="5%">学分</td>
    		<td width="10%">建议选修学期</td>
    		
  		</tr>
   	<s:iterator value="getGGXX()">
  		<tr class="table_bg1">
	    	
	    	<td><s:property value="peTchCourse.code" />&nbsp;</td>
	    	<td><s:property value="peTchCourse.name" />&nbsp;</td>
	    	<td><s:property value="credit" />&nbsp;</td>
    		<td><s:property value="unit"/> </td>
  		</tr>
  	</s:iterator>
		
	</table>
	
</div>
</div>
</div>
</body>
</html>
