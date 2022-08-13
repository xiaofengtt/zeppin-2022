<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="/entity/student/images/layout.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div id="main_content">
<div class="student_cntent_k">
    	<div class="k_cc">
<table border="0" cellpadding="1" cellspacing="1" align="center" >
 <tr>
    <td colspan="2" align="center">浏览课程<s:property value="peTchCourse.getName()"/>的信息</td>
  </tr>	
  <tr>
    <td>课程编号</td>
    <td><s:property value="peTchCourse.getCode()"/>&nbsp;</td>
  </tr>	
  <tr>
    <td  >课程名</td>
    <td><s:property value="peTchCourse.getName()"/>&nbsp;</td>
  </tr>
  <tr>
    <td>学分</td>
    <td><s:property value="peTchCourse.getCredit()"/>&nbsp;</td>
  </tr>		
  <tr>
    <td>学时</td>
    <td><s:property value="peTchCourse.getCourseTime()"/>&nbsp;</td>
  </tr>
  <tr>
    <td>讲课教材</td>
    <td><s:property value="peTchCourse.getBook()"/>&nbsp;</td>
  </tr>
  <tr>
    <td>讲课光盘</td>
    <td><s:property value="peTchCourse.getDisk()"/>&nbsp;</td>
  </tr>
  <tr>
    <td>课程简介</td>
    <td><s:property value="peTchCourse.getNote()"/>&nbsp;</td>
  </tr>
  <tr class="table_bg1">
    <td colspan="5">
    <p align="center">
  <a href="javascript:window.close()">[关闭]</a>
</p></td>
    </tr>
</table>
    </div>
    </div>
</div>
</body>
</html>
