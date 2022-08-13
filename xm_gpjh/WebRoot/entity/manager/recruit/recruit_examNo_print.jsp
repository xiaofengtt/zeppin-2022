<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>准考证打印</title>    
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
<style>
<!--
 /* Font Definitions */
 @font-face
	{font-family:宋体;
	panose-1:2 1 6 0 3 1 1 1 1 1;}
@font-face
	{font-family:"Cambria Math";
	panose-1:2 4 5 3 5 4 6 3 2 4;}
@font-face
	{font-family:"\@宋体";
	panose-1:2 1 6 0 3 1 1 1 1 1;}
 /* Style Definitions */
 p.MsoNormal, li.MsoNormal, div.MsoNormal
	{margin:0cm;
	margin-bottom:.0001pt;
	text-align:justify;
	text-justify:inter-ideograph;
	font-size:10.5pt;
	font-family:"Times New Roman","serif";}
p.MsoHeader, li.MsoHeader, div.MsoHeader
	{mso-style-link:"页眉 Char";
	margin:0cm;
	margin-bottom:.0001pt;
	text-align:center;
	layout-grid-mode:char;
	border:none;
	padding:0cm;
	font-size:9.0pt;
	font-family:"Times New Roman","serif";}
p.MsoFooter, li.MsoFooter, div.MsoFooter
	{mso-style-link:"页脚 Char";
	margin:0cm;
	margin-bottom:.0001pt;
	layout-grid-mode:char;
	font-size:9.0pt;
	font-family:"Times New Roman","serif";}
span.Char
	{mso-style-name:"页眉 Char";
	mso-style-link:页眉;}
span.Char0
	{mso-style-name:"页脚 Char";
	mso-style-link:页脚;}
 /* Page Definitions */
 @page Section1
	{size:419.6pt 21.0cm;
	margin:2.0cm 2.0cm 2.0cm 2.0cm;
	layout-grid:15.6pt;}
div.Section1
	{page:Section1;}
-->
</style>
  </head>
  
  <body style='text-justify-trim:punctuation;' onload="window.print()">
  <s:iterator value="getRecStuPrint()" status="i">
    <s:set name="s_id" value="id"/>
    <s:if test="#i.index!=0"><div style='page-break-after: always;'></div></s:if>
    <s:if test="getStuCourse(#s_id)!=null&&getStuCourse(#s_id).size()>0">
    <div class=Section1 align=left style='layout-grid:15.6pt;' >
    <p class=MsoNormal  style='width:400px;margin-bottom:15.6pt;text-align:center'><b><span
style='font-size:12.0pt;font-family:宋体'>华南师范大学网络教育学院<s:property value="prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.peGrade.name"/>季
<span ><br/>
</span>现代远程教育入学水平测试准考证</span></b></p>

<p class=MsoNormal align=left style='width:400px;margin-bottom:15.6pt;text-align:center'><b><span style='font-size:12.0pt;font-family:宋体'>考试日期：
<s:date name="prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.examStartDate" format="yyyy年MM月dd日"/>-<s:date name="prRecPlanMajorSite.prRecPlanMajorEdutype.peRecruitplan.examEndDate" format="MM月dd日"/>
</span></b></p>
 <table style="BORDER-RIGHT:   #000000   1px   solid;   BORDER-TOP:   #000000   1px   solid;   BORDER-LEFT:   #000000   1px   solid;   WIDTH:   100%;   
  	BORDER-BOTTOM:    #000000    0px   solid;   BORDER-COLLAPSE:   collapse;   HEIGHT:   300px ;width:400px"     
    borderColor="#000000"   cellspacing="0"  cellPadding="0"   rules="all"   border="1" >
  <tbody>
    <tr >
      <td width=87 style='width:68.4pt;border:solid windowtext 1.0pt;padding:0cm 5.4pt 0cm 5.4pt;
  height:29.25pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:12.0pt;font-family:宋体'>准考证号：</span></b></p></td>
      <td width=180 colspan=3 style='width:135.0pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:29.25pt'>
  <p class=MsoNormal><span style='font-size:14.0pt'>
      <s:property value="examCardNum"/>
      </span></p></td>
       <td width=126 colspan=2 rowspan=4 style='width:94.85pt;border:solid windowtext 1.0pt;
  border-left:none;padding:0cm 5.4pt 0cm 5.4pt;height:29.25pt'>
