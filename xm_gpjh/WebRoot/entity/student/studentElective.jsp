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
	<div class="content_title">您当前的位置是：选课&gt; &nbsp;<s:property value = "peSemester.name"/>学期</div>
    <div class="student_cntent_k">
    	<div class="k_cc">
    <input type="hidden" value="peSemester.id" name="peSemesterId"/>
<s:form action="/entity/workspaceStudent/studentElective_elective.action" name="studentElective">
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  		<thead>公共必修课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低学分:<s:property value="getGGBXcredit()"/>学分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您已修：<s:property value="alreadyGGBX()"/> 学分 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:set name="GGBXnum" value="getGGBXcredit()-alreadyGGBX()"/>还应选修：<s:if test="#GGBXnum >0"><s:property value="#GGBXnum"/></s:if><s:else>0</s:else> 学分 </thead>
  		<tr class="table_bg1">
    		
    		<td width="5%">课程编号</td>
    		<td width="20%">课程名</td>
    		<td width="5%">学分</td>
    		<td width="5%">考试场次</td>
    		<td width="50%">教材</td>
    		<td width="10%">建议选修学期</td>
    		<td width="5%">选择</td>
    		
  		</tr>
   	<s:iterator value="getGGBX()">
  		<tr class="table_bg1">
	    	
	    	<td><s:property value="peTchCourse.code" />&nbsp;</td>
	    	<td><s:property value="peTchCourse.name" />&nbsp;</td>
	    	<td><s:property value="credit" />&nbsp;</td>
	    	<td><s:property value="examNo(peTchCourse.id)" />&nbsp;</td>
    		<td><s:property value="books(peTchCourse.id)" escape="<br>"/> </td>
    		<td><s:property value="unit"/> </td>
    		<s:if test="isLearnt(id)">
    		<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' checked="checked" disabled="disabled"/></td>
    		</s:if>
    		<s:elseif test="isChosen(id)">
    		<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' checked="checked" /></td>
    		</s:elseif>
    		<s:else>
			<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' /></td>
			</s:else>
  		</tr>
  	</s:iterator>
		
	</table>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  		<thead>专业必修课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低学分：<s:property value="getZYBXcredit()"/>学分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您已修：<s:property value="alreadyZYBX()"/> 学分 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:set name="ZYBXnum" value="getZYBXcredit()-alreadyZYBX()"/>还应选修：<s:if test="#ZYBXnum >0"><s:property value="#ZYBXnum"/></s:if><s:else>0</s:else>学分  </thead>
  		<tr class="table_bg1">
    		
    		<td width="5%">课程编号</td>
    		<td width="20%">课程名</td>
    		<td width="5%">学分</td>
    		<td width="5%">考试场次</td>
    		<td width="50%">教材</td>
    		<td width="10%">建议选修学期</td>
    		<td width="5%">选择</td>
    		
  		</tr>
   	<s:iterator value="getZYBX()">
  		<tr class="table_bg1">
	    	
	    	<td><s:property value="peTchCourse.code" />&nbsp;</td>
	    	<td><s:property value="peTchCourse.name" />&nbsp;</td>
	    	<td><s:property value="credit" />&nbsp;</td>
	    	<td><s:property value="examNo(peTchCourse.id)" />&nbsp;</td>
    		<td><s:property value="books(peTchCourse.id)" escape="<br>"/> </td>
    		<td><s:property value="unit"/> </td>
    		<s:if test="isLearnt(id)">
			<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' checked="checked" disabled="disabled"/></td>
    		</s:if>
    		<s:elseif test="isChosen(id)">
    		<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' checked="checked" /></td>
    		</s:elseif>
    		<s:else>
			<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' /></td>
			</s:else>
  		</tr>
  	</s:iterator>
		
	</table>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  		<thead>专业选修课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低学分：<s:property value="getZYXXcredit()"/>学分 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您已修：<s:property value="alreadyZYXX()"/> 学分 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:set name="ZYXXnum" value="getZYXXcredit()-alreadyZYXX()"/>还应选修：<s:if test="#ZYXXnum >0"><s:property value="#ZYXXnum"/></s:if><s:else>0</s:else> 学分 </thead>
  		<tr class="table_bg1">
    		
    		<td width="5%">课程编号</td>
    		<td width="20%">课程名</td>
    		<td width="5%">学分</td>
    		<td width="5%">考试场次</td>
    		<td width="50%">教材</td>
    		<td width="10%">建议选修学期</td>
    		<td width="5%">选择</td>
    		
  		</tr>
   	<s:iterator value="getZYXX()">
  		<tr class="table_bg1">
	    	
	    	<td><s:property value="peTchCourse.code" />&nbsp;</td>
	    	<td><s:property value="peTchCourse.name" />&nbsp;</td>
	    	<td><s:property value="credit" />&nbsp;</td>
	    	<td><s:property value="examNo(peTchCourse.id)" />&nbsp;</td>
    		<td><s:property value="books(peTchCourse.id)" escape="<br>"/> </td>
    		<td><s:property value="unit"/> </td>
    		<s:if test="isLearnt(id)">
			<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' checked="checked" disabled="disabled"/></td>
    		</s:if>
    		<s:elseif test="isChosen(id)">
    		<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' checked="checked" /></td>
    		</s:elseif>
    		<s:else>
			<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' /></td>
			</s:else>
  		</tr>
  	</s:iterator>
		
	</table>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  		<thead>公共选修课&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;最低学分：<s:property value="getGGXXcredit()"/>学分&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您已修：<s:property value="alreadyGGXX()"/> 学分 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:set name="GGXXnum" value="getGGXXcredit()-alreadyGGXX()"/>还应选修：<s:if test="#GGXXnum >0"><s:property value="#GGXXnum"/></s:if><s:else>0</s:else> 学分 </thead>
  		<tr class="table_bg1">
    		
    		<td width="5%">课程编号</td>
    		<td width="20%">课程名</td>
    		<td width="5%">学分</td>
    		<td width="5%">考试场次</td>
    		<td width="50%">教材</td>
    		<td width="10%">建议选修学期</td>
    		<td width="5%">选择</td>
    		
  		</tr>
   	<s:iterator value="getGGXX()">
  		<tr class="table_bg1">
	    	
	    	<td><s:property value="peTchCourse.code" />&nbsp;</td>
	    	<td><s:property value="peTchCourse.name" />&nbsp;</td>
	    	<td><s:property value="credit" />&nbsp;</td>
	    	<td><s:property value="examNo(peTchCourse.id)" />&nbsp;</td>
    		<td><s:property value="books(peTchCourse.id)" escape="<br>"/> </td>
    		<td><s:property value="unit"/> </td>
    		<s:if test="isLearnt(id)">
			<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' checked="checked" disabled="disabled"/></td>
    		</s:if>
    		<s:elseif test="isChosen(id)">
    		<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' checked="checked" /></td>
    		</s:elseif>
    		<s:else>
			<td><input name="courseid" type="checkbox" value='<s:property value ="id"/>' /></td>
			</s:else>
  		</tr>
  	</s:iterator>
		
	</table>
	
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
          
          <tr>
            <td align=center>
            <input type="submit" value="提交选课"/>
            </td>
          </tr>
        </table>
</s:form>
</div>
</div>
</div>
</body>
</html>
