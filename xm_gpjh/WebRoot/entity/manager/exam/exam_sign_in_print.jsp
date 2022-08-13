<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<style type="text/css">
<!--
.STYLE3 {
	font-size: x-large;
	font-weight: bold;
	font-family: "宋体";
}
-->
</style>
</head>

<body onload="javascript:window.print()">
<s:iterator value="bookinglist" id="list" status="aa">
<s:if test="#aa.index>0"> <div style="PAGE-BREAK-AFTER: always"></div></s:if>
<div>
<p>&nbsp;</p>
<table width="600" border="0" align="center">
  <tr>
    <td colspan="3"><div align="center"><span class="STYLE3"><s:property value="#list.get(0).peExamNo.peSemester.name"/>期末考试<s:property value="sitename"/></span></div></td>
    </tr>
  <tr>
    <td><div align="center"><strong>第 <s:property value="#list.get(0).peExamNo.sequence"/> 场 座位表</strong></div></td>
    <td><div align="center">考场名称：<s:property value="#list.get(0).peExamRoom.classroom"/></div></td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td><div align="center">考试日期：<s:date format="yyyy-MM-dd" name="#list.get(0).peExamNo.startDatetime"/></div></td>
    <td><div align="center">考试开始时间：<s:date format="HH:mm" name="#list.get(0).peExamNo.startDatetime"/></div></td>
    <td><div align="center">考试结束时间：<s:date format="HH:mm" name="#list.get(0).peExamNo.endDatetime"/></div></td>
  </tr>
</table>
<br />
<table width="650"   border=1 align="center"   cellPadding=0   cellSpacing=0     
    borderColor=#000000   rules=all style="BORDER-RIGHT:   #000000   1px   solid;   BORDER-TOP:   #000000   1px   solid;   BORDER-LEFT:   #000000   1px   solid;   WIDTH:   100%;   
  	BORDER-BOTTOM:   #000000   1px   solid;   BORDER-COLLAPSE:   collapse;   WIDTH:636px; ">
  <tr>
    <td width="41" valign="top"><p>座号</p></td>
    <td width="55" valign="top"><p align="center">姓名</p></td>
    <td width="139" valign="top"><p>身份证号码</p></td>
    <td width="136" valign="top"><p>考生学号</p></td>
    <td width="139" valign="top"><p align="center">科目名称</p></td>
    <td width="43" valign="top"><p align="center">试卷</p></td>
    <td width="67" valign="top"><p>签到</p></td>
  </tr>
  <s:iterator value="list">
  <tr>
    <td width="41" valign="top"><p><s:property value="seatNo"/></p></td>
    <td width="55" valign="top"><p align="center"><s:property value="prTchStuElective.peStudent.trueName"/></p></td>
    <td width="139" valign="top"><p><s:property value="prTchStuElective.peStudent.prStudentInfo.cardNo"/></p></td>
    <td width="136" valign="top"><p><s:property value="prTchStuElective.peStudent.regNo"/></p></td>
    <td width="139" valign="top"><p align="center"><s:property value="prTchStuElective.prTchOpencourse.peTchCourse.name"/></p></td>
    <td width="43" valign="top"><p align="center"><s:property value="peExamNo.paperType"/></p></td>
    <td width="67" valign="top"><p>&nbsp;</p></td>
  </tr>
  </s:iterator>

</table>
</div>

</s:iterator>
</body>
</html>
