<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
</head>

<body onload="javascript:window.print()">
<s:iterator value="students" status="aa">
<s:if test="#aa.index>0"> <div style="PAGE-BREAK-AFTER: always"></div></s:if>
<table width="520"align="center" border="0">
  <tr>
    <td height="320"><p>&nbsp;</p><p>&nbsp;</p><div align="right" style="line-height:30px"><span style="font-size: 14pt; color: black; letter-spacing: 1pt">录取号:<b><span><s:property value="matriculateNum"/></span></b></span></div><div style="margin: 7.8pt 0cm 0pt;line-height:40px"><b><span style="font-size: 14pt; letter-spacing: 1pt"><s:property value="name"/></span></b><span style="font-size: 14pt; letter-spacing: 1pt">同学：</span></div><div style="text-indent: 32pt; line-height:40px"><span style="font-size: 14pt; letter-spacing: 1pt">华南师范大学是教育部批准的具有高等学历教育招生资格的普通高等学校。</span></div>
<div style="line-height:40px"><p style="text-indent: 32pt"><span style="font-size: 14pt; letter-spacing: 1pt">您被录取到我校</span>
  <b><span style="font-size: 16pt; letter-spacing: 1.6pt"><u>网络教育学院</u></span></b>&nbsp;，&nbsp;<b><span style="font-size: 15pt"><u><s:property value="prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name"/></u></span></b>
  <span style="font-size: 14pt; letter-spacing: 1pt">的</span><b><span style="font-size: 16pt; letter-spacing: 1.6pt"><u><s:property value="prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name"/></u></span></b>
   <span style="font-size: 14pt; letter-spacing: 1pt">专业学习，请凭本通知书和按所属学习中心规定的期限进行缴费注册，注册后立刻在网上进行[新生用户申请]和[学生学籍注册]。逾期者视为自动放弃入学资格。</span></div>
   <p>&nbsp;</p><p>&nbsp;</p>
   <div style="margin: 7.8pt 46.7pt 0pt 0cm;line-height:30px" align="right"><span style="font-size: 14pt; letter-spacing: 1pt">华南师范大学</span></div>
<div style="margin: 0cm 17.55pt 0pt 0cm; word-break: break-all;line-height:30px" align="right"><span style="font-size: 14pt"><s:property value="startDate"/></span></div><p>&nbsp;</p><p>&nbsp;</p>
<div style="margin: 0cm 19.4pt 0pt 0cm"><b><span style="font-size: 14pt">备注：</span></b></div>
<div style="margin: 0cm 19.4pt 0pt 0cm; text-indent: 33.6pt"><span style="font-size: 12pt">生殖健康咨询师培训网 主页网址：http://www.gdou.com</span></div>
<div style="margin: 0cm 19.4pt 0pt 0cm; text-indent: 33.6pt"><span style="font-size: 12pt">新生用户申请：http://www.gdou.com/student/registe</span></div>
<div style="margin: 0cm 0pt 0pt 0cm; text-indent: 33.6pt"><span style="font-size: 12pt">学生学籍注册：http://www.gdou.com/student/registerPerYear</span></div></td>
  </tr>
</table>
</s:iterator>
</body>
</html>
