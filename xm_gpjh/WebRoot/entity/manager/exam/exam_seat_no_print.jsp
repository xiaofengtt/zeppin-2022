<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<style type="text/css">
<!--
.STYLE2 {
	font-size: 36px;
	font-weight: bold;
	font-family: "新宋体";
}
.STYLE3 {
	font-size: x-large;
	font-weight: bold;
	font-family: "宋体";
}
.STYLE7 {font-size: 12px}
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
<table width="650" align="center" border="0">
  <s:iterator value="list" status="num">
  <s:if test="#num.index==0||#num.index%5==0"><tr></s:if>
  
    <td><table width="120px" border="1" align="center"  cellpadding="0"  cellspacing="0"  bordercolor="#000000" rules="none" >
      <tr>
        <td height="40" colspan="2" align="center" valign="middle"><s:property value="prTchStuElective.prTchOpencourse.peTchCourse.name"/></td>
        </tr>
      
      <tr>
        <td rowspan="2" align="right" valign="middle"><span class="STYLE2"><s:property value="seatNo"/></span></td>
        <td align="center" valign="middle"><strong><s:property value="prTchStuElective.peStudent.trueName"/></strong></td>
      </tr>
      <tr>
        <td align="center" valign="middle"><strong><s:property value="peExamNo.paperType"/></strong>卷</td>
      </tr>
      <tr>
        <td height="22" colspan="2" align="center" valign="middle"><span class="STYLE7"><s:property value="prTchStuElective.peStudent.prStudentInfo.cardNo"/></span></td>
        </tr>
      <tr>
        <td height="22" colspan="2" align="center" valign="middle"><span class="STYLE7"><s:property value="prTchStuElective.peStudent.regNo"/></span></td>
        </tr>
    </table></td>
  <s:if test="(#num.index+1)%5==0"></tr></s:if>  
  </s:iterator>
  <s:if test="list.size()%5!=0"></tr></s:if>  
</table>
</div>
</s:iterator>
</body>
</html>