<img height="140px" width="120px" src='<s:property value="photoLink"/>' border=0 />
  </td>
    </tr>
    <tr>
      <td width=91 style='width:68.4pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:29.25pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:12.0pt;font-family:宋体'>姓&nbsp;&nbsp;&nbsp;&nbsp;名：</span></b></p>
  </td>
     <td width=180 colspan=3 style='width:135.0pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:29.25pt'>
  <p class=MsoNormal><span  style='font-size:12.0pt;font-family:宋体'>
      <s:property value="name"/>
      </span></p></td>
    </tr>
    <tr>
      <td width=91 style='width:68.4pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:29.25pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:12.0pt;font-family:宋体'>学习层次：</span></b></p></td>
      <td width=180 colspan=3 style='width:135.0pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:29.25pt'>
  <p class=MsoNormal><span style='font-family:宋体'>
      <s:property value="prRecPlanMajorSite.prRecPlanMajorEdutype.peEdutype.name"/>
      </span></p></td>
    </tr>
    <tr>
      <td width=91 style='width:68.4pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:29.25pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:12.0pt;font-family:宋体'>报考专业：</span></b></p></td>
      <td width=180 colspan=3 style='width:135.0pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:29.25pt'>
  <p class=MsoNormal><span style='font-size:12.0pt;font-family:宋体'>
      <s:property value="prRecPlanMajorSite.prRecPlanMajorEdutype.peMajor.name"/>
      </span></p></td>
    </tr>
    <tr>
      <td width=91 style='width:68.4pt;border:solid windowtext 1.0pt;border-top:
  none;padding:0cm 5.4pt 0cm 5.4pt;height:19.85pt'>
  <p class=MsoNormal align=center style='text-align:center'><b><span
  style='font-size:12.0pt;font-family:宋体'>测试地点：</span></b></p></td>
      <td width=306 colspan=5 style='width:229.85pt;border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;border-right:solid windowtext 1.0pt;
  padding:0cm 5.4pt 0cm 5.4pt;height:19.85pt'>
  <p class=MsoNormal><span style='font-size:12.0pt;font-family:宋体'>
      <s:property value="peRecRoom.peSite.address"/>
      </span></p></td>
    </tr>
    <tr>
      <td width="144" colspan="2"><p align="center"><span style='font-size:12.0pt;font-family:宋体'>科目</span></p></td>
      <td width="84"><p align="center"><span style='font-size:12.0pt;font-family:宋体'>开考时间</span></p></td>
      <td width="121" colspan="2"><p align="center"><span style='font-size:12.0pt;font-family:宋体'>试室</span></p></td>
      <td width="49"><p align="center"><span style='font-size:12.0pt;font-family:宋体'>桌号</span></p></td>
    </tr>
    <s:iterator value="getStuCourse(#s_id)">
    <tr>
      <td width="144" colspan="2" align="center" style='border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;'><p><span xml:lang="EN-US"><span 
  	style='font-size:12.0pt;font-family:宋体'><s:property value="prRecExamCourseTime.peRecExamcourse.name"/></span>
      </span></p></td>
      <td width="90" style='border-top:none;border-left:none;border-bottom:solid windowtext 1.0pt;'><p 
  align="center"><span xml:lang="EN-US"><span style='font-size:11.0pt;font-family:宋体'><s:date 
      name="prRecExamCourseTime.startDatetime" format="yyyy-MM-dd HH:mm"/>-<s:date name="prRecExamCourseTime.endDatetime" format="HH:mm"/></span></span></p></td>
      <td width="121" colspan="2" style='border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;'><p align="center" ><span xml:lang="EN-US">
     <span style='font-size:12.0pt;font-family:宋体'><s:property value="peRecRoom.classroom"/></span>
      </span></p></td>
      <td width="49" style='border-top:none;border-left:
  none;border-bottom:solid windowtext 1.0pt;'><p align="center"><span xml:lang="EN-US"  style='font-size:12.0pt;font-family:宋体'>
      <s:property value="peRecStudent.seatNum"/>
      </span></p></td>
    </tr>
 <tr  style='border:0;height;0px'>
  <td width=87 style='border:0'></td>
  <td width=57 style='border:0'></td>
  <td width=90 style='border:0'></td>
  <td width=43 style='border:0'></td>
  <td width=78 style='border:0'></td>
  <td width=49 style='border:0'></td>
 </tr>
    </s:iterator> 
  </tbody>
</table>

<p class=MsoNormal><span  style='font-size:9.0pt;font-family:宋体'>&nbsp;</span></p>

<p class=MsoNormal><span style='font-size:9.0pt;font-family:宋体'>考试须知： </span></p>

<p class=MsoNormal><span  style='font-size:9.0pt;font-family:宋体'>1</span><span
style='font-size:9.0pt;font-family:宋体'>、本准考证与身份证同时使用。</span></p>

<p class=MsoNormal><span  style='font-size:9.0pt;font-family:宋体'>2</span><span
style='font-size:9.0pt;font-family:宋体'>、与考试无关的文具不准带入考场。</span></p>

<p class=MsoNormal><span  style='font-size:9.0pt;font-family:宋体'>3</span><span
style='font-size:9.0pt;font-family:宋体'>、凭准考证进入指定考场，对号入座，将准考证和本人身份证放在桌子右上角</span></p>

<p class=MsoNormal><span  style='font-size:9.0pt;font-family:宋体'>4</span><span
style='font-size:9.0pt;font-family:宋体'>、考生迟到<span >15</span>分钟不得入考场，开考<span
>30</span>分钟后才准交卷出场。</span></p>

<p class=MsoNormal><span  style='font-size:9.0pt;font-family:宋体'>5</span><span
style='font-size:9.0pt;font-family:宋体'>、遵守考场纪律，如有违反，按有关规定予以处罚</span><span
style='font-size:12.0pt;font-family:宋体'>。</span></p>

<p class=MsoNormal><span  style='font-size:9.0pt;font-family:宋体'>6</span><span
style='font-size:9.0pt;font-family:宋体'>、艺术类术科考试另行通知。</span></p>

<p class=MsoNormal><span  style='font-size:9.0pt;font-family:宋体'>7</span><span
style='font-size:9.0pt;font-family:宋体'>、考试时间统一为<span >120</span>分钟。</span></p>

<p class=MsoNormal><span  style='font-size:9.0pt;font-family:宋体'>8</span><span
style='font-size:9.0pt;font-family:宋体'>、科目显示为“────”的表示该场无考试。</span></p>
    </div>

    </s:if><s:else> <s:set name="page" value="true"/></s:else>
</s:iterator></body>
</html>
