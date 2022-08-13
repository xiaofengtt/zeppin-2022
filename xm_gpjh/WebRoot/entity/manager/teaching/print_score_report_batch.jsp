<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<html>
<head>
<style type="text/css">
<!--
.STYLE1 {
	font-size: 20px;
	font-weight: bold;
}
-->
</style>
</head>
<body  onload="javascript:window.print()">
<div style="width:600px;margin:10px auto;padding:0 10px;">
<s:iterator value="scoreReportList" status="index">
<s:if test="#index.index > 0 "><div style="PAGE-BREAK-AFTER: always"></div></s:if>
<p align="center" style=" font-size:24px;font:'黑体'; "><strong>成绩记录</strong></p>
<table><tr>
	<td>姓名：<s:property value="getPeStudent().getTrueName()"/></td><td> 学号：<s:property value="getPeStudent().getRegNo()"/></td>
	</tr>
	<tr>
	<td> 专业：<s:property value="getPeStudent().getPeMajor().getName()"/></td><td>层次：<s:property value="getPeStudent().getPeEdutype().getName()"/></td></tr>
</table>
<div align="center">
  <table style="BORDER-RIGHT:   #000000   1px   solid;   BORDER-TOP:   #000000   1px   solid;   BORDER-LEFT:   #000000   1px   solid;   WIDTH:   100%;   
  	BORDER-BOTTOM:   #000000   1px   solid;   BORDER-COLLAPSE:   collapse;   HEIGHT:   104px"     
    borderColor=#000000   cellSpacing=0   cellPadding=0   rules=all   border=1><tbody>
    <s:set name="credit" value="0"/>
    <tr>
      <td width="130" nowrap="nowrap"><p align="center">学期</p></td>
      <td width="240" nowrap="nowrap"><p align="center">课程名称 </p></td>
      <td width="60" nowrap="nowrap"><p align="center">成绩 </p></td>
      <td width="60" nowrap="nowrap"><p align="center">学分 </p></td>
    </tr>
    <s:set name="score" value="0"/>
     <s:set name="num" value="0"/>
    <s:iterator value="getElectiveList()" status="position">
    <s:set value="id" name="eleId"/>
    	<tr>
	      <td width="130" nowrap="nowrap"><p align="center"><s:property value="getPeSemesterName(#eleId)"/> </p></td>
	      <td width="240" nowrap="nowrap"><p align="center"><s:property value="prTchOpencourse.peTchCourse.name"/> </p></td>
	      <td width="60" nowrap="nowrap"><p align="center"><s:property value="getScoreTotal()"/> </p></td>
	      <td width="60" nowrap="nowrap"><p align="center"><s:property value="prTchProgramCourse.credit"/> </p></td>
	       <s:set name="credit" value="#credit + prTchProgramCourse.credit"/>
	       <s:set name="score" value="#score + scoreTotal"/>
	       <s:set name="num" value="#num + 1"/>
	    </tr>
    </s:iterator>
    <tr>
      <td width="533" nowrap="nowrap" colspan="5"><p align="right">平均成绩：<s:if test="#num > 0"><s:set name="sco" value="#score/#num"/> <s:property value="getSco(#sco)"/> </s:if><s:else></s:else>&nbsp;&nbsp;&nbsp;总学分：<s:property value="#credit"/></p></td>
    </tr></tbody>
  </table>
</div>
<br><br>
<p align="right">华南师范大学网络教育学院 </p>
<p align="right"><s:date name="currentDate" format="yyyy年MM月dd日"/> </p>
</s:iterator>
</div>
</body>
</html>