<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<html>
<head>
<meta http-equiv=Content-Type content="text/html; charset=UTF-8">
<title>07年秋季入学学生评优申请表</title>
<style>
<!--
h1
	{text-align:justify;
	text-justify:inter-ideograph;
	line-height:240%;
	page-break-after:avoid;
	font-size:22.0pt;
	font-family:"Times New Roman";}
-->
</style>
</head>
<body bgcolor="#FFFFFF" onload="javascript:window.print()">
  <s:iterator value="getApplys()" status="i">
    <s:if test="#i.index!=0"><div style='page-break-after: always;'></div></s:if>  
   <s:set name="s_id" value="peStudent.id"/>        
<div class=Section1 style='layout-grid:15.6pt'>
  <p align=center style='text-align:center'><b><span style='font-size:22.0pt;font-family:
宋体;&quot;Times New Roman&quot;'><s:property value="peStudent.peGrade.name"/>入学学生评优申请表</span></b></p>
  <table border=1 align="center" cellspacing=0 cellpadding=0 width=664
  style="BORDER-RIGHT:   #000000   1px   solid;   BORDER-TOP:   #000000   1px   solid;   BORDER-LEFT:   #000000   1px   solid;  
  	BORDER-BOTTOM:   #000000   1px   solid;   BORDER-COLLAPSE:   collapse;  "     
    borderColor=#000000  >
    <tr>
      <td width=100 height="36" colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>姓 名</span></p></td>
      <td width=120 colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'><s:property value="peStudent.trueName"/></span></p></td>
      <td width=60 colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>性别</span></p></td>
      <td width=120><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'><s:property value="peStudent.prStudentInfo.gender"/></span></p></td>
      <td width=84 colspan=3><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>出生年月</span></p></td>
      <td width=180 colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'><s:date name="peStudent.prStudentInfo.birthday" format="yyyy年MM月"/></span></p></td>
    </tr>
    <tr>
      <td width=100 height="36" colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>政治面貌</span></p></td>
      <td width=120 colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'><s:property value="peStudent.prStudentInfo.zzmm"/></span></p></td>
      <td width=60 colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>专业</span></p></td>
      <td width=120><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'><s:property value="peStudent.peMajor.name"/></span></p></td>
      <td width=84 colspan=3><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>学 号</span></p></td>
      <td width=180 colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'><s:property value="peStudent.regNo"/></span></p></td>
    </tr>
    <tr>
      <td width=100 height="29" colspan=2><p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'>在读期间曾</span><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'>任学生职务</span></p>
      </td>
      <td width=120 colspan=2>&nbsp;</td>
      <td width=60 colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>层次</span></p></td>
      <td width=120><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'><s:property value="peStudent.peEdutype.name"/></span></p></td>
      <td width=84 colspan=3><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>工作单位</span></p></td>
      <td width=180 colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'><s:property value="peStudent.prStudentInfo.workplace"/></span></p></td>
    </tr>
    <tr>
      <td width=119 height="36" colspan=3><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>所属学习中心</span></p></td>
      <td width=545 colspan=9><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'><s:property value="peStudent.peSite.name"/></span></p></td>
    </tr>
    <tr>
      <td width=119 height="39" colspan=3><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>评选项目</span></p></td>
      <td width=545 colspan=9><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'><s:if test="enumConstByApplyType.code==1">√</s:if> 学习积极分子</span><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span><span
  style='font-family:宋体;"Times New Roman"'><s:if test="enumConstByApplyType.code==2">√</s:if> 优秀学生干部</span></p></td>
    </tr>
    <tr>
      <td width=84 rowspan=11><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>各科</span></p>
      <p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>成绩</span></p>
      <p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>及</span></p>
      <p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>突出</span></p>
      <p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>表现</span></p></td>
      <td width=136 height="24" colspan=3><p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'>科目</span></p></td>
      <td width=48><p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'>成绩</span></p></td>
      <td width=156 colspan=3><p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'>科目</span></p></td>
      <td width=48><p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'>成绩</span></p></td>
      <td width=144 colspan=2><p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'>科目</span></p></td>
      <td width=48><p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'>成绩</span></p></td>
    </tr>
    <s:set name="totalScore" value="0"/>        
    <s:set name="course" value="getCourse(#s_id)"/>    
<s:bean name="org.apache.struts2.util.Counter" id="counter" >
   <s:param name="first" value="1" />
   <s:param name="last" value="25" />
    <s:set name="number" value="0"/>  
    <tr>   
   <s:iterator>
	<s:set name="number" value="#number+1"/>  
		<s:if test="#number%3==0"><td width=144 colspan=2></s:if>
		<s:elseif test="#number%2==0"><td width=156 colspan=3></s:elseif>
		<s:else><td width=136 height="35" colspan=3></s:else>
      <p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'><s:property value="#course.get(#number-1).prTchOpencourse.peTchCourse.name"/></span></p></td>
      <td width=48><p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'><s:property value="#course.get(#number-1).scoreTotal"/></span></p></td>
    <s:if test="#course.get(#number-1).scoreTotal>0"><s:set name="totalScore" value="#totalScore+(#course.get(#number-1).scoreTotal)"/> </s:if>         
<s:if test="#number%3==0"></tr> <tr></s:if>  
   </s:iterator>
      <td width=156 colspan=3><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>总 分</span></p></td>
      <td width=48><p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'><s:property value="#totalScore"/></span></p></td>
      <td width=144 colspan=2><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>平均成绩</span></p></td>
      <td width=48><p align=center style='text-align:center'><span
  style='font-size:9.0pt;font-family:宋体;
  &quot;Times New Roman&quot;'><s:if test="#totalScore>0"><s:property value="getAverageScore(#totalScore ,#course.size())"/></s:if><s:else>0</s:else></span></p> </td>   
    </tr>   
</s:bean>    

    <tr>
      <td width=580 height="114" colspan=11><p align=center style='text-align:center'>&nbsp;</p>
      <p align=center style='text-align:center'>&nbsp;</p>
      <p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;学生签名：</span></p></td>
    </tr>
    <tr>
      <td width=84 height="98"><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>学习中</span></p>
      <p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>心意见</span></p></td>
      <td width=580 colspan=11><p align=center style='text-align:center'></p>
      <p align=center style='text-align:center'>&nbsp;</p>
      <p align=center style='text-align:center'><span style='font-family:宋体;
  &quot;Times New Roman&quot;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签名（盖章）：</span></p></td>
    </tr>
    <tr>
      <td width=84 height="97"><p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>学院意见</span></p></td>
      <td width=580 colspan=11><p align=center style='text-align:center'>&nbsp;</p>
      <p align=center style='text-align:center'>&nbsp;</p>
      <p align=center style='text-align:center'><span
  style='font-family:宋体;"Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;签名（盖章）：</span></p></td>
    </tr>
    <tr height=0>
      <td width=84 height="0"></td>
      <td width=17></td>
      <td width=19></td>
      <td width=101></td>
      <td width=48></td>
      <td width=12></td>
      <td width=120></td>
      <td width=24></td>
      <td width=48></td>
      <td width=12></td>
      <td width=132></td>
      <td width=48></td>
    </tr>
  </table>
  <p><span style='font-family:宋体;
&quot;Times New Roman&quot;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;备注：其他材料复印件附件</span></p>
</div>
</s:iterator>
</body>
</html>
